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
        <title>Feature Detail</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
        <style>
            .hinhanh{
                text-align: center;
            }
        </style>
    </head>
    <body>
        <!--UI role admin, managet, teacher------------------------------------>
        <div class="container-scroller">
            <c:if test="${user.getRole_id() == 1 || user.getRole_id() == 2 || user.getRole_id() == 3}">
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
                                                <p class="card-title">Feature Detail</p>
                                            </div>
                                            <div class="col-md-9"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                </button>
                                            </div>
                                            <code>- Note - Feature Detail helps users (Teachers or Students) to edit the feature's information as well as change the active status of that feature.</code>
                                        </div>
                                        <c:if test="${detail != null}">
                                            <form class="forms-sample" id="form-detail" onsubmit="return false" action="feature" method="POST">
                                                <input type="hidden" name="go" value="detail">
                                                <input type="hidden" name="id" value="${detail.getId()}">
                                                <div class="form-group">
                                                    <label for="team_name">Team Code<code>(*)</code></label>
                                                    <select class="form-control" id="subject_code" style="height: 48px" name="team_name">
                                                        <option value="${detail.getId()}">${detail.getTeam_name()} -- ${detail.getClass_code()} -- ${detail.getSubject_code()}</option>
                                                        <c:forEach items="${listTeam}" var="temp" >
                                                            <option value="${temp.getId()}">${temp.getTeam_name()} -- ${temp.getClass_code()} -- ${temp.getSubject_code()}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputPassword1">Feature Name<code>(*)</code></label>
                                                    <input type="text" class="form-control" id="feature_name" style="height: 48px" name="feature_name" value="${detail.getFeature_name()}">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="desc">Feature Note</label>
                                                    <textarea class="form-control" name="desc" id="desc" maxlength="100" style="height: 48px">${detail.getDesc()}</textarea>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-2" for="exampleInputConfirmPassword1">Feature Status<code>(*)</code></label>
                                                    <c:if test="${detail.getStatus() == 1}">
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="feature_status" value="1" checked="">
                                                                Active
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="feature_status" value="0" >
                                                                Inactive
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="feature_status" value="2" >
                                                                Deactive
                                                            </label>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${detail.getStatus() == 0}">
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="feature_status" value="1" >
                                                                Active
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="feature_status" value="0" checked="" >
                                                                Inactive
                                                            </label>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form-check-label">
                                                                <input type="radio" class="form-check-input" name="feature_status" value="2">
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

            <!--UI role student-------------------------------------------->
            <c:if test="${user.getRole_id() == 4}">
                <jsp:include page="../../component/Sidebar_student.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>

                <div class="main-panel" style="width: 100%">        
                    <div class="content-wrapper">
                        <div class="content-wrapper" id="list">
                            <div class="col-sm-12">
                                <div class="home-tab">
                                    <div class="d-sm-flex align-items-center justify-content-between border-bottom" style="margin-bottom: 30px">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link" href="feature" role="tab">Feature List</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"  href="feature?go=add" role="tab">Add Feature</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="row grid-margin">
                                                <div class="col-md-2">
                                                    <p class="card-title">Feature Detail</p>
                                                </div>
                                                <div class="col-md-9"></div>
                                                <div class=" col-md-1 ">
                                                    <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                    </button>
                                                </div>
                                                <code>- Note - Feature Detail helps users (Teachers or Students) to edit the feature's information as well as change the active status of that feature.</code>
                                            </div>
                                            <c:if test="${detail != null}">
                                                <form class="forms-sample" id="form-detail" onsubmit="return false" action="feature" method="POST">
                                                    <input type="hidden" name="go" value="detail">
                                                    <input type="hidden" name="id" value="${detail.getId()}">
                                                    <div class="form-group">
                                                        <label for="team_name">Team Code<code>(*)</code></label>
                                                        <select class="form-control" id="subject_code" style="height: 48px" name="team_name">
                                                            <option value="${detail.getId()}">${detail.getTeam_name()} -- ${detail.getClass_code()} -- ${detail.getSubject_code()}</option>
                                                            <c:forEach items="${listTeam}" var="temp" >
                                                                <option value="${temp.getId()}">${temp.getTeam_name()} -- ${temp.getClass_code()} -- ${temp.getSubject_code()}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <span class="form-message"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="exampleInputPassword1">Feature Name<code>(*)</code></label>
                                                        <input type="text" class="form-control" id="feature_name" style="height: 48px" name="feature_name" value="${detail.getFeature_name()}">
                                                        <span class="form-message"></span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="desc">Feature Note</label>
                                                        <textarea class="form-control" name="desc" id="desc" maxlength="100" style="height: 48px">${detail.getDesc()}</textarea>
                                                    </div>
                                                    <div class="form-group row" style="display: none">
                                                        <label class="col-md-2" for="exampleInputConfirmPassword1">Feature Status<code>(*)</code></label>
                                                        <c:if test="${detail.getStatus() == 1}">
                                                            <div class="col-md-2">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="feature_status" value="1" checked="">
                                                                    Active
                                                                </label>
                                                            </div>
                                                            <div class="col-md-2">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="feature_status" value="0" >
                                                                    Inactive
                                                                </label>
                                                            </div>
                                                            <div class="col-md-2">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="feature_status" value="2" >
                                                                    Deactive
                                                                </label>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${detail.getStatus() == 0}">
                                                            <div class="col-md-2">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="feature_status" value="1" >
                                                                    Active
                                                                </label>
                                                            </div>
                                                            <div class="col-md-2">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="feature_status" value="0" checked="" >
                                                                    Inactive
                                                                </label>
                                                            </div>
                                                            <div class="col-md-2">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="feature_status" value="2">
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
                </div>
            </c:if>
        </div>
        <script src="../assets/js/validator.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
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
        <!--validate input-->
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                Validator({
                    form: '#form-detail',
                    formGroupSelector: '.form-group',
                    errorSelector: '.form-message',
                    rules: [
                        Validator.isRequired('#team_name', 'Please input Team Name!'),
                        Validator.isRequired('#feature_name', 'Please input Feature Name!'),
                        Validator.isCharacterSpecial('#feature_name'),
                        Validator.isRequired('input[name="feature_status"]', 'Please input Feature Status!')
                    ]
                });
            });
        </script>
        <!--xu y alert dong mo file-->
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

            function tai_lai_trang() {
                location.reload();
            }

            function skipSubmit() {
                document.querySelector("#form-detail").event.preventDefault();
            }

            function callBack() {
                window.location.href = "/swp-project/student/feature"
            }

        </script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <!-- End custom js for this page-->
    </body>
</html>
