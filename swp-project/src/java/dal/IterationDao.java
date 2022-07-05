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
import model.Iteration;
import model.Subject;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author SY NGUYEN
 */
public class IterationDao extends DBContext<Iteration> {

    Connection conn = getConnection();

    @Override
    public ArrayList<Iteration> list() {
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            String sql = "SELECT i.iteration_id, i.iteration_name, i.duration, i.status as status_iter, s.subject_id ,s.subject_code, s.subject_name, s.status as status_subject \n"
                    + "FROM iteration i inner join subject s on i.subject_id = s.subject_id WHERE i.status != '2' and s.status != '2'";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Iteration iteration = new Iteration();
                iteration.setId(rs.getInt("iteration_id"));
                iteration.setIteration_name(rs.getString("iteration_name"));
                iteration.setDuration(rs.getString("duration"));
                iteration.setStatus_iter(rs.getInt("status_iter"));
                iteration.setSubject_id(rs.getInt("subject_id"));
                iteration.setSubject_code(rs.getString("subject_code"));
                iteration.setSubject_name(rs.getString("subject_name"));
                iteration.setStatus_subject(rs.getInt("status_subject"));

                list.add(iteration);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public ArrayList<Iteration> getIterationBySubject(int subject_code, String iteration_name) {
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            String sql = "SELECT i.iteration_id, i.iteration_name, i.duration, i.status as status_iter, s.subject_id ,s.subject_code, s.subject_name, s.status as status_subject\n"
                    + "FROM iteration i inner join subject s on i.subject_id = s.subject_id \n"
                    + "WHERE i.status != '2' and s.status != '2'\n"
                    + "and s.subject_id = "+subject_code+" and i.iteration_name like '"+iteration_name+"'";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Iteration iteration = new Iteration();
                iteration.setId(rs.getInt("iteration_id"));
                iteration.setIteration_name(rs.getString("iteration_name"));
                iteration.setDuration(rs.getString("duration"));
                iteration.setStatus_iter(rs.getInt("status_iter"));
                iteration.setSubject_id(rs.getInt("subject_id"));
                iteration.setSubject_code(rs.getString("subject_code"));
                iteration.setSubject_name(rs.getString("subject_name"));
                iteration.setStatus_subject(rs.getInt("status_subject"));

                list.add(iteration);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public Iteration get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Iteration model) {
        try {
            String sql = "INSERT INTO `student_project_management`.`iteration` (`subject_id`, `iteration_name`, `duration`, `status`) "
                    + "VALUES ('" + model.getSubject_id() + "', '" + model.getIteration_name() + "', '" + model.getDuration() + "', '" + model.getStatus_iter() + "');\n";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int insertIteration(Iteration model) {
        int n = 0;
        try {
            String sql = "INSERT INTO `student_project_management`.`iteration` (`subject_id`, `iteration_name`, `duration`, `status`) "
                    + "VALUES ('" + model.getSubject_id() + "', '" + model.getIteration_name() + "', '" + model.getDuration() + "', '" + model.getStatus_iter() + "');\n";
            PreparedStatement stm = conn.prepareStatement(sql);
            n = stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateIteration(Iteration model) {
        int n = 0;
        String sql = "UPDATE `student_project_management`.`iteration` "
                + "SET `subject_id` = '" + model.getSubject_id() + "', `iteration_name` = '" + model.getIteration_name() + "',"
                + " `duration` = '" + model.getDuration() + "', `status` = '" + model.getStatus_iter() + "' "
                + "WHERE (`iteration_id` = '" + model.getId() + "');";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            n = stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    @Override
    public void update(Iteration model) {
        String sql = "UPDATE `student_project_management`.`iteration` "
                + "SET `subject_id` = '" + model.getSubject_id() + "', `iteration_name` = '" + model.getIteration_name() + "',"
                + " `duration` = '" + model.getDuration() + "', `status` = '" + model.getStatus_iter() + "' "
                + "WHERE (`iteration_id` = '" + model.getId() + "');";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE `student_project_management`.`iteration`\n"
                + "SET\n"
                + "`status` = 2\n"
                + "WHERE `iteration_id` = ?;";
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
        String sql = "UPDATE `student_project_management`.`iteration`\n"
                + "SET\n"
                + "`status` = 1\n"
                + "WHERE `iteration_id` = ?;";
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
        String sql = "UPDATE `student_project_management`.`iteration`\n"
                + "SET\n"
                + "`status` = 0\n"
                + "WHERE `iteration_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Iteration> listPagingIteration(int index) {
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            String sql = "SELECT i.iteration_id, i.iteration_name, i.duration, i.status as status_iter, s.subject_id ,s.subject_code, s.subject_name, s.status as status_subject \n"
                    + "FROM iteration i inner join subject s on i.subject_id = s.subject_id WHERE i.status != '2' and s.status != '2' LIMIT ?, 6;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, (index - 1) * 6);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Iteration iteration = new Iteration();
                iteration.setId(rs.getInt("iteration_id"));
                iteration.setIteration_name(rs.getString("iteration_name"));
                iteration.setDuration(rs.getString("duration"));
                iteration.setStatus_iter(rs.getInt("status_iter"));
                iteration.setSubject_id(rs.getInt("subject_id"));
                iteration.setSubject_code(rs.getString("subject_code"));
                iteration.setSubject_name(rs.getString("subject_name"));
                iteration.setStatus_subject(rs.getInt("status_subject"));

                list.add(iteration);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Iteration> search(String subject_code, String subject_name, String iteration_status, int row, int offset, String order) {
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            String sql = "SELECT i.iteration_id, i.iteration_name, i.duration, i.status as status_iter, s.subject_id ,s.subject_code, s.subject_name, s.status as status_subject\n"
                    + "FROM iteration i inner join subject s\n"
                    + "WHERE i.subject_id = s.subject_id and i.status != '2' and s.status != '2' and "
                    + "subject_code like '%" + subject_code + "%' and subject_name like '%" + subject_name + "%' and i.status like '%" + iteration_status + "%'\n"
                    + "order by " + order + "\n"
                    + "limit " + row + " offset " + offset + ";";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Iteration iteration = new Iteration();
                iteration.setId(rs.getInt("iteration_id"));
                iteration.setIteration_name(rs.getString("iteration_name"));
                iteration.setDuration(rs.getString("duration"));
                iteration.setStatus_iter(rs.getInt("status_iter"));
                iteration.setSubject_id(rs.getInt("subject_id"));
                iteration.setSubject_code(rs.getString("subject_code"));
                iteration.setSubject_name(rs.getString("subject_name"));
                iteration.setStatus_subject(rs.getInt("status_subject"));

                list.add(iteration);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int countItems(String subject_code, String subject_name, String iteration_status) {
        int n = 0;
        try {
            String sql = "SELECT i.iteration_id, i.iteration_name, i.duration, i.status as status_iter, s.subject_id ,s.subject_code, s.subject_name, s.status as status_subject\n"
                    + "FROM iteration i inner join subject s\n"
                    + "WHERE i.subject_id = s.subject_id and i.status != '2' and s.status != '2' and "
                    + "subject_code like '%" + subject_code + "%' and subject_name like '%" + subject_name + "%' and i.status like '%" + iteration_status + "%'\n"
                    + "order by iteration_name asc";
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

    public Iteration searchIterationID(int id) {
        try {
            String sql = "SELECT i.iteration_id, i.iteration_name, i.duration, i.status as status_iter, s.subject_id ,s.subject_code, s.subject_name, s.status as status_subject\n"
                    + "FROM iteration i inner join subject s\n"
                    + "WHERE i.subject_id = s.subject_id and i.status != '2' and s.status != '2' \n"
                    + "and subject_code like '%%' and subject_name like '%%' and i.status like '%%' and i.iteration_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Iteration iteration = new Iteration();
                iteration.setId(rs.getInt("iteration_id"));
                iteration.setIteration_name(rs.getString("iteration_name"));
                iteration.setDuration(rs.getString("duration"));
                iteration.setStatus_iter(rs.getInt("status_iter"));
                iteration.setSubject_id(rs.getInt("subject_id"));
                iteration.setSubject_code(rs.getString("subject_code"));
                iteration.setSubject_name(rs.getString("subject_name"));
                iteration.setStatus_subject(rs.getInt("status_subject"));

                return iteration;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
