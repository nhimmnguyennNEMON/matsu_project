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
        <title>Iteration Detail</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
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
                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row grid-margin">
                                            <div class="col-md-2">
                                                <p class="card-title">Iteration Detail</p>
                                            </div>
                                            <div class="col-md-9"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                </button>
                                            </div>
                                            <code>- Note - Iteration Detail helps users (Admin or Manager) to edit the iteration's information as well as change the active status of that iteration.</code>
                                        </div>
                                        <c:if test="${detail != null}">
                                            <form class="forms-sample" id="form-detail" onsubmit="return false" action="iteration" method="POST">
                                                <input type="hidden" name="go" value="update">
                                                <input type="hidden" name="id" value="${detail.getId()}">
                                                <div class="form-group">
                                                    <label for="subject_code">Subject Code<code>(*)</code></label>
                                                    <select class="form-control" id="subject_code" style="height: 48px" name="subject_code">
                                                        <option value="${detail.getSubject_id()}">${detail.getSubject_code()}</option>
                                                        <c:forEach items="${subjectList}" var="temp" >
                                                            <option value="${temp.id}">${temp.subject_code}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputPassword1">Iteration Name<code>(*)</code></label>
                                                    <input type="text" class="form-control" id="iteration_name" style="height: 48px" name="iteration_name" value="${detail.getIteration_name()}">
                                                    <span class="form-message"></span>
                                                    <c:if test="${error != null}">
                                                        <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputConfirmPassword1">Duration<code>(*)</code></label>
                                                    <li class="nav-item d-none d-lg-block">
                                                        <div id="datepicker-popup" class="input-group date datepicker navbar-date-picker">
                                                            <span class="input-group-addon input-group-prepend border-right">
                                                                <span class="icon-calendar input-group-text calendar-icon text-dark" style="height: 48px"></span>
                                                            </span>
                                                            <input type="text" class="form-control" id="duration" style="height: 48px" name="duration">
                                                        </div>
                                                    </li>
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-2" for="exampleInputConfirmPassword1">Iteration Status<code>(*)</code></label>
                                                    <c:if test="${detail.getStatus_iter() == 1}">
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="iteration_status" value="1" checked="">
                                                                Active
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="iteration_status" value="0" >
                                                                Inactive
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="iteration_status" value="2" >
                                                                Deactive
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${detail.getStatus_iter() == 0}">
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="iteration_status" value="1" >
                                                                Active
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="iteration_status" value="0" checked="" >
                                                                Inactive
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="iteration_status" value="2">
                                                                Deactive
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                </div>
                                                <div>
                                                    <button class="btn btn-light me-2" type="reset" >Clean</button>
                                                    <button class="btn btn-primary me-2" onclick="updateSubject()" >Update</button>
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
        <script src="../assets/js/validator.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <!--validate input-->
        <script>
                                                        document.addEventListener('DOMContentLoaded', function () {
                                                            Validator({
                                                                form: '#form-detail',
                                                                formGroupSelector: '.form-group',
                                                                errorSelector: '.form-message',
                                                                rules: [
                                                                    Validator.isRequired('#subject_code', 'Please input Subject Code!'),
                                                                    Validator.isRequired('#iteration_name', 'Please input Iteration Name!'),
                                                                    Validator.isCharacterSpecial('#iteration_name'),
                                                                    Validator.isRequired('#duration', 'Please input Duration!'),
                                                                    Validator.minDate('#duration'),
                                                                    Validator.isRequired('input[name="iteration_status"]', 'Vui lòng chọn Status Subject!')
                                                                ]
                                                            });
                                                        });
        </script>
        <!--xu y alert dong mo file-->
        <c:if test="${success != null}">
            <script>
                window.onload = function ()
                {
                    alert("success", "Success!", "${success}!")
                };
            </script>
        </c:if>

        <c:if test="${error != null}">
            <script>
                window.onload = function ()
                {
                    alert("error", "Fail!", "${error}!")
                };
            </script>
        </c:if>
        <script>
            function alert(_status, _title, _text) {
                new Notify({
                    status: _status,
                    title: _title,
                    text: _text,
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

            }
            function updateSubject() {
                swal({
                    title: "Are you sure?",
                    text: "Update infomation Iteration",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                document.getElementById("form-detail").submit();
                            } else {
                                window.location.reload();
                            }
                        });
            }

            function deleteSubject(id)
            {
                swal({
                    title: "Are you sure?",
                    text: "Once deleted, you will not be able to recover this iteration!",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                window.location.href = "/swp-project/manager/iteration?go=delete&id=" + id;
                            }
                        });
            }

            function tai_lai_trang() {
                location.reload();
            }

            function skipSubmit() {
                document.querySelector("#form-detail").event.preventDefault();
            }

            function callBack() {
                window.location.href = "/swp-project/manager/iteration"
            }

        </script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <!-- End custom js for this page-->
    </body>
</html>
