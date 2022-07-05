/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.ClassDao;
import dal.FeatureDao;
import dal.SubjectDao;
import dal.TeamDao;
import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Feature;
import model.Iteration;
import model.Subject;
import model.User;
import model.Class;
import model.Team;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author SY NGUYEN
 */
public class FeatureController extends HttpServlet {

    FeatureDao dao = new FeatureDao();
    UserDao dao1 = new UserDao();
    TeamDao dao2 = new TeamDao();
    SubjectDao dao3 = new SubjectDao();
    ClassDao dao4 = new ClassDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");

        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        int role = new Jwt().getRoleFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);

        if (go == null) {
            go = "view";
        }
        // controller by admin
        if (role == 1 || role == 2) {
            if (go.equals("view")) {
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "", "", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_team", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.search("", "", "", "", "", "", 6, 0, "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("add")) {
                ArrayList<Feature> listTeam = dao.listTeam();
                request.setAttribute("listTeam", listTeam);
                request.getRequestDispatcher("/template/student/add/FeatureAdd.jsp").forward(request, response);

            } else if (go.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Feature detail = dao.searchFeatureID(id);
                request.setAttribute("detail", detail);
                ArrayList<Feature> listTeam = dao.listTeam();
                request.setAttribute("listTeam", listTeam);
                request.getRequestDispatcher("/template/student/detail/FeatureDetail.jsp").forward(request, response);

            } else if (go.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("feature");
            } else if (go.equals("active")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.active(id);
                response.sendRedirect("feature");
            } else if (go.equals("inactive")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.inactive(id);
                response.sendRedirect("feature");
            }
        }
// controller by admin
        if (role == 3) {
            if (go.equals("view")) {
                int user_id = user.getId();
                request.setAttribute("page", 1);
                int pageEnd = dao.countItemsTrainer("", "", "", "", "", "", user_id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_team", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.listFeatureByTeacher("", "", "", "", "", "", 6, 0, user_id, "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("add")) {
                ArrayList<Feature> listTeam = dao.listTeam();
                request.setAttribute("listTeam", listTeam);
                request.getRequestDispatcher("/template/student/add/FeatureAdd.jsp").forward(request, response);

            } else if (go.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Feature detail = dao.searchFeatureID(id);
                request.setAttribute("detail", detail);
                ArrayList<Feature> listTeam = dao.listTeam();
                request.setAttribute("listTeam", listTeam);
                request.getRequestDispatcher("/template/student/detail/FeatureDetail.jsp").forward(request, response);

            } else if (go.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
                response.sendRedirect("feature");
            } else if (go.equals("active")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.active(id);
                response.sendRedirect("feature");
            } else if (go.equals("inactive")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.inactive(id);
                response.sendRedirect("feature");
            }
        }
        //controller by student
        if (role == 4) {
            if (go.equals("view")) {
                int user_id = user.getId();
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "", "", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                String number = request.getParameter("numb");
                if (number == null) {
                    number = "1";
                }
                int numb = Integer.parseInt(number);
                request.setAttribute("numb", numb);

                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.listFeatureByStudent("", "", "", "", "", "", 6, 0, numb, user_id);
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("add")) {
                ArrayList<Feature> listTeam = dao.listTeam();
                request.setAttribute("listTeam", listTeam);
                request.getRequestDispatcher("/template/student/add/FeatureAdd.jsp").forward(request, response);

            } else if (go.equals("detail")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Feature detail = dao.searchFeatureID(id);
                request.setAttribute("detail", detail);
                ArrayList<Feature> listTeam = dao.listTeam();
                request.setAttribute("listTeam", listTeam);
                request.getRequestDispatcher("/template/student/detail/FeatureDetail.jsp").forward(request, response);

            }
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
        int role = new Jwt().getRoleFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);

        if (go == null) {
            go = "view";
        }

        //controller by role admin
        if (role == 1 || role == 2) {
            if (go.equals("view")) {
                String class_code = request.getParameter("class_code");
                String subject_code = request.getParameter("subject_code");
                String feature_status = request.getParameter("feature_status");
                String feature_name = request.getParameter("feature_name");
                int page = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("page", page);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                String sqlOrder = request.getParameter("string_sort");
                String sort_name = request.getParameter("sort_name");
                String sort_team = request.getParameter("sort_team");
                String sort_code = request.getParameter("sort_code");
                request.setAttribute("sort_name", sort_name);
                request.setAttribute("sort_team", sort_team);
                request.setAttribute("sort_code", sort_code);
                request.setAttribute("string_sql", sqlOrder);
                ArrayList<Feature> list = dao.search(feature_name, class_code, "", feature_status, subject_code, "", 6, (page - 1) * 6, sqlOrder);
                int pageEnd = dao.countItems(feature_name, class_code, "", feature_status, subject_code, "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                if (class_code.equals("")) {
                    class_code = null;
                }
                if (subject_code.equals("")) {
                    subject_code = null;
                }
                if (feature_status.equals("1")) {
                    feature_status = "Active";
                } else if (feature_status.equals("0")) {
                    feature_status = "Inactive";
                } else {
                    feature_status = null;
                }
                if (feature_name.equals("")) {
                    feature_name = null;
                }

                request.setAttribute("class_code", class_code);
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("feature_status", feature_status);
                request.setAttribute("feature_name", feature_status);

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("list", list);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("add")) {
                String team_name = request.getParameter("team_name");
                String feature_name = request.getParameter("feature_name");
                String feature_status = request.getParameter("feature_status");
                String desc = request.getParameter("desc");

                Feature f = new Feature();
                f.setTeam_id(Integer.parseInt(team_name));
                f.setFeature_name(feature_name);
                f.setStatus(Integer.parseInt(feature_status));
                f.setDesc(desc);

                int n = dao.insertIteration(f);
                if (n >= 1) {
                    String success = "Add " + feature_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Add " + feature_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "", "", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_team", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.search("", "", "", "", "", "", 6, 0, "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("detail")) {
                String id = request.getParameter("id");
                String team_name = request.getParameter("team_name");
                String feature_name = request.getParameter("feature_name");
                String feature_status = request.getParameter("feature_status");
                String desc = request.getParameter("desc");

                Feature f = new Feature();
                f.setId(Integer.parseInt(id));
                f.setTeam_id(Integer.parseInt(team_name));
                f.setFeature_name(feature_name);
                f.setStatus(Integer.parseInt(feature_status));
                f.setDesc(desc);

                int n = dao.updateIteration(f);
                if (n >= 1) {
                    String success = "Update " + feature_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Update " + feature_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "", "", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_team", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.search("", "", "", "", "", "", 6, 0, "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            }
        }

        //controller by role teacher
        if (role == 3) {
            if (go.equals("view")) {
                int user_id = user.getId();
                String class_code = request.getParameter("class_code");
                String subject_code = request.getParameter("subject_code");
                String feature_status = request.getParameter("feature_status");
                String feature_name = request.getParameter("feature_name");
                int page = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("page", page);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                String sqlOrder = request.getParameter("string_sort");
                String sort_name = request.getParameter("sort_name");
                String sort_team = request.getParameter("sort_team");
                String sort_code = request.getParameter("sort_code");
                request.setAttribute("sort_name", sort_name);
                request.setAttribute("sort_team", sort_team);
                request.setAttribute("sort_code", sort_code);
                request.setAttribute("string_sql", sqlOrder);
                ArrayList<Feature> list = dao.listFeatureByTeacher(feature_name, class_code, "", feature_status, subject_code, "", 6, (page - 1) * 6, user_id, sqlOrder);
                int pageEnd = dao.countItemsTrainer(feature_name, class_code, "", feature_status, subject_code, "", user_id);
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                if (class_code.equals("")) {
                    class_code = null;
                }
                if (subject_code.equals("")) {
                    subject_code = null;
                }
                if (feature_status.equals("1")) {
                    feature_status = "Active";
                } else if (feature_status.equals("0")) {
                    feature_status = "Inactive";
                } else {
                    feature_status = null;
                }
                if (feature_name.equals("")) {
                    feature_name = null;
                }

                request.setAttribute("class_code", class_code);
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("feature_status", feature_status);
                request.setAttribute("feature_name", feature_status);

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("list", list);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("add")) {
                String team_name = request.getParameter("team_name");
                String feature_name = request.getParameter("feature_name");
                String feature_status = request.getParameter("feature_status");
                String desc = request.getParameter("desc");

                Feature f = new Feature();
                f.setTeam_id(Integer.parseInt(team_name));
                f.setFeature_name(feature_name);
                f.setStatus(Integer.parseInt(feature_status));
                f.setDesc(desc);

                int n = dao.insertIteration(f);
                if (n >= 1) {
                    String success = "Add " + feature_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Add " + feature_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                int user_id = user.getId();
                request.setAttribute("page", 1);
                int pageEnd = dao.countItemsTrainer("", "", "", "", "", "", user_id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_team", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.listFeatureByTeacher("", "", "", "", "", "", 6, 0, user_id, "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("detail")) {
                String id = request.getParameter("id");
                String team_name = request.getParameter("team_name");
                String feature_name = request.getParameter("feature_name");
                String feature_status = request.getParameter("feature_status");
                String desc = request.getParameter("desc");

                Feature f = new Feature();
                f.setId(Integer.parseInt(id));
                f.setTeam_id(Integer.parseInt(team_name));
                f.setFeature_name(feature_name);
                f.setStatus(Integer.parseInt(feature_status));
                f.setDesc(desc);

                int n = dao.updateIteration(f);
                if (n >= 1) {
                    String success = "Update " + feature_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Update " + feature_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                int user_id = user.getId();
                request.setAttribute("page", 1);
                int pageEnd = dao.countItemsTrainer("", "", "", "", "", "", user_id);
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                request.setAttribute("pageEnd", pageEnd);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_team", "asc");
                request.setAttribute("sort_code", "asc");
                request.setAttribute("string_sql", "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.listFeatureByTeacher("", "", "", "", "", "", 6, 0, user_id, "c.class_code asc, t.team_name asc, f.feature_name asc");
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                ArrayList<Feature> classCode = dao.listClassCode();
                request.setAttribute("classCode", classCode);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            }
        }

        //controller by student
        if (role == 4) {
            if (go.equals("add")) {
                String team_name = request.getParameter("team_name");
                String feature_name = request.getParameter("feature_name");
                String feature_status = request.getParameter("feature_status");
                String desc = request.getParameter("desc");

                Feature f = new Feature();
                f.setTeam_id(Integer.parseInt(team_name));
                f.setFeature_name(feature_name);
                f.setStatus(Integer.parseInt(feature_status));
                f.setDesc(desc);

                int n = dao.insertIteration(f);
                if (n >= 1) {
                    String success = "Add " + feature_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Add " + feature_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                int user_id = user.getId();
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "", "", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                String number = request.getParameter("numb");
                if (number == null) {
                    number = "1";
                }
                int numb = Integer.parseInt(number);
                request.setAttribute("numb", numb);

                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.listFeatureByStudent("", "", "", "", "", "", 6, 0, numb, user_id);
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            } else if (go.equals("detail")) {
                String id = request.getParameter("id");
                String team_name = request.getParameter("team_name");
                String feature_name = request.getParameter("feature_name");
                String feature_status = request.getParameter("feature_status");
                String desc = request.getParameter("desc");

                Feature f = new Feature();
                f.setId(Integer.parseInt(id));
                f.setTeam_id(Integer.parseInt(team_name));
                f.setFeature_name(feature_name);
                f.setStatus(Integer.parseInt(feature_status));
                f.setDesc(desc);

                int n = dao.updateIteration(f);
                if (n >= 1) {
                    String success = "Update " + feature_name + " to project successfull!";
                    request.setAttribute("success", success);
                } else {
                    String error = "Update " + feature_name + " to project fail!";
                    request.setAttribute("error", error);
                }
                int user_id = user.getId();
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "", "", "", "");
                request.setAttribute("count", pageEnd);

                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }

                String number = request.getParameter("numb");
                if (number == null) {
                    number = "1";
                }
                int numb = Integer.parseInt(number);
                request.setAttribute("numb", numb);

                request.setAttribute("pageEnd", pageEnd);
                ArrayList<Feature> list = dao.listFeatureByStudent("", "", "", "", "", "", 6, 0, numb, user_id);
                request.setAttribute("list", list);
                ArrayList<Subject> filter = dao3.listFilterSubjectCode();
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("/template/student/list/Feature.jsp").forward(request, response);

            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
