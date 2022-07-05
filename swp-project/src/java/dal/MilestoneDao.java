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
import model.Milestone;
import model.Class;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author quang
 */
public class MilestoneDao extends DBContext<Milestone> {

    Connection conn = getConnection();

    @Override
    public ArrayList<Milestone> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Milestone> search(String milestone_name, String iteration_id,
            String status, String class_id, int row, int offset, String order, int user_id) {
        ArrayList<Milestone> list = new ArrayList<>();
        try {
            String sql = "select m.milestone_id, m.milestone_name, m.iteration_id, m.class_id, m.from_date, m.to_date,\n"
                    + "m.`status`, m.desc, i.iteration_id, i.iteration_name, c.class_id, c.class_code, c.trainer_id \n"
                    + "from milestones m, iteration i, class c \n"
                    + "where m.iteration_id = i.iteration_id and m.class_id = c.class_id\n"
                    + "and m.milestone_name like '%" + milestone_name + "%' and m.iteration_id like "
                    + "'%" + iteration_id + "%' and m.class_id like '%" + class_id + "%' "
                    + "and m.`status` like '%" + status + "%' and c.trainer_id = " + user_id + " and m.`status` <> 2 "
                    + "order by " + order + "\n"
                    + "limit " + row + " offset " + offset;
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Milestone m = new Milestone();
                m.setId(rs.getInt("milestone_id"));
                m.setMilestone_name(rs.getString("milestone_name"));

                Iteration i = new Iteration();
                i.setId(rs.getInt("iteration_id"));
                i.setIteration_name(rs.getString("iteration_name"));
                m.setIter(i);

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));
                m.setClass_id(c);

                m.setFrom_date(rs.getString("from_date"));
                m.setTo_date(rs.getString("to_date"));
                m.setStatus(rs.getInt("status"));

                list.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int countItems(String milestone_name, String iteration_id, String status, String class_id, int user_id) {
        int n = 0;
        try {
            String sql = "select m.milestone_id, m.milestone_name, m.iteration_id, m.class_id, m.from_date, m.to_date,\n"
                    + "m.`status`, m.desc, i.iteration_id, i.iteration_name, c.class_id, c.class_code, c.trainer_id \n"
                    + "from milestones m, iteration i, class c \n"
                    + "where m.iteration_id = i.iteration_id and m.class_id = c.class_id\n"
                    + "and m.milestone_name like '%" + milestone_name + "%' and m.iteration_id like "
                    + "'%" + iteration_id + "%' and m.class_id like '%" + class_id + "%' "
                    + "and m.`status` like '%" + status + "%' and c.trainer_id = " + user_id + " and m.`status` <> 2;";
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
    public Milestone get(int id) {
        try {
            String sql = "select m.milestone_id, m.milestone_name, m.iteration_id, m.class_id, m.from_date, m.to_date, \n"
                    + "m.`status`, m.desc, c.class_id, c.class_code, i.iteration_id, i.iteration_name\n"
                    + "from milestones m inner join iteration i on m.iteration_id = i.iteration_id\n"
                    + "inner join class c on m.class_id = c.class_id\n"
                    + "where m.milestone_id = ? and m.status <> 2;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Milestone m = new Milestone();
                m.setId(rs.getInt("milestone_id"));
                m.setMilestone_name(rs.getString("milestone_name"));

                Iteration i = new Iteration();
                i.setId(rs.getInt("iteration_id"));
                i.setIteration_name(rs.getString("iteration_name"));
                m.setIter(i);

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                c.setClass_code(rs.getString("class_code"));
                m.setClass_id(c);

                m.setFrom_date(rs.getString("from_date"));
                m.setTo_date(rs.getString("to_date"));
                m.setStatus(rs.getInt("status"));

                return m;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void insert(Milestone m) {
        try {
            String sql = "INSERT INTO `student_project_management`.`milestones`\n"
                    + "(`milestone_name`,\n"
                    + "`iteration_id`,\n"
                    + "`class_id`,\n"
                    + "`from_date`,\n"
                    + "`to_date`,\n"
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
            stm.setString(1, m.getMilestone_name());
            stm.setInt(2, m.getIter().getId());
            stm.setInt(3, m.getClass_id().getId());
            stm.setString(4, m.getFrom_date());
            stm.setString(5, m.getTo_date());
            stm.setInt(6, m.getStatus());
            stm.setString(7, m.getDesc());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Milestone m) {
        try {
            String sql = "UPDATE `student_project_management`.`milestones`\n"
                    + "SET\n"
                    + "`milestone_name` = ?,\n"
                    + "`iteration_id` = ?,\n"
                    + "`class_id` = ?,\n"
                    + "`from_date` = ?,\n"
                    + "`to_date` = ?,\n"
                    + "`status` = ?,\n"
                    + "`desc` = ?\n"
                    + "WHERE `milestone_id` = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, m.getMilestone_name());
            stm.setInt(2, m.getIter().getId());
            stm.setInt(3, m.getClass_id().getId());
            stm.setString(4, m.getFrom_date());
            stm.setString(5, m.getTo_date());
            stm.setInt(6, m.getStatus());
            stm.setString(7, m.getDesc());
            stm.setInt(8, m.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "UPDATE `student_project_management`.`milestones`\n"
                + "SET\n"
                + "`status` = 2\n"
                + "WHERE `milestone_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Iteration> getIters(int trainer_id) {
        ArrayList<Iteration> list = new ArrayList<>();
        try {
            String sql = "select distinct i.iteration_name, i.iteration_id from milestones m \n"
                    + "inner join iteration i on m.iteration_id = i.iteration_id\n"
                    + "inner join class c on m.class_id = c.class_id where c.trainer_id = ? order by i.iteration_name asc";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, trainer_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Iteration i = new Iteration();
                i.setId(rs.getInt("iteration_id"));
                i.setIteration_name(rs.getString("iteration_name"));
                list.add(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Milestone> getMilestoneByClass(int class_id) {
        ArrayList<Milestone> list = new ArrayList<>();
        try {
            String sql = "select * from milestones where class_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, class_id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Milestone m = new Milestone();
                m.setId(rs.getInt("milestone_id"));
                m.setMilestone_name(rs.getString("milestone_name"));

                Iteration i = new Iteration();
                i.setId(rs.getInt("iteration_id"));
                m.setIter(i);

                Class c = new Class();
                c.setId(rs.getInt("class_id"));
                m.setClass_id(c);

                m.setFrom_date(rs.getString("from_date"));
                m.setTo_date(rs.getString("to_date"));
                m.setStatus(rs.getInt("status"));

                list.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void active(int id) {
        String sql = "UPDATE `student_project_management`.`milestones`\n"
                + "SET\n"
                + "`status` = 1\n"
                + "WHERE `milestone_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inactive(int id) {
        String sql = "UPDATE `student_project_management`.`milestones`\n"
                + "SET\n"
                + "`status` = 0\n"
                + "WHERE `milestone_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MilestoneDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        MilestoneDao dao = new MilestoneDao();
//        ArrayList<Milestone> list = dao.search("", "", "", "", 6, 0, 1, 80);
//        System.out.println(list);
//        Milestone mile = dao.get(5);
//        System.out.println(mile);
//        dao.delete(2);
//        dao.update(new Milestone("Milestone1", new Iteration(1), new Class(1), "2022-06-16", "2022-06-25", "", 1, 2));
        dao.insert(new Milestone("Milestone 4", new Iteration(4), new Class(1), "20/06/2022", "30/06/2022", "", 0));
//          ArrayList<Milestone> list = dao.getMilestoneByClass(1);
//          System.out.println(list);
//        ArrayList<Iteration> list = dao.getIters(80);
//        System.out.println(list);
    }

}
