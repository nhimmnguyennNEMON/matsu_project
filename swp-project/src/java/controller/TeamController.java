/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDao;
import dal.TeamDao;
import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Class;
import model.Team;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author quang
 */
public class TeamController extends HttpServlet {

    TeamDao teamDao = new TeamDao();
    ClassDao classDao = new ClassDao();
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

        if (go == null) {
            go = "list";
        }
        if (go.equals("list")) {
            request.setAttribute("page", 1);

            int pageEnd = teamDao.countItems("", "", "", user.id);
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

            request.setAttribute("sort_team_name", "asc");
            request.setAttribute("sort_class", "asc");
            request.setAttribute("sort_topic_code", "asc");
            request.setAttribute("string_sql", "t.team_name asc, c.class_code asc, t.topic_code asc");

            ArrayList<Team> list = teamDao.search("", "", "", 6, 0, user.id, "t.team_name asc, c.class_code asc, t.topic_code asc");
            request.setAttribute("list", list);

            request.getRequestDispatcher("/template/teacher/list/Team.jsp").forward(request, response);
        } else if (go.equals("detail")) {
            int id = Integer.parseInt(request.getParameter("id"));

            Team team = teamDao.get(id);
            request.setAttribute("team", team);

            request.getRequestDispatcher("/template/teacher/details/TeamDetails.jsp").forward(request, response);
        } else if (go.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            teamDao.delete(id);
            response.sendRedirect("team");
        } else if (go.equals("add")) {
            request.getRequestDispatcher("/template/teacher/add/TeamAdd.jsp").forward(request, response);
        } else if (go.equals("active")) {
            int id = Integer.parseInt(request.getParameter("id"));
            teamDao.active(id);
            response.sendRedirect("team");
        } else if (go.equals("inactive")) {
            int id = Integer.parseInt(request.getParameter("id"));
            teamDao.inactive(id);
            response.sendRedirect("team");
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

        String go = request.getParameter("go");
        if (go == null) {
            go = "list";
        }
        if (go.equals("list")) {
            String topic_name = request.getParameter("topic_name");
            request.setAttribute("topic_name", topic_name);

            String class_code = request.getParameter("class_code");
            request.setAttribute("class_code", class_code);

            String status = request.getParameter("status");
            request.setAttribute("status", status);

            int page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", page);

            String sqlOrder = request.getParameter("string_sort");
            String sort_team_name = request.getParameter("sort_team_name");
            String sort_class = request.getParameter("sort_class");
            String sort_topic_code = request.getParameter("sort_topic_code");
            request.setAttribute("sort_team_name", sort_team_name);
            request.setAttribute("sort_class", sort_class);
            request.setAttribute("sort_topic_code", sort_topic_code);
            request.setAttribute("string_sql", sqlOrder);

            ArrayList<Team> list = teamDao.search(class_code, status, topic_name, 6, (page - 1) * 6, user.id, sqlOrder);

            int pageEnd = teamDao.countItems(class_code, status, topic_name, user.id);
            request.setAttribute("count", pageEnd);

            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
            request.setAttribute("list", list);

            request.getRequestDispatcher("/template/teacher/list/Team.jsp").forward(request, response);
        } else if (go.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String team_name = request.getParameter("team_name");
            int class_id = Integer.parseInt(request.getParameter("class_code"));
            String topic_code = request.getParameter("topic_code");
            String topic_name = request.getParameter("topic_name");
            String gitlab_url = request.getParameter("gitlab_url");
            String desc = request.getParameter("desc");
            int status = Integer.parseInt(request.getParameter("status"));

            ArrayList<Team> list1 = teamDao.getTeamsByClass(user.id, class_id);
            boolean flag = true;
            for (Team team : list1) {
                if (team_name.equalsIgnoreCase(team.getTeam_name()) && id != team.getId()) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Class c = new Class();
                c.setId(class_id);

                Team t = new Team();
                t.setId(id);
                t.setTeam_name(team_name.toUpperCase());
                t.setClass_id(c);
                t.setTopic_code(topic_code);
                t.setTopic_name(topic_name);
                t.setGitlab_url(gitlab_url);
                t.setStatus(status);
                t.setDesc(desc);

                teamDao.update(t);
                
                String mess = "Updated successfully!";
                request.setAttribute("mess", mess);

                request.setAttribute("page", 1);

                int pageEnd = teamDao.countItems("", "", "", user.id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);

                request.setAttribute("sort_team_name", "asc");
                request.setAttribute("sort_class", "asc");
                request.setAttribute("sort_topic_code", "asc");
                request.setAttribute("string_sql", "t.team_name asc, c.class_code asc, t.topic_code asc");

                ArrayList<Team> list = teamDao.search("", "", "", 6, 0, user.id, "t.team_name asc, c.class_code asc, t.topic_code asc");
                request.setAttribute("list", list);

                request.getRequestDispatcher("/template/teacher/list/Team.jsp").forward(request, response);
            } else {
                request.setAttribute("id", id);
                request.setAttribute("class_id", class_id);
                request.setAttribute("team_name", team_name);
                request.setAttribute("topic_code", topic_code);
                request.setAttribute("topic_name", topic_name);
                request.setAttribute("gitlab_url", gitlab_url);
                request.setAttribute("desc", desc);
                request.setAttribute("status", status);
                String error = "Team Name must be unique in each class!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("/template/teacher/details/TeamDetails.jsp").forward(request, response);
            }

        } else if (go.equals("add")) {
            int class_id = Integer.parseInt(request.getParameter("class_code"));
            String team_name = request.getParameter("team_name");
            String topic_code = request.getParameter("topic_code");
            String topic_name = request.getParameter("topic_name");
            String gitlab_url = request.getParameter("gitlab_url");
            String desc = request.getParameter("desc");
            int status = Integer.parseInt(request.getParameter("status"));

            ArrayList<Team> list1 = teamDao.getTeamsByClass(user.id, class_id);
            boolean flag = true;
            for (Team team : list1) {
                if (team_name.equalsIgnoreCase(team.getTeam_name())) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                Class c = new Class();
                c.setId(class_id);

                Team t = new Team();
                t.setTeam_name(team_name.toUpperCase());
                t.setClass_id(c);
                t.setTopic_code(topic_code);
                t.setTopic_name(topic_name);
                t.setGitlab_url(gitlab_url);
                t.setStatus(status);
                t.setDesc(desc);

                teamDao.insert(t);
                
                String mess = "Added new Team successfully!";
                request.setAttribute("mess", mess);

                request.setAttribute("page", 1);

                int pageEnd = teamDao.countItems("", "", "", user.id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);

                request.setAttribute("sort_team_name", "asc");
                request.setAttribute("sort_class", "asc");
                request.setAttribute("sort_topic_code", "asc");
                request.setAttribute("string_sql", "t.team_name asc, c.class_code asc, t.topic_code asc");

                ArrayList<Team> list = teamDao.search("", "", "", 6, 0, user.id, "t.team_name asc, c.class_code asc, t.topic_code asc");
                request.setAttribute("list", list);

                request.getRequestDispatcher("/template/teacher/list/Team.jsp").forward(request, response);
            } else {
                request.setAttribute("class_id", class_id);
                request.setAttribute("team_name", team_name);
                request.setAttribute("topic_code", topic_code);
                request.setAttribute("topic_name", topic_name);
                request.setAttribute("gitlab_url", gitlab_url);
                request.setAttribute("desc", desc);
                request.setAttribute("status", status);
                String error = "Team Name must be unique in each class!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("/template/teacher/add/TeamAdd.jsp").forward(request, response);
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
