<%-- 
    Document   : ChangePassword
    Created on : May 25, 2022, 9:20:09 AM
    Author     : quang
--%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<!DOCTYPE html>
<html lang="en" dir="ltr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title> Change Your Password </title>
        <link href="assets/style/register.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
              <script src="https://cdn.jsdelivr.net/npm/tw-elements/dist/js/index.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    </head>
    <body>
        <div class="wrapper">
            <h2>Please input new password below</h2>
            <form action="auth?go=reset-password" method="POST">     
                <div class="d-none">
                    <input type="hidden" name="email" value="${email}" >                  
                </div>
                <div class="input-box">
                    <input onkeyup="trigger()" id="pass" type="password" placeholder="Create New password" name="new-pass" required>
                    <i class="uil uil-eye-slash toggle show-new-pass" onclick="showHideNewPass()"></i>
                </div>

                <div class="indicator">
                    <span class="week"></span>
                    <span class="medium"></span>
                    <span class="strong"></span>
                </div>
                <div class="text-indicator"></div>

                <div class="input-box">
                    <input id="confirm-new-pass" type="password" placeholder="Confirm New Password" name="confirm-new-pass" required>
                    <i class="uil uil-eye-slash toggle show-confirm-new-pass" onclick="showHideConfirmPass()"></i>
                    <span id='message'></span>
                </div>

                <div class="input-box button">
                    <input id="btn" type="Submit" value="Change Password" style="margin-top: 8px;">
                    
                </div>
                <p id="error" style="margin: 0;color: red;">
                    <c:if test="${requestScope.err != null}">
                        ${requestScope.err}
                    </c:if>
                </p>
                <p id="msg" style="margin: 0;color: green;">
                    <c:if test="${requestScope.msg != null}">
                        ${requestScope.msg}
                        You can <a href="/swp-project/auth?go=login">Login</a>
                    </c:if>
                </p>
                <div class=""></div>
            </form>
        </div>
    
        <script>
                        var isMatch = false;
                        $('#confirm-new-pass').on('keyup', function () {
                            if ($('#pass').val() === $('#confirm-new-pass').val()) {
                                $('#message').html('Matching').css('color', 'green');
                                isMatch = true;
                                console.log("log")
                            } else {
                                $('#message').html('Not Matching').css('color', 'red');
                                isMatch = false;
                                console.log("log")
                            }
                        });
        </script>

        <script>
            function showHideNewPass() {
                var pass = document.querySelector("#pass");
                var showPass = document.querySelector(".show-new-pass");
                if (pass.type === "password") {
                    pass.type = "text";
                    showPass.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    pass.type = "password";
                    showPass.classList.replace("uil-eye", "uil-eye-slash");
                }
            }

            function showHideConfirmPass() {
                var repass = document.querySelector("#confirm-new-pass");
                var showRepass = document.querySelector(".show-confirm-new-pass");
                if (repass.type === "password") {
                    repass.type = "text";
                    showRepass.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    repass.type = "password";
                    showRepass.classList.replace("uil-eye", "uil-eye-slash");
                }
            }
            
            function showHideOldPass() {
                var repass = document.querySelector("#old-pass");
                var showRepass = document.querySelector(".show-old-pass");
                if (repass.type === "password") {
                    repass.type = "text";
                    showRepass.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    repass.type = "password";
                    showRepass.classList.replace("uil-eye", "uil-eye-slash");
                }
            }
           
        </script>

        <script src="assets/js/registerJS.js" type="text/javascript"></script>
    </body>
</html>

