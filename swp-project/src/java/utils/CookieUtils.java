/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pallgree
 */
public class CookieUtils {

    public static String getToken(HttpServletRequest req, HttpServletResponse res) {
        String token = null;
        javax.servlet.http.Cookie cookie = null;
        javax.servlet.http.Cookie[] cookies = null;
        cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("_matsu_token")) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

}
