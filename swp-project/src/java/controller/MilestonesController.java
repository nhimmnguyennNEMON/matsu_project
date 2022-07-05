/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDao;
import dal.IterationDao;
import dal.MilestoneDao;
import dal.UserDao;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.Class;
import model.Iteration;
import model.Milestone;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author quang
 */
public class MilestonesController extends HttpServlet {

    ClassDao classDao = new ClassDao();
    IterationDao iterDao = new IterationDao();
    MilestoneDao mileDao = new MilestoneDao();
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String go = request.getParameter("go");

        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = userDao.getUserByEmail(email);
        request.setAttribute("user", user);

        ArrayList<Class> classList = classDao.listClassByTrainer(user.id);
        request.setAttribute("classList", classList);

        ArrayList<Iteration> iterList = mileDao.getIters(user.id);
        request.setAttribute("iterList", iterList);

        if (go == null) {
            go = "list";
        }

        if (go.equals("list")) {
            request.setAttribute("page", 1);

            int pageEnd = mileDao.countItems("", "", "", "", user.id);
            request.setAttribute("count", pageEnd);

            String number = request.getParameter("numb");
            if (number == null) {
                number = "1";
            }
            int numb = Integer.parseInt(number);
            request.setAttribute("numb", numb);

            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }

            request.setAttribute("pageEnd", pageEnd);

            request.setAttribute("sort_name", "asc");
            request.setAttribute("sort_class", "asc");
            request.setAttribute("sort_iter", "asc");
            request.setAttribute("string_sql", "m.milestone_name asc, c.class_code asc, i.iteration_name asc");

            ArrayList<Milestone> list = mileDao.search("", "", "", "", 6, 0,
                    "m.milestone_name asc, c.class_code asc, i.iteration_name asc", user.id);
            request.setAttribute("list", list);

            request.getRequestDispatcher("/template/teacher/list/Milestones.jsp").forward(request, response);
        } else if (go.equals("detail")) {
            int id = Integer.parseInt(request.getParameter("id"));

            Milestone milestone = mileDao.get(id);
            request.setAttribute("milestone", milestone);

            request.getRequestDispatcher("/template/teacher/details/MilestonesDetails.jsp").forward(request, response);
        } else if (go.equals("add")) {
            request.getRequestDispatcher("/template/teacher/add/MilestonesAdd.jsp").forward(request, response);
        } else if (go.equals("active")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mileDao.active(id);
            response.sendRedirect("milestones");
        } else if (go.equals("inactive")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mileDao.inactive(id);
            response.sendRedirect("milestones");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = userDao.getUserByEmail(email);
        request.setAttribute("user", user);

        String number = request.getParameter("numb");
        if (number == null) {
            number = "1";
        }
        int numb = Integer.parseInt(number);
        request.setAttribute("numb", numb);

        ArrayList<Class> classList = classDao.listClassByTrainer(user.id);
        request.setAttribute("classList", classList);

        ArrayList<Iteration> iterList = mileDao.getIters(user.id);
        request.setAttribute("iterList", iterList);

        String go = request.getParameter("go");
        if (go == null) {
            go = "list";
        }
        if (go.equals("list")) {
            String iter_id = request.getParameter("iter_id");
            request.setAttribute("iter_id", iter_id);

            String class_code = request.getParameter("class_code");
            request.setAttribute("class_code", class_code);

            String status = request.getParameter("status");
            request.setAttribute("status", status);

            String milestone_name = request.getParameter("milestone_name");
            request.setAttribute("milestone_name", milestone_name);

            int page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", page);

            String sqlOrder = request.getParameter("string_sort");
            String sort_name = request.getParameter("sort_name");
            String sort_class = request.getParameter("sort_class");
            String sort_iter = request.getParameter("sort_iter");
            request.setAttribute("sort_name", sort_name);
            request.setAttribute("sort_class", sort_class);
            request.setAttribute("sort_iter", sort_iter);
            request.setAttribute("string_sql", sqlOrder);

            ArrayList<Milestone> list = mileDao.search(milestone_name, iter_id, status, class_code, 6, (page - 1) * 6, sqlOrder, user.id);

            int pageEnd = mileDao.countItems(milestone_name, iter_id, status, class_code, user.id);
            request.setAttribute("count", pageEnd);

            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
            request.setAttribute("list", list);

            request.getRequestDispatcher("/template/teacher/list/Milestones.jsp").forward(request, response);
        } else if (go.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String milestone_name = request.getParameter("milestone_name");
            int iter_id = Integer.parseInt(request.getParameter("iter_id"));
            int class_id = Integer.parseInt(request.getParameter("class_code"));
            String from_date = request.getParameter("from_date");
            String to_date = request.getParameter("to_date");
            String desc = request.getParameter("desc");
            int status = Integer.parseInt(request.getParameter("status"));

            ArrayList<Milestone> list1 = mileDao.getMilestoneByClass(class_id);
            boolean flag = true;
            for (Milestone mile : list1) {
                if (milestone_name.equalsIgnoreCase(mile.getMilestone_name()) && id != mile.getId()) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Iteration i = new Iteration();
                i.setId(iter_id);

                Class c = new Class();
                c.setId(class_id);

                Milestone m = new Milestone();
                m.setId(id);
                m.setMilestone_name(milestone_name);
                m.setIter(i);
                m.setClass_id(c);
                m.setFrom_date(from_date);
                m.setTo_date(to_date);
                m.setStatus(status);
                m.setDesc(desc);

                mileDao.update(m);

                String mess = "Updated milestones successfully!";
                request.setAttribute("mess", mess);

                request.setAttribute("page", 1);

                int pageEnd = mileDao.countItems("", "", "", "", user.id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);

                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_class", "asc");
                request.setAttribute("sort_iter", "asc");
                request.setAttribute("string_sql", "m.milestone_name asc, c.class_code asc, i.iteration_name asc");

                ArrayList<Milestone> list = mileDao.search("", "", "", "", 6, 0,
                        "m.milestone_name asc, c.class_code asc, i.iteration_name asc", user.id);
                request.setAttribute("list", list);

                request.getRequestDispatcher("/template/teacher/list/Milestones.jsp").forward(request, response);
            } else {
                request.setAttribute("id", id);
                request.setAttribute("milestone_name", milestone_name);
                request.setAttribute("iter_id", iter_id);
                request.setAttribute("class_id", class_id);
                request.setAttribute("from_date", from_date);
                request.setAttribute("to_date", to_date);
                request.setAttribute("desc", desc);
                request.setAttribute("status", status);
                String error = "Milestone Name must be unique in each class!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("/template/teacher/details/MilestonesDetails.jsp").forward(request, response);
            }

        } else if (go.equals("add")) {
            String milestone_name = request.getParameter("milestone_name");
            int iter_id = Integer.parseInt(request.getParameter("iter_name"));
            int class_id = Integer.parseInt(request.getParameter("class_code"));
            String from_date = request.getParameter("from_date");
            String to_date = request.getParameter("to_date");
            String desc = request.getParameter("desc");
            int status = Integer.parseInt(request.getParameter("status"));

            ArrayList<Milestone> list1 = mileDao.getMilestoneByClass(class_id);
            boolean flag = true;
            for (Milestone mile : list1) {
                if (milestone_name.equalsIgnoreCase(mile.getMilestone_name())) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Iteration i = new Iteration();
                i.setId(iter_id);

                Class c = new Class();
                c.setId(class_id);

                Milestone m = new Milestone();
                m.setIter(i);
                m.setMilestone_name(milestone_name);
                m.setClass_id(c);
                m.setFrom_date(from_date);
                m.setTo_date(to_date);
                m.setStatus(status);
                m.setDesc(desc);

                mileDao.insert(m);

                String mess = "Added new Milesonte Successfully!";
                request.setAttribute("mess", mess);

                request.setAttribute("page", 1);

                int pageEnd = mileDao.countItems("", "", "", "", user.id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);

                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_class", "asc");
                request.setAttribute("sort_iter", "asc");
                request.setAttribute("string_sql", "m.milestone_name asc, c.class_code asc, i.iteration_name asc");

                ArrayList<Milestone> list = mileDao.search("", "", "", "", 6, 0,
                        "m.milestone_name asc, c.class_code asc, i.iteration_name asc", user.id);
                request.setAttribute("list", list);

                request.getRequestDispatcher("/template/teacher/list/Milestones.jsp").forward(request, response);
            } else {
                request.setAttribute("milestone_name", milestone_name);
                request.setAttribute("iter_id", iter_id);
                request.setAttribute("class_id", class_id);
                request.setAttribute("from_date", from_date);
                request.setAttribute("to_date", to_date);
                request.setAttribute("desc", desc);
                request.setAttribute("status", status);
                String error = "Milestone Name must be unique in each class!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("/template/teacher/add/MilestonesAdd.jsp").forward(request, response);
            }

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
