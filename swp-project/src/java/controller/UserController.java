/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

public class UserController extends HttpServlet {

    UserDao dao = new UserDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ArrayList<String> listUserRole = dao.viewAllRole();
        ServletConfig config = getServletConfig();
        HttpSession session = request.getSession();
        ArrayList<String> listRole = new ArrayList<>();
        String name_role = "";
        int count = 2;
        for (int i = 1; i < count; i++) {
            name_role = config.getInitParameter(String.valueOf(i));
            if (name_role != null) {
                listRole.add(String.valueOf(i));
                session.setAttribute(String.valueOf(i), name_role);
                count++;
            }
        }
        for (String obj : listUserRole) {
            name_role = config.getInitParameter(obj);
            if (name_role != null) {
                session.setAttribute(obj, name_role);
                count++;
            }
        }
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao.getUserByEmail(email);
        request.setAttribute("user", user);
        String go = request.getParameter("go");
        if (go == null) {
            go = "user-list";
        }
        if (go.equals("user-list")) {
            ArrayList<User> list = dao.listUser(1);
            for(int i=0;i<list.size();i++){
                Long l = Long.parseLong(list.get(i).getTime_Create_Token());
                Date d = new Date(l);
                Format format = new SimpleDateFormat("HH:mm:ss  dd/MM/yyy");
                String s = format.format(d).toString();
                list.get(i).setTime_Create_Token(s);
            }
            int Index = 1;
            int end = dao.count();
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
            request.setAttribute("list", list);
            request.setAttribute("listUserRole", listUserRole);
            request.setAttribute("listRole", listRole);
             request.setAttribute("index", Index);
            request.setAttribute("end", end);
            dispath(request, response, "/template/admin/list/User.jsp");
        } else if (go.equals("detail")) {
            int id = Integer.parseInt(request.getParameter("id"));
            User u = dao.searchUser(id);  
            request.setAttribute("u", u);
            request.setAttribute("listRole", listRole);
            dispath(request, response, "/template/admin/detail/UserDetail.jsp");
    }else if(go.equals("paging")){
        int index = Integer.parseInt(request.getParameter("index"));
           ArrayList<User> list = dao.listUser(index);

            int end = dao.count();
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                        <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByRole(this,1)\" id=\"search-type\" style=\"height: 48px\">\n");
                out.print("<option value=\"0\" >All Role</option>\n");
                for (String t : listUserRole) {
                    out.print("<option value=\"" + t + "\" >" + session.getAttribute(t) + "</option>\n");
                }
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"2\">All status</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value=\"0\">Deactive</option>\n"
                        + "                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                    <div class=\"input-group \">\n"
                        + "\n"
                        + "                                        <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search\" style=\"height: 48px;\" >\n"
                        + "                                        <select class=\"form-control col-md-2\" id=\"choice-search\" style=\"height: 48px\"> \n"
                        + "                                            <option value=\"0\">Name</option>\n"
                        + "                                            <option value=\"1\">Phone number</option>\n"
                        + "                                            <option value=\"2\">Mail</option>\n"
                        + "                                        </select>\n"
                        + "                                        <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"search()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "\n"
                        + "                        </div>\n"
                        + "                       <div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                            <div class=\"card\">\n"
                        + "                                <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">User List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                    </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                        <table class=\"table table-hover\">\n"
                        + "                                            <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                    <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Name</th>\n"
                        + "                                                    <th style=\"text-align: center;\" >Email</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Mobile</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Role</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Last Login</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                    <tr>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getUser_id() + "</td>\n"
                            + "                                                        <td style=\"text-align: center;\">" + list.get(i).getFull_name() + "</td>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getEmail() + "</td>\n");
                    if (list.get(i).getPhone_number() != null) {
                        out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getPhone_number() + "</td>\n");
                    } else {
                        out.print("                                                        <td style=\"text-align: center;\" ></td>\n");
                    }
                    for (String t : listRole) {
                        if (String.valueOf(list.get(i).getRole_id()).equals(t)) {
                            out.print("<td style=\"text-align: center;\" >" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getTime_Create_Token() + "</td>\n" + "<td style=\"text-align: center;\">\n");
                    if (list.get(i).isStatus() == true) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-success btn-fw\"id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,true,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" selected=\"\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");

                    }
                    if (list.get(i).isStatus() == false) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-danger btn-fw\" id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,false,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" selected=\"\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");
                    }
                    out.print("                                                        </td>\n"
                            + "                                                        <td style=\"text-align: center;\">\n"
                            + "                                                            <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail("+ list.get(i).getUser_id() +")\">\n" +
"                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n" +
"                                                                </button>"
                            + "                                                        </td>\n"
                            + "                                                    </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + index + ")\">" + index + "</button>\n");
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
                        + "                        </div>");}
    }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        ArrayList<String> listUserRole = dao.viewAllRole();
        ServletConfig config = getServletConfig();
        HttpSession session = request.getSession();
        ArrayList<String> listRole = new ArrayList<>();
        String name_role = "";
        int count = 2;
        for (int i = 1; i < count; i++) {
            name_role = config.getInitParameter(String.valueOf(i));
            if (name_role != null) {
                listRole.add(String.valueOf(i));
                session.setAttribute(String.valueOf(i), name_role);
                count++;
            }
        }
        for (String obj : listUserRole) {
            name_role = config.getInitParameter(obj);
            if (name_role != null) {
                session.setAttribute(obj, name_role);
                count++;
            }
        }
        if(go.equals("edit")){
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String mobile = request.getParameter("mobile");
            String date_of_birth = request.getParameter("date_of_birth");
            int gender = Integer.parseInt(request.getParameter("gender"));
            int role = Integer.parseInt(request.getParameter("role"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            String address = request.getParameter("address");
            User u = new User(name, role,date_of_birth, status, mobile, address, gender);
            out.print(u);
            int n = dao.updateUserDetail(id, u);
            response.sendRedirect("user");
        }
        else if (go.equals("user-update-status")) {
            boolean status = Boolean.parseBoolean(request.getParameter("status"));
            int id = Integer.parseInt(request.getParameter("id"));
            response.setContentType("text/html;charset=UTF-8");
            ArrayList<User> list = new ArrayList<>();
            int n = dao.updateStatus(status, id);
            list = dao.listUser(1);
            for(int i=0;i<list.size();i++){
                Long l = Long.parseLong(list.get(i).getTime_Create_Token());
                Date d = new Date(l);
                Format format = new SimpleDateFormat("HH:mm:ss  dd/MM/yyy");
                String s = format.format(d).toString();
                list.get(i).setTime_Create_Token(s);
            }
            int index = 1;
            int end = dao.count();
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                            <div class=\"card\">\n"
                        + "                                <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">User List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                    </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                        <table class=\"table table-hover\">\n"
                        + "                                            <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                    <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Name</th>\n"
                        + "                                                    <th style=\"text-align: center;\" >Email</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Mobile</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Role</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Last Login</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                    <tr>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getUser_id() + "</td>\n"
                            + "                                                        <td>" + list.get(i).getFull_name() + "</td>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getEmail() + "</td>\n");
                    if (list.get(i).getPhone_number() != null) {
                        out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getPhone_number() + "</td>\n");
                    } else {
                        out.print("                                                        <td style=\"text-align: center;\" ></td>\n");
                    }
                    for (String t : listRole) {
                        if (String.valueOf(list.get(i).getRole_id()).equals(t)) {
                            out.print("<td style=\"text-align: center;\" >" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                        <td style=\"text-align: center;\" >"+list.get(i).getTime_Create_Token()+"</td>\n" + "<td style=\"text-align: center;\">\n");
                    if (list.get(i).isStatus() == true) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-success btn-fw\"id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,true,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" selected=\"\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");

                    }
                    if (list.get(i).isStatus() == false) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-danger btn-fw\" id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,false,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" selected=\"\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");
                    }
                    out.print("                                                        </td>\n"
                            + "                                                        <td style=\"text-align: center;\">\n"
                            + "                                                            <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail("+ list.get(i).getUser_id() +")\">\n" +
"                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n" +
"                                                                </button>"
                            + "                                                        </td>\n"
                            + "                                                    </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"paging(" + index + ")\">" + index + "</button>\n");
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
                        + "                        </div>");;

            }
        } else if (go.equals("search-by-role")) {
            int role_id = Integer.parseInt(request.getParameter("role_id"));
            int index = Integer.parseInt(request.getParameter("index"));
            ArrayList<User> list = new ArrayList<>();
            
            if (role_id == 0) {
                list = dao.listUser(1);
            } else {
                list = dao.searchByRole(role_id,index);
            }
            int end = dao.countrole(role_id);
                if (end % 6 != 0) {
                    end = end / 6;
                    end++;
                } else {
                    end = end / 6;
                }
            for(int i=0;i<list.size();i++){
                Long l = Long.parseLong(list.get(i).getTime_Create_Token());
                Date d = new Date(l);
                Format format = new SimpleDateFormat("HH:mm:ss  dd/MM/yyy");
                String s = format.format(d).toString();
                list.get(i).setTime_Create_Token(s);
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                        <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByRole(this,1)\" id=\"search-type\" style=\"height: 48px\">\n");
                if (role_id == 0) {
                    out.print("<option value=\"0\" selected>All Role</option>\n");
                } else {
                    out.print("<option value=\"0\" >All Role</option>\n");
                }
                for (String t : listUserRole) {

                    if (String.valueOf(role_id).equals(t)) {
                        out.print("                                        <option value=\"" + t + "\" selected >" + session.getAttribute(t) + "</option>\n");
                    } else {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }
                }
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"2\">All status</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value=\"0\">Deactive</option>\n"
                        + "                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                    <div class=\"input-group \">\n"
                        + "\n"
                        + "                                        <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search-name\" style=\"height: 48px;\" >\n"
                        + "                                        <select class=\"form-control col-md-2\" id=\"choice-search\" style=\"height: 48px\"> \n"
                        + "                                            <option value=\"0\">Name</option>\n"
                        + "                                            <option value=\"1\">Phone number</option>\n"
                        + "                                            <option value=\"2\">Mail</option>\n"
                        + "                                        </select>\n"
                        + "                                        <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"search()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "\n"
                        + "                        </div>\n"
                        + "                       <div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                            <div class=\"card\">\n"
                        + "                                <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">User List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                    </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                        <table class=\"table table-hover\">\n"
                        + "                                            <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                    <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Name</th>\n"
                        + "                                                    <th style=\"text-align: center;\" >Email</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Mobile</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Role</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Last Login</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                    <tr>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getUser_id() + "</td>\n"
                            + "                                                        <td>" + list.get(i).getFull_name() + "</td>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getEmail() + "</td>\n");
                    if (list.get(i).getPhone_number() != null) {
                        out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getPhone_number() + "</td>\n");
                    } else {
                        out.print("                                                        <td style=\"text-align: center;\" ></td>\n");
                    }
                    for (String t : listRole) {
                        if (String.valueOf(list.get(i).getRole_id()).equals(t)) {
                            out.print("<td style=\"text-align: center;\" >" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getTime_Create_Token() + "</td>\n" + "<td style=\"text-align: center;\">\n");
                    if (list.get(i).isStatus() == true) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-success btn-fw\"id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,true,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" selected=\"\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");

                    }
                    if (list.get(i).isStatus() == false) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-danger btn-fw\"id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,false,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" selected=\"\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");
                    }
                    out.print("                                                        </td>\n"
                            + "                                                        <td style=\"text-align: center;\">\n"
                            + "                                                            <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail("+ list.get(i).getUser_id() +")\">\n" +
"                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n" +
"                                                                </button>"
                            + "                                                        </td>\n"
                            + "                                                    </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagerole("+role_id+"," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagerole("+role_id+"," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagerole("+role_id+"," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagerole("+role_id+"," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagerole("+role_id+"," + end + ")\">Last</button>\n");

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
            int status = Integer.parseInt(request.getParameter("status"));
            int index = Integer.parseInt(request.getParameter("index"));
            ArrayList<User> list = new ArrayList<>();
            if (status == 2) {
                list = dao.listUser(1);
            } else if (status == 0) {
                list = dao.searchByStatus(false,index);
            } else if (status == 1) {
                list = dao.searchByStatus(true,index);
            }
            int end = dao.count(status);
                if (end % 6 != 0) {
                    end = end / 6;
                    end++;
                } else {
                    end = end / 6;
                }
            for(int i=0;i<list.size();i++){
                Long l = Long.parseLong(list.get(i).getTime_Create_Token());
                Date d = new Date(l);
                Format format = new SimpleDateFormat("HH:mm:ss  dd/MM/yyy");
                String s = format.format(d).toString();
                list.get(i).setTime_Create_Token(s);
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                        <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByRole(this,1)\" id=\"search-type\" style=\"height: 48px\">\n");
                out.print("<option value=\"0\" >All Role</option>\n");
                for (String t : listUserRole) {
                    out.print("<option value=\"" + t + "\" >" + session.getAttribute(t) + "</option>\n");
                }

                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n");
                if (status == 2) {
                    out.print("                          <option value=\"2\" selected>All status</option>\n"
                            + "                                        <option value=\"1\">Active</option>\n"
                            + "                                        <option value=\"0\">Deactive</option>\n");
                }
                if (status == 1) {
                    out.print("                          <option value=\"2\" >All status</option>\n"
                            + "                                        <option value=\"1\" selected >Active</option>\n"
                            + "                                        <option value=\"0\">Deactive</option>\n");
                }
                if (status == 0) {
                    out.print("                          <option value=\"2\" >All status</option>\n"
                            + "                                        <option value=\"1\" >Active</option>\n"
                            + "                                        <option value=\"0\"selected>Deactive</option>\n");
                }
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                    <div class=\"input-group \">\n"
                        + "\n"
                        + "                                        <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search\" style=\"height: 48px;\" >\n"
                        + "                                        <select class=\"form-control col-md-2\" id=\"choice-search\" style=\"height: 48px\"> \n"
                        + "                                            <option value=\"0\">Name</option>\n"
                        + "                                            <option value=\"1\">Phone number</option>\n"
                        + "                                            <option value=\"2\">Mail</option>\n"
                        + "                                        </select>\n"
                        + "                                        <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"search()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "\n"
                        + "                        </div>\n"
                        + "                       <div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                            <div class=\"card\">\n"
                        + "                                <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">User List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                    </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                        <table class=\"table table-hover\">\n"
                        + "                                            <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                    <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Name</th>\n"
                        + "                                                    <th style=\"text-align: center;\" >Email</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Mobile</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Role</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Last Login</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                    <tr>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getUser_id() + "</td>\n"
                            + "                                                        <td>" + list.get(i).getFull_name() + "</td>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getEmail() + "</td>\n");
                    if (list.get(i).getPhone_number() != null) {
                        out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getPhone_number() + "</td>\n");
                    } else {
                        out.print("                                                        <td style=\"text-align: center;\" ></td>\n");
                    }
                    for (String t : listRole) {
                        if (String.valueOf(list.get(i).getRole_id()).equals(t)) {
                            out.print("<td style=\"text-align: center;\" >" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                        <td style=\"text-align: center;\" >"+ list.get(i).getTime_Create_Token()+"</td>\n" + "<td style=\"text-align: center;\">\n");
                    if (list.get(i).isStatus() == true) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-success btn-fw\" id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,true,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" selected=\"\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");

                    }
                    if (list.get(i).isStatus() == false) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-danger btn-fw\" id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,false,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" selected=\"\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");
                    }
                    out.print("                                                        </td>\n"
                            + "                                                        <td style=\"text-align: center;\">\n"
                            + "                                                            <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail("+ list.get(i).getUser_id() +")\">\n" +
"                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n" +
"                                                                </button>"
                            + "                                                        </td>\n"
                            + "                                                    </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+status+"," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+status+"," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+status+"," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+status+"," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+status+"," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }

        } else if (go.equals("search")) {
            int type = Integer.parseInt(request.getParameter("type"));
            String text = request.getParameter("text");
            int index = Integer.parseInt(request.getParameter("index"));
            ArrayList<User> list = new ArrayList<>();
            list = dao.search(text, type,index);
             int end=0;
            if(type==0){
                   end = dao.countPhone(text);
                if (end % 6 != 0) {
                    end = end / 6;
                    end++;
                } else {
                    end = end / 6;
                }
            }
            if(type==1){
                   end = dao.countEmail(text);
                if (end % 6 != 0) {
                    end = end / 6;
                    end++;
                } else {
                    end = end / 6;
                }
            }
            if(type==2){
                  end = dao.count(text);
                if (end % 6 != 0) {
                    end = end / 6;
                    end++;
                } else {
                    end = end / 6;
                }
            }
            for(int i=0;i<list.size();i++){
                Long l = Long.parseLong(list.get(i).getTime_Create_Token());
                Date d = new Date(l);
                Format format = new SimpleDateFormat("HH:mm:ss  dd/MM/yyy");
                String s = format.format(d).toString();
                list.get(i).setTime_Create_Token(s);
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                        <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByRole(this,1)\" id=\"search-type\" style=\"height: 48px\">\n");
                out.print("<option value=\"0\" >All Role</option>\n");
                for (String t : listUserRole) {
                    out.print("<option value=\"" + t + "\" >" + session.getAttribute(t) + "</option>\n");
                }

                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-2\" style=\"margin-right: 30px;\">\n"
                        + "                                <div>\n"
                        + "                                    <select class=\"form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"2\">All status</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value=\"0\">Deactive</option>\n"
                        + "                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                    <div class=\"input-group \">\n"
                        + "\n"
                        + "                                        <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search\" style=\"height: 48px;\" value=\""+text+"\">\n"
                        + "                                        <select class=\"form-control col-md-2\" id=\"choice-search\" style=\"height: 48px\"> \n");
                if (type == 0) {
                    out.print("                                            <option value=\"0\" selected>Name</option>\n"
                            + "                                            <option value=\"1\">Phone number</option>\n"
                            + "                                            <option value=\"2\">Mail</option>\n");
                }
                if (type == 1) {
                    out.print("                                            <option value=\"0\" >Name</option>\n"
                            + "                                            <option value=\"1\"selected>Phone number</option>\n"
                            + "                                            <option value=\"2\">Mail</option>\n");
                }
                if (type == 2) {
                    out.print("                                  <option value=\"0\" >Name</option>\n"
                            + "                                            <option value=\"1\">Phone number</option>\n"
                            + "                                            <option value=\"2\"selected>Mail</option>\n");
                }
                out.print("                                        </select>\n"
                        + "                                        <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"search()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "\n"
                        + "                        </div>\n"
                        + "                       <div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                            <div class=\"card\">\n"
                        + "                                <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">User List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                    </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                        <table class=\"table table-hover\">\n"
                        + "                                            <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                    <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Name</th>\n"
                        + "                                                    <th style=\"text-align: center;\" >Email</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Mobile</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Role</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Last Login</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                    <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>\n");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                    <tr>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getUser_id() + "</td>\n"
                            + "                                                        <td>" + list.get(i).getFull_name() + "</td>\n"
                            + "                                                        <td style=\"text-align: center;\" >" + list.get(i).getEmail() + "</td>\n");
                    if (list.get(i).getPhone_number() != null) {
                        out.print("                                                        <td style=\"text-align: center;\" >" + list.get(i).getPhone_number() + "</td>\n");
                    } else {
                        out.print("                                                        <td style=\"text-align: center;\" ></td>\n");
                    }
                    for (String t : listRole) {
                        if (String.valueOf(list.get(i).getRole_id()).equals(t)) {
                            out.print("<td style=\"text-align: center;\" >" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                        <td style=\"text-align: center;\" >"+list.get(i).getTime_Create_Token()+"</td>\n" + "<td style=\"text-align: center;\">\n");
                    if (list.get(i).isStatus() == true) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-success btn-fw\" id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,true,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" selected=\"\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");

                    }
                    if (list.get(i).isStatus() == false) {
                        out.print(
                                "                                                                    <select class=\"btn btn-inverse-danger btn-fw\"id=\"status-change\" onchange=\"updateStatus(" + list.get(i).getUser_id() + ", this,false,1)\" style=\"text-align: center; border-radius: 20px;\">\n"
                                + "                                                                        <option  value=\"true\" href=\"#\" style=\"background: white; color:#1FBEFF;\">Active</option>\n"
                                + "                                                                        <option  value=\"false\" href=\"#\" selected=\"\" style=\"background: white; color:#F73122;\">Deactive</option>\n"
                                + "                                                                    </select>");
                    }
                    out.print("                                                        </td>\n"
                            + "                                                        <td style=\"text-align: center;\">\n"
                            + "                                                           <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail("+ list.get(i).getUser_id() +")\">\n" +
"                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n" +
"                                                                </button>"
                            + "                                                        </td>\n"
                            + "                                                    </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");
                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+text+","+type+"," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+text+","+type+"," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+text+","+type+"," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+text+","+type+"," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagestatus("+text+","+type+"," + end + ")\">Last</button>\n");

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

    public void dispath(HttpServletRequest request, HttpServletResponse response, String page) throws IOException, ServletException {
        RequestDispatcher dispath = request.getRequestDispatcher(page);
        dispath.forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
