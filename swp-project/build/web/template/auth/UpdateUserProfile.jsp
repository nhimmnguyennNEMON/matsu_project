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
        <title>Update User Profile</title>
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
        <link href="assets/style/validator.css" rel="stylesheet" type="text/css"/>
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />

    </head>
    <body>

        <c:if test="${user != null}">
            <jsp:include page="../component/Sidebar_student.jsp">
                <jsp:param name="email" value="${user.getEmail()}"/>
                <jsp:param name="name" value="${user.getFull_name()}"/>
                <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
            </jsp:include>
        </c:if>

        <div class="main-panel">        
            <div class="content-wrapper">
                <div class="row">

                    <c:if test="${status != null}">
                        <div class="col-md-12 grid-margin stretch-card">
                            <div class="col-md-12 alert alert-success" id="success" role="alert" style="margin-top: 20px">
                                <div class="row">
                                    <span class="col-md-11"><svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/>
                                        </svg>Update Successfull! Congratulations you!</span>
                                    <button  type="button" class=" col-md-1 btn-close" data-bs-dismiss="alert" aria-label="Close" style="margin-left: 50px"></button>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${user != null}">
                        <div class="col-md-12 grid-margin stretch-card">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title" style="padding: 10px">Overview</h4>
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
                                                                        <i style="color: black">${temp.getDate_of_birth()}</i>
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
                    </c:if>

                    <c:if test="${user != null}">
                        <div class="col-12 grid-margin stretch-card">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title">Update User Profile</h4>
                                    <form class="forms-sample" id="form-1" action="auth?go=update-user-profile" method="get" enctype="multipart/form-data">
                                        <input type="hidden" name="go" value="update-user-profile" >
                                        <div class="form-group">
                                            <label for="fullname">Name</label>
                                            <input type="text" class="form-control" id="fullname" name="full_name" placeholder="${user.getFull_name()}" required="">
                                            <span class="form-message"></span>
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputDate">Date Of Birth</label>
                                            <input type="date" class="form-control" id="dob" name="dob" placeholder="${user.getDate_of_birth()}" required="">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputSlackAccount">Slack Account</label>
                                            <input type="text" class="form-control" id="password" name="slack_account" placeholder="${user.getSlack_account()}">
                                            <span class="form-message"></span>
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputSlackAccount">Slack Account</label>
                                            <input type="text" class="form-control" id="password_confirmation" name="slack_account" placeholder="${user.getSlack_account()}">
                                            <span class="form-message"></span>
                                        </div>
                                        <div class="form-group">
                                            <label style="margin-right: 20px;">Avatar</label><br>
                                            <input type="file" id="file" name="file" class="btn btn-outline-secondary" required=""/>
                                        </div>
                                        <button id="submit" class="btn btn-primary me-2" data-toggle="modal" href="#myModal" >Update</button>
                                        <a href="user-profile" class="btn btn-light" type="reset" >Cancle</a>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>

                <jsp:include page="../component/Footer.jsp"/>

            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                // Mong muốn của chúng ta
                Validator({
                    form: '#form-1',
                    formGroupSelector: '.form-group',
                    errorSelector: '.form-message',
                    rules: [
                        Validator.isRequired('#fullname', 'Vui lòng nhập tên đầy đủ của bạn'),
                        Validator.isEmail('#email'),
                        Validator.minLength('#password', 6),
                        Validator.isRequired('#password_confirmation'),
                        Validator.isConfirmed('#password_confirmation', function () {
                            return document.querySelector('#form-1 #password').value;
                        }, 'Mật khẩu nhập lại không chính xác')
                    ],
                    onSubmit: function (data) {
                        // Call API
                        console.log(data);
                    }
                });


                Validator({
                    form: '#form-2',
                    formGroupSelector: '.form-group',
                    errorSelector: '.form-message',
                    rules: [
                        Validator.isEmail('#email'),
                        Validator.minLength('#password', 6),
                    ],
                    onSubmit: function (data) {
                        // Call API
                        console.log(data);
                    }
                });
            });
        </script>
        
        <script src="assets/js/validator.js" type="text/javascript"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

        <!-- plugins:js -->
        <script src="../assets/assets_template/vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <script src="../assets/assets_template/vendors/chart.js/Chart.min.js"></script>
        <script src="../assets/assets_template/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
        <script src="../assets/assets_template/vendors/progressbar.js/progressbar.min.js"></script>

        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="../assets/assets_template/js/off-canvas.js"></script>
        <script src="../assets/assets_template/js/hoverable-collapse.js"></script>
        <script src="../assets/assets_template/js/template.js"></script>
        <script src="../assets/assets_template/js/settings.js"></script>
        <script src="../assets/assets_template/js/todolist.js"></script>
        <!-- endinject -->
        <!-- Custom js for this page-->
        <script src="../assets/assets_template/js/jquery.cookie.js" type="text/javascript"></script>
        <script src="../assets/assets_template/js/dashboard.js"></script>
        <script src="../assets/assets_template/js/Chart.roundedBarCharts.js"></script>




        <!-- End custom js for this page-->
    </body>
</html>
