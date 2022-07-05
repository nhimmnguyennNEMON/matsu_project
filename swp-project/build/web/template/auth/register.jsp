<%-- 
    Document   : register
    Created on : May 12, 2022, 4:01:31 PM
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
        <title> Registration </title>
        <link href="assets/style/register.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
    </head>
    <body>
        <div class="wrapper">
            <h2>Registration</h2>
            <form action="auth?go=register" method="POST">
                <div class="input-box">
                    <input type="text" placeholder="Enter your full name" name="name" required>
                </div>
                <div class="input-box">
                    <input type="text" placeholder="Enter your email" name="email" onchange="isEmail(this.value), disableInput()" required>
                </div>
                <div class="input-box">
                    <input onkeyup="trigger()" id="pass" type="password" placeholder="Create password" name="pass" required>
                    <i class="uil uil-eye-slash toggle show-pass" onclick="showHidePass()"></i>
                </div>

                <div class="indicator">
                    <span class="week"></span>
                    <span class="medium"></span>
                    <span class="strong"></span>
                </div>
                <div class="text-indicator"></div>

                <div class="input-box">
                    <input id="repass" type="password" placeholder="Confirm password" name="repass" required>
                    <i class="uil uil-eye-slash toggle show-repass" onclick="showHideRepass()"></i>
                    <span id='message'></span>
                </div>

                <div class="policy">
                    <input onclick="disableInput()" type="checkbox" required>
                    <h3>I accept all terms & condition</h3>
                </div>
                <div class="input-box button">
                    <input id="btn" type="Submit" value="Register Now" disabled>
                </div>
                <p id="error" style="margin: 0;color: red;">
                    <c:if test="${requestScope.mess != null}">
                        ${requestScope.mess}
                    </c:if>
                </p>
                <div class="text">
                    <h3>Already have an account? <a href="auth?go=login">Login now</a></h3>
                </div>
            </form>
        </div>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

        <script>
                        var isMatch = false;
                        $('#pass, #repass').on('keyup', function () {
                            if ($('#pass').val() === $('#repass').val()) {
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
            function showHidePass() {
                var pass = document.querySelector("#pass");
                var showPass = document.querySelector(".show-pass");
                if (pass.type === "password") {
                    pass.type = "text";
                    showPass.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    pass.type = "password";
                    showPass.classList.replace("uil-eye", "uil-eye-slash");
                }
            }

            function showHideRepass() {
                var repass = document.querySelector("#repass");
                var showRepass = document.querySelector(".show-repass");
                if (repass.type === "password") {
                    repass.type = "text";
                    showRepass.classList.replace("uil-eye-slash", "uil-eye");
                } else {
                    repass.type = "password";
                    showRepass.classList.replace("uil-eye", "uil-eye-slash");
                }
            }
        </script>

        <script>
            var isMailFpt = false;

            function isEmail(email) {
                var regex = /^([a-zA-Z0-9_.+-])+\@fpt.edu.vn/;
                if (!regex.test(email)) {
                    swal("Invalid Email!", "Please Enter Email FPT To SignUp!", "warning");
                    isMailFpt = false;
                } else {
                    isMailFpt = true;
                }
            }

            function disableInput() {
                if (isMailFpt === true && isMatch === true) {
                    document.getElementById("btn").disabled = false;
                } else {
                    document.getElementById("btn").disabled = true;
                }
            }
        </script>

        <script src="assets/js/registerJS.js" type="text/javascript"></script>
    </body>
</html>
