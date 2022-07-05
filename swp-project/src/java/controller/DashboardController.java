/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.UserDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

public class DashboardController extends HttpServlet {

    UserDao dao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        int role = new Jwt().getRoleFromToken(token);

        String time = new Jwt().createJwt(email, role);
        User user = dao.getUserByEmail(email);
        ArrayList<User> list = dao.listDESC();
        
        if (role == 1 || role == 2){
            list = dao.listDESC();
        }else{
            list = dao.listByRole(role);
        }
        
        //= to convert time to onl/off
        Long timestamp = System.currentTimeMillis();
        for (User i : list) {
            try {
                Long time_create = Long.parseLong(i.getTime_Create_Token());
                if (timestamp - time_create < 60 * 5 * 1 * 1000) {
                    i.setOnline(true);
                } else throw new NullPointerException("This item is false!");

            } catch (Exception e) {
             
                i.setOnline(false);
            }
        }
        
        request.setAttribute("user", user);
        request.setAttribute("list", list);
        request.getRequestDispatcher("template/DashBoard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
