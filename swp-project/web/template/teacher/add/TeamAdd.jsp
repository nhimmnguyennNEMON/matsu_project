

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
        <title>Team Add</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"
            />

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

                            <div class="col-md-12 grid-margin stretch-card" id="add-subject" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <p class="card-title">Add New Team</p>
                                            </div>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="/swp-project/teacher/team" method="POST">
                                            <input type="hidden" name="go" value="add" >
                                            <div class="form-group">
                                                <label for="team_name">Team Name<span style="color: red">(*)</span></label>
                                                <input type="text" style="height: 48px" 
                                                       <c:if test="${team_name != null}">
                                                           value="${team_name}"
                                                       </c:if>
                                                       class="form-control" id="team_name" name="team_name" placeholder="Team Name">
                                                <span class="form-message"></span>
                                                <c:if test="${error != null}">
                                                    <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label for="class_code">Class Code<span style="color: red">(*)</span></label>
                                                <select class="form-control" id="class_code" name="class_code" 
                                                        style="height: 48px">
                                                    <option value="">--- Choose Class Code ---</option>
                                                    <c:forEach items="${classList}" var="temp" >
                                                        <option value="${temp.id}"
                                                                <c:if test="${class_id eq temp.id}">
                                                                    selected="selected"
                                                                </c:if>
                                                                >${temp.class_code}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>

                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label for="topic_code">Topic Code<span style="color: red">(*)</span></label>
                                                    <input type="text" style="height: 48px" 
                                                           <c:if test="${topic_code != null}">
                                                               value="${topic_code}"
                                                           </c:if>
                                                           class="form-control" id="topic_code" name="topic_code" placeholder="Topic Code">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group col-md-8">
                                                    <label for="topic_name">Topic Name<span style="color: red">(*)</span></label>
                                                    <input type="text" style="height: 48px" 
                                                           <c:if test="${topic_name != null}">
                                                               value="${topic_name}"
                                                           </c:if>
                                                           class="form-control" id="topic_name" name="topic_name" placeholder="Topic Name">
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="gitlab_url">Gitlab Url<span style="color: red">(*)</span></label>
                                                <input type="url" style="height: 48px" 
                                                       <c:if test="${gitlab_url != null}">
                                                           value="${gitlab_url}"
                                                       </c:if>
                                                       class="form-control" id="gitlab_url" name="gitlab_url" placeholder="Gitlab URL">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="desc">Description</label>
                                                <textarea name="desc" id="desc" 
                                                          class="form-control" placeholder="Description..." style="height: 48px">
                                                    <c:if test="${desc != null}">
                                                        value="${desc}"
                                                    </c:if>
                                                </textarea>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-md-1" for="exampleInputConfirmPassword1">Status</label>
                                                <div class="col-md-1">
                                                    <label class="form-check-label">
                                                        <input type="radio" class="form-check-input" name="status" 
                                                               <c:if test="${status eq 1}">
                                                                   checked=""
                                                               </c:if>
                                                               id="optionsRadios1" value="1" checked="">
                                                        Active
                                                    </label>
                                                </div>
                                                <div class="col-md-1">
                                                    <label class="form-check-label">
                                                        <input type="radio" class="form-check-input" 
                                                               <c:if test="${status eq 0}">
                                                                   checked=""
                                                               </c:if>
                                                               name="status" id="optionsRadios2" value="0" >
                                                        Inactive
                                                    </label>
                                                </div>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <button type="button" class="btn btn-light" onclick="callBack()">Clear</button>
                                                    <button type="button" class="btn btn-primary me-2" onclick="submitForm()">Add</button>
                                                </div>
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
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="../assets/js/validator.js" type="text/javascript"></script>

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
// Mong muốn của chúng ta
                Validator({
                    form: '#form-add',
                    formGroupSelector: '.form-group',
                    errorSelector: '.form-message',
                    rules: [
                        Validator.isRequired('#team_name', 'Please type in Team Name!'),
                        Validator.isRequired('#class_code', 'Please choose Class Code!'),
                        Validator.isRequired('#topic_code', 'Please type in Topic Code!'),
                        Validator.isRequired('#topic_name', 'Please type in Topic Name!'),
                        Validator.isRequired('#gitlab_url', 'Please type in gitlab url!'),
                    ]
//                                                                onSubmit: function (data) {
//                                                                    // Call API
//                                                                    console.log(data);
//                                                                }
                });
            });
        </script>
        <!--Xu li alert and phân trang-->
        <script>
            function callBack() {
                swal({
                    title: "Warning!",
                    text: "Are you want to clear all?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                window.location.href = "/swp-project/teacher/team?go=add";
                            }
                        });
            }

            function submitForm() {
                var class_code = document.querySelector("#class_code").value;
                var topic_code = document.querySelector("#topic_code").value;
                var topic_name = document.querySelector("#topic_name").value;
                var gitlab_url = document.querySelector("#gitlab_url").value;

                if (class_code == "" || topic_code == "" || topic_name == "" || gitlab_url == "") {
                    swal("Error", "You need to fill all the fields!", "error");
                } else {
                    document.getElementById("form-add").submit();
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

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </body>
</html>
