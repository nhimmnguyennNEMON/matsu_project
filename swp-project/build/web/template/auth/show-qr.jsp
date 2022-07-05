<%-- 
    Document   : show-qr
    Created on : May 20, 2022, 10:01:09 PM
    Author     : pallgree
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Vui lòng mở ứng dụng để quét mã QR Two-Factor Authencation</h1>
        <img src="https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/MATSU:${requestScope.email}?secret=${requestScope.secret_key}&algorithm=SHA-1" width="500" height="500" alt="qr code"/>
        <h6>Bạn có thể dùng Authenticator của Google hoặc Microsoft</h6>
        <a type="button" href="auth?go=login">Login</a>
    </body>
</html>
