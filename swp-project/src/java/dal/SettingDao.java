/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import utils.DBContext;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaseModel;
import model.Setting;
import static utils.DBContext.getConnection;

/**
 *
 * @author admin
 */
public class SettingDao extends DBContext<Setting> {
    Connection conn = getConnection();

    public ArrayList<Setting> list(int index) {
        ArrayList<Setting> list = new ArrayList<>();
        String sql = "with x as (select *, row_number() over ( order by setting_id asc) as r from setting)\n" +
"select * from x where r between "+(index*6 - (6-1))+" and "+(index*6)+"";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int setting_id = rs.getInt(1);
                String setting_name = rs.getString(2);
                String setting_value = rs.getString(3);
                int type_id = rs.getInt(4);
                int order = rs.getInt(5);
                int status = rs.getInt(6);
                String note = rs.getString(7);
                Setting s = new Setting(setting_id, setting_name, setting_value, type_id, order, status, note);
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int addSetting(Setting s) {
        int n = 0;
        String sql = "INSERT INTO `student_project_management`.`setting` (`setting_name`, `setting_value`, `type_id`, `order`, `status`, `note`) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, s.getSetting_Name());
            pre.setString(2, s.getSetting_Value());
            pre.setInt(3, s.getType_Id());
            pre.setInt(4, s.getOrder());
            pre.setInt(5, s.getStatus());
            pre.setString(6, s.getNote());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

   
  

    public int updateSettingStatus(int status, int setting_id) {
        int n = 0;
        String sql = "UPDATE `student_project_management`.`setting` SET `status` = ? WHERE (`setting_id` = ?);";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, setting_id);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public int updateSetting(Setting s) {
        int n = 0;
        String sql = "UPDATE `student_project_management`.`setting` SET `setting_name` = ?, `setting_value` = ?, `type_id` = ?, `order` = ?, `note` = ? , `status` = ? WHERE (`setting_id` = ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, s.getSetting_Name());
            pre.setString(2, s.getSetting_Value());
            pre.setInt(3, s.getType_Id());
            pre.setInt(4, s.getOrder());
            pre.setString(5, s.getNote());       
            pre.setInt(6, s.getStatus());
            pre.setInt(7, s.getSetting_Id());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public ArrayList<Setting> searchByType(int type_id,int index) {
        ArrayList<Setting> listType = new ArrayList<>();
        String sql = "with x as (select *, row_number() over ( order by setting_id asc) as r from setting where type_id = "+type_id+" )\n" +
"select * from x where r between "+(index*6 - (6-1))+" and "+(index*6)+"";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int setting_id = rs.getInt(1);
                String setting_name = rs.getString(2);
                String setting_value = rs.getString(3);
                int Type_id = rs.getInt(4);
                int order = rs.getInt(5);
                int status = rs.getInt(6);
                String note = rs.getString(7);
                Setting s = new Setting(setting_id, setting_name, setting_value, type_id, order, status, note);
                listType.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listType;
    }

    public Setting searchSetting(int id) {
        String sql = "select * from setting where setting_id ='" + id + "'";
        Setting s = new Setting();
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int setting_id = rs.getInt(1);
                String setting_name = rs.getString(2);
                String setting_value = rs.getString(3);
                int Type_id = rs.getInt(4);
                int order = rs.getInt(5);
                int status = rs.getInt(6);
                String note = rs.getString(7);
                s = new Setting(setting_id, setting_name, setting_value, Type_id, order, status, note);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;

    }

    public ArrayList<Setting> searchSettingByName(String name, int index) {
        ArrayList<Setting> listSet = new ArrayList<>();
        String sql = "with x as (select *, row_number() over ( order by setting_id asc) as r from setting where  setting_name like  '%"+name+"%' )\n" +
"select * from x where r between "+(index*6 - (6-1))+" and "+(index*6)+"";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int setting_id = rs.getInt(1);
                String setting_name = rs.getString(2);
                String setting_value = rs.getString(3);
                int Type_id = rs.getInt(4);
                int order = rs.getInt(5);
                int status = rs.getInt(6);
                String note = rs.getString(7);
                Setting s = new Setting(setting_id, setting_name, setting_value, Type_id, order, status, note);
                listSet.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSet;

    }
    public ArrayList<Setting> searchByStatus(int st,int index){
            ArrayList<Setting> listSet = new ArrayList<>();
        String sql = "with x as (select *, row_number() over ( order by setting_id asc) as r from setting where status = "+st+" )\n" +
"select * from x where r between "+(index*6 - (6-1))+" and "+(index*6)+"";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                int setting_id = rs.getInt(1);
                String setting_name = rs.getString(2);
                String setting_value = rs.getString(3);
                int Type_id = rs.getInt(4);
                int order = rs.getInt(5);
                int status = rs.getInt(6);
                String note = rs.getString(7);
                Setting s = new Setting(setting_id, setting_name, setting_value, Type_id, order, status, note);
                listSet.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSet;

    }
    public ArrayList<String> viewAllType(){
        ArrayList<String> list = new ArrayList<>();
        String sql = "select distinct type_id from setting";
        try {
            ResultSet rs = getData(sql);
            while(rs.next()){
                String type = rs.getString(1);
                list.add(type);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
//   

    public int deleteSetting(int setting_id) {
        int n = 0;
        String sql = "DELETE FROM `student_project_management`.`setting` WHERE (`setting_id` = '" + setting_id + "');";
        try {
            Statement state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

     public int count()
    {
        try {
            String sql = "SELECT COUNT(*) as Total FROM setting ";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public int count(int st)
    {
        try {
            String sql = "SELECT COUNT(*) as Total FROM setting where status = "+st+"";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public int count(String name){
        try {
            String sql = "SELECT COUNT(*) as Total FROM setting where setting_name like '%"+name+"%'";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public int counttype(int type_id){
        try {
            String sql = "SELECT COUNT(*) as Total FROM setting where type_id = "+type_id+"";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        SettingDao dao = new SettingDao();

         int n = dao.addSetting(new Setting("Kỹ năng ca nhan", "12", 6, 8, 1, "rat hay"));
         if (n>0)System.out.println("ok");
//         if (n>0)System.out.println("add successfull");;
//           int n = dao.updateSettingName("quan li thanh vien", 5);         
//            int n = dao.updateSettingOrder(2, 1);
//              int n = dao.updateSettingStatus(2,4);
//                int n = dao.updateSettingValue("aaaa", 1);
//int n = dao.updateSettingNode("HayHay", 1);
//        int n = dao.updateSetting(new Setting(2, "Kỹ năng ca nhan", "12", 6, 8, 1, "rat hay"));
//        if (n > 0) {
//            System.out.println("success");
//        }
//    ArrayList<Setting> list = dao.searchSettingByName("1",1);
//    for(int i =0 ;i<list.size();i++){
//        System.out.println(""+list.get(i).getSetting_Id());
//    }
//    int n = dao.count(1);
//        System.out.println(n);
//             System.out.println(dao.searchSetting(1));
//System.out.println(dao.searchByStatus(1));
      
    }

    @Override
    public void insert(Setting model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Setting model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Setting get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Setting> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
