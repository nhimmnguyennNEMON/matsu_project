<%-- 
    Document   : UpdateUserProfile
    Created on : May 17, 2022, 6:00:50 PM
    Author     : SY NGUYEN
--%>


<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Subject Setting List</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"
            />
        <style>
            .hinhanh {margin-left: 60px;}
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
                    <div class="content-wrapper" id="content">
                        <div class="row" id="list">
                            <div class="col-md-12 grid-margin stretch-card " id="mySearch">
                                <div class="col-md-2" style="margin-right: 30px">
                                    <div>
                                        <select class="form-control" onchange="searchBySubjectCode(this, 1)" id="search-subject-code" style="height: 48px">
                                            <option value="0">All Subject Code</option>
                                            <c:forEach items="${requestScope.subjectList}" var="temp" >
                                                <option value="${temp.id}">${temp.subject_code}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2" style="margin-right: 30px">
                                    <div>
                                        <select class="form-control" onchange="searchByType(this, 1)" id="search-type" style="height: 48px">
                                            <option value="0">All Setting Type</option>
                                            <c:forEach items="${requestScope.listType}" var="t" >
                                                <option value="${t}"><c:out value="${sessionScope[t]}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2" style="margin-right: 30px;">
                                    <div>
                                        <select class="form-control" onchange="searchByStatus(this, 1)" id="search-status" style="height: 48px"> 
                                            <option value="3">All status</option>
                                            <option value="0">Inactive</option>
                                            <option value="1">Active</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-4" style="margin-right: 10px">
                                    <div class="form-group" style="height: 20px">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="input-search-title" style="height: 48px;" >
                                            <button class="btn btn-outline-secondary text-dark" id="btn-search-name" onclick="searchByTitle()" ><i class="ti-search" style="height: 20px"></i></button>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <button class="btn btn-outline-secondary text-dark" type="button" onclick="add()" id="btn-add-setting" >
                                        <i class="ti-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="myList">
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Subject Setting List</h4>
                                        <p class="card-description">
                                        </p>
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th style="text-align: center;">Order</th>
                                                        <th style="text-align: center;" >Subject Code</th>
                                                        <th>Setting Type</th>
                                                        <th>Setting Title</th>
                                                        <th>Setting Value</th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Detail</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                            <td style="text-align: center;" >${temp.display_order}</td>
                                                            <td style="text-align: center;" >${temp.subject_id.subject_code}</td>
                                                            <c:forEach items="${requestScope.listType}" var="t" >
                                                                <fmt:parseNumber var = "type_id" type = "number" value = "${t}" />
                                                                <c:if test="${temp.type_id == type_id}">
                                                                    <td><c:out value="${sessionScope[t]}"/></td>
                                                                </c:if>
                                                            </c:forEach>
                                                            <td>${temp.setting_title}</td>
                                                            <td>${temp.setting_value}</td>
                                                            <c:if test="${temp.status==0}">
                                                                <td style="text-align: center;">
                                                                    <select id="my-status" class="btn btn-inverse-danger btn-fw" onchange="changeStatus(${temp.id}, this, 0, 1)" style="text-align: center; border-radius: 20px;">
                                                                        <option value="0" selected="" style="background: white; color:#F73122;"><label >In Active</label></option>
                                                                        <option value="1" style="background: white; color:#6fb5ab;"><label> Active</label></option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${temp.status==1}">
                                                                <td style="text-align: center;">
                                                                    <select id="my-status" class="btn btn-inverse-success btn-fw" onchange="changeStatus(${temp.id}, this, 1, 1)" style="text-align: center; border-radius: 20px;">
                                                                        <option value="0" style="background: white; color:#F73122;"><label >In Active</label></option>
                                                                        <option value="1" selected style="background: white; color:#6fb5ab;"><label> Active</label></option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <td style="text-align: center;">
                                                                <button onclick="detail(${temp.id})" type="button" class="btn btn-icon" type="button" id="btn-add-setting">
                                                                    <i class="mdi mdi-format-list-bulleted"></i>
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 template-demo">
                                                <div class="btn-group" role="group" aria-label="Basic example" >
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
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script>

                                                            function searchByTitle() {
                                                                var setting_title = $("#input-search-title").val();
                                                                if (!setting_title) {
                                                                    return null;
                                                                } else {
                                                                    $.ajax({
                                                                        type: "POST",
                                                                        url: "/swp-project/admin/subject-setting",
                                                                        data: {
                                                                            go: "search-by-title",
                                                                            setting_title: setting_title,
                                                                            index: 1,
                                                                        }, // serializes the form's elements.
                                                                        success: function (response) {
                                                                            document.getElementById("myList").remove();
                                                                            var table = document.getElementById("list");
                                                                            table.innerHTML += response;
                                                                        },
                                                                    });
                                                                }

                                                            }

                                                            function searchByType(obj, index) {
                                                                var value = obj.value;
                                                                var index1 = index;
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/subject-setting",
                                                                    data: {
                                                                        go: "search-by-type",
                                                                        value: value,
                                                                        index: index1
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        $("#list").append(response);
                                                                    },
                                                                });
                                                            }

                                                            function changeStatus(id, obj, st, index) {
                                                                var status = $('#my-status').val();
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "Are you really want to change status?",
                                                                    icon: "warning",
                                                                    buttons: true,
                                                                    dangerMode: true,
                                                                })
                                                                        .then((willDelete) => {
                                                                            if (willDelete) {
//                                                                                swal("Status has been changed!", {
//                                                                                    icon: "success",
//                                                                                });
                                                                                new Notify({
                                                                                    status: "success",
                                                                                    title: "Success",
                                                                                    text: "Subject Setting Status Changed Successfully!",
                                                                                    effect: "slide",
                                                                                    speed: 300,
                                                                                    customClass: "",
                                                                                    customIcon: "",
                                                                                    showIcon: true,
                                                                                    showCloseButton: true,
                                                                                    autoclose: true,
                                                                                    autotimeout: 3000,
                                                                                    gap: 20,
                                                                                    distance: 20,
                                                                                    type: 1,
                                                                                    position: "right top",
                                                                                    customWrapper: "",
                                                                                });
                                                                                $.ajax({
                                                                                    type: "POST",
                                                                                    url: "/swp-project/admin/subject-setting",
                                                                                    data: {
                                                                                        go: "update-status",
                                                                                        status: status,
                                                                                        id: id,
                                                                                        index: index
                                                                                    }, // serializes the form's elements.
                                                                                    success: function (response) {
                                                                                        document.getElementById("myList").remove();
                                                                                        var list = document.getElementById("list");
                                                                                        list.innerHTML += response;
                                                                                    },
                                                                                });
                                                                            } else {
                                                                                swal("Your status is not change!");
                                                                                document.getElementById("my-status").value = st;
                                                                            }
                                                                        });
                                                            }

                                                            function searchByStatus(obj, index) {
                                                                var value = obj.value;
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/subject-setting",
                                                                    data: {
                                                                        go: "search-by-status",
                                                                        value: value,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        var list = document.getElementById("list");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }

                                                            function searchBySubjectCode(obj, index) {
                                                                var value = obj.value;
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/subject-setting",
                                                                    data: {
                                                                        go: "search-by-subject-code",
                                                                        value: value,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        var list = document.getElementById("list");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }

                                                            function detail(id) {
                                                                window.location.href = "/swp-project/admin/subject-setting?go=detail&id=" + id;
                                                            }

                                                            function add() {
                                                                window.location.href = "/swp-project/admin/subject-setting?go=add";
                                                            }

                                                            function pageStatus(status, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/subject-setting",
                                                                    data: {
                                                                        go: "search-by-status",
                                                                        value: status,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        var list = document.getElementById("list");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function pageTitle(title, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/subject-setting",
                                                                    data: {
                                                                        go: "search-by-title",
                                                                        setting_title: title,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        var list = document.getElementById("list");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function pageType(type, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/setting",
                                                                    data: {
                                                                        go: "search-by-type",
                                                                        value: type,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        var list = document.getElementById("list");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }
                                                            function pageSubject(subject_id, index) {
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/swp-project/admin/subject-setting",
                                                                    data: {
                                                                        go: "search-by-subject-code",
                                                                        value: subject_id,
                                                                        index: index,
                                                                    }, // serializes the form's elements.
                                                                    success: function (response) {
                                                                        document.getElementById("mySearch").remove();
                                                                        document.getElementById("myList").remove();
                                                                        var list = document.getElementById("list");
                                                                        list.innerHTML += response;
                                                                    },
                                                                });
                                                            }

                                                            function paging(index) {
                                                                $.ajax({
                                                                    type: "GET",
                                                                    url: "/swp-project/admin/subject-setting",
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

        <script src="https://unpkg.com/flowbite@1.4.7/dist/flowbite.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- End custom js for this page-->
    </body>
</html>
