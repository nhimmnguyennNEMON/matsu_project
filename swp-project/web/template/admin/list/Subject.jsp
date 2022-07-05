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
        <title>Subject List</title>
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
                    <div class="content-wrapper">
                        <div class="row" id="list">
                            <form action="subject" method="POST" id="form-search">
                                <input type="hidden" name="go" value="search">
                                <div class="col-md-12 grid-margin stretch-card ">
                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="subject_code" id="search-type" style="height: 48px">
                                                <option value="">
                                                    <c:if test="${subject_code != null}">${subject_code}</c:if>
                                                    <c:if test="${subject_code == null}">All Subject Code</c:if>
                                                    </option>
                                                <c:forEach items="${requestScope.list}" var="temp">
                                                    <option value="${temp.getSubject_code()}">${temp.getSubject_code()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="subject_authorID" id="search-type" style="height: 48px">
                                                <option value="">
                                                    <c:if test="${subject_authorID != null}">${subject_authorID}</c:if>
                                                    <c:if test="${subject_authorID == null}">All Author ID</c:if>
                                                    </option>
                                                <c:forEach items="${requestScope.list}" var="temp" >
                                                    <option value="${temp.getAuthor_id()}">${temp.getAuthor_id()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="subject_status" id="search-status" style="height: 48px"> 
                                                <option value="">
                                                    <c:if test="${subject_status != null}">${subject_status}</c:if>
                                                    <c:if test="${subject_status == null}">All status</c:if>
                                                    </option>
                                                    <option value="0">Inactive</option>
                                                    <option value="1">Active</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-3" style="margin-right: 10px">
                                            <div class="form-group" style="height: 20px">
                                                <input type="text" class="form-control shadow-sm" name="subject_name" id="input-search-title"
                                                <c:if test="${subject_name != null}">value="${subject_name}"</c:if>
                                                <c:if test="${subject_name == null}">placeholder="Search..."</c:if> 
                                                    style="height: 48px;" >
                                            </div>
                                        </div>
                                        <div style="margin-right: 10px">
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="submit" id="btn-search-name">
                                                <i class="ti-search" style="height: 20px"></i>
                                            </button>
                                        </div>              
                                        <div style="margin-right: 10px" shadow-sm>
                                            <button class="btn btn-outline-secondary text-dark" type="button" onclick="callBack()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="mdi mdi-filter-remove-outline"></i>
                                            </button>
                                        </div>
                                        <div>
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="callAdd()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="ti-plus"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                                <div class="col-md-12 grid-margin stretch-card" id="myList" >
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Subject List</h4>
                                            <p class="card-description">
                                            </p>
                                            <div class="table-responsive">
                                                <table class="table table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th style="text-align: center;" >Subject Code</th>
                                                            <th>Subject Name</th>
                                                            <th style="text-align: center;" >Author ID</th>
                                                            <th style="text-align: center;">Status</th>
                                                            <th style="text-align: center;">Detail</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${requestScope.listSearch}" var="temp">
                                                        <tr>
                                                            <td style="text-align: center;" >${temp.getSubject_code()}</td>
                                                            <td>${temp.getSubject_name()}</td>
                                                            <td style="text-align: center;" >${temp.getAuthor_id()}</td>
                                                            <td style="text-align: center;">
                                                                <c:if test="${temp.getStatus() == 1}">
                                                                    <span class="btn btn-inverse-success btn-fw" style="text-align: center; border-radius: 20px; width: 100px">Active</span>
                                                                </c:if>
                                                                <c:if test="${temp.getStatus() == 0}">
                                                                    <span class="btn btn-inverse-danger btn-fw" style="text-align: center; border-radius: 20px; width: 100px">In Active</span>
                                                                </c:if>
                                                            </td>
                                                            <td style="text-align: center;">
                                                                <a type="button" class="btn btn-icon" href="subject?go=subject-detail&subject_id=${temp.getId()}" id="btn-add-setting">
                                                                    <i class="mdi mdi-format-list-bulleted"></i>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row">
                                            <form>
                                                <div class=" col-md-12 template-demo">
                                                    <div class="btn-group " role="group" aria-label="Basic example" >
                                                        <c:if test="${tag > 1}">
                                                            <a href="subject?index=${1}" class="btn btn-outline-secondary text-dark">
                                                                <i class="mdi mdi-skip-backward"></i></a>
                                                            </c:if>
                                                            <c:forEach begin="1" end="${endPage}" var="i">
                                                            <a href="subject?index=${i}" class="${tag == i?"btn btn-primary":"btn btn-outline-secondary text-dark"}">${i}</a>
                                                        </c:forEach>
                                                        <c:if test="${tag < endPage}">
                                                            <a href="subject?index=${endPage}" class="btn btn-outline-secondary text-dark"><i class="mdi mdi-skip-forward"></i></a>
                                                            </c:if>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
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
// Mong muốn của chúng ta
                                                    Validator({
                                                        form: '#form-add',
                                                        formGroupSelector: '.form-group',
                                                        errorSelector: '.form-message',
                                                        rules: [
                                                            Validator.isRequired('#subject_code', 'Vui lòng nhập Subject Code!'),
                                                            Validator.isRequired('#subject_name', 'Vui lòng nhập Subject Name!'),
                                                            Validator.isRequired('#subject_authorID', 'Vui lòng nhập Author ID!'),
                                                            Validator.minLength('#subject_authorID', 5),
                                                            Validator.isRequired('input[name="subject_status"]', 'Vui lòng chon Status Subject!')
                                                        ]
//                                                                onSubmit: function (data) {
//                                                                    // Call API
//                                                                    console.log(data);
//                                                                }
                                                    });
                                                });
        </script>
        <!--Xu li alert and dong mo form-->
        <script>

            function callAdd() {
                window.location.href = "/swp-project/admin/subject?go=subject-add"
            }

            function callBack() {
                window.location.href = "/swp-project/admin/subject"
            }

            function submitForm() {
                document.getElementById("form-search").submit();
            }

        </script>

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
