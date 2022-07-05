
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
        <title>Milestone Detail</title>
        <link rel="shortcut icon" href="../assets/assets_template/images/icon.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"
            />
        <style>
            .hinhanh{
                margin-left: 58px;
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
                                        <h4 class="card-title">Milestone Detail</h4>
                                        <c:if test="${milestone != null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="/swp-project/teacher/milestones">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${milestone.id}"> 
                                                <div class="form-group">
                                                    <label for="milestone_name">Milestone Name</label>
                                                    <input type="text" class="form-control" id="exampleInputPassword1" 
                                                           name="milestone_name" value="${milestone.milestone_name}" style="height: 48px">
                                                    <c:if test="${error != null}">
                                                        <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="iter_id">Iteration Name</label>
                                                        <select class="form-control" id="iter_id" name="iter_id" 
                                                                style="height: 48px">
                                                            <c:forEach items="${iterList}" var="temp" >
                                                                <option 
                                                                    ${temp.id == milestone.iter.id ? "selected" : ""}
                                                                    value="${temp.id}">${temp.iteration_name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="class_code">Class Code</label>
                                                        <select class="form-control" id="class_code" name="class_code" 
                                                                style="height: 48px">
                                                            <c:forEach items="${classList}" var="temp" >
                                                                <option 
                                                                    ${temp.id == milestone.class_id.id ? "selected" : ""}
                                                                    value="${temp.id}">${temp.class_code}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="row"> 
                                                    <div class="form-group col-md-6">
                                                        <label for="from_date">From Date(*)</label>
                                                        <input type="date" class="form-control" id="from_date" name="from_date" style="height: 48px" value="${milestone.from_date}">
                                                        <span class="form-message"></span>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="to_date">To Date(*)</label>
                                                        <input type="date" class="form-control" id="to_date" name="to_date" style="height: 48px" value="${milestone.to_date}">
                                                        <span class="form-message"></span>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="desc">Description</label>
                                                    <textarea class="form-control" name="desc" id="desc" placeholder="Description..." style="height: 48px">
                                                        <c:if test="${milestone.desc ne null}">
                                                            ${milestone.desc}
                                                        </c:if>
                                                    </textarea>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-1" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios1" value="1" 
                                                                   ${milestone.status eq 1?"checked":""}>
                                                            Open
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="0" 
                                                                   ${milestone.status eq 0?"checked":""}>
                                                            Closed
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="2" 
                                                                   ${milestone.status eq 2?"checked":""}>
                                                            Cancelled
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancel(${milestone.id})">Clear</button>
                                                        <button type="button" class="btn btn-primary me-2" onclick="update(${milestone.id})">Update</button>
                                                    </div>
                                                </div>
                                            </form>

                                        </c:if>

                                        <c:if test="${milestone == null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="/swp-project/teacher/milestones">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${id}"> 
                                                <div class="form-group">
                                                    <label for="milestone_name">Milestone Name</label>
                                                    <input type="text" class="form-control" id="exampleInputPassword1" 
                                                           name="milestone_name" value="${milestone_name}" style="height: 48px">
                                                    <c:if test="${error != null}">
                                                        <span id="error-team-name" class="form-message" style="color: red">${error}</span>
                                                    </c:if>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="iter_id">Iteration Name</label>
                                                        <select class="form-control" id="iter_id" name="iter_id" 
                                                                style="height: 48px">
                                                            <c:forEach items="${iterList}" var="temp" >
                                                                <option 
                                                                    ${temp.id == iter_id ? "selected" : ""}
                                                                    value="${temp.id}">${temp.iteration_name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div>
                                                        <label for="class_code">Class Code</label>
                                                        <select class="form-control" id="class_code" name="class_code" 
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
                                                    <div class="form-group col-md-6">
                                                        <label for="from_date">From Date(*)</label>
                                                        <input type="date" class="form-control" id="from_date" name="from_date" style="height: 48px" value="${from_date}">
                                                        <span class="form-message"></span>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="to_date">To Date(*)</label>
                                                        <input type="date" class="form-control" id="to_date" name="to_date" style="height: 48px" value="${to_date}">
                                                        <span class="form-message"></span>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="desc">Description</label>
                                                    <textarea class="form-control" name="desc" id="desc" placeholder="Description..." style="height: 48px">
                                                        <c:if test="${desc ne null}">
                                                            ${desc}
                                                        </c:if>
                                                    </textarea>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-1" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios1" value="1" 
                                                                   ${status eq 1?"checked":""}>
                                                            Open
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="0" 
                                                                   ${status eq 0?"checked":""}>
                                                            Closed
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="2" 
                                                                   ${status eq 2?"checked":""}>
                                                            Cancelled
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancel(${id})">Clear</button>
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
            document.addEventListener('DOMContentLoaded', function () {
// Mong muốn của chúng ta
                Validator({
                    form: '#form-detail',
                    formGroupSelector: '.form-group',
                    errorSelector: '.form-message',
                    rules: [
                        Validator.minDate('#from_date', 'Cannot pick date before today!'),
                    ]
//                                                                onSubmit: function (data) {
//                                                                    // Call API
//                                                                    console.log(data);
//                                                                }
                });
            });
            function update(id) {
                document.getElementById("form-detail").submit();
            }

            function cancel(id) {
                swal({
                    title: "Warning!",
                    text: "Are you want to clear all?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true,
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                window.location.href = "/swp-project/teacher/milestones?go=detail&id=" + id;
                            }
                        });
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
