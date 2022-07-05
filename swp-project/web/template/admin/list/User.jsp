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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>User List</title>
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
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />

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
                    <div class="content-wrapper" id="content">
                        <div class="row" id="list">
                            <div class="col-md-12 grid-margin stretch-card ">
                                <div class="col-md-2" style="margin-right: 30px">
                                    <div>
                                        <select class="form-control" onchange="searchByRole(this,1)" id="search-role" style="height: 48px">
                                            <option value="0">All Role</option>
                                            <c:forEach items="${requestScope.listUserRole}" var="t" >
                                                <option value="${t}"><c:out value="${sessionScope[t]}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2" style="margin-right: 30px">
                                    <div>
                                        <select class="form-control" onchange="searchByStatus(this,1)" id="search-status" style="height: 48px"> 
                                            <option value="2">All status</option>
                                            <option value="1">Active</option>
                                            <option value="0">Deactive</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6 " style="margin-right: 10px">
                                    <div class="form-group" style="height: 20px">
                                        <div class="input-group ">

                                            <input type="text" class="form-control col-md-8" id="input-search" style="height: 48px;" >
                                            <select class="form-control col-md-2" id="choice-search" style="height: 48px"> 
                                                <option value="0">Name</option>
                                                <option value="1">Phone number</option>
                                                <option value="2">Mail</option>
                                            </select>
                                            <button class="btn btn-outline-secondary text-dark col-md-1" id="btn-search-name" onclick="search()" ><i class="ti-search" style="height: 20px"></i></button>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="table">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">User List</h4>
                                        <p class="card-description">
                                        </p>
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th style="text-align: center;" >ID</th>
                                                        <th style="text-align: center;">Name</th>
                                                        <th style="text-align: center;" >Email</th>
                                                        <th style="text-align: center;">Mobile</th>
                                                        <th style="text-align: center;">Role</th>
                                                        <th style="text-align: center;">Last Login</th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Detail</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                            <td style="text-align: center;" >${temp.getUser_id()}</td>
                                                            <td>${temp.getFull_name()}</td>
                                                            <td style="text-align: center;" >${temp.getEmail()}</td>
                                                            <td style="text-align: center;" >${temp.getPhone_number()}</td>
                                                            <c:forEach items="${requestScope.listUserRole}" var="t" >
                                                                <fmt:parseNumber var = "role" type = "number" value = "${t}" />
                                                                <c:if test="${temp.getRole_id()== role}">
                                                                    <td><c:out value="${sessionScope[t]}"/></td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <td style="text-align: center;" > 
                                                                ${temp.getTime_Create_Token()}
                                                            </td>
                                                            <td style="text-align: center;">
                                                                <c:if test="${temp.isStatus() == true}">
                                                                    <select class="btn btn-inverse-success btn-fw" id="status-change" onchange="updateStatus(${temp.getUser_id()}, this, true, 1)" style="text-align: center; border-radius: 20px;">
                                                                        <option  value="true" href="#" selected="" style="background: white; color:#1FBEFF;">Active</option>
                                                                        <option  value="false" href="#" style="background: white; color:#F73122;">Deactive</option>
                                                                    </select>
                                                                </c:if>

                                                                <c:if test="${temp.isStatus() == false}">
                                                                    <select class="btn btn-inverse-danger btn-fw" id="status-change" onchange="updateStatus(${temp.getUser_id()}, this, false, 1)" style="text-align: center; border-radius: 20px;">
                                                                        <option  value="true" href="#" style="background: white; color:#1FBEFF;">Active</option>
                                                                        <option  value="false" href="#" selected="" style="background: white; color:#F73122;">Deactive</option>
                                                                    </select>
                                                                </c:if>
                                                            </td>
                                                            <td style="text-align: center;">
                                                                <button type="button" class="btn btn-icon" type="button" data-modal-toggle="defaultModal_detail" onclick="detail(${temp.getUser_id()})">
                                                                    <i class="mdi mdi-format-list-bulleted"></i>
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-10"></div>
                                            <div class=" col-md-2 template-demo">
                                                <div class="btn-group " role="group" aria-label="Basic example" >
                                                    <c:if test="${(requestScope.index-1)>1}" >
                                                        <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(1)">First</button>
                                                    </c:if>
                                                    <c:forEach begin="${requestScope.index-1}" end="${requestScope.index}" var="i">
                                                        <c:if test="${i>=1}">
                                                            <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${i})"><c:out value="${i}"></c:out></button>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:forEach begin="${requestScope.index+1}" end="${requestScope.index+1}" var="i">
                                                        <c:if test="${i<=requestScope.end}">
                                                            <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${i})"><c:out value="${i}"></c:out></button>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:if test="${requestScope.index+1<requestScope.end}">
                                                        <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${requestScope.end})">Last</button>
                                                    </c:if>  
                                                </div>
                                            </div>
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


        <script>


            function paging(index) {
                $.ajax({
                    type: "GET",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "paging",
                        index: index,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function updateStatus(id, obj, st, index) {
                var status = obj.value;
                var s;
                                                               
                                                                if (status == true)
                                                                    s = "Active";
                                                                if (status == false)
                                                                    s = "Deactive";
                swal({
                    title: "Are you sure?",
                    text: "Are you sure you want to change the status of the User ID " + id + " ?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                $.ajax({
                                    type: "POST",
                                    url: "/swp-project/admin/user",
                                    data: {
                                        go: "user-update-status",
                                        status: status,
                                        id: id,
                                    }, // serializes the form's elements.
                                    success: function (response) {
                                        document.getElementById("table").remove();
                                        var list = document.getElementById("list");
                                        list.innerHTML += response;
                                    },
                                });
                                swal("Changed the status of the User with ID " + id + " to " + s + "", {
                                    icon: "success",
                                });
                            } else {
                                document.getElementById("status-change").value = st;
                                swal("Your User is safe!");
                            }
                        });

            }
            function searchByRole(obj) {
                var role_id = obj.value;
                console.log(role_id);
                $.ajax({
                    type: "POST",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "search-by-role",
                        role_id: role_id,
                        index:1,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function searchByStatus(obj) {
                var status = obj.value;
                $.ajax({
                    type: "POST",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "search-by-status",
                        status: status,
                        index:1,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function search() {
                var text = document.getElementById("input-search").value;
                var type = document.getElementById("choice-search").value;
                console.log(text + ":" + type);
                $.ajax({
                    type: "POST",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "search",
                        type: type,
                        text: text,
                        index:1,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function pagerole(role_id , index){
                $.ajax({
                    type: "POST",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "search-by-role",
                        role_id: role_id,
                        index:index,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function pagestatus(status,index){
                $.ajax({
                    type: "POST",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "search-by-status",
                        status: status,
                        index:index,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function page(text,type,index){
                $.ajax({
                    type: "POST",
                    url: "/swp-project/admin/user",
                    data: {
                        go: "search",
                        type: type,
                        text: text,
                        index:index,
                    }, // serializes the form's elements.
                    success: function (response) {
                        document.getElementById("list").remove();
                        var list = document.getElementById("content");
                        list.innerHTML += response;
                    },
                });
            }
            function detail(id) {
                window.location.href = "/swp-project/admin/user?go=detail&id=" + id;
            }
            
        </script>
        <script src="https://unpkg.com/flowbite@1.4.7/dist/flowbite.js"></script>
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>



        <!-- End custom js for this page-->
    </body>
</html>
