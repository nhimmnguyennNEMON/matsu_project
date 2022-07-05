/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDao;
import dal.IssueDao;
import dal.UserDao;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import utils.CookieUtils;
import utils.Jwt;
import model.Class;
import model.Issue;

/**
 *
 * @author pallgree
 */
public class IssueController extends HttpServlet {

     UserDao dao = new UserDao();   
     ClassDao daoClass = new ClassDao();
     IssueDao daoIssue = new IssueDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao.getUserByEmail(email);
        request.setAttribute("user", user);
          String _user_id = String.valueOf(user.id ) ;
        
        if(go.equals("list")){
            ArrayList<Class> listClassCode = daoClass.listClassByTrainer2(_user_id);
            request.setAttribute("listClassCode", listClassCode);
            request.setAttribute("page", 1);
            int pageEnd = daoIssue.countItems("","","", _user_id);
            request.setAttribute("count", pageEnd);
            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
           ArrayList<Issue> list = daoIssue.search("full_name asc", 0, "", "", "",_user_id);
           request.setAttribute("list",list);
            request.getRequestDispatcher("/template/student/list/issuelist.jsp").forward(request, response);
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao.getUserByEmail(email);
        request.setAttribute("user", user);
          String _user_id = String.valueOf(user.id ) ;
          if(go.equals("list")){
            ArrayList<Class> listClassCode = daoClass.listClassByTrainer2(_user_id);
            request.setAttribute("listClassCode", listClassCode);
            int page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", page);
            int pageEnd = daoIssue.countItems("","","", _user_id);
            request.setAttribute("count", pageEnd);
            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
           ArrayList<Issue> list = daoIssue.search("full_name asc",(page - 1) * 6, "", "", "",_user_id);
           request.setAttribute("list",list);
            request.getRequestDispatcher("/template/student/list/issuelist.jsp").forward(request, response);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
