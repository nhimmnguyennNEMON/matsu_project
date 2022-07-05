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
import model.Team;
import model.Class;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author quang
 */
public class TeamDao extends DBContext<Team> {

    Connection conn = getConnection();

    public ArrayList<Team> getTeamsByClass(int trainer_id, int class_id) {
        ArrayList<Team> list = new ArrayList<>();
        try {
            String sql = "SELECT t.team_id, t.team_name, t.topic_code, t.topic_name, t.gitlab_url, t.status, t.desc, c.class_id, \n"
                    + "c.class_code, c.trainer_id, c.subject_id, c.class_year, c.class_term, c.block5_class\n"
                    + "FROM team t inner join class c \n"
                    + "on t.class_id = c.class_id where t.status <> 2 and c.trainer_id = ? and c.class_id = ? order by c.class_id asc";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, trainer_id);
            stm.setInt(2, class_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Team t = new Team();
                t.setId(rs.getInt("team_id"));
                t.setTeam_name(rs.getString("team_name"));

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                t.setClass_id(c);
                t.setTopic_code(rs.getString("topic_code"));
                t.setTopic_name(rs.getString("topic_name"));
                t.setGitlab_url(rs.getString("gitlab_url"));
                t.setStatus(rs.getInt("status"));
                t.setDesc(rs.getString("desc"));
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int countItems(String class_id, String status, String topic_name, int trainer_id) {
        int n = 0;
        try {
            String sql = "select t.team_id, t.team_name, t.topic_code, t.topic_name, t.gitlab_url, t.status, t.desc, c.class_id, \n"
                    + "c.class_code, c.trainer_id from team t , class c where t.class_id = c.class_id and c.class_id \n"
                    + "like '%" + class_id + "%' and t.status like '%" + status + "%' and "
                    + "t.topic_name like '%" + topic_name + "%' and c.trainer_id = " + trainer_id + " and t.status <> 2";
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

    @Override
    public Team get(int id) {
        try {
            String sql = "SELECT t.team_id, t.team_name, t.topic_code, t.topic_name, t.gitlab_url, t.status, t.desc,\n"
                    + "c.class_id, c.class_code FROM team t inner join class c \n"
                    + "on t.class_id = c.class_id where t.team_id = ?\n"
                    + "and t.status <> 2;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Team t = new Team();
                t.setId(rs.getInt("team_id"));
                t.setTeam_name(rs.getString("team_name"));

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                t.setClass_id(c);
                t.setTopic_code(rs.getString("topic_code"));
                t.setTopic_name(rs.getString("topic_name"));
                t.setGitlab_url(rs.getString("gitlab_url"));
                t.setStatus(rs.getInt("status"));
                t.setDesc(rs.getString("desc"));

                return t;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void insert(Team t) {
        try {
            String sql = "INSERT INTO `student_project_management`.`team`\n"
                    + "(`team_name`,\n"
                    + "`class_id`,\n"
                    + "`topic_code`,\n"
                    + "`topic_name`,\n"
                    + "`gitlab_url`,\n"
                    + "`status`,\n"
                    + "`desc`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, t.getTeam_name());
            stm.setInt(2, t.getClass_id().getId());
            stm.setString(3, t.getTopic_code());
            stm.setString(4, t.getTopic_name());
            stm.setString(5, t.getGitlab_url());
            stm.setInt(6, t.getStatus());
            stm.setString(7, t.getDesc());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Team t) {
        try {
            String sql = "UPDATE `student_project_management`.`team`\n"
                    + "SET\n"
                    + "`team_name` = ?,\n"
                    + "`class_id` = ?,\n"
                    + "`topic_code` = ?,\n"
                    + "`topic_name` = ?,\n"
                    + "`gitlab_url` = ?,\n"
                    + "`status` = ?,\n"
                    + "`desc` = ?\n"
                    + "WHERE `team_id` = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, t.getTeam_name());
            stm.setInt(2, t.getClass_id().getId());
            stm.setString(3, t.getTopic_code());
            stm.setString(4, t.getTopic_name());
            stm.setString(5, t.getGitlab_url());
            stm.setInt(6, t.getStatus());
            stm.setString(7, t.getDesc());
            stm.setInt(8, t.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE `student_project_management`.`team`\n"
                + "SET\n"
                + "`status` = 2\n"
                + "WHERE `team_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Team> searchByClass(int class_id, int index) {
        ArrayList<Team> list = new ArrayList<>();
        try {
            String sql = "SELECT team_id, class_id, class_code, topic_code, topic_name, gitlab_url, `status` FROM\n"
                    + "(SELECT t.team_id, t.topic_code, t.topic_name, t.gitlab_url, t.status, c.class_id, \n"
                    + "c.class_code, c.trainer_id, c.subject_id, c.class_year, c.class_term, c.block5_class, ROW_NUMBER() OVER (ORDER BY c.class_id ASC) as row_index\n"
                    + "FROM team t inner join class c \n"
                    + "on t.class_id = c.class_id where t.class_id = ? and t.status <> 2) tb\n"
                    + "WHERE row_index >=(?-1)* 6 +1 AND row_index <= ? * 6;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, class_id);
            stm.setInt(2, index);
            stm.setInt(3, index);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Team t = new Team();
                t.setId(rs.getInt("team_id"));

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                t.setClass_id(c);
                t.setTopic_code(rs.getString("topic_code"));
                t.setTopic_name(rs.getString("topic_name"));
                t.setGitlab_url(rs.getString("gitlab_url"));
                t.setStatus(rs.getInt("status"));
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Team> search(String class_id, String status, String topic_name, int row, int offset, int trainer_id, String order) {
        ArrayList<Team> list = new ArrayList<>();
        try {
            String sql = "select t.team_id, t.team_name, t.topic_code, t.topic_name, t.gitlab_url, t.status, t.desc, c.class_id, \n"
                    + "c.class_code, c.trainer_id from team t, class c where t.class_id = c.class_id \n"
                    + "and c.class_id like '%" + class_id + "%' and t.`status` like '%" + status + "%' \n"
                    + "and t.topic_name like '%" + topic_name + "%' and \n"
                    + "c.trainer_id = " + trainer_id + " and t.status <> 2 \n"
                    + "order by " + order + "\n"
                    + "limit " + row + " offset " + offset;
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Team t = new Team();
                t.setId(rs.getInt("team_id"));
                t.setTeam_name(rs.getString("team_name"));

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                t.setClass_id(c);
                t.setTopic_code(rs.getString("topic_code"));
                t.setTopic_name(rs.getString("topic_name"));
                t.setGitlab_url(rs.getString("gitlab_url"));
                t.setStatus(rs.getInt("status"));
                t.setDesc(rs.getString("desc"));
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> viewAllTopicCode() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "select distinct topic_code from team";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                String code = rs.getString(1);
                list.add(code);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void active(int id) {
        String sql = "UPDATE `student_project_management`.`team`\n"
                + "SET\n"
                + "`status` = 1\n"
                + "WHERE `team_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inactive(int id) {
        String sql = "UPDATE `team`\n"
                + "SET\n"
                + "`status` = 0\n"
                + "WHERE `team_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==hung
    public ArrayList<String> listTeamIdbyClassId(int class_id) {
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM \n"
                    + "team  a,\n"
                    + "class  b\n"
                    + "where a.class_id = b.class_id and b.class_id like '%" + class_id + "%'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String tmp = rs.getString("team_id");
                list.add(tmp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //==hung
    public ArrayList<Team> listTeamIdbyClassId2(int class_id) {
        ArrayList<Team> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM \n"
                    + "team  a,\n"
                    + "class  b\n"
                    + "where a.class_id = b.class_id and b.class_id like '%" + class_id + "%'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Team team = new Team();
                team.setTeam_name(rs.getString("team_name"));
                team.setId(Integer.parseInt(rs.getString("team_id")));
                list.add(team);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //==hung
    public int findByTeamName(String team_name, String class_id) {
        try {
            String sql = "SELECT * FROM \n"
                    + "team \n"
                    + "where class_id='" + class_id + "' and team_name = '" + team_name + "'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    //==hung

    public int getTeamIDfromTeamName(String team_name, String class_id) {

        try {
            String sql = "SELECT * FROM \n"
                    + "student_project_management.team \n"
                    + "where class_id='" + class_id + "' and team_name = '" + team_name + "'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("team_id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<Team> list() {
        ArrayList<Team> list = new ArrayList<>();
        try {
            String sql = "select distinct team_name from team order by team_name asc";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Team t = new Team();
                t.setTeam_name(rs.getString("team_name"));
                list.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void main(String[] args) {
        TeamDao dao = new TeamDao();
//        dao.update(new Team("g1", new Class(1), "03", "Check attendence", "https://gitlab.com/", 1, "this is desc", 3));

//        int n = dao.countItems("3", "1", "", 80);
//        System.out.println(dao.findByTeamName("g3", "1"));
//        ArrayList<Team> list = dao.search("", "", "", 6, 0, 80, "t.team_name asc, c.class_code asc, t.topic_code asc");
//        System.out.println(list);
        int n = dao.countItems("", "", "", 80);
        System.out.println(n);
    }

}
