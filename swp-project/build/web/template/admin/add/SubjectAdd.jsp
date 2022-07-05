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
        <title>Subject Add</title>
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
        <link href="../assets/style/validator.css" rel="stylesheet" type="text/css"/>
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
    </head>
    <body>
        <div class="container-scroller">
            <c:if test="${user != null}">
                <jsp:include page="../../component/Sidebar_admin.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>

                <div class="main-panel">        
                    <div class="content-wrapper" id="list">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="home-tab">
                                    <div class="d-sm-flex align-items-center justify-content-between border-bottom" style="margin-bottom: 30px">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link"  href="subject?go=list" role="tab">Subject List</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"  href="subject?go=subject-add" role="tab">Add Subject</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="add-subject" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <p class="card-title">Add Subject</p>
                                            </div>
                                            <div class="col-md-9"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                </button>
                                            </div>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="subject" method="POST">
                                            <input type="hidden" name="go" value="subject-add">
                                            <div class="form-group">
                                                <label for="exampleInputEmail1">Subject Code</label>
                                                <input type="text" class="form-control" id="subject_code" name="subject_code" placeholder="Subject Code">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputPassword1">Subject Name</label>
                                                <input type="text" class="form-control" id="subject_name" name="subject_name" placeholder="Subject Name">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputConfirmPassword1">Author ID</label>
                                                <input type="number" class="form-control" id="subject_authorID" name="subject_authorID" maxlength="5" placeholder="AuthorID">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <div class="row">
                                                    <label class="col-md-2" for="exampleInputConfirmPassword1">Subject Status</label>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="subject_status" id="optionsRadios1" value="1">
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="subject_status" id="optionsRadios2" value="0" >
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-9"></div>
                                                <div class="col-md-3">
                                                    <button type="reset" class="btn btn-light me-2">Reset</button>
                                                    <button class="btn btn-primary me-2" onclick="submitForm()" >Add</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="../../component/Footer.jsp"/>
                </div>

            </c:if>
        </div>
        <script src="../assets/js/validator.js" type="text/javascript"></script>

        <!--validate input-->
        <script>
                                                        document.addEventListener('DOMContentLoaded', function () {
                                                            Validator({
                                                                form: '#form-add',
                                                                formGroupSelector: '.form-group',
                                                                errorSelector: '.form-message',
                                                                rules: [
                                                                    Validator.isRequired('#subject_code', 'Vui lòng nhập Subject Code!'),
                                                                    Validator.isRequired('#subject_name', 'Vui lòng nhập Subject Name!'),
                                                                    Validator.isRequired('#subject_authorID', 'Vui lòng nhập Author ID!'),
                                                                    Validator.minLength('#subject_authorID', 3),
                                                                    Validator.maxLength('#subject_authorID', 5),
                                                                    Validator.isRequired('input[name="subject_status"]', 'Vui lòng chọn Status Subject!')
                                                                ]
//                                                                onSubmit: function (data) {
//                                                                    // Call API
//                                                                    console.log(data);
//                                                                }
                                                            });
                                                        });
        </script>
        <!--Xu li alert and phân trang-->
        <script>

            function callBack() {
                window.location.href = "/swp-project/admin/subject"
            }

            function submitForm() {
                var subject_status;
                var checkstatus = document.getElementsByName("subject_status");
                for (var i = 0; i < checkstatus.length; i++) {
                    if (checkstatus[i].checked === true) {
                        subject_status = checkstatus[i].value;
                    }
                }
                var subject_code = document.getElementById("subject_code").value;
                var subject_name = document.getElementById("subject_name").value;
                var subject_authorID = document.getElementById("subject_authorID").value;
                if (!subject_code || !subject_name || subject_authorID == 0 || !subject_status)
                {
                    swal("OOPS!", "You need to fill all the fields.", "warning");
                } else {
                    swal("Done!", "Your new subject added successfull!", "success")
                            .then((value) => {
                                document.getElementById("form-add").submit();
                            });
                }
            }
        </script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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
