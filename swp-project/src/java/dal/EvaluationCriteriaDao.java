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
import model.EvaluationCriteria;
import model.Iteration;
import org.apache.jasper.tagplugins.jstl.ForEach;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author admin
 */
public class EvaluationCriteriaDao extends DBContext<EvaluationCriteria> {
    Connection conn = getConnection();
    @Override
    public ArrayList<EvaluationCriteria> list() {
        ArrayList<EvaluationCriteria> list = new ArrayList<>();
        String sql = "SELECT c.criteria_id , c.iteration_id ,i.iteration_name,i.subject_id,s.subject_code,c.evaluation_weight, c.team_evaluation,c.criteria_order,c.max_loc,c.status \n" +
"        FROM student_project_management.evaluation_criteria c,iteration i, subject s where c.iteration_id = i.iteration_id and s.subject_id = i.subject_id \n" +
"        and  \n" +
"         s.status <>2 and i.status <>2";
        try {
            ResultSet rs = getData(sql);
            while(rs.next()){
                EvaluationCriteria e = new EvaluationCriteria();
                e.setCriteria_id(rs.getInt(1));
                
                Iteration c = new Iteration();
                c.setId(rs.getInt(2));
                c.setIteration_name(rs.getString(3));
                c.setSubject_id(rs.getInt(4));
                c.setSubject_code(rs.getString(5));
                e.setIteration_id(c);
                
                e.setEvaluation_weight(rs.getInt(6));
                e.setTeam_evaluation(rs.getBoolean(7));
                e.setCriteria_order(rs.getInt(8));
                e.setMax_loc(rs.getInt(9));
                e.setStatus(rs.getBoolean(10));
               list.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<EvaluationCriteria> search(String id,String subject_code,String status,int row,int offset,String order) {
        ArrayList<EvaluationCriteria> list = new ArrayList<>();
        String sql = "SELECT c.criteria_id , c.iteration_id ,i.iteration_name,i.subject_id,s.subject_code,c.evaluation_weight, c.team_evaluation,c.criteria_order,c.max_loc,c.status \n" +
"        FROM student_project_management.evaluation_criteria c,iteration i, subject s where c.iteration_id = i.iteration_id and s.subject_id = i.subject_id and s.author_id like '%"+id+"%'\n" +
"        and  \n" +
"        subject_code like '%"+subject_code+"%' and c.status like '%"+status+"%' and s.status <>2 and i.status <>2 "
                + " order by  "+ order + "\n"
                + " limit "+row+" offset "+offset+";";
        try {
            ResultSet rs = getData(sql);
            while(rs.next()){
                EvaluationCriteria e = new EvaluationCriteria();
                e.setCriteria_id(rs.getInt(1));
                
                Iteration c = new Iteration();
                c.setId(rs.getInt(2));
                c.setIteration_name(rs.getString(3));
                c.setSubject_id(rs.getInt(4));
                c.setSubject_code(rs.getString(5));
                e.setIteration_id(c);
                
                e.setEvaluation_weight(rs.getInt(6));
                e.setTeam_evaluation(rs.getBoolean(7));
                e.setCriteria_order(rs.getInt(8));
                e.setMax_loc(rs.getInt(9));
                e.setStatus(rs.getBoolean(10));
               list.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
     public int countItems(String id,String subject_code,String status) {
        int n = 0;
        try {
            String sql = "SELECT c.criteria_id , c.iteration_id ,i.iteration_name,i.subject_id,s.subject_code,c.evaluation_weight, c.team_evaluation,c.criteria_order,c.max_loc,c.status \n" +
"        FROM student_project_management.evaluation_criteria c,iteration i, subject s where c.iteration_id = i.iteration_id and s.subject_id = i.subject_id and s.author_id like '%"+id+"%'\n" +
"        and  \n" +
"        subject_code like '%"+subject_code+"%' and c.status like '%"+status+"%' and s.status <>2 and i.status <>2;";

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
     public ArrayList<EvaluationCriteria> listFilterSubjectCode(String manager_id) {
        ArrayList<EvaluationCriteria> list = new ArrayList<>();
        try {
            String sql = "SELECT distinct i.subject_id , s.subject_code FROM student_project_management.iteration i,evaluation_criteria c, student_project_management.subject s where s.subject_id = i.subject_id and i.status<>2 and i.iteration_id = c.iteration_id and s.author_id like '"+manager_id+"' ;";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                EvaluationCriteria e = new EvaluationCriteria();
                Iteration c = new Iteration();
                c.setSubject_id(rs.getInt(1));
                c.setSubject_code(rs.getString(2));
                e.setIteration_id(c);
                list.add(e);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
     public int searchSubject_id(int iteration_id){
         String sql ="SELECT subject_id FROM student_project_management.iteration where iteration_id = "+iteration_id+";";
         int n =0;
        try {
            ResultSet rs= getData(sql);
            while(rs.next()){
            n = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return n;
     }
     public void updateEvaluationWeight(int criteria_id,int weight){
                  String sql = "UPDATE `student_project_management`.`evaluation_criteria` SET `evaluation_weight` = '"+weight+"' WHERE (`criteria_id` = '"+criteria_id+"');";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     public void updateAllEvaluationWeight(int subject_id){
         String sqlList="select * from evaluation_criteria c, iteration i  where c.iteration_id = i.iteration_id and i.subject_id = "+subject_id+";";
         ArrayList<EvaluationCriteria> list = new ArrayList<>();
         int sum=0;
         try {
            ResultSet rsList = getData(sqlList);
            while(rsList.next()){
                EvaluationCriteria e = new EvaluationCriteria();
                e.setCriteria_id(rsList.getInt(1));
                
                Iteration c = new Iteration();
                c.setId(rsList.getInt(2));
                
                e.setIteration_id(c);
                
                e.setEvaluation_weight(rsList.getInt(3));
                e.setTeam_evaluation(rsList.getBoolean(4));
                e.setCriteria_order(rsList.getInt(5));
                e.setMax_loc(rsList.getInt(6));
                e.setStatus(rsList.getBoolean(7));
               list.add(e);
               sum = sum + e.getMax_loc();
            }
            for(int i=0;i<list.size();i++){
                System.out.println(""+list.get(i).toString());
                int weight = ((list.get(i).getMax_loc()*100)/sum);
                updateEvaluationWeight( list.get(i).getCriteria_id(), weight);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
     }
     public ArrayList<EvaluationCriteria> listIteration(){
         EvaluationCriteriaDao dao = new EvaluationCriteriaDao();
         ArrayList<EvaluationCriteria> listI = new ArrayList<>();
         String sql = "SELECT distinct i.iteration_id,i.iteration_name,i.subject_id,s.subject_code FROM student_project_management.iteration i,evaluation_criteria c,subject s where i.subject_id = s.subject_id and i.status <> 2 and s.status <>2 and i.iteration_id not in(";
         ArrayList<EvaluationCriteria> list = dao.list();
         for(int i=0;i<list.size();i++){
             if(i==list.size()-1){
             sql+=""+list.get(i).getIteration_id().getId();
             }else{
             sql+=""+list.get(i).getIteration_id().getId()+",";
             }
         }
         sql+=")";
        try {
            ResultSet rs = getData(sql);
            while(rs.next()){
                EvaluationCriteria e = new EvaluationCriteria();
                Iteration c = new Iteration();
                c.setId(rs.getInt(1));
                c.setIteration_name(rs.getString(2));
                c.setSubject_id(rs.getInt(3));
                c.setSubject_code(rs.getString(4));
                e.setIteration_id(c);
                listI.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listI; 
     }
    @Override
    public EvaluationCriteria get(int id) {
        String sql = "SELECT c.criteria_id , c.iteration_id ,i.iteration_name,i.subject_id,s.subject_code,c.evaluation_weight, c.team_evaluation,c.criteria_order,c.max_loc,c.status \n" +
"      FROM student_project_management.evaluation_criteria c,iteration i, subject s where c.iteration_id = i.iteration_id and s.subject_id = i.subject_id and c.criteria_id = "+id+"";
        ResultSet rs;
        try {
            rs = getData(sql);
            while(rs.next()){
                EvaluationCriteria e = new EvaluationCriteria();
                e.setCriteria_id(rs.getInt(1));
                
                Iteration c = new Iteration();
                c.setId(rs.getInt(2));
                c.setIteration_name(rs.getString(3));
                c.setSubject_id(rs.getInt(4));
                c.setSubject_code(rs.getString(5));
                e.setIteration_id(c);
                
                e.setEvaluation_weight(rs.getInt(6));
                e.setTeam_evaluation(rs.getBoolean(7));
                e.setCriteria_order(rs.getInt(8));
                e.setMax_loc(rs.getInt(9));
                e.setStatus(rs.getBoolean(10));
               return e;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        }

    @Override
    public void insert(EvaluationCriteria model) {
        String team_evaluation = "";
        if(model.getTeam_evaluation()==true) 
        {team_evaluation="0";}
        else{team_evaluation="1";}
        String status = "";
        if(model.isStatus()==true) 
        {status="0";}
        else{status="1";}
 String sql = "INSERT INTO `student_project_management`.`evaluation_criteria` (`iteration_id`, `team_evaluation`, `criteria_order`, `max_loc`, `status`) VALUES "
            + "('"+model.getIteration_id().getId()+"', '"+team_evaluation+"', '"+model.getCriteria_order()+"', '"+model.getMax_loc()+"', '"+status+"');";
        try {
           PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(EvaluationCriteria model) {
       String team_evaluation = "";
        if(model.getTeam_evaluation()==true) 
        {team_evaluation="0";}
        else{team_evaluation="1";}
        String status = "";
        if(model.isStatus()==true) 
        {status="0";}
        else{status="1";}
        String sql = "UPDATE `student_project_management`.`evaluation_criteria` SET `team_evaluation` = '"+team_evaluation+"', `criteria_order` = '"+model.getCriteria_order()+"', `max_loc` = '"+model.getMax_loc()+"', `status` = '"+status+"' WHERE (`criteria_id` = '"+model.getCriteria_id()+"'); ";
        try {
           PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void active(int id) {
        String sql = "UPDATE `student_project_management`.`evaluation_criteria`\n"
                + "SET\n"
                + "`status` = 1\n"
                + "WHERE `criteria_id` = "+id+";";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void inactive(int id) {
        String sql = "UPDATE `student_project_management`.`evaluation_criteria`\n"
                + "SET\n"
                + "`status` = 0\n"
                + "WHERE `criteria_id` = "+id+";";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvaluationCriteriaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {
        EvaluationCriteriaDao dao = new EvaluationCriteriaDao();
        ArrayList<EvaluationCriteria> list = dao.listIteration();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(""+list.get(i).toString());
        }
        int n = dao.countItems("", "", "");
        System.out.println(n);
        Iteration it = new Iteration();
        it.setId(45);
//        dao.insert(new EvaluationCriteria(it, true, 10,180 , true));
        dao.updateAllEvaluationWeight(5);
        
    }
    
}
