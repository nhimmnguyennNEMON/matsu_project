
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
        <title>Team Detail</title>
        <link rel="shortcut icon" href="../assets/assets_template/images/icon.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"
            />

        <style>
            .hinhanh{
                text-align: center;
            }
        </style>

    </head>
    <body>
        <div class="container-scroller">

            <c:if test="${user != null}">
                <jsp:include page="../../component/Sidebar_teacher.jsp">
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
                                        <h4 class="card-title">Team Detail</h4>
                                        <c:if test="${team != null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="/swp-project/teacher/team">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${team.id}"> 
                                                <div class="form-group">
                                                    <label for="team_name">Team Name</label>
                                                    <input type="text" class="form-control" id="exampleInputPassword1" 
                                                           name="team_name" value="${team.team_name}">
                                                    <c:if test="${error != null}">
                                                        <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="class-code">Class Code</label>
                                                        <select class="form-control" id="class-code" name="class_code" 
                                                                style="height: 48px">
                                                            <c:forEach items="${classList}" var="temp" >
                                                                <option 
                                                                    ${temp.id == team.class_id.id ? "selected" : ""}
                                                                    value="${temp.id}">${temp.class_code}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="form-group col-md-4">
                                                        <label for="topic_code">Topic Code</label>
                                                        <input type="text" class="form-control" id="exampleInputPassword1" 
                                                               name="topic_code" value="${team.topic_code}">
                                                    </div>
                                                    <div class="form-group col-md-8">
                                                        <label for="topic_name">Topic Name</label>
                                                        <input type="text" class="form-control" id="exampleInputConfirmPassword1" 
                                                               name="topic_name" value="${team.topic_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="gitlab_url">Gitlab URL</label>
                                                    <input type="url" class="form-control" id="gitlab_url" 
                                                           name="gitlab_url" value="${team.gitlab_url}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="desc">Description</label>
                                                    <textarea class="form-control" name="desc" id="desc" maxlength="100" style="height: 48px">
                                                        ${team.desc}
                                                    </textarea>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-1" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios1" value="1" 
                                                                   ${team.status eq 1?"checked":""}>
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="0" 
                                                                   ${team.status eq 0?"checked":""}>
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="2" 
                                                                   ${team.status eq 2?"checked":""}>
                                                            Deactive
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancel()">Cancel</button>
                                                        <button type="button" class="btn btn-primary me-2" onclick="update(${team.id})">Update</button>
                                                    </div>
                                                </div>
                                            </form>

                                        </c:if>

                                        <c:if test="${team == null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="/swp-project/teacher/team">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${id}"> 
                                                <div class="form-group">
                                                    <label for="team_name">Team Name</label>
                                                    <input type="text" class="form-control" id="exampleInputPassword1" 
                                                           name="team_name" value="${team_name}">
                                                    <c:if test="${error != null}">
                                                        <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="class-code">Class Code</label>
                                                        <select class="form-control" id="class-code" name="class_code" 
                                                                style="height: 48px">
                                                            <c:forEach items="${classList}" var="temp" >
                                                                <option 
                                                                    ${temp.id == class_id ? "selected" : ""}
                                                                    value="${temp.id}">${temp.class_code}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="form-group col-md-4">
                                                        <label for="topic_code">Topic Code</label>
                                                        <input type="text" class="form-control" id="exampleInputPassword1" 
                                                               name="topic_code" value="${topic_code}">
                                                    </div>
                                                    <div class="form-group col-md-8">
                                                        <label for="topic_name">Topic Name</label>
                                                        <input type="text" class="form-control" id="exampleInputConfirmPassword1" 
                                                               name="topic_name" value="${topic_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="gitlab_url">Gitlab URL</label>
                                                    <input type="url" class="form-control" id="gitlab_url" 
                                                           name="gitlab_url" value="${gitlab_url}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="desc">Description</label>
                                                    <textarea class="form-control" name="desc" id="desc" maxlength="100" style="height: 48px">
                                                        ${desc}
                                                    </textarea>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-1" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios1" value="1" 
                                                                   ${status eq 1?"checked":""}>
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="0" 
                                                                   ${status eq 0?"checked":""}>
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="2" 
                                                                   ${status eq 2?"checked":""}>
                                                            Deactive
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancel()">Cancel</button>
                                                        <button type="button" class="btn btn-primary me-2" onclick="update(${id})">Update</button>
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
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

        <c:if test="${error != null}">
            <script>
                window.onload = function ()
                {
                    alert("error", "Fail!", "${error}!")
                };
            </script>
        </c:if>
        
        <script>

                                                            function update(id) {
                                                                document.getElementById("form-detail").submit();
                                                            }

                                                            function cancel() {
                                                                window.location.href = "/swp-project/teacher/team";
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

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </body>
</html>
