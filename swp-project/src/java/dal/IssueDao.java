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
import model.Issue;
import model.Iteration;
import static utils.DBContext.getConnection;

/**
 *
 * @author pallgree
 */
public class IssueDao extends utils.DBContext<Iteration> {

    Connection conn = getConnection();

    public int countItems(String class_id, String status, String team_id, String user_id) {
        int n = 0;
        PreparedStatement pre = null;
        try {

            String filter = "";
            if (!status.equals("")) {
                filter += "and f.type_value like '%" + status + "%'";
            }
            if (!class_id.equals("")) {
                filter += "and f.class_id like '%" + class_id + "%'";
            }
            if (!team_id.equals("")) {
                filter += "and a.team_id like '%" + team_id + "%'";
            }
            String sql = "select b.full_name,a.issue_title,a.desciption,a.gitlab_url,a.due_date,d.milestone_name, e.function_name,f.type_value status ,c.type_value labels from\n"
                    + "issue a ,  user b , milestones d , function e , class_setting f ,class_setting c,team h,class g\n"
                    + "where a.assignee_id = b.user_id  and a.milestone_id = d.milestone_id \n"
                    + "and a.function_id = e.function_id and a.status= f.id and a.labels = c.id and a.team_id =h.team_id and h.class_id = g.class_id and g.trainer_id like '%" + user_id + "%'\n"
                    + filter;
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

    public ArrayList<Issue> search(String order, int offset, String class_id, String status, String team_id, String user_id) {
        ArrayList<Issue> list = new ArrayList<>();
        PreparedStatement pre = null;
        try {

            String filter = "";
            if (!status.equals("")) {
                filter += "and f.type_value like '%" + status + "%'";
            }
            if (!class_id.equals("")) {
                filter += "and f.class_id like '%" + class_id + "%'";
            }
            if (!team_id.equals("")) {
                filter += "and a.team_id like '%" + team_id + "%'";
            }
            String sql = "select b.full_name,a.issue_title,a.desciption,a.gitlab_url,a.due_date,d.milestone_name, e.function_name,f.type_value status ,c.type_value labels from\n"
                    + "issue a ,  user b , milestones d , function e , class_setting f ,class_setting c,team h,class g\n"
                    + "where a.assignee_id = b.user_id  and a.milestone_id = d.milestone_id \n"
                    + "and a.function_id = e.function_id and a.status= f.id and a.labels = c.id and a.team_id =h.team_id and h.class_id = g.class_id and g.trainer_id like '%" + user_id + "%'\n"
                    + filter
                    + "order by " + order + "\n"
                    + "limit 6 offset " + offset;

            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Issue is = new Issue();
                is.setAssignee_name(rs.getNString("full_name"));
                is.setIssue_title(rs.getString("issue_title"));
                is.setDesciption(rs.getString("desciption"));
                is.setGitlab_link(rs.getString("desciption"));
                is.setDue_date(rs.getString("due_date"));
                is.setMilestone_name(rs.getString(6));
                is.setFunction_name(rs.getString(7));
                is.setStatus(rs.getString(8));
                is.setLabels(rs.getString(9));
                list.add(is);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public ArrayList<Iteration> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iteration get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Iteration model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Iteration model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        IssueDao dao = new IssueDao();
         ArrayList<Issue> list = dao.search("full_name asc", 1, "", "", "","80");
        for (Issue i : list) {
            System.out.println(i.toString());
        };
    }

}
