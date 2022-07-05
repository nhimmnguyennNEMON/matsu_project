/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import dal.UserDao;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import utils.CookieUtils;
import utils.Jwt;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import utils.env;
/**
 *
 * @author pallgree
 */
public class ResetTokenFilter implements Filter {
  
     static Logger logger = Logger.getLogger(ResetTokenFilter.class);
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    UserDao dao = new UserDao();
  
    

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("ResetTokenFilter:doFilter()");
        }

        Throwable problem = null;
        try {
            
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String token = null;
            Cookie cookie = null;
            Cookie[] cookies = null;
            cookies = req.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    cookie = cookies[i];
                    if (cookie.getName().equals("_matsu_token")) {
                        token = cookie.getValue();
                    }
                }
            }

            Jwt jwt = new Jwt();
            boolean checkJWT = jwt.verifyJwt(token);
            if (checkJWT == true) {
                String email = jwt.getEmailFromToken(token);
                int role_id = jwt.getRoleFromToken(token);
                String newtoken = jwt.createJwt(email, role_id);
                Cookie newcookie = new Cookie("_matsu_token", newtoken);
                cookie.setMaxAge( env.TIME_OUT_COOKIE_JWT / 1000);
                res.addCookie(newcookie);
                User user=dao.getUserByEmail(email);
                String timestamp=Long.toString( System.currentTimeMillis()) ;
                user.setTime_Create_Token(timestamp);
                dao.updateTimeCreateToken(user);
                chain.doFilter(request, response);
            } else throw new Exception("");

        } catch (Throwable t) {
            
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            req.setAttribute("statusLogin", 1);
            request.getRequestDispatcher("template/auth/Login.jsp").forward(request, response);
            problem = t;
            t.printStackTrace();
            
        }

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("ResetTokenFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ResetTokenFilter()");
        }
        StringBuffer sb = new StringBuffer("ResetTokenFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
