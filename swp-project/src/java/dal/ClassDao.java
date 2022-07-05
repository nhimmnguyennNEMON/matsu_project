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
import utils.DBContext;
import model.Class;
import model.Subject;
import model.User;
import static utils.DBContext.getConnection;

/**
 *
 * @author quang
 */
public class ClassDao extends DBContext<Class> {

    Connection conn = getConnection();

    public ArrayList<Class> listClassByTrainer(int id) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            String sql = "SELECT c.class_id, c.class_code, u.user_id, u.full_name, s.subject_id, s.subject_code, c.class_year, c.class_term, "
                    + "c.block5_class, c.status FROM class c inner join user u on c.trainer_id = u.user_id\n"
                    + "inner join subject s on s.subject_id = c.subject_id where c.status <> 2 and c.trainer_id = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                User trainer = new User();
                trainer.setId(rs.getInt("user_id"));
                trainer.setFull_name(rs.getString("full_name"));

                c.setTrainer_id(trainer);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));

                c.setSubject_id(subject);
                c.setClass_year(rs.getInt("class_year"));
                c.setClass_term(rs.getInt("class_term"));
                c.setBlock5_class(rs.getBoolean("block5_class"));
                c.setStatus(rs.getInt("status"));

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Class> listClassByTrainer2(String id) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            String sql = "SELECT c.class_id, c.class_code, u.user_id, u.full_name, s.subject_id, s.subject_code, c.class_year, c.class_term, "
                    + "c.block5_class, c.status FROM class c inner join user u on c.trainer_id = u.user_id\n"
                    + "inner join subject s on s.subject_id = c.subject_id where c.status <> 2 and c.trainer_id like '%" + id + "%'";

            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                User trainer = new User();
                trainer.setId(rs.getInt("user_id"));
                trainer.setFull_name(rs.getString("full_name"));

                c.setTrainer_id(trainer);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));

                c.setSubject_id(subject);
                c.setClass_year(rs.getInt("class_year"));
                c.setClass_term(rs.getInt("class_term"));
                c.setBlock5_class(rs.getBoolean("block5_class"));
                c.setStatus(rs.getInt("status"));

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<Class> listClassByTeacher(String id, String class_code, String subject_id, String status, int row, int offset,String order) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            String sql = "SELECT c.class_id, c.class_code, u.user_id, u.full_name, s.subject_id, s.subject_code, c.class_year, c.class_term, "
                    + "c.block5_class, c.status FROM class c inner join user u on c.trainer_id = u.user_id\n"
                    + "inner join subject s on s.subject_id = c.subject_id where c.status <> 2 and c.trainer_id like '%" + id + "%'"
                    + "and c.class_code like '%" + class_code + "%'  and s.subject_code like '%" + subject_id + "%' and c.status like '%" + status + "%' "
                    +" order by "+order +" limit " + row + " offset " + offset + ";";
            ResultSet rs = getData(sql);
            while (rs.next()) {
                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                User trainer = new User();
                trainer.setUser_id(rs.getInt("user_id"));
                trainer.setFull_name(rs.getString("full_name"));

                c.setTrainer_id(trainer);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));

                c.setSubject_id(subject);
                c.setClass_year(rs.getInt("class_year"));
                c.setClass_term(rs.getInt("class_term"));
                c.setBlock5_class(rs.getBoolean("block5_class"));
                c.setStatus(rs.getInt("status"));

                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        ClassDao classDao = new ClassDao();
        int id = 80;
//        ArrayList<Class> list = classDao.listClassByTeacher(String.valueOf(id), "", "", "", 6, (2 - 1) * 6);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("" + list.get(i).toString());
//        }
//        int n = classDao.countItems("", "", "", "", "");
//        System.out.println(n);

    }

    public int countItems(String trainer_id, String class_code, String key, String subject_id, String status) {
        int n = 0;
        try {
            String sql = "SELECT c.class_id ,c.class_code,u.user_id, u.full_name,s.subject_id, s.subject_code, c.class_year, c.class_term,c.block5_class, c.status "
                    + "FROM class c , user u , subject s where c.subject_id = s.subject_id and c.trainer_id= u.user_id and c.subject_id = s.subject_id and u.status <> 2 "
                    + "and c.trainer_id like '%" + trainer_id + "%' and  c.class_code like '%" + class_code + "%' and u.full_name like '%" + key + "%' and s.subject_code like '%" + subject_id + "%' and c.status like '%" + status + "%' ;";

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

    //==hung
    public ArrayList<Class> listClassCodebyUserId(int trainer_id) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            String sql = "select *  from student_project_management.class where trainer_id ='" + trainer_id + "'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setClass_code(rs.getString("class_code"));
                c.setId(rs.getInt("class_id"));
                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Class get(int id) {
        String sql = "SELECT c.class_id ,c.class_code,u.user_id, u.full_name,s.subject_id, s.subject_code, c.class_year, c.class_term,c.block5_class, c.status "
                + "FROM class c , user u , subject s where c.subject_id = s.subject_id and c.trainer_id= u.user_id and c.subject_id = s.subject_id and u.status <> 2 and class_id=" + id + ";";
        Class c = new Class();
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {

                c.setId(rs.getInt(1));
                c.setClass_code(rs.getString(2));

                User trainer = new User();
                trainer.setUser_id(rs.getInt(3));
                trainer.setFull_name(rs.getString(4));

                c.setTrainer_id(trainer);

                Subject subject = new Subject();
                subject.setId(rs.getInt(5));
                subject.setSubject_code(rs.getString(6));

                c.setSubject_id(subject);
                c.setClass_year(rs.getInt(7));
                c.setClass_term(rs.getInt(8));
                c.setBlock5_class(rs.getBoolean(9));
                c.setStatus(rs.getInt(10));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    @Override
    public void insert(Class model) {
        String Block5 = "";
        if (model.isBlock5_class() == true) {
            Block5 = "0";
        } else {
            Block5 = "1";
        }
        try {
            String sql = "INSERT INTO student_project_management.`class` (class_code, trainer_id, subject_id, class_year, class_term, block5_class, status) VALUES"
                    + " ('" + model.getClass_code() + "', '" + model.getTrainer_id().getId() + "', '" + model.getSubject_id().getId() + "', '" + model.getClass_year() + "', '" + model.getClass_term() + "', '" + Integer.parseInt(Block5) + "', '" + model.getStatus() + "');";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Class model) {
        String Block5 = "";
        if (model.isBlock5_class() == true) {
            Block5 = "0";
        } else {
            Block5 = "1";
        }

        try {
            String sql = "UPDATE student_project_management.`class` SET class_code = '" + model.getClass_code() + "', trainer_id = '" + model.getTrainer_id().getId() + "', subject_id = '" + model.getSubject_id().getId() + "', class_year = '" + model.getClass_year() + "', class_term = '" + model.getClass_term() + "', block5_class = '" + Integer.parseInt(Block5) + "', status = '" + model.getStatus() + "' WHERE (class_id = '" + model.getId() + "');";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Class> search(String class_code, String key, String subject_id, String status, int row, int offset,String order) {
        ArrayList<Class> list = new ArrayList<>();
        String sql = "SELECT c.class_id ,c.class_code,u.user_id, u.full_name,s.subject_id, s.subject_code, c.class_year, c.class_term,c.block5_class, c.status "
                + "FROM class c , user u , subject s where c.subject_id = s.subject_id and c.trainer_id= u.user_id and c.subject_id = s.subject_id and u.status <> 2 "
                + "and c.class_code like '%" + class_code + "%' and u.full_name like '%" + key + "%' and s.subject_code like '%" + subject_id + "%' and c.status like '%" + status + "%' "
                +"order by "+order +" limit " + row + " offset " + offset + ";";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));

                User trainer = new User();
                trainer.setUser_id(rs.getInt("user_id"));
                trainer.setFull_name(rs.getString("full_name"));

                c.setTrainer_id(trainer);

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));

                c.setSubject_id(subject);
                c.setClass_year(rs.getInt("class_year"));
                c.setClass_term(rs.getInt("class_term"));
                c.setBlock5_class(rs.getBoolean("block5_class"));
                c.setStatus(rs.getInt("status"));

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Class> listFilterSubjectCode(String trainer_id) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            String sql = "SELECT distinct s.subject_code\n"
                    + "FROM class c inner join subject s\n"
                    + "WHERE c.subject_id = s.subject_id and c.trainer_id like '" + trainer_id + "' and c.status != '2' and s.status != '2' ";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                Subject s = new Subject();
                s.setSubject_code(rs.getString(1));
                c.setSubject_id(s);
                list.add(c);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<Class> listFilterClassCode(String trainer_id) {
        ArrayList<Class> list = new ArrayList<>();
        try {
            String sql = "SELECT distinct class_code\n"
                    + "FROM class c\n"
                    + "WHERE  c.status != '2'and c.trainer_id like '" + trainer_id + "' ";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setClass_code(rs.getString(1));
                list.add(c);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<User> listFilterTrainer() {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "SELECT user_id,full_name FROM student_project_management.user where role_id = 3;";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUser_id(Integer.parseInt(rs.getString(1)));
                u.setFull_name(rs.getString(2));
                list.add(u);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<Subject> listSubject() {
        ArrayList<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT subject_id,subject_code FROM student_project_management.subject where status <> 3;";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject s = new Subject();
                s.setId(rs.getInt(1));
                s.setSubject_code(rs.getString(2));
                list.add(s);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
    public void active(int id){
        String sqlString= "UPDATE `student_project_management`.`class` SET `status` = '1' WHERE (`class_id` = '"+id+"');";
        try {
            PreparedStatement pre = conn.prepareStatement(sqlString);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void inactive(int id){
        String sqlString= "UPDATE `student_project_management`.`class` SET `status` = '0' WHERE (`class_id` = '"+id+"');";
        try {
            PreparedStatement pre = conn.prepareStatement(sqlString);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public ArrayList<Class> list() {
        ArrayList<Class> list = new ArrayList<>();
        String sql = "SELECT c.class_id ,c.class_code,u.user_id, u.full_name,s.subject_id, s.subject_code, c.class_year, c.class_term,c.block5_class, c.status FROM class c , user u , subject s where c.subject_id = s.subject_id and c.trainer_id= u.user_id and c.subject_id = s.subject_id and u.status <> 2 ;";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Class c = new Class();
                c.setId(rs.getInt(1));
                c.setClass_code(rs.getString(2));

                User trainer = new User();
                trainer.setUser_id(rs.getInt(3));
                trainer.setFull_name(rs.getString(4));
                c.setTrainer_id(trainer);

                Subject subject = new Subject();
                subject.setId(rs.getInt(5));
                subject.setSubject_code(rs.getString(6));
                c.setSubject_id(subject);

                c.setClass_year(rs.getInt(7));
                c.setClass_term(rs.getInt(8));
                c.setBlock5_class(rs.getBoolean(9));
                c.setStatus(rs.getInt(10));

                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
