<%-- 
    Document   : Login
    Created on : May 17, 2022, 8:55:23 AM
    Author     : pallgree
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Login</title>
        <!-- plugins:css -->
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="assets/assets_template/vendors/feather/feather.css" type="text/css">
        <link rel="stylesheet" href="assets/assets_template/vendors/mdi/css/materialdesignicons.min.css" type="text/css">
        <link rel="stylesheet" href="assets/assets_template/vendors/ti-icons/css/themify-icons.css" type="text/css">
        <link rel="stylesheet" href="assets/assets_template/vendors/typicons/typicons.css" type="text/css">
        <link rel="stylesheet" href="assets/assets_template/vendors/simple-line-icons/css/simple-line-icons.css"type="text/css">
        <link rel="stylesheet" href="assets/assets_template/vendors/css/vendor.bundle.base.css" type="text/css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="assets/assets_template/css/vertical-layout-light/style.css">
       
        <!-- endinject -->
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
    </head>

    <body>
        <div class="container-scroller">
            <div class="container-fluid page-body-wrapper full-page-wrapper">
                <div class="content-wrapper d-flex align-items-center auth px-0">
                    <div class="row w-100 mx-0">
                        <div class="col-lg-4 mx-auto">
                            <div class="shadow auth-form-light text-left py-5 px-4 px-sm-5">
                                <div id="status0" class="alert alert-danger" role="alert" style="display: none">
                                    Đăng nhập không thành công!Vui lòng kiểm tra lại!
                                </div>
                                <div id="status1" class="alert alert-danger" role="alert" style="display: none">
                                    Phiên đăng nhập đã kết thúc! Vui lòng re-login.
                                </div>
                                <div id="status2" class="alert alert-danger" role="alert" style="display: none">
                                    Bạn cần đăng nhập tài khoản của FPT Edu.
                                </div>
                                <h4>Wellcome</h4>
                                <h6 class="fw-light">Sign in to continue.</h6>
                                <form class="pt-3" method="post" action="/swp-project/auth?go=login">
                                    <div class="form-group">
                                        <input type="email" name="email" value="${email}" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="Email" required>
                                    </div>
                                    <div class="form-group">
                                        <input type="password" name="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="Password" required>
                                    </div>
<!--                                    <div class="g-recaptcha" data-sitekey="6Ldb8xkgAAAAAG0U2OJ7lLCbUM1ZmtRxIl9Yg3PJ"></div>-->

                                    <div class="mt-3">
                                        <input type="submit" class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" value="SIGN IN" name="SIGN IN" />
                                    </div>

                                    <div class="my-2 d-flex justify-content-between align-items-center mb-3">
                                        <a href="auth?go=forgot-password" class="auth-link text-black">Forgot password?</a>
                                    </div> 
                                    <div class="mb-2">
                                        <a href="https://accounts.google.com/o/oauth2/auth?scope=profile email&redirect_uri=http://localhost:8080/swp-project/auth?go=login-google&response_type=code
                                           &client_id=90081686861-c3nuinbhjpe9rckd1ta4sk92v443q4fh.apps.googleusercontent.com&approval_prompt=force" type="button" class="btn btn-block btn-facebook auth-form-btn  ">
                                            Connect using Google FPT Edu
                                        </a>
                                    </div>
                                    <div class="text-center mt-4 fw-light">
                                        Don't have an account? <a href="/swp-project/auth?go=register" class="text-primary">Create</a>                 
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- content-wrapper ends -->
            </div>
            <!-- page-body-wrapper ends -->
        </div>
        <!-- container-scroller -->
        <!-- plugins:js -->
        <script src="assets/assets_template/vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <script src="assets/assets_template/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="assets/assets_template/js/off-canvas.js"></script>
        <script src="assets/assets_template/js/hoverable-collapse.js"></script>
        <script src="assets/assets_template/js/template.js"></script>
        <script src="assets/assets_template/js/settings.js"></script>
        <script src="assets/assets_template/js/todolist.js"></script>
<!--        <script src='https://www.google.com/recaptcha/api.js'></script>-->

        <!-- endinject -->
    </body>
    
    <script>
        if (${statusLogin } == 1) {
            document.getElementById("status1").style.display = "inline";
        }
        if (${statusLogin } == 0) {
            document.getElementById("status0").style.display = "inline";
        }
        if (${statusLogin } == 2) {
            document.getElementById("status2").style.display = "inline";
        }
    </script>

</html>
