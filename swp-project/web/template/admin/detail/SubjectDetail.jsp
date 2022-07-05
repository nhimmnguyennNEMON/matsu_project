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
        <title>Subject Detail</title>
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
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />

        <style>
            .hinhanh{
                text-align: center;
            }
        </style>

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
                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Subject Detail</h4>
                                        <c:if test="${detail != null}">
                                            <form class="forms-sample" id="form-detail" onsubmit="return false" action="subject" method="POST">
                                                <input type="hidden" name="go" value="subject-update">
                                                <div class="form-group">
                                                    <label for="exampleInputUsername1">Subject ID</label>
                                                    <input type="text" class="form-control" id="subject_id" disabled="" value="${detail.getId()}">
                                                    <input type="hidden" class="form-control" id="subject_id" name="subject_id" value="${detail.getId()}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Subject Code</label>
                                                    <input type="text" class="form-control" id="subject_code" name="subject_code" value="${detail.getSubject_code()}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputPassword1">Subject Name</label>
                                                    <input type="text" class="form-control" id="subject_name" name="subject_name" value="${detail.getSubject_name()}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputConfirmPassword1">Author ID</label>
                                                    <input type="number" class="form-control" id="subject_authorID" name="subject_authorID" value="${detail.getAuthor_id()}">
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-2" for="exampleInputConfirmPassword1">Subject Status</label>
                                                    <c:if test="${detail.getStatus() == 1}">
                                                        <div class="col-md-1">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="subject_status" value="1" checked="">
                                                                Active
                                                            </label>
                                                        </div>
                                                        <div class="col-md-1">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="subject_status" value="0" >
                                                                Inactive
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${detail.getStatus() == 0}">
                                                        <div class="col-md-1">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="subject_status" value="1" >
                                                                Active
                                                            </label>
                                                        </div>
                                                        <div class="col-md-1">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="subject_status" value="0" checked="" >
                                                                Inactive
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-8"></div>
                                                    <div class="col-md-4">
                                                        <button class="btn btn-light me-2" type="reset" >Reset</button>
                                                        <button class="btn btn-primary me-2" onclick="updateSubject(${detail.getId()})" >Update</button>
                                                        <button class="btn btn-danger me-2" onclick="deleteSubject(${detail.getId()})">Delete</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="../../component/Footer.jsp"/>
                </div>

            </c:if>
        </div>

        <!--xu y alert dong mo file-->
        <script>

            function updateSubject(id) {
                swal({
                    title: "Are you sure?",
                    text: "Update infomation Subject",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                swal("Done!", "Update setting successfull!", "success")
                                        .then((value) => {
                                            document.getElementById("form-detail").submit();
                                        });
                            } else {
                                swal("Subject is still not deleted!");
                            }
                        });
            }

            function deleteSubject(id)
            {
                swal({
                    title: "Are you sure?",
                    text: "Once deleted, you will not be able to recover this setting!",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                swal("Done!", "Subject has been deleted!", "success")
                                        .then((value) => {
                                            window.location.href = "/swp-project/admin/subject?go=subject-delete&id=" + id;
                                        });
                            } else {
                                swal("Subject is still not deleted!");
                            }
                        });
            }

            function cancelDelete() {
                window.location.href = "/swp-project/admin/subject";
            }

            function tai_lai_trang() {
                location.reload();
            }

            function skipSubmit() {
                document.querySelector("#form-detail").event.preventDefault();
            }

            function callBack() {
                window.location.href = "/swp-project/admin/subject"
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
