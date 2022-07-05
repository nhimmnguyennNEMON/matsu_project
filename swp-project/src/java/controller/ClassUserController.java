/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dal.ClassDao;
import dal.ClassUserDao;
import dal.UserDao;
import dal.TeamDao;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.ClassUser;
import model.User;
import model.Class;
import model.Team;
import model.UserImport;
import org.apache.log4j.PropertyConfigurator;
import utils.CookieUtils;
import utils.Hash;
import utils.Jwt;
import utils.SendMail;
import utils.TwoFactorUtils;
import utils.env;
import utils.xlsxUtils;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 70,//70
        location = "/Users/pallgree/Desktop/g4/swp-project/web/assets/file-export-import/"
//location ="C:\\Users\\SY NGUYEN\\OneDrive\\Máy tính\\SWP_root\\SWP_Project_Clone_1_6_2022\\g4\\swp-project\\web\\assets\\images_upload"
)
public class ClassUserController extends HttpServlet {

    ClassUser model = new ClassUser();
    UserDao dao = new UserDao();
    ClassUserDao daoClassU = new ClassUserDao();

    ClassDao daoClass = new ClassDao();
    TeamDao daoTeam = new TeamDao();
    private final int ARBITARY_SIZE = 1048;
    String log4jConfigFile = env.USER_DIR_MAC + "/swp-project/web/WEB-INF/log4j.properties";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PropertyConfigurator.configure(log4jConfigFile);
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao.getUserByEmail(email);
        request.setAttribute("user", user);
        String _user_id = String.valueOf(user.id ) ;
            if(user.getRole_id()==1){
               _user_id=""; 
            }

        if (go.equals("view")) {
            
            ArrayList<Class> listClassCode = daoClass.listClassByTrainer2(_user_id);
            request.setAttribute("listClassCode", listClassCode);
            request.setAttribute("page", 1);
            int pageEnd = daoClassU.countItems("", "", "",_user_id);
            request.setAttribute("count", pageEnd);
            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
            request.setAttribute("sort_name","asc");
            request.setAttribute("sort_team","asc");
            request.setAttribute("sort_loc","asc");
            request.setAttribute("string_sql","d.team_name asc,b.full_name asc");
            ArrayList<ClassUser> list = daoClassU.search("", 6, 0, "", "","d.team_name asc,b.full_name asc",_user_id);
            request.setAttribute("list",list);
            request.getRequestDispatcher("/template/teacher/list/classuser.jsp").forward(request, response);
        } else if (go.equals("add")) {
            ArrayList<Class> listClassCode = daoClass.listClassByTrainer2(_user_id);
            request.setAttribute("listClassCode", listClassCode);
//            ArrayList<String> listTeam_Id = daoTeam.listTeamIdbyClassId(1);
//            request.setAttribute("listTeam_Id", listTeam_Id);
            request.getRequestDispatcher("/template/teacher/add/classuseradd.jsp").forward(request, response);
        } else if (go.equals("export-file")) {
            String class_code = request.getParameter("class_code");
            response.setContentType("text/plain");
            response.setHeader("Content-disposition", "attachment; filename=" + class_code + ".xlsx");
            try (InputStream in = request.getServletContext().getResourceAsStream("/assets/file-export-import/class-user.xlsx");
                    OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
        } else if (go.equals("write-file")) {
            String class_code = request.getParameter("class_code");
            String class_id = request.getParameter("class_id");
            ArrayList<ClassUser> list = daoClassU.search("", 100, 0, class_id,"","d.team_name asc,b.full_name asc",_user_id);
            xlsxUtils.writeFile(list);
            try {
                Thread.sleep(3000);
                response.sendRedirect("/swp-project/teacher/class-user?go=export-file&class_code=" + class_code);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClassUserController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (go.equals("import-file-view")) {
            request.setAttribute("table", -1);
            request.getRequestDispatcher("/template/teacher/import/classuserimport.jsp").forward(request, response);

        } else if (go.equals("down-load-teamplate")) {
            response.setContentType("text/plain");
            response.setHeader("Content-disposition", "attachment; filename=template_import_class_user.xlsx");
            try (InputStream in = request.getServletContext().getResourceAsStream("/assets/file-export-import/template_import_class_user.xlsx");
                    OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }

        }else if (go.equals("details")) {
            
            String class_id = request.getParameter("class_id");
            String team_id = request.getParameter("team_id");
            String user_id = request.getParameter("user_id");
            ClassUser  cu=daoClassU.get(class_id, team_id, user_id);
            request.setAttribute("item",cu);
            request.setAttribute("statusUpdate","0");
            request.getRequestDispatcher("/template/teacher/details/classuserdetails.jsp").forward(request, response);

        }
        else if (go.equals("update-status")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String class_id = request.getParameter("class_id"); 
            String team_id = request.getParameter("team_id");
            String user_id = request.getParameter("user_id");
            String status = request.getParameter("status");
            ClassUser cu = new ClassUser();
            cu.setClass_id(Integer.parseInt(class_id));
                cu.setTeam_id(Integer.parseInt(team_id));
                cu.setUser_id(Integer.parseInt(user_id));
                cu.setStatus(Integer.parseInt(status)); 
           int n = daoClassU.updateStatus(cu);
           System.out.println("n :"+n);
           String data = "{\"n\":"+n+"}";
           out.print(data);
           out.flush();
           
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PropertyConfigurator.configure(log4jConfigFile);
        String go = request.getParameter("go");
        String token = CookieUtils.getToken(request, response);
        String email = new Jwt().getEmailFromToken(token);
        User user = dao.getUserByEmail(email);
        request.setAttribute("user", user);
        String _user_id = String.valueOf(user.id ) ;
            if(user.getRole_id()==1){
               _user_id=""; 
            }
        if (go.equals("view")) {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("utf-8");
            ArrayList<Class> listClassCode = daoClass.listClassByTrainer2(_user_id);
            request.setAttribute("listClassCode", listClassCode);
            String key = request.getParameter("key");
            request.setAttribute("key", key);
            String class_id = request.getParameter("class_id");
            request.setAttribute("class_id", class_id);
            String status = request.getParameter("status");
            request.setAttribute("status", status);
            int page = Integer.parseInt(request.getParameter("page"));
            request.setAttribute("page", page);
            String sqlOrder = request.getParameter("string_sort");
            String sort_name = request.getParameter("sort_name");
            String sort_team = request.getParameter("sort_team");
            String sort_loc = request.getParameter("sort_loc");
            request.setAttribute("sort_name", sort_name);
            request.setAttribute("sort_team", sort_team);
            request.setAttribute("sort_loc", sort_loc);
            request.setAttribute("string_sql", sqlOrder);
            ArrayList<ClassUser> list = daoClassU.search(key, 6, (page - 1) * 6, class_id, status,sqlOrder,_user_id);
            int pageEnd = daoClassU.countItems(key, class_id, status,_user_id);
            request.setAttribute("count", pageEnd);
            if (pageEnd % 6 == 0) {
                pageEnd = (int) pageEnd / 6;
            } else {
                pageEnd = (int) pageEnd / 6 + 1;
            }
            request.setAttribute("pageEnd", pageEnd);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/template/teacher/list/classuser.jsp").forward(request, response);
        } else if (go.equals("load-team")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            int class_id = Integer.parseInt(request.getParameter("class_id"));
            ArrayList<Team> listTeam_Id = daoTeam.listTeamIdbyClassId2(class_id);
            String data = new Gson().toJson(listTeam_Id);
            out.print(data);
            out.flush();
        } else if (go.equals("load-student")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String class_id = request.getParameter("class_id");
            String team_id = request.getParameter("team_id");
            ArrayList<ClassUser> list = daoClassU.searchByClassIdAndTeamID(class_id, team_id, "1");
            System.out.println(list.toString());
            String data = new Gson().toJson(list);
            out.print(data);
            out.flush();
        } else if (go.equals("find-student-to-add")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            String _email = request.getParameter("email");
            String roll_number = request.getParameter("roll_number");
            String class_id = request.getParameter("class_id");
            UserDao daoUser = new UserDao();
            ArrayList<User> list = daoUser.searchInClassUser(roll_number, _email, name,class_id);
            String data = new Gson().toJson(list);
            out.print(data);
            out.flush();
        } else if (go.equals("insert-data")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String class_id = request.getParameter("class_id");
            String team_id = request.getParameter("team_id");
            String team_lead = request.getParameter("team_lead");
            String list = request.getParameter("list_user_id");
            list = list.substring(1, list.length() - 1);
            String[] listUserId = list.split(",");
            int done = 0, error = 0;
            for (String i : listUserId) {
                try {
                    if (team_lead == null) {
                        team_lead = "1";
                    }
                    ClassUser cu = new ClassUser();
                    cu.setClass_id(Integer.parseInt(class_id));
                    cu.setTeam_id(Integer.parseInt(team_id));
                    cu.setUser_id(Integer.parseInt(i));
                    cu.setStatus(1);
                    cu.setTeam_lead(Integer.parseInt(team_lead));
                    System.out.println(cu.toString());
                    if (daoClassU.insertCallBack(cu) == 1) {
                        done++;
                    } else {
                        error++;
                    }

                } catch (Exception e) {
                    error++;
                }
            }
            String data = "{\"done\": \"" + done + "\", \"error\": \"" + error + "\"}";
            out.print(data);
            out.flush();
        } else if (go.equals("import-file")) {
            Part part = request.getPart("file");
            String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            part.write(filename);
            String xlsx = "/Users/pallgree/Desktop/g4/swp-project/web/assets/file-export-import/" + filename;
            ArrayList<UserImport> list = xlsxUtils.readFile(xlsx);
            list.remove(0);
            request.setAttribute("table", 1);
            request.setAttribute("list", list);
            request.setAttribute("filename", filename);
            ArrayList<Class> listClassCode = daoClass.listClassCodebyUserId(user.id);
            request.setAttribute("listClassCode", listClassCode);
            request.getRequestDispatcher("/template/teacher/import/classuserimport.jsp").forward(request, response);

        } else if (go.equals("import-insert-xlsx")) {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String class_id = request.getParameter("class_id");
            String file_name = request.getParameter("file_name");
            String xlsx = "/Users/pallgree/Desktop/g4/swp-project/web/assets/file-export-import/" + file_name;
            ArrayList<UserImport> list = xlsxUtils.readFile(xlsx);
            list.remove(0);
            int done = 0, error = 0;
            for (UserImport i : list) {
                System.out.println(i.toString());
            }
            for (UserImport i : list) {
                try {
                    String team_name = i.getTeam().toUpperCase();

                    if (daoTeam.findByTeamName(team_name, class_id) != 1) {
                        // tạo team mới
                        System.out.println("create team");
                        Class c = new Class();
                        c.setId(Integer.parseInt(class_id));
                        
                        Team t = new Team();
                        t.setTeam_name(team_name);
                        t.setClass_id(c);
                        t.setTopic_code("TEST");
                        t.setTopic_name("This Is Test");
                        t.setGitlab_url("https://gitlab.com/");
                        t.setStatus(1);
                        t.setDesc("");
                        daoTeam.insert(t);
                    }
                    if (dao.findByEmail(i.getEmail()) != 1) {
                        // tạo user mới
                         System.out.println("create user");
                        SendMail mail = new SendMail();
                        User u = new User();
                        u.setFull_name(i.getFull_name());
                        u.setEmail(i.getEmail());
                        String new_pass = TwoFactorUtils.generateSecret(10);
                        mail.sentEmail(u.getEmail(),"Thông báo tạo tài khoản mới","Tài khoản của bạn được tạo tại Web MATSU EDU với account:"+u.getEmail()+", và mật khẩu là:"+new_pass+" ,vui lòng đăng nhập để thay đổi mật khẩu trong vòng 72h");
                        Hash hash = new Hash();
                        String hashPass = hash.hashPass(new_pass);
                        u.setPassword(hashPass);
                        int n = dao.insert2(u);
                    }

                    int team_id = daoTeam.getTeamIDfromTeamName(team_name, class_id);
                    User user1 = dao.getUserByEmail(i.getEmail());
                    ClassUser cu = new ClassUser();
                    cu.setClass_id(Integer.parseInt(class_id));
                    cu.setTeam_id(team_id);
                    cu.setUser_id(user1.id);
                    cu.setStatus(1);
                    if (i.isLeader() == true) {
                        cu.setTeam_lead(1);
                    } else {
                        cu.setTeam_lead(0);
                    }

                    if (daoClassU.insertCallBack(cu) == 1) {
                        done++;
                    } else {
                        error++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    error++;
                }
            }

           String data = "{\"done\": \"" + done + "\", \"error\": \"" + error + "\"}";
           out.print(data);
           out.flush();
            
        }else if (go.equals("details")) {
           int class_id =Integer.parseInt(request.getParameter("class_id"));
           int team_id  =Integer.parseInt(request.getParameter("team_id"));
           int user_id  = Integer.parseInt(request.getParameter("user_id"));
           int team_lead = Integer.parseInt(request.getParameter("team_lead"));
           String dropout_date = request.getParameter("dropout_date");
           String user_notes = request.getParameter("user_notes");
           String ongoing_eval = request.getParameter("ongoing_eval");
           String final_pres__eval = request.getParameter("final_pres_eval");
           String final_topic_eval = request.getParameter("final_topic_eval");
           String[] statusList = request.getParameterValues("status");
           int status;
           if(statusList[0].equals("0")) status= 0;
           else status =1;
                ClassUser cu= new ClassUser();
                cu.setClass_id(class_id);
                cu.setTeam_id(team_id);
                cu.setUser_id(user_id);
                cu.setTeam_lead(team_lead);
                cu.setDropout_date(dropout_date);
                cu.setUser_notes(user_notes);
                cu.setOngoing_eval(ongoing_eval);
                cu.setFinal_pres__eval(final_pres__eval);
                cu.setFinal_topic_eval(final_topic_eval);
                cu.setStatus(status);
            int n = daoClassU.updateCallBack(cu);
            if(n==1) request.setAttribute("statusUpdate", "1");
            else request.setAttribute("statusUpdate","-1");
            request.setAttribute("item",cu);
            request.getRequestDispatcher("/template/teacher/details/classuserdetails.jsp").forward(request, response);

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
