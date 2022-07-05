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
import model.Subject;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author SY NGUYEN
 */
public class SubjectDao extends DBContext<Subject> {
    Connection conn = getConnection();

    @Override
    public ArrayList<Subject> list() {
        ArrayList<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM student_project_management.subject WHERE status != '2';";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));

                list.add(subject);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public Subject get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Subject model) {
        try {
            String sql = "INSERT INTO `student_project_management`.`subject` (`subject_code`, `subject_name`, `author_id`, `status`) "
                    + "VALUES (?, ?, ?, ?);";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, model.getSubject_code());
            stm.setString(2, model.getSubject_name());
            stm.setInt(3, model.getAuthor_id());
            stm.setInt(4, model.getStatus());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Subject model) {
        String sql = "UPDATE student_project_management.subject \n"
                + "SET subject_code ='" + model.getSubject_code() + "',subject_name = '" + model.getSubject_name() + "',author_id = '" + model.getAuthor_id() + "',subject_name = '" + model.getStatus() + "'\n"
                + "WHERE subject_id = '" + model.getId() + "';";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int updateSubject(Subject model) {
        int n = 0;
        String sql = "UPDATE student_project_management.subject \n"
                + "SET subject_code ='" + model.getSubject_code() + "',subject_name = '" + model.getSubject_name() + "',author_id = '" + model.getAuthor_id() + "',status = '" + model.getStatus() + "'\n"
                + "WHERE subject_id = '" + model.getId() + "';";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            n = stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int insertSubject(Subject model) {
        int n = 0;
        try {
            String sql = "INSERT INTO `student_project_management`.`subject` (`subject_code`, `subject_name`, `author_id`, `status`) "
                    + "VALUES (?, ?, ?, ?);";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, model.getSubject_code());
            stm.setString(2, model.getSubject_name());
            stm.setInt(3, model.getAuthor_id());
            stm.setInt(4, model.getStatus());
            n = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Subject searchBysubjectID(int subject_id) {
        try {
            String sql = "SELECT * FROM student_project_management.subject where subject_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, subject_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                return subject;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Subject> searchBySubject(String subject_code, String subject_name, String author_id, String status, int index) {
        ArrayList<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM student_project_management.subject\n"
                + "WHERE status like '%" + status + "%' and subject_code like '%" + subject_code + "%' "
                + "and subject_name like '%" + subject_name + "%' and author_id like '%" + author_id + "%' and status != '2' LIMIT ?, 6;";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, (index-1)*6);    
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                list.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateStatus_deactive(int subject_id) {
        String sql = "UPDATE `student_project_management`.`subject`\n"
                + "SET\n"
                + "`status` = 2\n"
                + "WHERE `subject_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, subject_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Subject> listPagingSubject(int index) {
        ArrayList<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM student_project_management.subject WHERE status != '2' LIMIT ?, 6;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, (index-1)*6);    
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                list.add(subject);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int count() {
        try {
            String sql = "SELECT COUNT(*) as Total FROM student_project_management.subject WHERE status != '2';";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int countSearch(String subject_code, String subject_name, String author_id, String status) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM student_project_management.subject\n" +
"                WHERE status like '%"+status+"%' and subject_code like '%"+subject_code+"%'\n" +
"                and subject_name like '%"+subject_name+"%' and author_id like '%"+author_id+"%' and status != '2'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public ArrayList<Subject> listFilterSubjectCode() {
        ArrayList<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT distinct subject_code FROM student_project_management.subject WHERE status != '2';";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubject_code(rs.getString("subject_code"));

                list.add(subject);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
    
//    public static void main(String[] args) {
//        SubjectDao dao =new SubjectDao();
//        int count = dao.countSearch("", "", "", "1");
//        System.out.println(count);
//    }

}
