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
import utils.DBContext;
import java.util.ArrayList;
import model.ClassUser;

public class ClassUserDao extends DBContext<ClassUser> {

    Connection conn = getConnection();

    public int countItems(String key, String class_id, String status,String teacher_id) {
        PreparedStatement pre = null;
        int n = 0;
        try {
            String sql = "select * \n"
                    + "from  class_user  a ,\n"
                    + "user b,\n"
                    + "class c \n"
                    + "where a.user_id = b.user_id  and a.class_id = c.class_id and c.class_id like '%" + class_id + "%'  and a.status like '%" + status + "%' and b.full_name like '%" + key + "%' and c.trainer_id like '%" + teacher_id + "%'\n"
                    + "order by  b.full_name asc";

            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                n++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public ArrayList<ClassUser> search(String key, int row, int offset, String class_id, String status, String order,String teacher_id) {
        ArrayList<ClassUser> list = new ArrayList<>();
        PreparedStatement pre = null;
        try {
            String sql = "select * \n"
                    + "from  class_user  a ,\n"
                    + "user b,\n"
                    + "class c,\n"
                    + "team d \n"
                    + "where a.user_id = b.user_id  and a.class_id = c.class_id and a.team_id = d.team_id \n"
                    + "and c.class_id like '%" + class_id + "%'  and a.status like '%" + status + "%' and b.full_name like '%" + key + "%'and c.trainer_id like '%" + teacher_id + "%'\n"
                    + "order by " + order + "\n"
                    + "limit " + row + " offset " + offset;

            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ClassUser user = new ClassUser();
                user.setClass_id(rs.getInt("class_id"));
                user.setTeam_id(rs.getInt("team_id"));
                user.setUser_id(rs.getInt("user_id"));
                user.setTeam_lead(rs.getInt("team_leader"));
                user.setDropout_date(rs.getString("dropout_date"));
                user.setUser_notes(rs.getString("user_note"));
                user.setOngoing_eval(rs.getString("ongoing_eval"));
                user.setFinal_pres__eval(rs.getString("final_pres_eval"));
                user.setFinal_topic_eval(rs.getString("final_topic_eval"));
                user.setStatus(rs.getInt("status"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setRoll_number(rs.getString("roll_number"));
                user.setTeam_name(rs.getString("team_name"));
                list.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public ArrayList<ClassUser> searchByClassIdAndTeamID(String class_id, String team_id, String status) {
        ArrayList<ClassUser> list = new ArrayList<>();
        PreparedStatement pre = null;
        try {
            String sql = "select * \n"
                    + "class_user a ,\n"
                    + "user b,\n"
                    + "class c\n"
                    + "where a.user_id = b.user_id  and a.class_id = c.class_id\n"
                    + " and c.class_id like '%" + class_id + "%'  and a.status like '%" + status + "%' and a.team_id like '%" + team_id + "%' \n"
                    + "order by  b.full_name asc\n";

            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                ClassUser user = new ClassUser();
                user.setClass_id(rs.getInt("class_id"));
                user.setTeam_id(rs.getInt("team_id"));
                user.setUser_id(rs.getInt("user_id"));
                user.setTeam_lead(rs.getInt("team_leader"));
                user.setDropout_date(rs.getString("dropout_date"));
                user.setUser_notes(rs.getString("user_note"));
                user.setOngoing_eval(rs.getString("ongoing_eval"));
                user.setFinal_pres__eval(rs.getString("final_pres_eval"));
                user.setFinal_topic_eval(rs.getString("final_topic_eval"));
                user.setStatus(rs.getInt("status"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setRoll_number(rs.getString("roll_number"));
                list.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public ClassUser get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ClassUser get(String class_id, String team_id, String user_id) {
        ClassUser user = new ClassUser();
        PreparedStatement pre = null;
        try {
            String sql = "select * from  class_user a ,\n"
                    + "user b ,\n"
                    + "class c\n"
                    + "where a.user_id = b.user_id  and a.class_id = c.class_id and a.user_id ="+user_id+" and a.team_id="+team_id+" and a.class_id="+class_id;
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                user.setClass_id(rs.getInt("class_id"));
                user.setTeam_id(rs.getInt("team_id"));
                user.setUser_id(rs.getInt("user_id"));
                user.setTeam_lead(rs.getInt("team_leader"));
                user.setDropout_date(rs.getString("dropout_date"));
                user.setUser_notes(rs.getString("user_note"));
                user.setOngoing_eval(rs.getString("ongoing_eval"));
                user.setFinal_pres__eval(rs.getString("final_pres_eval"));
                user.setFinal_topic_eval(rs.getString("final_topic_eval"));
                user.setStatus(rs.getInt("status"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setRoll_number(rs.getString("roll_number"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public int insertCallBack(ClassUser model) {
        PreparedStatement pre = null;
        int n = 0;
        try {
            String sql = "INSERT INTO `class_user`\n"
                    + "(`class_id`,\n"
                    + "`team_id`,\n"
                    + "`user_id`,\n"
                    + "`team_leader`,\n"
                    + "`dropout_date`,\n"
                    + "`user_note`,\n"
                    + "`ongoing_eval`,\n"
                    + "`final_pres_eval`,\n"
                    + "`final_topic_eval`,\n"
                    + "`status`)\n"
                    + "VALUES\n"
                    + "(" + model.getClass_id() + ",\n"
                    + model.getTeam_id() + ",\n"
                    + model.getUser_id() + ",\n"
                    + model.getTeam_lead() + ",\n"
                    + " '" + model.getDropout_date() + "',\n"
                    + " '" + model.getUser_notes() + "',\n"
                    + " " + model.getOngoing_eval() + ",\n"
                    + " " + model.getFinal_pres__eval() + ",\n"
                    + " " + model.getFinal_topic_eval() + ",\n"
                    + model.getStatus() + ");\n";
            pre = conn.prepareStatement(sql);
            pre.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

  
    public int updateCallBack(ClassUser model) {
        PreparedStatement pre = null;
        try {
            String sql = "update `class_user`\n"
                    + "set\n"
                    + " `team_leader`  =" + model.getTeam_lead() + ",\n"
                    + " `dropout_date`  = '" + model.getDropout_date() + "',\n"
                    + " `user_note` ='" + model.getUser_notes() + "',\n"
                    + " `ongoing_eval` = " + model.getOngoing_eval() + ",\n"
                    + " `final_pres_eval` = " + model.getFinal_pres__eval() + ",\n"
                    + " `final_topic_eval` = " + model.getFinal_topic_eval() + " ,\n"
                    + " `status` = " + model.getStatus() + "\n"
                    + "where (user_id=" + model.getUser_id() + " and class_id=" +model.getClass_id()+" and team_id="+model.getTeam_id()+")";
            pre = conn.prepareStatement(sql);
            pre.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    public int updateStatus(ClassUser model) {
        PreparedStatement pre = null;
        try {
            String sql = "update `class_user`\n"
                    + "set\n"
                    + " `status` = " + model.getStatus() + "\n"
                    + "where (user_id=" + model.getUser_id() + " and class_id=" +model.getClass_id()+" and team_id="+model.getTeam_id()+")";
            pre = conn.prepareStatement(sql);
            pre.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        ClassUserDao dao = new ClassUserDao();
           ClassUser cu = new ClassUser();
            cu.setClass_id(1);
                cu.setTeam_id(3);
                cu.setUser_id(70);
                cu.setStatus(0); 
           int n = dao.updateStatus(cu);

    }

    @Override
    public void insert(ClassUser model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ClassUser> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ClassUser model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
