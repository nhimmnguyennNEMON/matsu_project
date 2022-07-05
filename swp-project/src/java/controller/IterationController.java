/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.IterationDao;
import dal.SubjectDao;
import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Iteration;
import model.Subject;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author SY NGUYEN
 */
public class IterationController extends HttpServlet {

    IterationDao dao = new IterationDao();
    UserDao dao1 = new UserDao();
    SubjectDao dao2 = new SubjectDao();

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
            request.setAttribute("page", 1);
            int pageEnd = dao.countItems("", "", "");
            request.setAttribute("count", pageEnd);

            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
            request.setAttribute("sort_name", "asc");
            request.setAttribute("sort_due", "asc");
            request.setAttribute("sort_code", "asc");
            request.setAttribute("string_sql", "i.iteration_name asc, i.duration asc, s.subject_code asc");
            request.setAttribute("pageEnd", pageEnd);
            ArrayList<Iteration> list = dao.search("", "", "", 6, 0, "i.iteration_name asc, i.duration asc, s.subject_code asc");
            request.setAttribute("list", list);
            ArrayList<Subject> filter = dao2.listFilterSubjectCode();
            request.setAttribute("filter", filter);
            request.getRequestDispatcher("/template/manager/list/Iteration.jsp").forward(request, response);

        } else if (go.equals("add")) {
            ArrayList<Subject> subjectList = dao2.list();
            request.setAttribute("subjectList", subjectList);
            request.getRequestDispatcher("/template/manager/add/IterationAdd.jsp").forward(request, response);

        } else if (go.equals("detail")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Iteration detail = dao.searchIterationID(id);
            request.setAttribute("detail", detail);
            ArrayList<Subject> subjectList = dao2.list();
            request.setAttribute("subjectList", subjectList);
            request.getRequestDispatcher("/template/manager/detail/IterationDetail.jsp").forward(request, response);

        } else if (go.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            response.sendRedirect("iteration");
        } else if (go.equals("active")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.active(id);
            response.sendRedirect("iteration");
        } else if (go.equals("inactive")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.inactive(id);
            response.sendRedirect("iteration");
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
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);
        if (go.equals("view")) {

            String subject_code = request.getParameter("subject_code");
            String subject_name = request.getParameter("subject_name");
            String iteration_status = request.getParameter("iteration_status");

            String sqlOrder = request.getParameter("string_sort");
            String sort_name = request.getParameter("sort_name");
            String sort_due = request.getParameter("sort_due");
            String sort_code = request.getParameter("sort_code");
            request.setAttribute("sort_name", sort_name);
            request.setAttribute("sort_due", sort_due);
            request.setAttribute("sort_code", sort_code);
            request.setAttribute("string_sql", sqlOrder);
            int page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", page);
            ArrayList<Iteration> list = dao.search(subject_code, subject_name, iteration_status, 6, (page - 1) * 6, sqlOrder);
            int pageEnd = dao.countItems(subject_code, subject_name, iteration_status);
            request.setAttribute("count", pageEnd);
            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            if (iteration_status.equals("1")) {
                iteration_status = "Active";
            } else if (iteration_status.equals("0")) {
                iteration_status = "Inactive";
            } else {
                iteration_status = "All Status";
            }
            if (subject_code.equals("")) {
                subject_code = null;
            }
            if (subject_name.equals("")) {
                subject_name = null;
            }
            request.setAttribute("iteration_status", iteration_status);
            request.setAttribute("subject_name", subject_name);
            request.setAttribute("subject_code", subject_code);
            request.setAttribute("pageEnd", pageEnd);
            request.setAttribute("list", list);
            ArrayList<Subject> filter = dao2.listFilterSubjectCode();
            request.setAttribute("filter", filter);
            request.getRequestDispatcher("/template/manager/list/Iteration.jsp").forward(request, response);

        } else if (go.equals("add")) {
            String subject_code = request.getParameter("subject_code");
            String iteration_name = request.getParameter("iteration_name");
            String duration = request.getParameter("duration");
            String iteration_status = request.getParameter("iteration_status");

            Date date = new Date(duration);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String durationFormet = dateFormat.format(date);

            ArrayList<Iteration> list1 = dao.getIterationBySubject(Integer.parseInt(subject_code), iteration_name);
            boolean flag = true;
            for (Iteration iter : list1) {
                if (iteration_name.equalsIgnoreCase(iter.getIteration_name())) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Iteration iter = new Iteration();
                iter.setSubject_id(Integer.parseInt(subject_code));
                iter.setIteration_name(iteration_name);
                iter.setDuration(durationFormet);
                iter.setStatus_iter(Integer.parseInt(iteration_status));
                int n = dao.insertIteration(iter);
                if (n >= 1) {
                    String success = "Add " + iteration_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Add " + iteration_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_due", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "i.iteration_name asc, i.duration asc, s.subject_code asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Iteration> list = dao.search("", "", "", 6, 0, "i.iteration_name asc, i.duration asc, s.subject_code asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao2.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/template/manager/list/Iteration.jsp").forward(request, response);

            } else {
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("iteration_name", iteration_name);
                request.setAttribute("duration", duration);
                request.setAttribute("iteration_status", iteration_status);
                String error = "" + iteration_name + " must be unique in each Subject!";
                request.setAttribute("error", error);
                ArrayList<Subject> subjectList = dao2.list();
                request.setAttribute("subjectList", subjectList);
                request.getRequestDispatcher("/template/manager/add/IterationAdd.jsp").forward(request, response);
            }

        } else if (go.equals("update")) {
            String id = request.getParameter("id");
            String subject_code = request.getParameter("subject_code");
            String iteration_name = request.getParameter("iteration_name");
            String duration = request.getParameter("duration");
            String iteration_status = request.getParameter("iteration_status");

            Date date = new Date(duration);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String durationFormet = dateFormat.format(date);

            ArrayList<Iteration> list1 = dao.getIterationBySubject(Integer.parseInt(subject_code), iteration_name);
            boolean flag = true;
            for (Iteration iter : list1) {
                if (iteration_name.equalsIgnoreCase(iter.getIteration_name()) && Integer.parseInt(id) != iter.getId()) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Iteration iter = new Iteration();
                iter.setId(Integer.parseInt(id));
                iter.setSubject_id(Integer.parseInt(subject_code));
                iter.setIteration_name(iteration_name);
                iter.setDuration(durationFormet);
                iter.setStatus_iter(Integer.parseInt(iteration_status));
                int n = dao.updateIteration(iter);

                String success = "Update " + iteration_name + " successfull!";
                request.setAttribute("success", success);
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_due", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "i.iteration_name asc, i.duration asc, s.subject_code asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Iteration> list = dao.search("", "", "", 6, 0, "i.iteration_name asc, i.duration asc, s.subject_code asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao2.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/template/manager/list/Iteration.jsp").forward(request, response);

            } else {
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("iteration_name", iteration_name);
                request.setAttribute("duration", duration);
                request.setAttribute("iteration_status", iteration_status);
                String error = "" + iteration_name + " must be unique in each Subject!";
                request.setAttribute("error", error);
                ArrayList<Subject> subjectList = dao2.list();
                request.setAttribute("subjectList", subjectList);
                Iteration detail = dao.searchIterationID(Integer.parseInt(id));
                request.setAttribute("detail", detail);
                request.getRequestDispatcher("/template/manager/detail/IterationDetail.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
