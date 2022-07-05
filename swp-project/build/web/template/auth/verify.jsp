<%-- 
    Document   : verify
    Created on : May 12, 2022, 8:59:03 PM
    Author     : quang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Verify</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
    </head>
    <body>
        <section class="vh-100" style="background-color: #508bfc;">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                        <div class="card shadow-2-strong" style="border-radius: 1rem;">
                            <div class="card-body p-5 text-center">

                                <h3 class="mb-5">Verify Your Account</h3>
                                <form method="post" action="verify">

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="typeEmailX-2">Email</label>
                                        <input type="email" id="typeEmailX-2" class="form-control form-control-lg" name="email" id="user" value="${requestScope.email}"/>

                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="typePasswordX-2">OTP</label>
                                        <input type="text" id="typePasswordX-2" class="form-control form-control-lg" id="pass" name="otp"/>

                                    </div>
                                    <p style="color:red ;margin-top: 30px;margin-left: 15px;" id="h">
                                        <c:if test="${requestScope.mess != null}">
                                            ${requestScope.mess}
                                        </c:if>
                                    </p>

                                    <hr class="my-4">
                                    <button class="btn btn-primary btn-lg btn-block" class="login" type="submit">Active</button>
                                </form>
                                <nav style="display: flex;width: 250px;padding-top: 20px;margin-left: 26%;"></nav>
                                </form>
                                <a class="reg" href="login">
                                    <button class="btn btn-primary btn-lg btn-block">Sign in</button>
                                </a>
                                <nav style="display: flex;width: 400px;padding-top: 20px;margin-left: 10%;">
                                    <a href="forgot">Forgot password</a>
                                    <a href="change" style="margin-left: 70px;">Change Password</a>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
