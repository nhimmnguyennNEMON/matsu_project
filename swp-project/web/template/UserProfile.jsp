<%-- 
    Document   : UpdateUserProfile
    Created on : May 17, 2022, 6:00:50 PM
    Author     : SY NGUYEN
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
<%@page import="dal.UserDao"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="uri" value="${req.requestURI}" />
<c:set var="url">${req.requestURL}</c:set>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title> User Profile</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="assets/assets_template/vendors/feather/feather.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/typicons/typicons.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="assets/assets_template/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
        <link rel="stylesheet" href="assets/assets_template/js/select.dataTables.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
    </head>
    <body>


        <c:if test="${user != null}">
            <jsp:include page="component/Sidebar_admin.jsp">
                <jsp:param name="email" value="${user.getEmail()}"/>
                <jsp:param name="name" value="${user.getFull_name()}"/>
                <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
            </jsp:include>

            <div class="main-panel">        
                <div class="content-wrapper">

                    <div class="row">
                        <div class="col-md-12 grid-margin stretch-card">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title" style="padding: 10px">Profile</h4>
                                    <div class="row">
                                        <div class="col-md-1"></div>
                                        <div class="col-md-3 grid-margin stretch-card">
                                            <img src="${user.getAvatar_link()}" style=" border-radius: 50%; width: 250px; height: 250px; object-fit: cover;" alt="" >
                                        </div>
                                        <div class="col-md-1"></div>
                                        <div class="col-md-7">
                                            <div class="card card-rounded">
                                                <div class="card-body">
                                                    <div class="table-responsive  mt-1">
                                                        <table class="table select-table">
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                        Name:
                                                                    </td>
                                                                    <td>
                                                                        <i style="color: black;"> ${user.getFull_name()} </i>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        E-mail:
                                                                    </td>
                                                                    <td>
                                                                        <i style="color: black;">${user.getEmail()}   <i class="mdi mdi-checkbox-marked-outline"></i></i>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Date Of Birth: 
                                                                    </td>
                                                                    <td>
                                                                        <i style="color: black">${user.getDate_of_birth()}</i>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Slack Account:
                                                                    </td>
                                                                    <td>
                                                                        <i style="color: black;"> ${user.getSlack_account()}</i>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td>
                                                                        Class:
                                                                    </td>
                                                                    <td>
                                                                        <i style="color: black;"> ${user.getClass_user()} </i>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <a type="button" class="btn btn-outline-primary" href="auth?go=change-password" value="K" style=" margin-left:75px; margin-right: 20px">Change Password</a>
                            <script>
                               
                            </script>
                            <a class="btn btn-outline-primary" href="auth?go=overview" name="button" value="OK" type="button"  style="magin-left:20px">Update Profile</a>  
                            <script>
                              
                            </script>
                        </div>
                    </div>

                    <footer class="footer">
                        <div class="d-sm-flex justify-content-center justify-content-sm-between">
                            <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin template</a> from BootstrapDash.</span>
                            <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Copyright © 2021. All rights reserved.</span>
                        </div>
                    </footer>

                </div>
            </div>
        </c:if>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>


        <!-- plugins:js -->
        <script src="assets/assets_template/vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <script src="assets/assets_template/vendors/chart.js/Chart.min.js"></script>
        <script src="assets/assets_template/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
        <script src="assets/assets_template/vendors/progressbar.js/progressbar.min.js"></script>

        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="assets/assets_template/js/off-canvas.js"></script>
        <script src="assets/assets_template/js/hoverable-collapse.js"></script>
        <script src="assets/assets_template/js/template.js"></script>
        <script src="assets/assets_template/js/settings.js"></script>
        <script src="assets/assets_template/js/todolist.js"></script>
        <!-- endinject -->
        <!-- Custom js for this page-->
        <script src="assets/assets_template/js/jquery.cookie.js" type="text/javascript"></script>
        <script src="assets/assets_template/js/dashboard.js"></script>
        <script src="assets/assets_template/js/Chart.roundedBarCharts.js"></script>



        <!-- End custom js for this page-->
    </body>
</html>
