/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import utils.DBContext;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import model.User;

/**
 *
 * @author quang
 */
public class UserDao extends DBContext<User> {

    Connection conn = getConnection();

    public User getUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM user where email = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setPassword(rs.getString("password"));
                user.setRole_id(rs.getInt("role_id"));
                user.setSlack_account(rs.getString("slack_account"));
                user.setAvatar_link(rs.getString("avatar_link"));
                user.setStatus(rs.getBoolean("status"));
                user.setIs_verify(rs.getBoolean("is_verify"));
                user.setUUID(rs.getString("UUID"));
                user.setClass_user(rs.getString("class_user"));
                user.setCreated_time(rs.getLong("created_time"));
                user.setSecret_key(rs.getString("secret_key"));
                user.setTime_Create_Token(rs.getString("time_create_token"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int findByEmail(String email) {
        try {
            String sql = "SELECT * FROM user where email = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public User getUserByUUID(String uuid) {
        try {
            String sql = "SELECT * FROM user where UUID = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, uuid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setPassword(rs.getString("password"));
                user.setRole_id(rs.getInt("role_id"));
                user.setSlack_account(rs.getString("slack_account"));
                user.setAvatar_link(rs.getString("avatar_link"));
                user.setStatus(rs.getBoolean("status"));
                user.setIs_verify(rs.getBoolean("is_verify"));
                user.setUUID(rs.getString("UUID"));
                user.setClass_user(rs.getString("class_user"));
                user.setCreated_time(rs.getLong("created_time"));
                user.setSecret_key(rs.getString("secret_key"));
                user.setTime_Create_Token(rs.getString("time_create_token"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void verify(int id) {
        try {
            String sql = "UPDATE `user`\n"
                    + "SET\n"
                    + "is_verify = 1\n"
                    + "WHERE user_id = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<User> list() {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "select * from user";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setPassword(rs.getString("password"));
                user.setRole_id(rs.getInt("role_id"));
                user.setSlack_account(rs.getString("slack_account"));
                user.setAvatar_link(rs.getString("avatar_link"));
                user.setStatus(rs.getBoolean("status"));
                user.setIs_verify(rs.getBoolean("is_verify"));
                user.setUUID(rs.getString("UUID"));
                user.setClass_user(rs.getString("class_user"));
                user.setTime_Create_Token(rs.getString("time_create_token"));
                user.setCreated_time(rs.getLong("created_time"));
                user.setSecret_key(rs.getString("secret_key"));
                list.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    //hung
    public ArrayList<User> searchInClassUser(String roll_number, String email, String name,String class_id) {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "select full_name , email ,roll_number,user_id from \n"
                    + "user\n"
                    + "where role_id=4  and status = 1  and user_id not in(\n"
                    + "select b.user_id from \n"
                    + "student_project_management.class_user a,\n"
                    + "student_project_management.user b\n"
                    + "where a.user_id = b.user_id and  a.class_id = '"+class_id+"' ) ";
            if (!email.isEmpty()) {
                sql += "and email like '%" + email + "%' ";
            }
            if (!name.isEmpty()) {
                sql += "and full_name like '%" + name + "%' ";
            }
            if (!roll_number.isEmpty()) {
                sql += "and roll_number like '%" + roll_number + "%' ";
            } 
              sql+="limit 6";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
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

    public ArrayList<User> listDESC() {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user ORDER BY time_create_token DESC";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setPassword(rs.getString("password"));
                user.setRole_id(rs.getInt("role_id"));
                user.setSlack_account(rs.getString("slack_account"));
                user.setAvatar_link(rs.getString("avatar_link"));
                user.setStatus(rs.getBoolean("status"));
                user.setIs_verify(rs.getBoolean("is_verify"));
                user.setUUID(rs.getString("UUID"));
                user.setClass_user(rs.getString("class_user"));
                user.setTime_Create_Token(rs.getString("time_create_token"));
                user.setCreated_time(rs.getLong("created_time"));
                user.setSecret_key(rs.getString("secret_key"));
                list.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<User> listByRole(int role_id) {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM user WHERE role_id =  ? ORDER BY time_create_token DESC";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, role_id);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFull_name(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setDate_of_birth(rs.getString("date_of_birth"));
                user.setPassword(rs.getString("password"));
                user.setRole_id(rs.getInt("role_id"));
                user.setSlack_account(rs.getString("slack_account"));
                user.setAvatar_link(rs.getString("avatar_link"));
                user.setStatus(rs.getBoolean("status"));
                user.setIs_verify(rs.getBoolean("is_verify"));
                user.setUUID(rs.getString("UUID"));
                user.setClass_user(rs.getString("class_user"));
                user.setTime_Create_Token(rs.getString("time_create_token"));
                user.setCreated_time(rs.getLong("created_time"));
                user.setSecret_key(rs.getString("secret_key"));
                list.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
//    

    public User getUserByUserID(String userID) {
        try {
            String sql = "SELECT * FROM user where user_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, userID);
            ResultSet rs = getData(sql);
            if (rs.next()) {
                int user_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String date_of_birth = rs.getString(4);
                String password = rs.getString(5);
                int role_id = rs.getInt(6);
                String slack_account = rs.getString(7);
                String avatar_link = rs.getString(8);
                boolean status = rs.getBoolean(9);
                boolean is_verify = rs.getBoolean(10);
                String UUID = rs.getString(11);
                String class_user = rs.getString(12);
                long created_time = rs.getLong(13);
                String secret_key = rs.getString(14);
                String phone_number = rs.getString(15);
                String address = rs.getString(16);
                int gender = rs.getInt(17);
                String time_create_token = rs.getString(18);
                User user = new User(full_name, email, date_of_birth, password,
                        role_id, slack_account, avatar_link, status, is_verify,
                        UUID, class_user, created_time, secret_key, phone_number,
                        address, gender, time_create_token);
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(User user) {
        try {
            String sql = "INSERT INTO `user`\n"
                    + "(full_name,\n"
                    + "email,\n"
                    + "date_of_birth,\n"
                    + "password,\n"
                    + "role_id,\n"
                    + "slack_account,\n"
                    + "avatar_link,\n"
                    + "status,\n"
                    + "is_verify,\n"
                    + "UUID,\n"
                    + "class_user,\n"
                    + "created_time,\n"
                    + "secret_key,\n"
                    + "phone_number,\n"
                    + "address,\n"
                    + "gender,\n"
                    + "time_create_token)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "4,\n"
                    + "?,\n"
                    + "?,\n"
                    + "1,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insert2(User user) {
        try {
            String sql = "INSERT INTO `user`\n"
                    + "(`full_name`,\n"
                    + "`email`,\n"
                    + "`password`,\n"
                    + "`role_id`,\n"
                    + "`avatar_link`,\n"
                    + "`status`,\n"
                    + "`is_verify`,\n"
                    + "`roll_number`)\n"
                    + "VALUES\n"
                    + "('" + user.getFull_name() + "',\n"
                    + "'" + user.getEmail() + "',\n"
                    + "'" + user.getPassword() + "',\n"
                    + "4,\n"
                    + "'" + user.getAvatar_link() + "',\n"
                    + "1,\n"
                    + "1,\n"
                    + "'" + user.getRoll_number() + "');";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void updateUserByEmail(User email) {
        try {
            String sql = "UPDATE user \n"
                    + "SET full_name = ?,date_of_birth = ?,slack_account = ?,avatar_link = ?"
                    + "WHERE email = '" + email.getEmail() + "'";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, email.getFull_name());
            stm.setString(2, email.getDate_of_birth());
            stm.setString(3, email.getSlack_account());
            stm.setString(4, email.getAvatar_link());
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User model) {
        String sql = "UPDATE user \n"
                + "SET full_name ='" + model.getFull_name() + "',avatar_link = '" + model.getAvatar_link() + "'\n"
                + "WHERE email = '" + model.getEmail() + "';";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateTimeCreateToken(User model) {
        String sql = "UPDATE user \n"
                + "SET time_create_token ='" + model.getTime_create_token() + "'\n"
                + "WHERE email = '" + model.getEmail() + "';";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void updateUUIDandCreateTime(User model) {
        String sql = "UPDATE user \n"
                + "SET created_time ='" + model.getCreated_time() + "',\n"
                + "UUID ='" + model.getUUID() + "'\n"
                + "WHERE email = '" + model.getEmail() + "';";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public int updateUserDetail(int id, User u) {
        int n = 0;
        String sql = "UPDATE `user`\n"
                + "SET\n"
                + "full_name = ?,\n"
                + "role_id = ?,\n"
                + "date_of_birth=?,\n"
                + "status = ?,\n"
                + "phone_number = ?,\n"
                + "address = ?,\n"
                + "gender = ?\n"
                + "WHERE user_id = " + id + ";";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, u.getFull_name());
            pre.setInt(2, u.getRole_id());
            pre.setString(3, u.getDate_of_birth());
            pre.setBoolean(4, u.isStatus());
            pre.setString(5, u.getPhone_number());
            pre.setString(6, u.getAddress());
            pre.setInt(7, u.getGender());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void changePassword(String password, int id) {
        try {
            String sql = "UPDATE `user`\n"
                    + "SET\n"
                    + "`password` = ?\n"
                    + "WHERE `user_id` = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, password);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //userlist
    public ArrayList<User> listUser(int index) {
        ArrayList<User> list = new ArrayList<>();
        String sql = "with x as (select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token, row_number() over ( order by user_id asc) as r from user)\n"
                + "select * from x where r between " + (index * 6 - (6 - 1)) + " and " + (index * 6) + "";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int user_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String date_of_birth = rs.getString(4);
                int role_id = rs.getInt(5);
                String avatar_link = rs.getString(6);
                boolean status = rs.getBoolean(7);
                String phone_number = rs.getString(8);
                String address = rs.getString(9);
                int gender = rs.getInt(10);
                String time_create_token = rs.getString(11);
                User u = new User(user_id, full_name, email, date_of_birth, role_id, avatar_link, status, phone_number, address, gender, time_create_token);
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<String> viewAllRole() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "select  distinct role_id from user order by role_id desc";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                String role = rs.getString(1);
                list.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int updateStatus(boolean status, int user_id) {
        int n = 0;
        int s;
        String sql = "UPDATE `student_project_management`.`user` SET `status` = ? WHERE (`user_id` = ?);";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setBoolean(1, status);
            pre.setInt(2, user_id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public User searchUser(int id) {
        String sql = "select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token from user where user_id ='" + id + "'";
        User u = new User();
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int user_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String date_of_birth = rs.getString(4);
                int role_id = rs.getInt(5);
                String avatar_link = rs.getString(6);
                boolean status = rs.getBoolean(7);
                String phone_number = rs.getString(8);
                String address = rs.getString(9);
                int gender = rs.getInt(10);
                String time_create_token = rs.getString(11);
                u = new User(user_id, full_name, email, date_of_birth, role_id, avatar_link, status, phone_number, address, gender, time_create_token);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;
    }

    public ArrayList<User> searchByRole(int role_id, int index) {
        ArrayList<User> list = new ArrayList<>();
        String sql = "with x as (select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token, row_number() over ( order by user_id asc) as r from user where role_id = " + role_id + ")\n"
                + "select * from x where r between " + (index * 6 - (6 - 1)) + " and " + (index * 6) + "";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int user_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String date_of_birth = rs.getString(4);
                int Role_id = rs.getInt(5);
                String avatar_link = rs.getString(6);
                boolean status = rs.getBoolean(7);
                String phone_number = rs.getString(8);
                String address = rs.getString(9);
                int gender = rs.getInt(10);
                String time_create_token = rs.getString(11);
                User u = new User(user_id, full_name, email, date_of_birth, Role_id, avatar_link, status, phone_number, address, gender, time_create_token);
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<User> searchByStatus(boolean status, int index) {
        ArrayList<User> list = new ArrayList<>();
        String sql = "with x as (select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token, row_number() over ( order by user_id asc) as r from user where status = " + status + ")\n"
                + "select * from x where r between " + (index * 6 - (6 - 1)) + " and " + (index * 6) + "";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int user_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String date_of_birth = rs.getString(4);
                int Role_id = rs.getInt(5);
                String avatar_link = rs.getString(6);
                Boolean Status = rs.getBoolean(7);
                String phone_number = rs.getString(8);
                String address = rs.getString(9);
                int gender = rs.getInt(10);
                String time_create_token = rs.getString(11);
                User u = new User(user_id, full_name, email, date_of_birth, Role_id, avatar_link, Status, phone_number, address, gender, time_create_token);
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<User> search(String text, int type, int index) {
        ArrayList<User> list = new ArrayList<>();
        String sql = "with x as (select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token, row_number() over ( order by user_id asc) as r from user where email like '%" + text + "%')\n"
                + "select * from x where r between " + (index * 6 - (6 - 1)) + " and " + (index * 6) + "";
        String sql1 = "with x as (select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token, row_number() over ( order by user_id asc) as r from user where phone_number like '%" + text + "%')\n"
                + "select * from x where r between " + (index * 6 - (6 - 1)) + " and " + (index * 6) + "";
        String sql2 = "with x as (select user_id,full_name,email,date_of_birth,role_id,avatar_link,status,phone_number,address,gender,time_create_token, row_number() over ( order by user_id asc) as r from user where full_name like '%" + text + "%')\n"
                + "select * from x where r between " + (index * 6 - (6 - 1)) + " and " + (index * 6) + "";
        try {
            ResultSet rs = null;
            if (type == 0) {
                rs = getData(sql2);
            }
            if (type == 1) {
                rs = getData(sql1);
            }
            if (type == 2) {
                rs = getData(sql);
            }
            while (rs.next()) {
                int user_id = rs.getInt(1);
                String full_name = rs.getString(2);
                String email = rs.getString(3);
                String date_of_birth = rs.getString(4);
                int Role_id = rs.getInt(5);
                String avatar_link = rs.getString(6);
                Boolean status = rs.getBoolean(7);
                String phone_number = rs.getString(8);
                String address = rs.getString(9);
                int gender = rs.getInt(10);
                String time_create_token = rs.getString(11);
                User u = new User(user_id, full_name, email, date_of_birth, Role_id, avatar_link, status, phone_number, address, gender, time_create_token);
                list.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int count() {
        try {
            String sql = "SELECT COUNT(*) as Total FROM user ";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int count(int st) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM user where status = " + st + "";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int count(String name) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM setting where full_name like '%" + name + "%'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countPhone(String phone_number) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM setting where phone_number like '%" + phone_number + "%'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countEmail(String email) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM user where email like '%" + email + "%'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countrole(int role_id) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM user where role_id = " + role_id + "";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

//    public static void main(String[] args) {
//        UserDao dao = new UserDao();
//        User u = dao.getUserByEmail("teacher1@fpt.edu.vn");
//        System.out.println(""+u.toString());
////        ArrayList<User> list = dao.listUser();
//        //        for (User user : list) {
//        //            System.out.println(""+user.toString());
//        //        }
//        //        User u = new User("Tong Sy Oi",1,true,"0949044261","tam dao",1);
//        //        int n = dao.updateUserDetail(63, u);
//        //dao.updateUUIDandCreateTime("hungvthe153016@fpt.edu.vn","123123","321321");
//        
//
//        //ArrayList<User> list = dao.searchInClassUser("", "", "");
//
//        User u = new User();
//        u.setFull_name("vu tien hung");
//        u.setEmail("hungvt@fpt.edu.vn");
//        u.setPassword("123");
//        u.setRole_id(4);
//        int n = dao.insert2(u);
//        System.out.println(n);
//
//    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
