/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.SettingDao;
import dal.UserDao;

import dal.SettingDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.GapContent;
import model.Setting;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author pallgree
 */
public class SettingController extends HttpServlet {

    SettingDao dao = new SettingDao();
    UserDao dao1 = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        ArrayList<String> listType = dao.viewAllType();
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
            }
        }
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao1.getUserByEmail(email);
        request.setAttribute("user", user);
        String go = request.getParameter("go");
        int end = dao.count();
        if (go == null) {
            go = "setting-list";
        }
        if (go.equals("setting-list")) {
            int Index = 1;
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
            ArrayList<Setting> list = dao.list(Index);
            request.setAttribute("index", Index);
            request.setAttribute("end", end);
            request.setAttribute("list", list);
            request.setAttribute("listType", listType);
            request.setAttribute("listSettingType", listSettingType);
            dispath(request, response, "/template/admin/list/Setting.jsp");
        } else if (go.equals("detail")) {
            int id = Integer.parseInt(request.getParameter("setting_id"));
            Setting s = dao.searchSetting(id);
            request.setAttribute("setting", s);
            request.setAttribute("listSettingType", listSettingType);
            request.getRequestDispatcher("/template/admin/detail/SettingDetail.jsp").forward(request, response);
        } else if (go.equals("add")) {
            request.setAttribute("listSettingType", listSettingType);
            request.getRequestDispatcher("/template/admin/add/SettingAdd.jsp").forward(request, response);

        } else if (go.equals("paging")) {
            int index = Integer.parseInt(request.getParameter("index"));
            ArrayList<Setting> list = dao.list(index);

            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
            try (PrintWriter out = response.getWriter()) {

                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                            <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"search-by-type form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\">\n"
                        + "  <option value=\"0\">  All Setting Type</option>\n");

                for (String t : listType) {
                    if (session.getAttribute(t) != null) {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }

                }
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <select class=\"search-by-status form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"3\">All status</option>\n"
                        + "                                        <option value=\"0\">Inactive</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value =\"2\">Deactive</option>\n"
                        + "\n"
                        + "                                    </select>\n"
                        + "                                </div>");
                out.print("<div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group \">\n"
                        + "                                            <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search-name\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"searchName()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <button  class=\"add-btn btn btn-outline-secondary text-dark col-md-1\" id=\"btn-add-setting\" onclick=\"add()\">\n"
                        + "                                    <i class=\"ti-plus\"></i>\n"
                        + "                                </button>\n"
                        + "\n"
                        + "                            </div>");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">Setting List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                     <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Setting Type</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Name</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getSetting_Id() + "</td>\n");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_Id()).equals(t)) {
                            out.print("                                                 <td style=\"text-align: center;\">" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td style=\"text-align: center;\">" + list.get(i).getSetting_Name() + "</td>\n"
                            + "                                                    <td style=\"text-align: center;\">" + list.get(i).getOrder() + "</td>\n"
                            + "                                                   <td style=\"text-align: center;\">" + list.get(i).getSetting_Value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,0,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\" selected style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,1,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" selected style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    if (list.get(i).getStatus() == 2) {
                        out.print("                                                         <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,2,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" selected style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    out.print("<td style=\"text-align: center;\">\n"
                            + "                                                                <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail(" + list.get(i).getSetting_Id() + ")\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>");
                    out.print("                                                </tr>\n");
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
                        + "                        </div>");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        ServletConfig config = getServletConfig();
        HttpSession session = request.getSession();
        ArrayList<String> listType = dao.viewAllType();
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
        if (go.equals("edit")) {
            int setting_id = Integer.parseInt(request.getParameter("id"));
            String setting_name = request.getParameter("setting_name");   
            String order = request.getParameter("setting_order");
            String type_id = request.getParameter("setting_type");
            String value = request.getParameter("setting_value");
            String note = request.getParameter("setting_note");
            String status = request.getParameter("setting_status");
            int Type_id = Integer.parseInt(type_id);
            int Order = Integer.parseInt(order);
            int Status = Integer.parseInt(status);
            Setting s = new Setting(setting_id, setting_name, value, Type_id, Order, Status, note);
            int n = dao.updateSetting(s);
            response.sendRedirect("setting");
        } else if (go.equals("setting-search-by-type")) {
            int type = Integer.parseInt(request.getParameter("value"));
            int index = Integer.parseInt(request.getParameter("index"));
            response.setContentType("text/html;charset=UTF-8");
            ArrayList<Setting> list = new ArrayList<>();
            try (PrintWriter out = response.getWriter()) {
                if (type == 0) {
                    list = dao.list();
                } else {
                    list = dao.searchByType(type, index);
                }
                int end = dao.counttype(type);
                if (end % 6 != 0) {
                    end = end / 6;
                    end++;
                } else {
                    end = end / 6;
                }
                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                            <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"search-by-type form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\">\n");
                if (type == 0) {
                    out.print("                                <option value=\"0\" selected>All Setting Type</option>\n");
                } else {
                    out.print("                                <option value=\"0\" >All Setting Type</option>\n");
                }
                for (String t : listType) {

                    if (String.valueOf(type).equals(t)) {
                        out.print("                                        <option value=\"" + t + "\" selected >" + session.getAttribute(t) + "</option>\n");
                    } else {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }
                }
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <select class=\"search-by-status form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"3\">All status</option>\n"
                        + "                                        <option value=\"0\">Inactive</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value =\"2\">Deactive</option>\n"
                        + "\n"
                        + "                                    </select>\n"
                        + "                                </div>");
                out.print("<div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group \">\n"
                        + "                                            <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search-name\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"searchName()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <button  class=\"add-btn btn btn-outline-secondary text-dark col-md-1\" id=\"btn-add-setting\" onclick=\"add()\">\n"
                        + "                                    <i class=\"ti-plus\"></i>\n"
                        + "                                </button>\n"
                        + "\n"
                        + "                            </div>");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">Setting List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                     <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Setting Type</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Name</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getSetting_Id() + "</td>\n");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_Id()).equals(t)) {
                            out.print("                                                 <td style=\"text-align: center;\">" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td style=\"text-align: center;\">" + list.get(i).getSetting_Name() + "</td>\n"
                            + "                                                    <td style=\"text-align: center;\">" + list.get(i).getOrder() + "</td>\n"
                            + "                                                   <td style=\"text-align: center;\">" + list.get(i).getSetting_Value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,0,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\" selected style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,1,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" selected style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    if (list.get(i).getStatus() == 2) {
                        out.print("                                                         <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,2,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" selected style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    out.print("<td style=\"text-align: center;\">\n"
                            + "                                                                <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail(${s.getSetting_Id()})\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>");
                    out.print("                                                </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagetype(" + type + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagetype(" + type + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagetype(" + type + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagetype(" + type + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pagetype(" + type + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("setting-search-by-name")) {
            String setting_name = request.getParameter("setting_name");
            int index = Integer.parseInt(request.getParameter("index"));
            ArrayList<Setting> list = new ArrayList<>();
            list = dao.searchSettingByName(setting_name, index);
            int end = dao.count(setting_name);
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
//            if(list.size()== 0){
//                
//            }
            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"row\" id=\"list\">\n"
                        + "                            <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"search-by-type form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\">\n"
                        + "                                            <option value=\"0\">All Setting Type</option>\n");
                for (String t : listType) {
                    if (session.getAttribute(t) != null) {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }

                }
                out.print("                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <select class=\"search-by-status form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"3\">All status</option>\n"
                        + "                                        <option value=\"0\">Inactive</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value =\"2\">Deactive</option>\n"
                        + "\n"
                        + "                                    </select>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group \">\n"
                        + "                                            <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search-name\" style=\"height: 48px;\" value=\"" + setting_name + "\">\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"searchName()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <button  class=\"add-btn btn btn-outline-secondary text-dark col-md-1\" id=\"btn-add-setting\" onclick=\"add()\">\n"
                        + "                                    <i class=\"ti-plus\"></i>\n"
                        + "                                </button>\n"
                        + "\n"
                        + "                            </div>");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">Setting List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                     <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Setting Type</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Name</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getSetting_Id() + "</td>\n");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_Id()).equals(t)) {
                            out.print("                                                 <td style=\"text-align: center;\">" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td style=\"text-align: center;\">" + list.get(i).getSetting_Name() + "</td>\n"
                            + "                                                    <td style=\"text-align: center;\">" + list.get(i).getOrder() + "</td>\n"
                            + "                                                   <td style=\"text-align: center;\">" + list.get(i).getSetting_Value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,0,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\" selected style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,1,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" selected style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    if (list.get(i).getStatus() == 2) {
                        out.print("                                                         <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,2,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" selected style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    out.print("<td style=\"text-align: center;\">\n"
                            + "                                                                <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail(" + list.get(i).getSetting_Id() + ")\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>");
                    out.print("                                                </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageName(" + setting_name + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageName(" + setting_name + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageName(" + setting_name + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageName(" + setting_name + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"pageName(" + setting_name + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("setting-search-by-status")) {
            int status = Integer.parseInt(request.getParameter("value"));
            int index = Integer.parseInt(request.getParameter("index"));
            response.setContentType("text/html;charset=UTF-8");
            ArrayList<Setting> list = new ArrayList<>();
            if (status == 3) {
                list = dao.list();
            } else {
                list = dao.searchByStatus(status, index);
            }
            int end = dao.count(status);
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }

            try (PrintWriter out = response.getWriter()) {
                out.print("<div class=\"row\" id=\"list\">\n"
                        + "                            <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"search-by-type form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\">\n"
                        + "                                            <option value=\"0\">All Setting Type</option>\n");
                for (String t : listType) {
                    if (session.getAttribute(t) != null) {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }

                }
                out.print("                                        </select>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <select class=\"search-by-status form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"3\">All status</option>\n"
                        + "                                        <option value=\"0\">Inactive</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value =\"2\">Deactive</option>\n"
                        + "                                    </select>\n"
                        + "                                </div>\n" + "<div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group \">\n"
                        + "                                            <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search-name\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"searchName()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <button  class=\"add-btn btn btn-outline-secondary text-dark col-md-1\" id=\"btn-add-setting\" onclick=\"add()\">\n"
                        + "                                    <i class=\"ti-plus\"></i>\n"
                        + "                                </button>\n"
                        + "\n"
                        + "                            </div>");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">Setting List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                     <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Setting Type</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Name</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getSetting_Id() + "</td>\n");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_Id()).equals(t)) {
                            out.print("                                                 <td style=\"text-align: center;\">" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td style=\"text-align: center;\">" + list.get(i).getSetting_Name() + "</td>\n"
                            + "                                                    <td style=\"text-align: center;\">" + list.get(i).getOrder() + "</td>\n"
                            + "                                                   <td style=\"text-align: center;\">" + list.get(i).getSetting_Value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,0,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\" selected style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,1,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" selected style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    if (list.get(i).getStatus() == 2) {
                        out.print("                                                         <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,2,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" selected style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    out.print("<td style=\"text-align: center;\">\n"
                            + "                                                                <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail(" + list.get(i).getSetting_Id() + ")\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>");
                    out.print("                                                </tr>\n");
                }
                out.print("                                 </tbody>\n"
                        + "                                    </table>\n"
                        + "                                </div>\n"
                        + "                            <div class=\"row\">\n"
                        + "                                            <div class=\"col-md-10\"></div>\n"
                        + "                                            <div class=\" col-md-2 template-demo\">\n"
                        + "                                                <div class=\"btn-group \" role=\"group\" aria-label=\"Basic example\" >\n");

                if (index - 1 > 1) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"page(" + status + "," + 1 + ")\">First</button>\n");
                }
                for (int i = (index - 1); i < index; i++) {
                    if (i >= 1) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"page(" + status + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"page(" + status + "," + index + ")\">" + index + "</button>\n");
                for (int i = (index + 1); i <= index + 1; i++) {
                    if (i <= end) {
                        out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"page(" + status + "," + i + ")\">" + i + "</button>\n");

                    }
                }
                if (index + 1 < end) {
                    out.print("                                                    <button type=\"button\" class=\"btn btn-outline-secondary text-dark \" onclick=\"page(" + status + "," + end + ")\">Last</button>\n");

                }
                out.print("                                                </div>\n"
                        + "                                            </div>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </div>");
            }
        } else if (go.equals("setting-update-status")) {
            int status = Integer.parseInt(request.getParameter("status"));
            int id = Integer.parseInt(request.getParameter("id"));
            int index = Integer.parseInt(request.getParameter("index"));
            response.setContentType("text/html;charset=UTF-8");
            ArrayList<Setting> list = new ArrayList<>();
            int n = dao.updateSettingStatus(status, id);
            list = dao.list(index);
            int end = dao.count();
            if (end % 6 != 0) {
                end = end / 6;
                end++;
            } else {
                end = end / 6;
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(" <div class=\"row\" id=\"list\">\n"
                        + "                            <div class=\"col-md-12 grid-margin stretch-card \">\n"
                        + "                                <div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <div>\n"
                        + "                                        <select class=\"search-by-type form-control\" onchange=\"searchByType(this,1)\" id=\"search-type\" style=\"height: 48px\">\n"
                        + "  <option value=\"0\">  All Setting Type</option>\n");
                for (String t : listType) {
                    if (session.getAttribute(t) != null) {
                        out.print("                                        <option value=\"" + t + "\"  >" + session.getAttribute(t) + "</option>\n");
                    }

                }
                out.print("                                    </select>\n"
                        + "                                </div>\n"
                        + "                            </div>\n");
                out.print("<div class=\"col-md-2\" style=\"margin-right: 30px\">\n"
                        + "                                    <select class=\"search-by-status form-control\" onchange=\"searchByStatus(this,1)\" id=\"search-status\" style=\"height: 48px\"> \n"
                        + "                                        <option value=\"3\">All status</option>\n"
                        + "                                        <option value=\"0\">Inactive</option>\n"
                        + "                                        <option value=\"1\">Active</option>\n"
                        + "                                        <option value =\"2\">Deactive</option>\n"
                        + "\n"
                        + "                                    </select>\n"
                        + "                                </div>");
                out.print("<div class=\"col-md-6 \" style=\"margin-right: 10px\">\n"
                        + "                                    <div class=\"form-group\" style=\"height: 20px\">\n"
                        + "                                        <div class=\"input-group \">\n"
                        + "                                            <input type=\"text\" class=\"form-control col-md-8\" id=\"input-search-name\" style=\"height: 48px;\" >\n"
                        + "                                            <button class=\"btn btn-outline-secondary text-dark col-md-1\" id=\"btn-search-name\" onclick=\"searchName()\" ><i class=\"ti-search\" style=\"height: 20px\"></i></button>\n"
                        + "                                        </div>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <button  class=\"add-btn btn btn-outline-secondary text-dark col-md-1\" id=\"btn-add-setting\" onclick=\"add()\">\n"
                        + "                                    <i class=\"ti-plus\"></i>\n"
                        + "                                </button>\n"
                        + "\n"
                        + "                            </div>");
                out.print("<div class=\"col-md-12 grid-margin stretch-card\" id=\"table\">\n"
                        + "                                <div class=\"card\">\n"
                        + "                                    <div class=\"card-body\">\n"
                        + "                                    <h4 class=\"card-title\">Setting List</h4>\n"
                        + "                                    <p class=\"card-description\">\n"
                        + "                                        </p>\n"
                        + "                                    <div class=\"table-responsive\">\n"
                        + "                                            <table class=\"table table-hover\">\n"
                        + "                                                <thead>\n"
                        + "                                                <tr>\n"
                        + "                                                     <th style=\"text-align: center;\" >ID</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Setting Type</th>\n"
                        + "                                                        <th style=\"text-align: center;\" >Name</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Order</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Value</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Status</th>\n"
                        + "                                                        <th style=\"text-align: center;\">Detail</th>\n"
                        + "                                                </tr>\n"
                        + "                                            </thead>\n"
                        + "                                            <tbody>");
                for (int i = 0; i < list.size(); i++) {
                    out.print("                                                <tr>\n"
                            + "                                                 <td style=\"text-align: center;\">" + list.get(i).getSetting_Id() + "</td>\n");
                    for (String t : listType) {
                        if (String.valueOf(list.get(i).getType_Id()).equals(t)) {
                            out.print("                                                 <td style=\"text-align: center;\">" + session.getAttribute(t) + "</td>\n");
                        }
                    }
                    out.print("                                                    <td style=\"text-align: center;\">" + list.get(i).getSetting_Name() + "</td>\n"
                            + "                                                    <td style=\"text-align: center;\">" + list.get(i).getOrder() + "</td>\n"
                            + "                                                   <td style=\"text-align: center;\">" + list.get(i).getSetting_Value() + "</td>\n");
                    if (list.get(i).getStatus() == 0) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,0,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\" selected style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }

                    if (list.get(i).getStatus() == 1) {
                        out.print("                                                       <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,1,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" selected style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    if (list.get(i).getStatus() == 2) {
                        out.print("                                                         <td style=\"text-align: center;\">\n"
                                + "                                                                <select class=\"btn btn-inverse-success btn-fw\" onchange=\"changeStatus(" + list.get(i).getSetting_Id() + ", this,2,1)\" style=\"text-align: center; border-radius: 20px;\" id=\"status-change\">\n"
                                + "                                                                    <option value=\"0\"  style=\"color: #F95F53;\" ><label >Inactive</label></option>\n"
                                + "                                                                    <option value=\"1\" style=\"background: white; color:#1FBEFF;\" ><label> Active</label></option>\n"
                                + "                                                                    <option value=\"2\" selected style=\"background: white; color:#F73122;\" ><label>Deactive</label></option>\n"
                                + "                                                                </select>\n"
                                + "                                                            </td>");
                    }
                    out.print("<td style=\"text-align: center;\">\n"
                            + "                                                                <button type=\"button\" class=\"btn btn-icon\" type=\"button\" data-modal-toggle=\"defaultModal_detail\" onclick=\"detail(" + list.get(i).getSetting_Id() + ")\">\n"
                            + "                                                                    <i class=\"mdi mdi-format-list-bulleted\"></i>\n"
                            + "                                                                </button>\n"
                            + "                                                            </td>");
                    out.print("                                                </tr>\n");
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
                        + "                        </div>");
            }
        } else if (go.equals("add")) {
            String setting_name = request.getParameter("setting_name");
            String order = request.getParameter("setting_order");
            String type_id = request.getParameter("setting_type");
            String value = request.getParameter("setting_value");
            String note = request.getParameter("setting_note");
            String status = request.getParameter("status");
            int Type_id = Integer.parseInt(type_id);
            int Order = Integer.parseInt(order);
            int Status = Integer.parseInt(status);
            Setting s = new Setting(setting_name, value, Type_id, Order, Status, note);
            int n = dao.addSetting(s);
            response.sendRedirect("setting");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public void dispath(HttpServletRequest request, HttpServletResponse response, String page) throws IOException, ServletException {
        RequestDispatcher dispath = request.getRequestDispatcher(page);
        dispath.forward(request, response);

    }

}
