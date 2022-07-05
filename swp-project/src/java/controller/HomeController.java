/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ContactDao;
import dal.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Contact;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author pallgree
 */
public class HomeController extends HttpServlet {

    UserDao dao = new UserDao();
    ContactDao dao1 = new ContactDao();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
            String token = CookieUtils.getToken(request, response);
           String statusFilter = request.getParameter("statusFilter");
            if (token != null) {
                String email = new Jwt().getEmailFromToken(token);
                User user = dao.getUserByEmail(email);
                request.setAttribute("user", user);
                request.setAttribute("statusFilter", statusFilter);
                request.setAttribute("statusLogin", 0);
                request.getRequestDispatcher("template/HomePage.jsp").forward(request, response);
            }
            if (token == null) {
                request.getRequestDispatcher("template/HomePage.jsp").forward(request, response);
            }
        

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");
        if (go == null) {
            go = "addContact";
        }
        if (go.equals("addContact")) {
            String name = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String message = request.getParameter("message");
            Contact obj = new Contact(name, email, phone, message);
            dao1.insert(obj);

            response.sendRedirect("home");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
