<%-- 
    Document   : RedirectEmail
    Created on : May 20, 2022, 9:32:26 PM
    Author     : quang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="assets/style/NotiVerify.css" rel="stylesheet" type="text/css"/>
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h1 class="verify-success" style="color: #008a00">
                <c:if test="${requestScope.mess != null}">
                    ${requestScope.mess}
                </c:if>
            </h1>
            <h1 class="verify-fail" style="color: red">
                <c:if test="${requestScope.err != null}">
                    ${requestScope.err}
                </c:if>
            </h1>
            <a type="button" href="auth?go=show-qr&email=${requestScope.user.getEmail()}&secret=${requestScope.user.getSecret_key()}">Quét mã QR</a>
        </div>
    </body>
</html>
