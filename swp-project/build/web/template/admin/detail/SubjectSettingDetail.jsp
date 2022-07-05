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
        <title>Subject Setting Detail</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />

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
                                    <div class="d-sm-flex align-items-center justify-content-between border-bottom" 
                                         style="margin-bottom: 30px">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link" href="subject?go=subject-detail" role="tab" 
                                                   aria-selected="false">Subject Setting Detail</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Subject Setting Detail</h4>
                                        <c:if test="${subjectSetting != null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="subject-setting">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${subjectSetting.id}"> 
                                                <div class="form-group">
                                                    <div>
                                                        <label for="search-subject-code">Subject Code</label>
                                                        <select class="form-control" id="search-subject-code" name="subject-code" 
                                                                style="height: 48px">
                                                            <c:forEach items="${subjectList}" var="temp" >
                                                                <option 
                                                                    ${temp.id == subjectSetting.subject_id.id ? "selected" : ""}
                                                                    value="${temp.id}">${temp.subject_code}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="search-type">Subject Setting Type</label>
                                                        <select class="form-control" id="search-type" name="type-id" 
                                                                style="height: 48px">
                                                            <c:forEach items="${requestScope.listType}" var="t" >
                                                                <option 
                                                                    ${t eq subjectSetting.type_id?"selected":""}
                                                                    value="${t}"><c:out value="${sessionScope[t]}"/></option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputPassword1">Subject Setting Title</label>
                                                    <input type="text" class="form-control" id="exampleInputPassword1" 
                                                           name="setting-title" value="${subjectSetting.setting_title}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputConfirmPassword1">Subject Setting Value</label>
                                                    <input type="text" class="form-control" id="exampleInputConfirmPassword1" 
                                                           name="setting-value" value="${subjectSetting.setting_value}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="display-order">Display Order</label>
                                                    <input type="number" class="form-control" id="display-order" 
                                                           name="display-order" value="${subjectSetting.display_order}">
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-2" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="setting-status" id="optionsRadios1" value="1" 
                                                                   ${subjectSetting.status eq 1?"checked":""}>
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="setting-status" id="optionsRadios2" value="0" 
                                                                   ${subjectSetting.status eq 0?"checked":""}>
                                                            Inactive
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-8"></div>
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancelDelete()">Cancel</button>
                                                        <button type="button" class="btn btn-primary me-2" onclick="updateSubjectSetting(${subjectSetting.id})">Update</button>
                                                        <button class="btn btn-danger me-2" type="button"
                                                                onclick="deleteSubjectSetting(${subjectSetting.id})">Delete</button>
                                                    </div>
                                                </div>
                                            </form>

                                        </c:if>

                                        <c:if test="${subjectSetting == null}">
                                            No record to display
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

        <script>

            function updateSubjectSetting(id) {
                swal("Done!", "Update setting successfull!", "success")
                    .then((value) => {
                        document.getElementById("form-detail").submit();
                    });
            }

            function deleteSubjectSetting(id)
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
                                swal("Done!", "Your status has been deleted!", "success")
                                    .then((value) => {
                                      window.location.href = "/swp-project/admin/subject-setting?go=delete&id=" + id;
                                    });
                            } else {
                                swal("Your setting is still not deleted!");
                            }
                        });
            }

            function cancelDelete() {
                window.location.href = "/swp-project/admin/subject-setting";
            }
        </script>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    </body>
</html>
