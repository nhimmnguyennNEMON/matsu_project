/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.roleNameType;
import dal.EvaluationCriteriaDao;
import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Iteration;
import model.User;
import utils.CookieUtils;
import utils.Jwt;

/**
 *
 * @author admin
 */
public class EvaluationCriteriaController extends HttpServlet {

    UserDao dao1 = new UserDao();
    EvaluationCriteriaDao dao = new EvaluationCriteriaDao();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            if (user.getRole_id() == 1) {
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_order",  "asc");
                request.setAttribute("sort_weight",  "asc");
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems("", "", "");
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<model.EvaluationCriteria> list = dao.search("", "", "", 6, 0, "c.criteria_order asc,i.iteration_name asc, c.evaluation_weight asc");
                request.setAttribute("list", list);
                list = dao.listFilterSubjectCode("");
                request.setAttribute("listS", list);
                dispath(request, response, "/template/admin/list/EvaluationCriteria.jsp");
            } else if(user.getRole_id() == 2){
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                request.setAttribute("sort_name", "asc");
                request.setAttribute("sort_order",  "asc");
                request.setAttribute("sort_weight",  "asc");
                request.setAttribute("page", 1);
                int pageEnd = dao.countItems(String.valueOf(user.getId()), "", "");
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<model.EvaluationCriteria> list = dao.search(String.valueOf(user.getId()), "", "", 6, 0, "c.criteria_order asc,i.iteration_name asc, c.evaluation_weight asc");
                request.setAttribute("list", list);
                list = dao.listFilterSubjectCode(String.valueOf(user.getId()));
                request.setAttribute("listS", list);
                dispath(request, response, "/template/manager/list/EvaluationCriteria.jsp");
            }
        } else if (go.equals("add")) {
            ArrayList<model.EvaluationCriteria> list = new ArrayList<>();
            list = dao.listIteration();
            request.setAttribute("listI", list);
            dispath(request, response, "/template/admin/add/EvaluationCriteriaAdd.jsp");

        } else if (go.equals("detail")) {
            ArrayList<model.EvaluationCriteria> list = new ArrayList<>();
            list = dao.listIteration();
            request.setAttribute("listI", list);
            int n = Integer.parseInt(request.getParameter("id"));
            model.EvaluationCriteria c = dao.get(n);
            request.setAttribute("c", c);
            dispath(request, response, "/template/admin/detail/EvaluationCriteriaDetail.jsp");
        } else if (go.equals("active")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.active(id);
            response.sendRedirect("evaluation_criteria");
        } else if (go.equals("inactive")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.inactive(id);
            response.sendRedirect("evaluation_criteria");
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
        if (go == null) {
            go = "view";
        }
        if (go.equals("view")) {
            if (user.getRole_id() == 1) {
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                int page = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("page", page);
                int pageEnd = dao.countItems("", subject_code, status);
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                String sqlOrder = request.getParameter("string_sort");
                String sort_name = request.getParameter("sort_name");
                String sort_weight = request.getParameter("sort_weight");
                String sort_order = request.getParameter("sort_order");
                request.setAttribute("sort_name", sort_name);
                request.setAttribute("sort_order", sort_order);
                request.setAttribute("sort_weight", sort_weight);
                request.setAttribute("string_sql", sqlOrder);
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<model.EvaluationCriteria> list = dao.search("", subject_code, status, 6, (page - 1) * 6, sqlOrder);
                request.setAttribute("list", list);
                list = dao.listFilterSubjectCode("");
                request.setAttribute("listS", list);
                dispath(request, response, "/template/admin/list/EvaluationCriteria.jsp");
            }else if(user.getRole_id()==2){
                String subject_code = request.getParameter("subject_code");
                String status = request.getParameter("status");
                request.setAttribute("subject_code", subject_code);
                request.setAttribute("status", status);
                int page = Integer.parseInt(request.getParameter("page"));
                request.setAttribute("page", page);
                int pageEnd = dao.countItems(String.valueOf(user.getId()), subject_code, status);
                request.setAttribute("count", pageEnd);
                if (pageEnd % 6 == 0) {
                    pageEnd = (int) pageEnd / 6;
                } else {
                    pageEnd = (int) pageEnd / 6 + 1;
                }
                String sqlOrder = request.getParameter("string_sort");
                String sort_name = request.getParameter("sort_name");
                String sort_weight = request.getParameter("sort_weight");
                String sort_order = request.getParameter("sort_order");
                request.setAttribute("sort_name", sort_name);
                request.setAttribute("sort_order", sort_order);
                request.setAttribute("sort_weight", sort_weight);
                request.setAttribute("string_sql", sqlOrder);
                request.setAttribute("pageEnd", pageEnd);
                ArrayList<model.EvaluationCriteria> list = dao.search(String.valueOf(user.getId()), subject_code, status, 6, (page - 1) * 6, sqlOrder);
                request.setAttribute("list", list);
                list = dao.listFilterSubjectCode(String.valueOf(user.getId()));
                request.setAttribute("listS", list);
                dispath(request, response, "/template/manager/list/EvaluationCriteria.jsp");
            }
           
        } else if (go.equals("add")) {
            Iteration i = new Iteration();
            int iteration_id = Integer.parseInt(request.getParameter("iteration_id"));
            i.setId(iteration_id);
            int criteria_order = Integer.parseInt(request.getParameter("criteria_order"));
            int max_log = Integer.parseInt(request.getParameter("max_loc"));
            boolean status = Boolean.getBoolean(request.getParameter("status"));
            boolean team_evaluation = Boolean.getBoolean(request.getParameter("team_evaluation"));
            int subject_id = dao.searchSubject_id(iteration_id);
            dao.insert(new model.EvaluationCriteria(i, team_evaluation, criteria_order, max_log, status));
            dao.updateAllEvaluationWeight(subject_id);
            response.sendRedirect("evaluation_criteria");
        } else if (go.equals("detail")) {
            Iteration i = new Iteration();
            int iteration_id = Integer.parseInt(request.getParameter("iteration_id"));
            i.setId(iteration_id);
            int criteria_id = Integer.parseInt(request.getParameter("criteria_id"));
            int criteria_order = Integer.parseInt(request.getParameter("criteria_order"));
            int max_loc = Integer.parseInt(request.getParameter("max_loc"));
            boolean status = Boolean.getBoolean(request.getParameter("status"));
            boolean team_evaluation = Boolean.getBoolean(request.getParameter("team_evaluation"));
            model.EvaluationCriteria e = new model.EvaluationCriteria();
            e.setCriteria_id(criteria_id);
            e.setCriteria_order(criteria_order);
            e.setMax_loc(max_loc);
            e.setStatus(status);
            e.setTeam_evaluation(team_evaluation);
            dao.update(e);
            int subject_id = dao.searchSubject_id(iteration_id);
            dao.updateAllEvaluationWeight(subject_id);
            response.sendRedirect("evaluation_criteria");
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
