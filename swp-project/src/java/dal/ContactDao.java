/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaseModel;
import model.Contact;
import static utils.DBContext.getConnection;

/**
 *
 * @author SY NGUYEN
 */
public class ContactDao extends DBContext<BaseModel>{
    Connection conn = getConnection();
    
    public ArrayList<Contact> listContact() {
        ArrayList<Contact> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM student_project_management.web_contact";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getInt("contact_id"));
                contact.setFullname(rs.getString("fullname"));
                contact.setEmail(rs.getString("email"));
                contact.setMobile(rs.getString("mobile"));
                contact.setCategoryid(rs.getInt("category_id"));
                contact.setMessage(rs.getString("message"));
                contact.setResponse(rs.getString("response"));
                contact.setStatus(rs.getInt("status"));
                list.add(contact);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public ArrayList<BaseModel> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseModel get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void insert(Contact contact) {
        try {
            String sql = "INSERT INTO `student_project_management`.`web_contact` "
                    + "(`fullname`, `email`, `mobile`, `category_id`, `message`, `status`) "
                    + "VALUES (?, ?, ?, 123, ?, 0);";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, contact.getFullname());
            stm.setString(2, contact.getEmail());
            stm.setString(3, contact.getMobile());
            stm.setString(4, contact.getMessage());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
