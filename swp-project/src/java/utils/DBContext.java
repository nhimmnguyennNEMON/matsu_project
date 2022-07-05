package utils;

import java.sql.*;
import java.util.ArrayList;
import model.BaseModel;

public abstract class DBContext<T extends BaseModel> {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("new connection");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_project_management?allowPublicKeyRetrieval=true&useSSL=false", "root", "123456789");
            conn =DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net:3306/sql6503933?allowPublicKeyRetrieval=true&useSSL=false", "sql6503933", "TbsGlWWBta");

            } catch (ClassNotFoundException | SQLException ex) {

                ex.printStackTrace();
            }
        }
        return conn;
    }

    public ResultSet getData(String sql) throws SQLException {
        ResultSet rs = null;
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = state.executeQuery(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public abstract ArrayList<T> list();

    public abstract T get(int id);

    public abstract void insert(T model);

    public abstract void update(T model);

    public abstract void delete(int id);

}
