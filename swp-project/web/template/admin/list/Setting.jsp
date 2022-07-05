<%-- 
    Document   : Setting
    Created on : May 21, 2022, 4:54:51 PM
    Author     : admin
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
        <title>Setting List</title>
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
                                        <select class="search-by-type form-control" onchange="searchByType(this, 1)" id="search-type" style="height: 48px">
                                            <option value="0">All Setting Type</option>
                                            <c:forEach items="${requestScope.listType}" var="t" >
                                                <c:if test="${sessionScope[t]!= null}">
                                                <option value="${t}"><c:out value="${sessionScope[t]}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2" style="margin-right: 30px">
                                    <select class="search-by-status form-control" onchange="searchByStatus(this, 1)" id="search-status" style="height: 48px"> 
                                        <option value="3">All status</option>
                                        <option value="0">Inactive</option>
                                        <option value="1">Active</option>
                                        <option value ="2">Deactive</option>

                                    </select>
                                </div>
                                <div class="col-md-6 " style="margin-right: 10px">
                                    <div class="form-group" style="height: 20px">
                                        <div class="input-group ">
                                            <input type="text" class="form-control col-md-8" id="input-search-name" style="height: 48px;" >
                                            <button class="btn btn-outline-secondary text-dark col-md-1" id="btn-search-name" onclick="searchName()" ><i class="ti-search" style="height: 20px"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <button  class="add-btn btn btn-outline-secondary text-dark col-md-1" id="btn-add-setting" onclick="add()">
                                    <i class="ti-plus"></i>
                                </button>

                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="table">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Setting List</h4>
                                        <p class="card-description">
                                        </p>
                                        <div class="table-responsive">
                                            <table class="table table-hover sortable">
                                                <thead>
                                                    <tr>
                                                        <th style="text-align: center;" >ID</th>
                                                        <th style="text-align: center;">Setting Type</th>
                                                        <th style="text-align: center;" >Name</th>
                                                        <th style="text-align: center;">Order</th>
                                                        <th style="text-align: center;">Value</th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Detail</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="s" >
                                                        <tr>
                                                            <td style="text-align: center;">${s.getSetting_Id()}</td>
                                                            <c:forEach items="${requestScope.listType}" var="t" >
                                                                <fmt:parseNumber var = "type_id" type = "number" value = "${t}" />
                                                                <c:if test="${s.getType_Id()== type_id}">
                                                                    <td><c:out value="${sessionScope[t]}"/></td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <td style="text-align: center;">${s.getSetting_Name()}</td>
                                                            <td style="text-align: center;">${s.getOrder()}</td>
                                                            <td style="text-align: center;">${s.getSetting_Value()}</td>
                                                            <c:if test="${s.getStatus()==0}">
                                                                <td style="text-align: center;">
                                                                    <select class="btn btn-inverse-success btn-fw" onchange="changeStatus(${s.getSetting_Id()}, this, 0, 1)" style="text-align: center; border-radius: 20px;" id="status-change">
                                                                        <option value="0" selected style="color: #F95F53;" ><label >Inactive</label></option>
                                                                        <option value="1" style="background: white; color:#1FBEFF;" ><label> Active</label></option>
                                                                        <option value="2" style="background: white; color:#F73122;" ><label>Deactive</label></option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${s.getStatus()==1}">
                                                                <td style="text-align: center;">
                                                                    <select class="btn btn-inverse-success btn-fw" onchange="changeStatus(${s.getSetting_Id()}, this, 1, 1)" style="text-align: center; border-radius: 20px;" id="status-change">
                                                                        <option value="0"  style="color: #F95F53;" ><label >Inactive</label></option>
                                                                        <option value="1"selected style="background: white; color:#1FBEFF;" ><label> Active</label></option>
                                                                        <option value="2" style="background: white; color:#F73122;" ><label>Deactive</label></option>
                                                                    </select>

                                                                </td>
                                                            </c:if>
                                                            <c:if test="${s.getStatus()==2}">
                                                                <td style="text-align: center;">
                                                                    <select class="btn btn-inverse-success btn-fw" onchange="changeStatus(${s.getSetting_Id()}, this, 2, 1)" style="text-align: center; border-radius: 20px;" id="status-change">
                                                                        <option value="0"  style="color: #F95F53;" ><label >Inactive</label></option>
                                                                        <option value="1" style="background: white; color:#1FBEFF;" ><label> Active</label></option>
                                                                        <option value="2"selected style="background: white; color:#F73122;" ><label>Deactive</label></option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <td style="text-align: center;">
                                                                <button type="button" class="btn btn-icon" type="button" data-modal-toggle="defaultModal_detail" onclick="detail(${s.getSetting_Id()})">
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
        <script src="sorttable.js" type="text/javascript"></script>
        <script src="assets/assets_template/js/Chart.roundedBarCharts.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

        <script>
                                                            function add() {
                                                                window.location.href = "/swp-project/admin/setting?go=add";
                                                            }
                                                            function detail(id) {
                                                                window.location.href = "/swp-project/admin/setting?go=detail&setting_id=" + id;
                                                            }
                                                            function searchName() {
                                                                var setting_name = $("#input-search-name").val();
                                                                if (!setting_name) {
                                                                    return null;
                                                                } else {
                                                                    $.ajax({
                                                                        type: "POST",
                                                                        url: "/swp-project/admin/setting",
                                                                        data: {
                                                                            go: "setting-search-by-name",
                                                                            setting_name: setting_name,
                                                                            index: 1,
                                                                        }, // serializes the form's elements.
                                                                        success: function (response) {
                                                                            document.getElementById("list").remove();
                                                                            var list = document.getElementById("content");
                                                                            list.innerHTML += response;
                                                                        },
                                                                    });
                                                                }

                                                            }
                                                            function searchByType(obj, index) {
                                                                var value = obj.value;
                                                                var index1 = index;
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/setting",
                                                                    data: {
                                                                        go: "setting-search-by-type",
                                                                        value: value,
                                                                        index: index1,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("list").remove();
                                                                        var list = document.getElementById("content");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function changeStatus(id, obj, st, index) {
                                                                var status = obj.value;
                                                                var s;
                                                                if (status == 0)
                                                                    s = "Inactive";
                                                                if (status == 1)
                                                                    s = "Active";
                                                                if (status == 2)
                                                                    s = "Deactive";
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "Are you sure you want to change the status of the SETTING ID " + id + " ?",
                                                                    icon: "warning",
                                                                    buttons: true,
                                                                    dangerMode: true,
                                                                })
                                                                        .then((willUpdate) => {
                                                                            if (willUpdate) {
                                                                                $.ajax({
                                                                                    type: "POST",
                                                                                    url: "/swp-project/admin/setting",
                                                                                    data: {
                                                                                        go: "setting-update-status",
                                                                                        status: status,
                                                                                        id: id,
                                                                                        index: index,
                                                                                    }, // serializes the form's elements.
                                                                                    success: function (response) {
                                                                                        document.getElementById("list").remove();
                                                                                        var list = document.getElementById("content");
                                                                                        list.innerHTML += response;
                                                                                    },
                                                                                });
                                                                                swal("Changed the status of the SETTING with ID " + id + " to " + s + "", {
                                                                                    icon: "success",
                                                                                });
                                                                            } else {
                                                                                document.getElementById("status-change").value = st;
                                                                                swal("Your SETTING is safe!");
                                                                            }
                                                                        });

                                                            }
                                                            function searchByStatus(obj, index) {
                                                                var value = obj.value;
                                                                var index1 = index;
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/setting",
                                                                    data: {
                                                                        go: "setting-search-by-status",
                                                                        value: value,
                                                                        index: index1,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("list").remove();
                                                                        var list = document.getElementById("content");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function page(status, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/setting",
                                                                    data: {
                                                                        go: "setting-search-by-status",
                                                                        value: status,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("list").remove();
                                                                        var list = document.getElementById("content");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function pageName(name, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/setting",
                                                                    data: {
                                                                        go: "setting-search-by-name",
                                                                        setting_name: name,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("list").remove();
                                                                        var list = document.getElementById("content");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function pagetype(type, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/setting",
                                                                    data: {
                                                                        go: "setting-search-by-type",
                                                                        value: type,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("list").remove();
                                                                        var list = document.getElementById("content");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function paging(index) {
                                                                $.ajax({
                                                                    type: "GET",
                                                                    url: "/swp-project/admin/setting",
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
        </script>
    </body>
</html>
