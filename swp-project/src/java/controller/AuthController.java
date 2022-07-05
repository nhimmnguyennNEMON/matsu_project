/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Google;
import model.User;
import org.apache.log4j.PropertyConfigurator;
import utils.CookieUtils;
import utils.GenerateOTP;
import utils.GoogleUtils;
import utils.Hash;
import utils.Jwt;
import utils.SendMail;
import utils.TwoFactorUtils;
import utils.env;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 70,//70
        //location ="/Users/pallgree/Desktop/g4/swp-project/web/assets/images_upload"
        location ="C:\\Users\\SY NGUYEN\\OneDrive\\Máy tính\\SWP_root\\SWP_Project_Clone_1_6_2022\\g4\\swp-project\\web\\assets\\images_upload"
        // may dua nao tu link o day location ="/Users/pallgree/Desktop/g4/swp-project/web/assets/images_upload"
)

public class AuthController extends HttpServlet {

    UserDao dao = new UserDao();
    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AuthController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String log4jConfigFile = env.USER_DIR_MAC + "/swp-project/web/WEB-INF/log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");

        if (go.equals("login")) {
            request.getRequestDispatcher("template/auth/Login.jsp").forward(request, response);
        } else if (go.equals("logout")) {
            Cookie cookie = new Cookie("_matsu_token", "");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            response.sendRedirect("/swp-project/home");
        } else if (go.equals("login-google")) {
            String code = request.getParameter("code");
            String accessToken = GoogleUtils.getToken(code);
            Google google = GoogleUtils.getUserInfo(accessToken);
            Pattern pattern = Pattern.compile("@fpt.edu.vn", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(google.getEmail());
            boolean checkMatch = matcher.find();
            if (!checkMatch) {
                request.setAttribute("statusLogin", 2);
                request.getRequestDispatcher("template/auth/Login.jsp").forward(request, response);
            } else {

                Hash hash = new Hash();
                Jwt jwt = new Jwt();
                String token;
                User user;
                try {
                    //account đã tồn tại
                    user = dao.getUserByEmail(google.getEmail());
                    if (!user.getFull_name().equals(google.getGiven_name())) {
                        user.setFull_name(google.getGiven_name());
                        request.setAttribute("titles", "Hi you're back");
                        request.setAttribute("mess", "We recognize that you have used this email to register with the system before, we will sync information between gmail to your pre-existing account");
                        request.setAttribute("statusLogin", 2);
                    }
                    user.setAvatar_link(google.getPicture());
                    dao.update(user);
                    //System.out.println(user);
                    token = jwt.createJwt(user.getEmail(), user.getRole_id());

                } catch (Exception e) {
                    // account chưa tồn tại
                    user = new User();
                    user.setFull_name(google.getGiven_name());
                    user.setEmail(google.getEmail());
                    user.setAvatar_link(google.getPicture());
                    user.setIs_verify(true);
                    user.setPassword("123!#@$*huasid923-8j9123");
                    System.out.println(user.toString());
                    dao.insert(user);
                    token = jwt.createJwt(user.getEmail(), 4);
                    request.setAttribute("titles", "Welcome first time visitor");
                    request.setAttribute("mess", "Please scan the qr code to use the tow factor authenticator security ");
                    request.setAttribute("statusLogin", 1);
                    request.setAttribute("email", user.getEmail());
                    request.setAttribute("secret_key", user.getSecret_key());

                }
                //logger.error("User :"+user.getFull_name()+" login by google");
                Cookie cookie = new Cookie("_matsu_token", token);
                cookie.setMaxAge(60 * 5 * 1);
                response.addCookie(cookie);
                request.setAttribute("statusFilter", 0);
                request.setAttribute("user", user);
                request.getRequestDispatcher("template/HomePage.jsp").forward(request, response);
            }

        } else if (go.equals("register")) {
            request.getRequestDispatcher("template/auth/register.jsp").forward(request, response);
        } else if (go.equals("verify")) {
            String uuid = request.getParameter("uuid");
            long expiredTime = System.currentTimeMillis();
            String mess = "";
            String err = "";

            UserDao userDB = new UserDao();
            User user = userDB.getUserByUUID(uuid);

            if (user != null) {
                if (uuid.equals(user.getUUID())) {
                    if ((expiredTime - user.getCreated_time()) < (60 * 5 * 1 * 1000)) {
                        userDB.verify(user.getId());
                        mess = "Verify successfull! Please scan qr code";
                        request.setAttribute("mess", mess);
                        request.setAttribute("user", user);
                        request.getRequestDispatcher("template/auth/VerifyDone.jsp").forward(request, response);
                    } else {
                        err = "The time is out!";
                        request.setAttribute("err", err);
                        request.getRequestDispatcher("template/auth/VerifyDone.jsp").forward(request, response);
                    }
                } else {
                    err = "Your link cannot verify!";
                    request.setAttribute("err", err);
                    request.getRequestDispatcher("template/auth/VerifyDone.jsp").forward(request, response);
                }
            } else {
                err = "User not found! Please check your email!";
                request.setAttribute("err", err);
                request.getRequestDispatcher("template/auth/VerifyDone.jsp").forward(request, response);
            }

        } else if (go.equals("forgot-password")) {
            request.getRequestDispatcher("template/auth/forgotpassword.jsp").forward(request, response);

        } else if (go.equals("show-qr")) {
            String email = request.getParameter("email");
            String secret_key = request.getParameter("secret");
            request.setAttribute("secret_key", secret_key);
            request.setAttribute("email", email);
            request.getRequestDispatcher("template/auth/show-qr.jsp").forward(request, response);

        } else if (go.equals("change-password")) {
            request.getRequestDispatcher("template/auth/ChangePassword.jsp").forward(request, response);;

        } else if (go.equals("over-view")) {
            String token = CookieUtils.getToken(request, response);
            String email = new Jwt().getEmailFromToken(token);
            User user = dao.getUserByEmail(email);
            request.setAttribute("user", user);
            request.getRequestDispatcher("template/auth/UpdateUserProfile.jsp").forward(request, response);

        } else if (go.equals("user-profile")) {
            String token = CookieUtils.getToken(request, response);
            String email = new Jwt().getEmailFromToken(token);
            User user = dao.getUserByEmail(email);
            request.setAttribute("user", user);
            request.getRequestDispatcher("template/UserProfile.jsp").forward(request, response);
        } else if (go.equals("reset-password")) {
            String uuid = request.getParameter("uuid");
            long expiredTime = System.currentTimeMillis();
            UserDao userDB = new UserDao();
            User user = userDB.getUserByUUID(uuid);
            if (user != null) {
                if (uuid.equals(user.getUUID())) {
                    if ((expiredTime - user.getCreated_time()) < ( env.TIME_OUT_FORGET_PASSWOR )) {
                        request.setAttribute("email", user.getEmail());
                        request.getRequestDispatcher("/template/auth/resetpassword.jsp").forward(request, response);
                    }
                }
            }

            request.getRequestDispatcher("/template/auth/checkUUIDfasle.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String go = request.getParameter("go");
        if (go.equals("login")) {
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            Hash hash = new Hash();
            User user = null;
            try {
                user = dao.getUserByEmail(email);
                boolean n = hash.checkPass(password, user.getPassword());
                System.out.println(n);
                if (n == true && user.isIs_verify() == true && user.isStatus() == true) {
                    Jwt jwt = new Jwt();
                    String token = jwt.createJwt(user.getEmail(), user.getRole_id());
                    Cookie cookie = new Cookie("_matsu_token", token);
                    cookie.setMaxAge(env.TIME_OUT_COOKIE_JWT/1000);
                    response.addCookie(cookie);
                    String timestamp = Long.toString(System.currentTimeMillis());
                    user.setTime_Create_Token(timestamp);
                    dao.updateTimeCreateToken(user);
                    response.sendRedirect("/swp-project/home");
                } else {
                    throw new Exception("Exception message");
                }
            } catch (Exception e) {
                request.setAttribute("statusLogin", 0);
                request.setAttribute("email", email);
                request.getRequestDispatcher("template/auth/Login.jsp").forward(request, response);
            }

        } else if (go.equals("register")) {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("utf-8");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            String repass = request.getParameter("repass");
            String mess = "";

            UserDao userDB = new UserDao();
            User user = userDB.getUserByEmail(email);
            SendMail sm = new SendMail();
            Hash hash = new Hash();
            GenerateOTP generateOTP = new GenerateOTP();

            String hashPass = hash.hashPass(pass);

            if (user != null) {
                mess = "This email has been use, please choose another one!";
                request.setAttribute("mess", mess);
                request.getRequestDispatcher("template/auth/register.jsp").forward(request, response);
            } else {
                if (!pass.equals(repass)) {
                    mess = "Confirm password fail, please enter again!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("template/auth/register.jsp").forward(request, response);
                } else {
                    User u = new User();
                    u.setFull_name(name);
                    u.setEmail(email);
                    u.setPassword(hashPass);
                    u.setUUID(generateOTP.generateUUID());
                    u.setCreated_time(System.currentTimeMillis());
                    sm.sentEmail(email, "Verify Your Account",
                            "Click this link to verify your account: "
                            + "http://localhost:8080/swp-project/auth?go=verify&uuid=" + u.getUUID());
                    userDB.insert(u);
                }
                response.sendRedirect("template/auth/NotiVerify.jsp");
            }
        } else if (go.equals("forgot-password")) {
            String email = request.getParameter("email").trim();
            PrintWriter out = response.getWriter();
            try {
                User user = dao.getUserByEmail(email);
                out.print("{ \"name\":\"" + user.getFull_name() + "\" ,\"email\":\""
                        + user.getEmail() + "\",\"avatar_link\":\"" + user.getAvatar_link() + "\" }");
            } catch (Exception e) {
                out.print("{ \"code\":\"1\"}");
            }

            out.flush();

        } else if (go.equals("change-password")) {
            String token = CookieUtils.getToken(request, response);
            String email = new Jwt().getEmailFromToken(token);
            User user = dao.getUserByEmail(email);
            SendMail sm = new SendMail();
            Hash hash = new Hash();

            String oldPass = request.getParameter("old-pass");
            String newPass = request.getParameter("new-pass");
            String confirmNewPass = request.getParameter("confirm-new-pass");
            String err = "";

            if (user != null) {
                if (hash.checkPass(oldPass, user.getPassword())) {
                    if (newPass.equals(oldPass)) {
                        err = "Your new password and current password are the same! Please choose another password to change!";
                        request.setAttribute("err", err);
                        request.getRequestDispatcher("template/auth/ChangePassword.jsp").forward(request, response);
                    } else {
                        if (newPass.equals(confirmNewPass)) {
                            String hashNewPass = hash.hashPass(newPass);
                            dao.changePassword(hashNewPass, user.getId());
                            sm.sentEmail(user.getEmail(), "Change Password", "Your Password have changed successfully!");
                            response.sendRedirect("home");
                        } else {
                            err = "Confirm Password Failed!";
                            request.setAttribute("err", err);
                            request.getRequestDispatcher("template/auth/ChangePassword.jsp").forward(request, response);
                        }
                    }

                } else {
                    err = "Your current password is wrong!";
                    request.setAttribute("err", err);
                    request.getRequestDispatcher("template/auth/ChangePassword.jsp").forward(request, response);
                }
            } else {
                err = "User not found!";
                request.setAttribute("err", err);
                request.getRequestDispatcher("template/auth/ChangePassword.jsp").forward(request, response);
            }
        } else if (go.equals("update-user-profile")) {
            String name = request.getParameter("full_name");
            String slack = request.getParameter("slack_account");
            String dob = request.getParameter("dob");

            Part part = request.getPart("file");
            String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            part.write(filename);
            String img = "assets/images_upload/" + filename;
            User ojb = new User(name, dob, slack, img);
            dao.updateUserByEmail(ojb);
            String status = null;
            if (ojb != null) {
                status = "success";
                request.setAttribute("status", status);
            }
            if (ojb == null) {
                status = null;
                request.setAttribute("status", status);
            }

            ArrayList<User> list = dao.list();
            String token = CookieUtils.getToken(request, response);
            String email = new Jwt().getEmailFromToken(token);
            User user = dao.getUserByEmail(email);
            request.setAttribute("user", user);
            request.setAttribute("list", list);
            status = null;
            request.setAttribute("status", status);
            request.getRequestDispatcher("template/auth/UpdateUserProfile.jsp").forward(request, response);
        } else if (go.equals("check-otp")) {
            GenerateOTP generateOTP = new GenerateOTP();
            PrintWriter out = response.getWriter();
            String otp = request.getParameter("otp");
            String email = request.getParameter("email");
            try {
                User user = dao.getUserByEmail(email);
                long _otp = TwoFactorUtils.getSecretKey(user.getSecret_key());
                String _otp_string = Long.toString(_otp);
                if (otp.equals(_otp_string)) {
                    user.setCreated_time(System.currentTimeMillis());
                    user.setUUID(generateOTP.generateUUID());
                    dao.updateUUIDandCreateTime(user);
                    out.print("{ \"code\":\"1\",\"uuid\":\"" + user.getUUID() + "\" }");
                } else {
                    out.print("{ \"code\":\"0\"}");
                }
            } catch (GeneralSecurityException ex) {
                out.print("{ \"code\":\"0\"}");
                logger.warn(email + ":" + ex.getMessage());
            }
            out.flush();
        } else if (go.equals("send-mail-uuid")) {
            System.out.println("send-mail-uuid");
            PrintWriter out = response.getWriter();
            String email = request.getParameter("email");
            User user = dao.getUserByEmail(email);
            GenerateOTP generateOTP = new GenerateOTP();
            try {
                System.out.println(email);
                user.setCreated_time(System.currentTimeMillis());
                user.setUUID(generateOTP.generateUUID());
                SendMail mail = new SendMail();
                mail.sentEmail(email, "Reset Password",
                        "Click this link to verify your account(this link has expired time after 30 min): "
                        + "http://localhost:8080/swp-project/auth?go=reset-password&uuid=" + user.getUUID());
                dao.updateUUIDandCreateTime(user);
                out.print("{ \"code\":\"1\"}");
                System.out.println("donee");
            } catch (Exception e) {
                logger.warn(email + ":" + e.getMessage());
                out.print("{ \"code\":\"0\"}");

            }
            out.flush();

        } else if (go.equals("reset-password")) {
            String email = request.getParameter("email");
            System.out.println(email);
            User user = dao.getUserByEmail(email);
            SendMail sm = new SendMail();
            Hash hash = new Hash();
            String newPass = request.getParameter("new-pass");
            String confirmNewPass = request.getParameter("confirm-new-pass");
            String err = "";
            if (user != null) {

                if (newPass.equals(confirmNewPass)) {
                    String hashNewPass = hash.hashPass(newPass);
                    user.setPassword(hashNewPass);
                    dao.changePassword(hashNewPass, user.getId());
                    sm.sentEmail(user.getEmail(), "Change Password", "Your Password have changed successfully!");
                    request.setAttribute("msg", "Your Password have changed successfully!");
                    request.getRequestDispatcher("template/auth/resetpassword.jsp").forward(request, response);
                } else {
                    request.setAttribute("email", email);
                    err = "Confirm Password Failed!";
                    request.setAttribute("err", err);
                    request.getRequestDispatcher("template/auth/resetpassword.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("email", email);
                err = "Failed to change password";
                request.setAttribute("err", err);
                request.getRequestDispatcher("template/auth/resetpassword.jsp").forward(request, response);
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
