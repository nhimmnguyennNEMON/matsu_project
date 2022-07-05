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
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Iteration Add </title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
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
                            <div class="col-md-12 grid-margin stretch-card" id="add-subject" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row grid-margin">
                                            <div class="col-md-2">
                                                <p class="card-title">Add Iteration</p>
                                            </div>
                                            <div class="col-md-9"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                </button>
                                            </div>
                                            <code>- Note - Iteration Add helps users (Admin or Manager) to add new iteration to individual projects of each team.</code>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="iteration" method="POST">
                                            <input type="hidden" name="go" value="add">
                                            <div class="form-group">
                                                <label for="subject_code">Subject Code<code>(*)</code></label>
                                                <select class="form-control" id="subject_code" style="height: 48px" name="subject_code">
                                                    <option value="">--- Choose Subject Code ---</option>
                                                    <c:forEach items="${subjectList}" var="temp" >
                                                        <option value="${temp.id}">${temp.subject_code}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputPassword1">Iteration Name<code>(*)</code></label>
                                                <input type="text" class="form-control" id="iteration_name" style="height: 48px" name="iteration_name" placeholder="Iteration Name">
                                                <span class="form-message"></span>
                                                <c:if test="${error != null}">
                                                    <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label for="exampleInputConfirmPassword1">Duration<code>(*)</code></label>
                                                <li class="nav-item d-none d-lg-block" >
                                                    <div id="datepicker-popup" class="input-group date datepicker navbar-date-picker">
                                                        <span class="input-group-addon input-group-prepend border-right">
                                                            <span class="icon-calendar input-group-text calendar-icon text-dark" style="height: 48px"></span>
                                                        </span>
                                                        <input type="text" class="form-control" id="duration" style="height: 48px" name="duration">
                                                    </div>
                                                </li>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <div class="row">
                                                    <label class="col-md-2" for="exampleInputConfirmPassword1">Iteration Status<code>(*)</code></label>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="iteration_status" id="optionsRadios1" value="1" checked="">
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="iteration_status" id="optionsRadios2" value="0" >
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div><span class="form-message"></span>
                                            </div>
                                            <div>
                                                <button type="reset" class="btn btn-light me-2">Clean</button>
                                                <button class="btn btn-primary me-2" onclick="submitForm()" >Add</button>
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
        <!--validate input-->
        <c:if test="${error != null}">
            <script>
                window.onload = function ()
                {
                    alert("error", "Fail!", "${error}!")
                };
            </script>
        </c:if>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                Validator({
                    form: '#form-add',
                    formGroupSelector: '.form-group',
                    errorSelector: '.form-message',
                    rules: [
                        Validator.isRequired('#subject_code', 'Vui lòng nhập Subject Code!'),
                        Validator.isRequired('#iteration_name', 'Vui lòng nhập Iteration Name!'),
                        Validator.isCharacterSpecial('#iteration_name'),
                        Validator.isRequired('#duration', 'Vui lòng nhập Duration!'),
                        Validator.minDate('#duration'),
                        Validator.isRequired('input[name="iteration_status"]', 'Vui lòng chọn Status Iteration!')
                    ]
                });
            });

            function callBack() {
                window.location.href = "/swp-project/manager/iteration"
            }

            function submitForm() {
                var elem = document.querySelector("#form-add");
                var error = elem.classList.contains("invalid");
                var iteration_status;
                var checkstatus = document.getElementsByName("iteration_status");
                for (var i = 0; i < checkstatus.length; i++) {
                    if (checkstatus[i].checked === true) {
                        iteration_status = checkstatus[i].value;
                    }
                }
                var subject_code = document.getElementById("subject_code").value;
                var iteration_name = document.getElementById("iteration_name").value;
                var duration = document.getElementById("duration").value;
                if (!subject_code || !iteration_name || !duration || !iteration_status || error === true)
                {
                    swal("OOPS!", "You need to fill all the fields or the fields invalid.", "warning");
                }
            }

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
        </script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </body>
</html>
