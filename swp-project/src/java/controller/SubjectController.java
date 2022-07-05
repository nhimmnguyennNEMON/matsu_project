/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.SubjectDao;
import dal.UserDao;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Subject;
import model.SubjectSetting;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author SY NGUYEN
 */
public class SubjectController extends HttpServlet {

    SubjectDao dao = new SubjectDao();
    UserDao dao1 = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);

        int count = dao.count();
        int endPage = count / 6;
        if (count % 6 != 0) {
            endPage++;
        }
        request.setAttribute("endPage", endPage);
        request.setAttribute("tag", index);

        if (go == null) {
            go = "list";
        }
        if (go.equals("list")) {
            ArrayList<Subject> list = dao.list();
            ArrayList<Subject> listSearch = dao.listPagingSubject(index);
            request.setAttribute("listSearch", listSearch);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/template/admin/list/Subject.jsp").forward(request, response);

        } else if (go.equals("subject-detail")) {
            int id = Integer.parseInt(request.getParameter("subject_id"));
            Subject detail = dao.searchBysubjectID(id);
            request.setAttribute("detail", detail);
            request.getRequestDispatcher("/template/admin/detail/SubjectDetail.jsp").forward(request, response);

        } else if (go.equals("subject-add")) {
            request.getRequestDispatcher("/template/admin/add/SubjectAdd.jsp").forward(request, response);

        } else if (go.equals("subject-delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.updateStatus_deactive(id);
            response.sendRedirect("subject");
//            request.getRequestDispatcher("/template/admin/add/SubjectDetail.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String go = request.getParameter("go");

        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);
        ArrayList<Subject> list = dao.list();

        if (go.equals("subject-add")) {
            String message = "";
            String subject_code = request.getParameter("subject_code");
            String subject_name = request.getParameter("subject_name");
            String subject_authorID = request.getParameter("subject_authorID");
            String subject_status = request.getParameter("subject_status");
            int authorID = Integer.parseInt(subject_authorID);
            int status = Integer.parseInt(subject_status);

            Subject sb = new Subject(subject_code, subject_name, authorID, status);
            int n = dao.insertSubject(sb);
            if (n > 0) {
                request.setAttribute("Add Success", message);
            } else {
                request.setAttribute("Add Fail", message);
            }
            response.sendRedirect("subject");

        } else if (go.equals("subject-update")) {
            String message = "";

            int id = Integer.parseInt(request.getParameter("subject_id"));
            String subject_authorID = request.getParameter("subject_authorID");
            String subject_status = request.getParameter("subject_status");
            int authorID = Integer.parseInt(subject_authorID);
            int status = Integer.parseInt(subject_status);

            Subject subject = dao.searchBysubjectID(id);

            subject.setId(id);
            subject.setSubject_code(request.getParameter("subject_code"));
            subject.setSubject_name(request.getParameter("subject_name"));
            subject.setAuthor_id(authorID);
            subject.setStatus(status);

            int n = dao.updateSubject(subject);
            if (n > 0) {
                request.setAttribute("Update Success", message);
            } else {
                request.setAttribute("Update Fail", message);
            }
            response.sendRedirect("subject");

        } else if (go.equals("search")) {
            String filter = "filter";
            String indexPage = request.getParameter("index");
            if (indexPage == null) {
                indexPage = "1";
            }
            int index = Integer.parseInt(indexPage);
            String subject_code = request.getParameter("subject_code");
            String subject_name = request.getParameter("subject_name");
            String subject_authorID = request.getParameter("subject_authorID");
            String subject_status = request.getParameter("subject_status");

            ArrayList<Subject> listSearch = dao.searchBySubject(subject_code, subject_name, subject_authorID, subject_status, index);
            int count = dao.countSearch(subject_code, subject_name, subject_authorID, subject_status);

            if (subject_code.equals("")) {
                subject_code = null;
            }
            if (subject_name.equals("")) {
                subject_name = null;
            }
            if (subject_authorID.equals("")) {
                subject_authorID = null;
            }
            if (subject_status.equals("")) {
                subject_status = null;
            }

            int endPage = count / 6;
            if (count % 6 != 0) {
                endPage++;
            }
            request.setAttribute("endPage", endPage);
            request.setAttribute("tag", index);
            request.setAttribute("filter", filter);

            request.setAttribute("subject_code", subject_code);
            request.setAttribute("subject_name", subject_name);
            request.setAttribute("subject_authorID", subject_authorID);
            request.setAttribute("subject_status", subject_status);

            request.setAttribute("listSearch", listSearch);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/template/admin/list/Subject.jsp").forward(request, response);

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
