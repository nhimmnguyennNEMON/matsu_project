
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.SubjectDao;
import dal.SubjectSettingDao;
import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Subject;
import model.SubjectSetting;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author pallgree
 */
public class SubjectSettingController extends HttpServlet {

    SubjectSettingDao subjectSettingDB = new SubjectSettingDao();
    SubjectDao subjectDB = new SubjectDao();
    UserDao userDB = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> listType = subjectSettingDB.viewAllType();
        ServletConfig config = getServletConfig();
        HttpSession session = request.getSession();
        ArrayList<String> listSettingType = new ArrayList<>();
        String name_type = "";
        int count = 2;
        for (int i = 1; i < count; i++) {
            name_type = config.getInitParameter(String.valueOf(i));
            if (name_type != null) {
                listSettingType.add(String.valueOf(i));
                session.setAttribute(String.valueOf(i), name_type);
                count++;
            }
        }
        for (String obj : listType) {
            name_type = config.getInitParameter(obj);
            if (name_type != null) {
                session.setAttribute(obj, name_type);
                count++;
            }
        }
        request.setAttribute("listType", listType);
        request.setAttribute("listSettingType", listSettingType);

        String go = request.getParameter("go");

        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = userDB.getUserByEmail(email);
        request.setAttribute("user", user);

        ArrayList<Subject> subjectList = subjectDB.list();
        request.setAttribute("subjectList", subjectList);

        int end = subjectSettingDB.count();

        if (go == null) {
            go = "list";
        }
        if (go.equals("list")) {

            String raw_subject_id = request.getParameter("subject_id");
            raw_subject_id = (raw_subject_id == null || raw_subject_id.length() == 0) ? "0" : raw_subject_id;
            int subject_id = Integer.parseInt(raw_subject_id);
            request.setAttribute("subject_id", subject_id);

            int Index = 1;
            if (end % 10 != 0) {
                end = end / 10;
                end++;
            } else {
                end = end / 10;
            }
            ArrayList<SubjectSetting> list = subjectSettingDB.listPagination(Index);
            request.setAttribute("index", Index);
            request.setAttribute("end", end);
            request.setAttribute("list", list);

            request.getRequestDispatcher("/template/admin/list/SubjectSetting.jsp").forward(request, response);

        } else if (go.equals("detail")) {
            int id = Integer.parseInt(request.getParameter("id"));

            SubjectSetting subjectSetting = subjectSettingDB.getSubjectSetting(id);
            request.setAttribute("subjectSetting", subjectSetting);

            request.getRequestDispatcher("/template/admin/detail/SubjectSettingDetail.jsp").forward(request, response);
        } else if (go.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            subjectSettingDB.deleteSubjectSetting(id);
            response.sendRedirect("subject-setting");
        } else if (go.equals("add")) {
            request.getRequestDispatcher("/template/admin/add/SubjectSettingAdd.jsp").forward(request, response);
        } else if (go.equals("paging")) {
            int index = Integer.parseInt(request.getParameter("index"));
            ArrayList<SubjectSetting> list = subjectSettingDB.listPagination(index);

            if (end % 10 != 0) {
                end = end / 10;
                end++;
            } else {
                end = end / 10;
            }

            try (PrintWriter out = response.getWriter()) {

                out.print("<div class=\"row\" id=\"list\">"
                        + "     <div class=\"col-md-12 grid-margin stretch-card \" id=\"mySearch\">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchBySubjectCode(this, 1)\" id=\"search-subject-code\" style=\"height: 48px\">\n"
                        + "                                            <option value=\"0\">All Subject Code</option>\n");
                subjectList.forEach((s) -> {
                    out.print("<option value=\"" + s.getId() + "\">" + s.getSubject_code() + "</option>");
                });
                out.print("                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByType(this, 1)\" id=\"search-type\" style=\"height: 48px\"> \n"
                        + "                                                <option value=\"0\">All Setting Type</option>");
                listType.forEach((t) -> {
                    out.print("                                        <option value=\"" + t + "\">" + session.getAttribute(t) + "</option>\n");
                });
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByStatus(this, 1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                            <option value=\"3\">All status</option>\n"
                        + "                                            <option value=\"0\">Inactive</option>\n"
                        + "                                            <option value=\"1\">Active</option>\n"
                        + "                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                            <div class=\"col-md-4\" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group\">\n"
                        + "                                            <input type=\"text\" class=\"form-control\" id=\"input-search-title\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark\" id=\"btn-search-name\" onclick=\"searchByTitle()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                            </div>\n"
                        + "                                <div class=\"col-md-1\">\n"
                        + "                                    <button class=\"btn btn-outline-secondary text-dark\" onclick=\"add()\" type=\"button\" data-modal-toggle=\"defaultModal\" id=\"btn-add-setting\" >\n"
                        + "                                        <i class=\"ti-plus\"></i>\n"
                        + "                                    </button>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"myList\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                        <h4 class=\"card-title\">Subject Setting List</h4>\n"
                        + "                                        <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                        <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                    <tr>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Subject Code</th>\n"
                        + "                                                        <th>Setting Type</th>\n"
                        + "                                                        <th>Setting Title</th>\n"
                        + "                                                        <th>Setting Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                    </tr>\n"
                        + "                                                </thead>\n"
                        + "                                                <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                   <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getDisplay_order() + "</td>\n");
                    out.print("<td style=\"text-align: center;\" >" + list.get(i).getSubject_id().getSubject_code() + "</td>");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_id()).equals(t)) {
                            out.print("                                                 <td>" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td>" + list.get(i).getSetting_title() + "</td>\n"
                            + "                                                    <td>" + list.get(i).getSetting_value() + "</td>\n");

                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-danger btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 0," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" selected=\"\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 1," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" selected style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    out.print("                                                             <td style=\"text-align: center;\">\n"
                            + "                                                                <button onclick=\"detail(" + list.get(i).getId() + ")\" type=\"button\" class=\"btn btn-icon\" type=\"button\" id=\"btn-add-setting\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>\n"
                            + "                                                        </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                            </table>\n"
                        + "                                        </div>\n"
                        + "                                        <div class=\"row\">\n"
                        + "                                            <div class=\" col-md-12 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >");
                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" disabled class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> listType = subjectSettingDB.viewAllType();
        ServletConfig config = getServletConfig();
        HttpSession session = request.getSession();
        ArrayList<String> listSettingType = new ArrayList<>();
        String name_type = "";
        int count = 2;
        for (int i = 1; i < count; i++) {
            name_type = config.getInitParameter(String.valueOf(i));
            if (name_type != null) {
                listSettingType.add(String.valueOf(i));
                session.setAttribute(String.valueOf(i), name_type);
                count++;
            }
        }
        for (String obj : listType) {
            name_type = config.getInitParameter(obj);
            if (name_type != null) {
                session.setAttribute(obj, name_type);
                count++;
            }
        }

        ArrayList<SubjectSetting> list = new ArrayList<>();

        String go = request.getParameter("go");

        if (go.equals("search-by-type")) {
            int type = Integer.parseInt(request.getParameter("value"));
            int index = Integer.parseInt(request.getParameter("index"));
            response.setContentType("text/html;charset=UTF-8");

            ArrayList<Subject> subjectList = subjectDB.list();
            try (PrintWriter out = response.getWriter()) {
                if (type == 0) {
                    index = 1;
                    list = subjectSettingDB.listPagination(index);
                } else {
                    list = subjectSettingDB.searchByType(type, index);
                }

                int end = subjectSettingDB.countType(type);
                if (end % 10 != 0) {
                    end = end / 10;
                    end++;
                } else {
                    end = end / 10;
                }

                out.print("<div class=\"col-md-12 grid-margin stretch-card \" id=\"mySearch\">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchBySubjectCode(this,1)\" id=\"search-subject-code\" style=\"height: 48px\">\n"
                        + "                                            <option value=\"0\">All Subject Code</option>\n");
                subjectList.forEach((s) -> {
                    out.print("<option value=\"" + s.getId() + "\">" + s.getSubject_code() + "</option>");
                });
                out.print("                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\"> \n");
                if (type == 0) {
                    out.print("                                     <option value=\"0\" selected>All Setting Type</option>\n");
                } else {
                    out.print("                                     <option value=\"0\" >All Setting Type</option>\n");
                }
                listType.forEach((t) -> {
                    if (String.valueOf(type).equals(t)) {
                        out.print("                                        <option value=\"" + t + "\" selected >" + session.getAttribute(t) + "</option>\n");
                    } else {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }
                });
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByStatus(this," + index + ")\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                            <option value=\"3\">All status</option>\n"
                        + "                                            <option value=\"0\">Inactive</option>\n"
                        + "                                            <option value=\"1\">Active</option>\n"
                        + "                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                            <div class=\"col-md-4\" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group\">\n"
                        + "                                            <input type=\"text\" class=\"form-control\" id=\"input-search-title\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark\" id=\"btn-search-name\" onclick=\"searchByTitle()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                            </div>\n"
                        + "                                <div class=\"col-md-1\">\n"
                        + "                                    <button class=\"btn btn-outline-secondary text-dark\" onclick=\"add()\" type=\"button\" data-modal-toggle=\"defaultModal\" id=\"btn-add-setting\" >\n"
                        + "                                        <i class=\"ti-plus\"></i>\n"
                        + "                                    </button>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"myList\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                        <h4 class=\"card-title\">Subject Setting List</h4>\n"
                        + "                                        <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                        <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                    <tr>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Subject Code</th>\n"
                        + "                                                        <th>Setting Type</th>\n"
                        + "                                                        <th>Setting Title</th>\n"
                        + "                                                        <th>Setting Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                    </tr>\n"
                        + "                                                </thead>\n"
                        + "                                                <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                   <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getDisplay_order() + "</td>\n");
                    out.print("<td style=\"text-align: center;\" >" + list.get(i).getSubject_id().getSubject_code() + "</td>");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_id()).equals(t)) {
                            out.print("                                                 <td>" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td>" + list.get(i).getSetting_title() + "</td>\n"
                            + "                                                    <td>" + list.get(i).getSetting_value() + "</td>\n");

                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-danger btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 0," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" selected=\"\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 1," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" selected style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    out.print("                                                             <td style=\"text-align: center;\">\n"
                            + "                                                                <button onclick=\"detail(" + list.get(i).getId() + ")\" type=\"button\" class=\"btn btn-icon\" type=\"button\" id=\"btn-add-setting\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>\n"
                            + "                                                        </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\" col-md-12 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageType(" + type + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageType(" + type + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageType(" + type + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageType(" + type + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageType(" + type + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("search-by-title")) {
            String setting_title = request.getParameter("setting_title");
            int index = Integer.parseInt(request.getParameter("index"));

            int end = subjectSettingDB.countTitle(setting_title);
            if (end % 10 != 0) {
                end = end / 10;
                end++;
            } else {
                end = end / 10;
            }

            list = subjectSettingDB.searchSubjectSettingByTitle(setting_title, index);
            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"myList\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                        <h4 class=\"card-title\">Subject Setting List</h4>\n"
                        + "                                        <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                        <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                    <tr>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Subject Code</th>\n"
                        + "                                                        <th>Setting Type</th>\n"
                        + "                                                        <th>Setting Title</th>\n"
                        + "                                                        <th>Setting Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                    </tr>\n"
                        + "                                                </thead>\n"
                        + "                                                <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getDisplay_order() + "</td>\n");
                    out.print("<td style=\"text-align: center;\" >" + list.get(i).getSubject_id().getSubject_code() + "</td>");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_id()).equals(t)) {
                            out.print("                                                 <td style=\"text-align: center;\">" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td>" + list.get(i).getSetting_title() + "</td>\n"
                            + "                                                    <td>" + list.get(i).getSetting_value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-danger btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 0," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" selected=\"\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 1," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" selected style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }
                    out.print("                                                             <td style=\"text-align: center;\">\n"
                            + "                                                                <button onclick=\"detail(" + list.get(i).getId() + ")\" type=\"button\" class=\"btn btn-icon\" type=\"button\" id=\"btn-add-setting\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>\n"
                            + "                                                        </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\" col-md-12 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageTitle(" + setting_title + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageTitle(" + setting_title + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageTitle(" + setting_title + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageTitle(" + setting_title + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageTitle(" + setting_title + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("search-by-status")) {
            int status = Integer.parseInt(request.getParameter("value"));
            int index = Integer.parseInt(request.getParameter("index"));
            response.setContentType("text/html;charset=UTF-8");

            int end = subjectSettingDB.countStatus(status);
            if (end % 10 != 0) {
                end = end / 10;
                end++;
            } else {
                end = end / 10;
            }

            ArrayList<Subject> subjectList = subjectDB.list();

            if (status == 3) {
                list = subjectSettingDB.listPagination(index);
            } else {
                list = subjectSettingDB.searchByStatus(status, index);
            }

            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"col-md-12 grid-margin stretch-card \" id=\"mySearch\">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchBySubjectCode(this, 1)\" id=\"search-subject-code\" style=\"height: 48px\">\n"
                        + "                                            <option value=\"0\">All Subject Code</option>\n");
                subjectList.forEach((s) -> {
                    out.print("<option value=\"" + s.getId() + "\">" + s.getSubject_code() + "</option>");
                });
                out.print("                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByType(this, 1)\" id=\"search-type\" style=\"height: 48px\"> \n"
                        + "<option value=\"0\">All Setting Type</option>");

                listType.forEach((t) -> {

                    out.print("                                        <option value=\"" + t + "\">" + session.getAttribute(t) + "</option>\n");

                });
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n");
                if (status == 0) {
                    out.print("<option value=\"3\">All status</option>\n"
                            + "                                            <option value=\"0\" selected>Inactive</option>\n"
                            + "                                            <option value=\"1\">Active</option>\n");
                }
                if (status == 1) {
                    out.print("<option value=\"3\">All status</option>\n"
                            + "                                            <option value=\"0\">Inactive</option>\n"
                            + "                                            <option value=\"1\" selected>Active</option>\n");
                }
                if (status == 3) {
                    out.print("<option value=\"3\" selected>All status</option>\n"
                            + "                                            <option value=\"0\" >Inactive</option>\n"
                            + "                                            <option value=\"1\">Active</option>\n");
                }
                out.print("</select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                            <div class=\"col-md-4\" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group\">\n"
                        + "                                            <input type=\"text\" class=\"form-control\" id=\"input-search-title\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark\" id=\"btn-search-name\" onclick=\"searchByTitle()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                            </div>\n"
                        + "                                <div class=\"col-md-1\">\n"
                        + "                                    <button class=\"btn btn-outline-secondary text-dark\" onclick=\"add()\" type=\"button\" data-modal-toggle=\"defaultModal\" id=\"btn-add-setting\" >\n"
                        + "                                        <i class=\"ti-plus\"></i>\n"
                        + "                                    </button>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"myList\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                        <h4 class=\"card-title\">Subject Setting List</h4>\n"
                        + "                                        <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                        <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                    <tr>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Subject Code</th>\n"
                        + "                                                        <th>Setting Type</th>\n"
                        + "                                                        <th>Setting Title</th>\n"
                        + "                                                        <th>Setting Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                    </tr>\n"
                        + "                                                </thead>\n"
                        + "                                                <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                   <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getDisplay_order() + "</td>\n");
                    out.print("<td style=\"text-align: center;\" >" + list.get(i).getSubject_id().getSubject_code() + "</td>");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_id()).equals(t)) {
                            out.print("                                                 <td>" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td>" + list.get(i).getSetting_title() + "</td>\n"
                            + "                                                    <td>" + list.get(i).getSetting_value() + "</td>\n");

                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-danger btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 0," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" selected=\"\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 1," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" selected style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }
                    out.print("                                                             <td style=\"text-align: center;\">\n"
                            + "                                                                <button onclick=\"detail(" + list.get(i).getId() + ")\" type=\"button\" class=\"btn btn-icon\" type=\"button\" id=\"btn-add-setting\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>\n"
                            + "                                                        </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\" col-md-12 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageStatus(" + status + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageStatus(" + status + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageStatus(" + status + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageStatus(" + status + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageStatus(" + status + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("update-status")) {
            int status = Integer.parseInt(request.getParameter("status"));
            int id = Integer.parseInt(request.getParameter("id"));
            int index = Integer.parseInt(request.getParameter("index"));

            response.setContentType("text/html;charset=UTF-8");

            int end = subjectSettingDB.count();
            if (end % 10 != 0) {
                end = end / 10;
                end++;
            } else {
                end = end / 10;
            }

            subjectSettingDB.updateSettingStatus(status, id);

            list = subjectSettingDB.listPagination(index);
            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"myList\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                        <h4 class=\"card-title\">Subject Setting List</h4>\n"
                        + "                                        <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                        <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                    <tr>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Subject Code</th>\n"
                        + "                                                        <th>Setting Type</th>\n"
                        + "                                                        <th>Setting Title</th>\n"
                        + "                                                        <th>Setting Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                    </tr>\n"
                        + "                                                </thead>\n"
                        + "                                                <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td>" + list.get(i).getDisplay_order() + "</td>\n");
                    out.print("<td style=\"text-align: center;\" >" + list.get(i).getSubject_id().getSubject_code() + "</td>");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_id()).equals(t)) {
                            out.print("                                                 <td>" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td>" + list.get(i).getSetting_title() + "</td>\n"
                            + "                                                    <td>" + list.get(i).getSetting_value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-danger btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 0," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" selected=\"\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 1," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" selected style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }
                    out.print("                                                             <td style=\"text-align: center;\">\n"
                            + "                                                                <button onclick=\"detail(" + list.get(i).getId() + ")\" type=\"button\" class=\"btn btn-icon\" type=\"button\" id=\"btn-add-setting\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>\n"
                            + "                                                        </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\" col-md-12 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + status + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + status + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + status + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + status + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + status + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("search-by-subject-code")) {
            int subject_code = Integer.parseInt(request.getParameter("value"));
            int index = Integer.parseInt(request.getParameter("index"));

            response.setContentType("text/html;charset=UTF-8");
            ArrayList<Subject> subjectList = subjectDB.list();

            int end = subjectSettingDB.countSubject(subject_code);
            if (end % 10 != 0) {
                end = end / 10;
                end++;
            } else {
                end = end / 10;
            }

            if (subject_code == 0) {
                list = subjectSettingDB.listPagination(index);
            } else {
                list = subjectSettingDB.searchBySubjectCode(subject_code, index);
            }

            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"col-md-12 grid-margin stretch-card \" id=\"mySearch\">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchBySubjectCode(this,1)\" id=\"search-type\" style=\"height: 48px\">\n");
                if (subject_code == 0) {
                    out.print("                                            <option value=\"0\" selected>All Subject Code</option>\n");
                } else {
                    out.print("                                            <option value=\"0\">All Subject Code</option>\n");
                }
                subjectList.forEach((s) -> {
                    if (s.getId() == subject_code) {
                        out.print("<option selected value=\"" + s.getId() + "\">" + s.getSubject_code() + "</option>");
                    } else {
                        out.print("<option value=\"" + s.getId() + "\">" + s.getSubject_code() + "</option>");
                    }
                });
                out.print("                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\"> \n"
                        + "<option value=\"0\">All Setting Type</option>");

                listType.forEach((t) -> {
                    out.print("                                        <option value=\"" + t + "\">" + session.getAttribute(t) + "</option>\n");
                });
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("                                <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                            <option value=\"3\">All status</option>\n"
                        + "                                            <option value=\"0\">Inactive</option>\n"
                        + "                                            <option value=\"1\">Active</option>\n"
                        + "                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n");
                out.print("                            <div class=\"col-md-4\" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group\">\n"
                        + "                                            <input type=\"text\" class=\"form-control\" id=\"input-search-name\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark\" id=\"btn-search-name\" onclick=\"searchByTitle()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                            </div>\n"
                        + "                                <div class=\"col-md-1\">\n"
                        + "                                    <button class=\"btn btn-outline-secondary text-dark\" onclick=\"add()\" type=\"button\" data-modal-toggle=\"defaultModal\" id=\"btn-add-setting\" >\n"
                        + "                                        <i class=\"ti-plus\"></i>\n"
                        + "                                    </button>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"myList\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                        <h4 class=\"card-title\">Subject Setting List</h4>\n"
                        + "                                        <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                        <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                    <tr>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Subject Code</th>\n"
                        + "                                                        <th>Setting Type</th>\n"
                        + "                                                        <th>Setting Title</th>\n"
                        + "                                                        <th>Setting Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                    </tr>\n"
                        + "                                                </thead>\n"
                        + "                                                <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                   <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getDisplay_order() + "</td>\n");
                    out.print("<td style=\"text-align: center;\" >" + list.get(i).getSubject_id().getSubject_code() + "</td>");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_id()).equals(t)) {
                            out.print("                                                 <td>" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td>" + list.get(i).getSetting_title() + "</td>\n"
                            + "                                                    <td>" + list.get(i).getSetting_value() + "</td>\n");

                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-danger btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 0," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" selected=\"\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                        <td style=\"text-align: center;\">\n"
                                + "                                                                    <select id=\"my-status\" class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getId() + ", this, 1," + index + ")\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"0\" style=\"background: white; color:#F73122;\"><label >In Active</label></option>\n"
                                + "                                                                        <option  value=\"1\" selected style=\"background: white; color:#6fb5ab;\"><label> Active</label></option>\n"
                                + "                                                                    </select>\n"
                                + "                                                                </td>\n");
                    }
                    out.print("                                                             <td style=\"text-align: center;\">\n"
                            + "                                                                <button onclick=\"detail(" + list.get(i).getId() + ")\" type=\"button\" class=\"btn btn-icon\" type=\"button\" id=\"btn-add-setting\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>\n"
                            + "                                                        </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\" col-md-12 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageSubject(" + subject_code + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageSubject(" + subject_code + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageSubject(" + subject_code + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageSubject(" + subject_code + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageSubject(" + subject_code + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            int subject_id = Integer.parseInt(request.getParameter("subject-code"));
            int type = Integer.parseInt(request.getParameter("type-id"));
            String setting_title = request.getParameter("setting-title");
            String setting_value = request.getParameter("setting-value");
            int order = Integer.parseInt(request.getParameter("display-order"));
            int status = Integer.parseInt(request.getParameter("setting-status"));

            Subject subject = new Subject();
            subject.setId(subject_id);

            SubjectSetting s = new SubjectSetting();
            s.setId(id);
            s.setSubject_id(subject);
            s.setType_id(type);
            s.setSetting_title(setting_title);
            s.setSetting_value(setting_value);
            s.setDisplay_order(order);
            s.setStatus(status);

            subjectSettingDB.updateSubjectSetting(s);
            response.sendRedirect("subject-setting");
        } else if (go.equals("add")) {
            int subject_id = Integer.parseInt(request.getParameter("subject-code"));
            int type = Integer.parseInt(request.getParameter("type-id"));
            String setting_title = request.getParameter("setting_title");
            String setting_value = request.getParameter("setting_value");
            int order = Integer.parseInt(request.getParameter("display_order"));
            int status = Integer.parseInt(request.getParameter("status"));

            Subject subject = new Subject();
            subject.setId(subject_id);

            SubjectSetting s = new SubjectSetting();
            s.setSubject_id(subject);
            s.setType_id(type);
            s.setSetting_title(setting_title);
            s.setSetting_value(setting_value);
            s.setDisplay_order(order);
            s.setStatus(status);

            subjectSettingDB.addSubjectSetting(s);
            response.sendRedirect("subject-setting");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
