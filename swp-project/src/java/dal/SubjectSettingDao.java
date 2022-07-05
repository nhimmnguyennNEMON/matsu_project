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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Setting;
import model.Subject;
import model.SubjectSetting;
import utils.DBContext;
import static utils.DBContext.getConnection;

/**
 *
 * @author quang
 */
public class SubjectSettingDao extends DBContext<SubjectSetting> {
    Connection conn = getConnection();

    @Override
    public ArrayList<SubjectSetting> list() {
        ArrayList<SubjectSetting> list = new ArrayList<>();
        try {
            String sql = "SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, "
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, "
                    + "s.author_id FROM subject_setting ss inner join subject s "
                    + "on ss.subject_id = s.subject_id where ss.status <> 2 order by ss.display_order asc ;";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<SubjectSetting> listPagination(int index) {
        ArrayList<SubjectSetting> list = new ArrayList<>();
        try {
            String sql = "SELECT setting_id, subject_id, type_id, setting_title, \n"
                    + "setting_value, display_order, `status`, subject_code, subject_name, \n"
                    + "author_id FROM\n"
                    + "(SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, \n"
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, \n"
                    + "s.author_id, ROW_NUMBER() OVER (ORDER BY display_order ASC) as row_index\n"
                    + " FROM subject_setting ss inner join subject s \n"
                    + "on ss.subject_id = s.subject_id where ss.status <> 2) tb\n"
                    + "WHERE row_index >=(?-1)* 10 +1 AND row_index <= ? * 10;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, index);
            stm.setInt(2, index);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int count() {
        try {
            String sql = "SELECT COUNT(*) as Total FROM subject_setting where status <> 2";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countType(int type_id) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM subject_setting where type_id = ? and status <> 2";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, type_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countStatus(int status) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM subject_setting where status = ? and status <> 2";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, status);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countTitle(String setting_title) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM subject_setting where setting_title like ? and status <> 2";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + setting_title + "%");
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countSubject(int subject_id) {
        try {
            String sql = "SELECT COUNT(*) as Total FROM subject_setting where subject_id = ? and status <> 2";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, subject_id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public SubjectSetting getSubjectSetting(int id) {
        try {
            String sql = "SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, \n"
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_id, s.subject_code, s.subject_name, \n"
                    + "s.author_id FROM subject_setting ss inner join subject s \n"
                    + "on ss.subject_id = s.subject_id where ss.setting_id = ? "
                    + "and ss.status <> 2 order by ss.display_order asc ;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));

                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addSubjectSetting(SubjectSetting s) {
        try {
            String sql = "INSERT INTO `student_project_management`.`subject_setting`\n"
                    + "(`subject_id`,\n"
                    + "`type_id`,\n"
                    + "`setting_title`,\n"
                    + "`setting_value`,\n"
                    + "`display_order`,\n"
                    + "`status`)\n"
                    + "VALUES\n"
                    + "(?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?);";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, s.getSubject_id().getId());
            stm.setInt(2, s.getType_id());
            stm.setString(3, s.getSetting_title());
            stm.setString(4, s.getSetting_value());
            stm.setInt(5, s.getDisplay_order());
            stm.setInt(6, s.getStatus());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SubjectSettingDao sdb = new SubjectSettingDao();
        ArrayList<SubjectSetting> list = sdb.listPagination(1);
        System.out.println(list);
    }

    public void updateSubjectSetting(SubjectSetting s) {
        try {
            String sql = "UPDATE `student_project_management`.`subject_setting`\n"
                    + "SET\n"
                    + "`subject_id` = ?,\n"
                    + "`type_id` = ?,\n"
                    + "`setting_title` = ?,\n"
                    + "`setting_value` = ?,\n"
                    + "`display_order` = ?,\n"
                    + "`status` = ?\n"
                    + "WHERE `setting_id` = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, s.getSubject_id().getId());
            stm.setInt(2, s.getType_id());
            stm.setString(3, s.getSetting_title());
            stm.setString(4, s.getSetting_value());
            stm.setInt(5, s.getDisplay_order());
            stm.setInt(6, s.getStatus());
            stm.setInt(7, s.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSettingStatus(int status, int setting_id) {
        try {
            String sql = "UPDATE `student_project_management`.`subject_setting`\n"
                    + "SET\n"
                    + "`status` = ?\n"
                    + "WHERE `setting_id` = ?;";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, status);
            pre.setInt(2, setting_id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<SubjectSetting> searchBySubjectCode(int subject_id, int index) {
        ArrayList<SubjectSetting> list = new ArrayList<>();
        try {
            String sql = "SELECT setting_id, subject_id, type_id, setting_title, \n"
                    + "setting_value, display_order, `status`, subject_code, subject_name, \n"
                    + "author_id FROM\n"
                    + "(SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, \n"
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, \n"
                    + "s.author_id, ROW_NUMBER() OVER (ORDER BY display_order ASC) as row_index\n"
                    + " FROM subject_setting ss inner join subject s \n"
                    + "on ss.subject_id = s.subject_id where ss.subject_id = ? and ss.status <> 2) tb\n"
                    + "WHERE row_index >=(?-1)* 10 +1 AND row_index <= ? * 10;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, subject_id);
            stm.setInt(2, index);
            stm.setInt(3, index);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<SubjectSetting> searchByType(int type_id, int index) {
        ArrayList<SubjectSetting> listType = new ArrayList<>();
        try {
            String sql = "SELECT setting_id, subject_id, type_id, setting_title, \n"
                    + "setting_value, display_order, `status`, subject_code, subject_name, \n"
                    + "author_id FROM\n"
                    + "(SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, \n"
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, \n"
                    + "s.author_id, ROW_NUMBER() OVER (ORDER BY display_order ASC) as row_index\n"
                    + " FROM subject_setting ss inner join subject s \n"
                    + "on ss.subject_id = s.subject_id where ss.type_id = ? and ss.status <> 2) tb\n"
                    + "WHERE row_index >=(?-1)* 10 +1 AND row_index <= ? * 10;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, type_id);
            stm.setInt(2, index);
            stm.setInt(3, index);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                listType.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listType;
    }

    public SubjectSetting searchSubjectSettingByID(int id) {
        try {
            String sql = "SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, "
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, "
                    + "s.author_id FROM subject_setting ss inner join subject s "
                    + "on ss.subject_id = s.subject_id where ss.setting_id = ? and ss.status <> 2;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<SubjectSetting> searchSubjectSettingByTitle(String title, int index) {
        ArrayList<SubjectSetting> list = new ArrayList<>();
        try {
            String sql = "SELECT setting_id, subject_id, type_id, setting_title, \n"
                    + "setting_value, display_order, `status`, subject_code, subject_name, \n"
                    + "author_id FROM\n"
                    + "(SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, \n"
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, \n"
                    + "s.author_id, ROW_NUMBER() OVER (ORDER BY display_order ASC) as row_index\n"
                    + " FROM subject_setting ss inner join subject s \n"
                    + "on ss.subject_id = s.subject_id where ss.setting_title like ? and ss.status <> 2) tb\n"
                    + "WHERE row_index >=(?-1)* 10 +1 AND row_index <= ? * 10;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + title + "%");
            stm.setInt(2, index);
            stm.setInt(3, index);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<SubjectSetting> searchByStatus(int st, int index) {
        ArrayList<SubjectSetting> list = new ArrayList<>();
        try {
            String sql = "SELECT setting_id, subject_id, type_id, setting_title, \n"
                    + "setting_value, display_order, `status`, subject_code, subject_name, \n"
                    + "author_id FROM\n"
                    + "(SELECT ss.setting_id, ss.subject_id, ss.type_id, ss.setting_title, \n"
                    + "ss.setting_value, ss.display_order, ss.status, s.subject_code, s.subject_name, \n"
                    + "s.author_id, ROW_NUMBER() OVER (ORDER BY display_order ASC) as row_index\n"
                    + " FROM subject_setting ss inner join subject s \n"
                    + "on ss.subject_id = s.subject_id where ss.status = ? and ss.status <> 2) tb\n"
                    + "WHERE row_index >=(?-1)* 10 +1 AND row_index <= ? * 10;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, st);
            stm.setInt(2, index);
            stm.setInt(3, index);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubjectSetting s = new SubjectSetting();
                s.setId(rs.getInt("setting_id"));

                Subject subject = new Subject();
                subject.setId(rs.getInt("subject_id"));
                subject.setSubject_code(rs.getString("subject_code"));
                subject.setSubject_name(rs.getString("subject_name"));
                subject.setAuthor_id(rs.getInt("author_id"));
                subject.setStatus(rs.getInt("status"));
                s.setSubject_id(subject);

                s.setType_id(rs.getInt("type_id"));
                s.setSetting_title(rs.getString("setting_title"));
                s.setSetting_value(rs.getString("setting_value"));
                s.setDisplay_order(rs.getInt("display_order"));
                s.setStatus(rs.getInt("status"));
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public ArrayList<String> viewAllType() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "select distinct type_id from subject_setting";
        try {
            ResultSet rs = getData(sql);
            while (rs.next()) {
                String type = rs.getString(1);
                list.add(type);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    
    public void deleteSubjectSetting(int setting_id) {
        String sql = "UPDATE `student_project_management`.`subject_setting`\n"
                + "SET\n"
                + "`status` = 2\n"
                + "WHERE `setting_id` = ?;";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, setting_id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SubjectSettingDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(SubjectSetting model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(SubjectSetting model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubjectSetting get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
