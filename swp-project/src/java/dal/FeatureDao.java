/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feature;
import model.Team;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author SY NGUYEN
 */
public class FeatureDao extends DBContext<Feature> {

    Connection conn = getConnection();

    @Override
    public ArrayList<Feature> list() {
        ArrayList<Feature> list = new ArrayList<>();
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name as team_id_cu, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("feature_id"));
                feature.setFeature_name(rs.getString("feature_name"));
                feature.setTeam_id(rs.getInt("team_id"));
                feature.setClass_id(rs.getInt("class_id"));
                feature.setClass_code(rs.getString("class_code"));
                feature.setTrainer_id(rs.getInt("trainer_id"));
                feature.setTopic_code(rs.getString("topic_code"));
                feature.setTopic_name(rs.getString("topic_name"));
                feature.setSubject_id(rs.getInt("subject_id"));
                feature.setSubject_code(rs.getString("subject_code"));
                feature.setSubject_name(rs.getString("subject_name"));
                feature.setTeam_name(rs.getString("team_id_cu"));
                feature.setTeam_leader(rs.getInt("team_leader"));
                feature.setUser_id(rs.getInt("user_id"));
                feature.setFull_name(rs.getString("full_name"));
                feature.setStatus(rs.getInt("status"));

                list.add(feature);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public Feature get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Feature model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Feature model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertIteration(Feature model) {
        int n = 0;
        try {
            String sql = "INSERT INTO `student_project_management`.`feature` "
                    + "(`team_id`, `feature_name`, `status`, `desc`) "
                    + "VALUES ('" + model.getTeam_id() + "', '" + model.getFeature_name() + "',"
                    + " '" + model.getStatus() + "', '" + model.getDesc() + "');";
            PreparedStatement stm = conn.prepareStatement(sql);
            n = stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateIteration(Feature model) {
        int n = 0;
        String sql = "UPDATE `student_project_management`.`feature` "
                + "SET `team_id` = '" + model.getTeam_id() + "', `feature_name` = '" + model.getFeature_name() + "', "
                + "`status` = '" + model.getStatus() + "', `desc` = '" + model.getDesc() + "' "
                + "WHERE (`feature_id` = '" + model.getId() + "');";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            n = stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE `student_project_management`.`feature`\n"
                + "SET\n"
                + "`status` = 2\n"
                + "WHERE `feature_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void active(int id) {
        String sql = "UPDATE `student_project_management`.`feature`\n"
                + "SET\n"
                + "`status` = 1\n"
                + "WHERE `feature_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inactive(int id) {
        String sql = "UPDATE `student_project_management`.`feature`\n"
                + "SET\n"
                + "`status` = 0\n"
                + "WHERE `feature_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Feature> search(String feature_name, String class_code, String trainer_id, String status, String subject_code, String team_id, int row, int offset, String order) {
        ArrayList<Feature> list = new ArrayList<>();
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name as team_id_cu, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2 "
                    + "and f.feature_name like '%" + feature_name + "%' and c.class_code like '%" + class_code + "%'\n"
                    + "and c.trainer_id like '%" + trainer_id + "%' and f.status like '%" + status + "%'\n"
                    + "and s.subject_code like '%" + subject_code + "%' and cu.team_id like '%" + team_id + "%'\n"
                    + "order by " + order + "\n"
                    + "limit " + row + " offset " + offset + ";";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("feature_id"));
                feature.setFeature_name(rs.getString("feature_name"));
                feature.setTeam_id(rs.getInt("team_id"));
                feature.setClass_id(rs.getInt("class_id"));
                feature.setClass_code(rs.getString("class_code"));
                feature.setTrainer_id(rs.getInt("trainer_id"));
                feature.setTopic_code(rs.getString("topic_code"));
                feature.setTopic_name(rs.getString("topic_name"));
                feature.setSubject_id(rs.getInt("subject_id"));
                feature.setSubject_code(rs.getString("subject_code"));
                feature.setSubject_name(rs.getString("subject_name"));
                feature.setTeam_name(rs.getString("team_id_cu"));
                feature.setTeam_leader(rs.getInt("team_leader"));
                feature.setUser_id(rs.getInt("user_id"));
                feature.setFull_name(rs.getString("full_name"));
                feature.setStatus(rs.getInt("status"));

                list.add(feature);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int countItems(String feature_name, String class_code, String trainer_id, String status, String subject_code, String team_id) {
        int n = 0;
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name as team_id_cu, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2 "
                    + "and f.feature_name like '%" + feature_name + "%' and c.class_code like '%" + class_code + "%'\n"
                    + "and c.trainer_id like '%" + trainer_id + "%' and f.status like '%" + status + "%'\n"
                    + "and s.subject_code like '%" + subject_code + "%' and cu.team_id like '%" + team_id + "%'";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                n++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int countItemsTrainer(String feature_name, String class_code, String trainer_id, String status, String subject_code, String team_id, int teacher_id) {
        int n = 0;
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name as team_id_cu, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2 "
                    + "and f.feature_name like '%" + feature_name + "%' and c.class_code like '%" + class_code + "%'\n"
                    + "and c.trainer_id like '%" + trainer_id + "%' and f.status like '%" + status + "%'\n"
                    + "and s.subject_code like '%" + subject_code + "%' and cu.team_id like '%" + team_id + "%'\n"
                    + "and c.trainer_id = " + teacher_id + "";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                n++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public Feature searchFeatureID(int id) {
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status, f.desc from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2 and f.feature_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("feature_id"));
                feature.setTeam_id(rs.getInt("team_id"));
                feature.setTeam_name(rs.getString("team_name"));
                feature.setClass_code(rs.getString("class_code"));
                feature.setSubject_code(rs.getString("subject_code"));
                feature.setFeature_name(rs.getString("feature_name"));
                feature.setStatus(rs.getInt("status"));
                feature.setDesc(rs.getString("desc"));

                return feature;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Feature> listFeatureByStudent(String feature_name, String class_code, String trainer_id,
            String status, String subject_code, String team_id, int row, int offset, int numb, int user_id) {
        ArrayList<Feature> list = new ArrayList<>();
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name as team_id_cu, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2 "
                    + "and f.feature_name like '%" + feature_name + "%' and c.class_code like '%" + class_code + "%'\n"
                    + "and c.trainer_id like '%" + trainer_id + "%' and f.status like '%" + status + "%'\n"
                    + "and s.subject_code like '%" + subject_code + "%' and cu.team_id like '%" + team_id + "%' \n"
                    + "and u.user_id = " + user_id + "";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("feature_id"));
                feature.setFeature_name(rs.getString("feature_name"));
                feature.setTeam_id(rs.getInt("team_id"));
                feature.setClass_id(rs.getInt("class_id"));
                feature.setClass_code(rs.getString("class_code"));
                feature.setTrainer_id(rs.getInt("trainer_id"));
                feature.setTopic_code(rs.getString("topic_code"));
                feature.setTopic_name(rs.getString("topic_name"));
                feature.setSubject_id(rs.getInt("subject_id"));
                feature.setSubject_code(rs.getString("subject_code"));
                feature.setSubject_name(rs.getString("subject_name"));
                feature.setTeam_name(rs.getString("team_id_cu"));
                feature.setTeam_leader(rs.getInt("team_leader"));
                feature.setUser_id(rs.getInt("user_id"));
                feature.setFull_name(rs.getString("full_name"));
                feature.setStatus(rs.getInt("status"));

                list.add(feature);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<Feature> listFeatureByTeacher(String feature_name, String class_code, String trainer_id,
            String status, String subject_code, String team_id, int row, int offset, int teacher_id, String order) {
        ArrayList<Feature> list = new ArrayList<>();
        try {
            String sql = "SELECT f.feature_id, f.feature_name, f.team_id, c.class_id, c.class_code, c.trainer_id, t.topic_code, \n"
                    + "t.topic_name, s.subject_id, s.subject_code, s.subject_name, t.team_name as team_id_cu, \n"
                    + "cu.team_leader, u.user_id, u.full_name, f.status from\n"
                    + "feature f, team t, class c, subject s, class_user cu, user u\n"
                    + "where f.team_id = t.team_id \n"
                    + "and t.class_id = c.class_id\n"
                    + "and c.subject_id = s.subject_id\n"
                    + "and c.class_id = cu.class_id\n"
                    + "and cu.user_id = u.user_id\n"
                    + "and s.status != 2 and f.status != 2\n"
                    + "and f.feature_name like '%" + feature_name + "%' and c.class_code like '%" + class_code + "%'\n"
                    + "and c.trainer_id like '%" + trainer_id + "%' and f.status like '%" + status + "%'\n"
                    + "and s.subject_code like '%" + subject_code + "%' and cu.team_id like '%" + team_id + "%'\n"
                    + "and c.trainer_id = " + teacher_id + "\n"
                    + "order by " + order + "\n"
                    + "limit " + row + " offset " + offset + ";";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Feature feature = new Feature();
                feature.setId(rs.getInt("feature_id"));
                feature.setFeature_name(rs.getString("feature_name"));
                feature.setTeam_id(rs.getInt("team_id"));
                feature.setClass_id(rs.getInt("class_id"));
                feature.setClass_code(rs.getString("class_code"));
                feature.setTrainer_id(rs.getInt("trainer_id"));
                feature.setTopic_code(rs.getString("topic_code"));
                feature.setTopic_name(rs.getString("topic_name"));
                feature.setSubject_id(rs.getInt("subject_id"));
                feature.setSubject_code(rs.getString("subject_code"));
                feature.setSubject_name(rs.getString("subject_name"));
                feature.setTeam_name(rs.getString("team_id_cu"));
                feature.setTeam_leader(rs.getInt("team_leader"));
                feature.setUser_id(rs.getInt("user_id"));
                feature.setFull_name(rs.getString("full_name"));
                feature.setStatus(rs.getInt("status"));

                list.add(feature);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<Feature> listTeam() {
        ArrayList<Feature> list = new ArrayList<>();
        try {
            String sql = "SELECT distinct  t.team_id, t.team_name, c.class_code, s.subject_code FROM team t, class c, subject s\n"
                    + "where t.class_id = c.class_id and c.subject_id = s.subject_id "
                    + "order by t.team_name asc";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setId(rs.getInt("team_id"));
                f.setTeam_name(rs.getString("team_name"));
                f.setClass_code(rs.getString("class_code"));
                f.setSubject_code(rs.getString("subject_code"));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Feature> listClassCode() {
        ArrayList<Feature> list = new ArrayList<>();
        try {
            String sql = "SELECT distinct class_code FROM class WHERE status != '2'\n"
                    + "order by class_code asc";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setClass_code(rs.getString("class_code"));
                list.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void main(String[] args) {
        FeatureDao dao = new FeatureDao();
        ArrayList<Feature> list = dao.listTeam();
        System.out.println(list);
    }
}
