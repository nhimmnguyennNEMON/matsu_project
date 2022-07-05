

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
        <title>Milestone Add</title>
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
                                                <p class="card-title">Add New Milestone</p>
                                            </div>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="/swp-project/teacher/milestones" method="POST">
                                            <input type="hidden" name="go" value="add" >
                                            <div class="form-group">
                                                <label for="milestone_name">Milestone Name<span style="color: red">(*)</span></label>
                                                <input type="text" style="height: 48px" 
                                                       <c:if test="${milestone_name != null}">
                                                           value="${milestone_name}"
                                                       </c:if>
                                                       class="form-control" id="milestone_name" name="milestone_name" placeholder="Milestone Name">
                                                <span class="form-message"></span>
                                                <c:if test="${error != null}">
                                                    <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label for="iter_name">Iteration Name<span style="color: red">(*)</span></label>
                                                <select class="form-control" id="iter_name" name="iter_name" 
                                                        style="height: 48px">
                                                    <option value="">--- Choose Iteration ---</option>
                                                    <c:forEach items="${iterList}" var="temp" >
                                                        <option value="${temp.id}"
                                                                <c:if test="${iter_id eq temp.id}">
                                                                    selected="selected"
                                                                </c:if>
                                                                >${temp.iteration_name}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
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
                                                <div class="form-group col-md-6">
                                                    <label for="from_date">From Date<span style="color: red">(*)</span></label>
                                                    <input type="date" class="form-control" id="from_date" name="from_date" style="height: 48px" value="${from_date}">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="to_date">To Date<span style="color: red">(*)</span></label>
                                                    <input type="date" class="form-control" id="to_date" name="to_date" style="height: 48px" value="${to_date}">
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="desc">Description</label>
                                                <textarea name="desc" id="desc" class="form-control" placeholder="Description..." style="height: 48px"></textarea>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-md-1" for="exampleInputConfirmPassword1">Status</label>
                                                <div class="col-md-1">
                                                    <label class="form-check-label">
                                                        <input type="radio" class="form-check-input" name="status" id="optionsRadios1" value="1" checked="">
                                                        Active
                                                    </label>
                                                </div>
                                                <div class="col-md-1">
                                                    <label class="form-check-label">
                                                        <input type="radio" class="form-check-input" name="status" id="optionsRadios2" value="0" >
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
                                                                    Validator.isRequired('#milestone_name', 'Please type in Milestone Name!'),
                                                                    Validator.isRequired('#iter_name', 'Please choose Iteration Name!'),
                                                                    Validator.isRequired('#class_code', 'Please choose Class Code!'),
                                                                    Validator.isRequired('#from_date', 'Please choose From Date!'),
                                                                    Validator.isRequired('#to_date', 'Please choose To Date!'),
                                                                    Validator.minDate('#from_date', 'Cannot pick date before today!')
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
                                window.location.href = "/swp-project/teacher/milestones?go=add";
                            }
                        });
            }

            function submitForm() {
                var milestone_name = document.querySelector("#milestone_name").value;
                var iter_name = document.querySelector("#iter_name").value;
                var class_code = document.querySelector("#class_code").value;
                var from_date = document.querySelector("#from_date").value;
                var to_date = document.querySelector("#to_date").value;

                if (milestone_name == "" || class_code == "" || iter_name == "" || from_date == "" || to_date == "") {
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
