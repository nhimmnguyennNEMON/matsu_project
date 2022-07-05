/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDao;
import dal.UserDao;
import model.Class;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Subject;
import model.User;
import org.apache.log4j.PropertyConfigurator;
import utils.CookieUtils;
import utils.Jwt;
import utils.env;

/**
 *
 * @author admin
 */
public class ClassController extends HttpServlet {

    ClassDao dao = new ClassDao();
    UserDao dao1 = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);
        if (go == null) {
            go = "view";
        }
        if (go.equals("view")) {
            if (user.getRole_id() == 1 || user.getRole_id() == 2) {
                String key = request.getParameter("key");
                String class_code = request.getParameter("class_code");
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("key", key);
                request.setAttribute("class_code", class_code);
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                request.setAttribute("page", 1);
                 request.setAttribute("sort_code", "asc");
                request.setAttribute("sort_year",  "asc");
                request.setAttribute("sort_term",  "asc");
                int pageEnd = dao.countItems("", "", "", "", "");
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                ArrayList<Class> list = new ArrayList<>();
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Class> filterS = dao.listFilterSubjectCode("");
                request.setAttribute("filterS", filterS);
                ArrayList<Class> filterC = dao.listFilterClassCode("");
                request.setAttribute("filterC", filterC);
                list = dao.search("", "", "", "", 6, 0,"c.class_code asc , c.class_year asc , c.class_term asc ");
                request.setAttribute("list", list);
                request.getRequestDispatcher("/template/manager/list/Class.jsp").forward(request, response);
            } else if (user.getRole_id() == 3) {
                String key = request.getParameter("key");
                String class_code = request.getParameter("class_code");
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("key", key);
                request.setAttribute("class_code", class_code);
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                request.setAttribute("page", 1);
                request.setAttribute("sort_code", "asc");
                request.setAttribute("sort_year",  "asc");
                request.setAttribute("sort_term",  "asc");
                ArrayList<Class> filterS = dao.listFilterSubjectCode(String.valueOf(user.getUser_id()));
                request.setAttribute("filterS", filterS);
                ArrayList<Class> filterC = dao.listFilterClassCode(String.valueOf(user.getUser_id()));
                request.setAttribute("filterC", filterC);
                ArrayList<Class> list = dao.listClassByTeacher(String.valueOf(user.getUser_id()),"", "", "", 6, 0,"c.class_code asc , c.class_year asc , c.class_term asc ");
                request.setAttribute("list", list);
                int pageEnd = dao.countItems(String.valueOf(user.getUser_id()), "", "", "", "");
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                request.setAttribute("pageEnd", pageEnd);
                dispath(request, response, "/template/teacher/list/Class.jsp");
            }
        } else if (go.equals("add")) {
            ArrayList<Subject> listS = dao.listSubject();
            request.setAttribute("listS", listS);
            ArrayList<User> filterU = dao.listFilterTrainer();
            request.setAttribute("filterU", filterU);
            dispath(request, response, "/template/manager/add/ClassAdd.jsp");
        } else if (go.equals("detail")) {
            int class_id = Integer.parseInt(request.getParameter("class_id"));
            Class c = dao.get(class_id);
            ArrayList<Subject> listS = dao.listSubject();
            request.setAttribute("listS", listS);
            ArrayList<User> filterU = dao.listFilterTrainer();
            request.setAttribute("filterU", filterU);
            request.setAttribute("class", c);
            dispath(request, response, "/template/manager/detail/ClassDetail.jsp");
        } else if (go.equals("active")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.active(id);
            response.sendRedirect("class");
        } else if (go.equals("inactive")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.inactive(id);
            response.sendRedirect("class");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);
        if (go.equals("view")) {

            if (user.getRole_id() == 1 || user.getRole_id() == 2) {
                String key = request.getParameter("key");
                String class_code = request.getParameter("class_code");
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("key", key);
                request.setAttribute("class_code", class_code);
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                int page = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("page", page);
                int pageEnd = dao.countItems("", class_code, key, subject_code, status);
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                    
                }
                String sqlOrder =  request.getParameter("string_sort");
                String sort_code = request.getParameter("sort_code");
                String sort_year = request.getParameter("sort_year");
                String sort_term = request.getParameter("sort_term");
                request.setAttribute("sort_code", sort_code);
                request.setAttribute("sort_year", sort_year );
                request.setAttribute("sort_term",  sort_term);
                request.setAttribute("string_sql", sqlOrder);
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Class> filterS = dao.listFilterSubjectCode("");
                request.setAttribute("filterS", filterS);
                ArrayList<Class> filterC = dao.listFilterClassCode("");
                request.setAttribute("filterC", filterC);
                ArrayList<Class> list = dao.search(class_code, key, subject_code, status, 6, (page - 1) * 6,sqlOrder);
                request.setAttribute("list", list);
                request.getRequestDispatcher("/template/manager/list/Class.jsp").forward(request, response);
            }
            if (user.getRole_id() == 3) {
                String class_code = request.getParameter("class_code");
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("class_code", class_code);
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                String sqlOrder = request.getParameter("string_sort");
                String sort_code = request.getParameter("sort_code");
                String sort_year = request.getParameter("sort_year");
                String sort_term = request.getParameter("sort_term");
                request.setAttribute("sort_code", sort_code);
                request.setAttribute("sort_year", sort_year );
                request.setAttribute("sort_term",  sort_term);
                request.setAttribute("string_sql", sqlOrder);
                int page = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("page", page);
                ArrayList<Class> filterS = dao.listFilterSubjectCode(String.valueOf(user.getUser_id()));
                request.setAttribute("filterS", filterS);
                ArrayList<Class> filterC = dao.listFilterClassCode(String.valueOf(user.getUser_id()));
                request.setAttribute("filterC", filterC);
                ArrayList<Class> list = dao.listClassByTeacher(String.valueOf(user.getUser_id()), class_code,subject_code ,status , 6, (page-1)*6,sqlOrder);
                request.setAttribute("list", list);
                int pageEnd = dao.countItems(String.valueOf(user.getUser_id()), class_code, "", subject_code, status);
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                request.setAttribute("pageEnd", pageEnd);
                dispath(request, response, "/template/teacher/list/Class.jsp");
            }
        } else if (go.equals("add")) {
            String class_code = request.getParameter("class_code");
            int class_year = Integer.parseInt(request.getParameter("class_year"));
            int class_term = Integer.parseInt(request.getParameter("class_term"));
            int status = Integer.parseInt(request.getParameter("class_status"));
            boolean block5 = Boolean.getBoolean(request.getParameter("block5"));

            Subject s = new Subject();
            s.setId(Integer.parseInt(request.getParameter("subject_id")));
            User u = new User();
            u.setId(Integer.parseInt(request.getParameter("trainer_id")));
            dao.insert(new Class(class_code, u, s, class_year, class_term, block5, status));
            response.sendRedirect("class");
        } else if (go.equals("update")) {
            int class_id = Integer.parseInt(request.getParameter("class_id"));

            String class_code = request.getParameter("class_code");
            int class_year = Integer.parseInt(request.getParameter("class_year"));
            int class_term = Integer.parseInt(request.getParameter("class_term"));
            int status = Integer.parseInt(request.getParameter("class_status"));
            boolean block5 = Boolean.getBoolean(request.getParameter("block5"));

            Subject s = new Subject();
            s.setId(Integer.parseInt(request.getParameter("subject_id")));
            User u = new User();
            u.setId(Integer.parseInt(request.getParameter("trainer_id")));
            dao.update(new Class(class_id, class_code, u, s, class_year, class_term, block5, status));
            response.sendRedirect("class");
        }
        
    }

    public void dispath(HttpServletRequest request, HttpServletResponse response, String page) throws IOException, ServletException {
        RequestDispatcher dispath = request.getRequestDispatcher(page);
        dispath.forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
