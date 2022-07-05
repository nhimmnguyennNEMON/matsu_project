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
        <title>Subject Setting Add</title>

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
                                    <div class="d-sm-flex align-items-center justify-content-between border-bottom" style="margin-bottom: 30px">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link"  href="subject-setting?go=list" role="tab">Subject Setting List</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"  href="subject-setting?go=add" role="tab">Add Subject Setting</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="add-subject" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <p class="card-title">Add Subject Setting</p>
                                            </div>
                                            <div class="col-md-8"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBAck()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                </button>
                                            </div>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="subject-setting" method="POST">
                                            <input type="hidden" name="go" value="add" >
                                            <div class="form-group">
                                                <label for="subject_code">Subject Code</label>
                                                <select class="form-control" id="subject_code" name="subject-code" 
                                                        style="height: 48px">
                                                    <option value="">--- Choose Subject Code ---</option>
                                                    <c:forEach items="${subjectList}" var="temp" >
                                                        <option value="${temp.id}">${temp.subject_code}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="setting_type">Subject Setting Type</label>
                                                <select class="form-control" id="setting_type" name="type-id" 
                                                        style="height: 48px">
                                                    <option value="">--- Choose Setting Type ---</option>
                                                    <c:forEach items="${requestScope.listType}" var="t" >
                                                        <option value="${t}"><c:out value="${sessionScope[t]}"/></option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="subject_setting_title">Subject Setting Title</label>
                                                <input type="text" style="height: 48px" class="form-control" id="subject_setting_title" name="setting_title" placeholder="Subject Setting Name">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="subject_setting_value">Subject Setting Value</label>
                                                <input type="text" style="height: 48px" class="form-control" id="subject_setting_value" name="setting_value" placeholder="Subject Setting Value">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="display_order">Display Order</label>
                                                <input type="number" style="height: 48px" class="form-control" id="display_order" name="display_order" placeholder="Display Order">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <div class="row">
                                                    <label class="col-md-2" for="exampleInputConfirmPassword1">Status</label>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="status" id="optionsRadios1" value="1" checked="">
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="status" id="optionsRadios2" value="0" >
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-9"></div>
                                                <div class="col-md-3">
                                                    <button type="button" class="btn btn-light" onclick="callBack()">Cancel</button>
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="../assets/js/validator.js" type="text/javascript"></script>

        <!--validate input-->
        <script>
                                                        document.addEventListener('DOMContentLoaded', function () {
// Mong muốn của chúng ta
                                                            Validator({
                                                                form: '#form-add',
                                                                formGroupSelector: '.form-group',
                                                                errorSelector: '.form-message',
                                                                rules: [
                                                                    Validator.isRequired('#subject_code', 'Please choose Subject Code!'),
                                                                    Validator.isRequired('#setting_type', 'Please choose Setting Type!'),
                                                                    Validator.isRequired('#subject_setting_title', 'Please input title for new subject setting!'),
                                                                    Validator.isRequired('#subject_setting_value', 'Please input value for new subject setting!'),
                                                                    Validator.isRequired('#display_order', 'Please enter display order!'),
                                                                    Validator.isRequired('input[name="status"]', 'Please choose Status Subject!')
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
                swal("Warning!", "Are you want to cancel this process?", "warning")
                        .then((value) => {
                            window.location.href = "/swp-project/admin/subject-setting";
                        });
            }

            function submitForm() {
                var subject_code = document.querySelector("#subject_code").value;
                var setting_type = document.querySelector("#setting_type").value;
                var setting_title = document.querySelector("#subject_setting_title").value;
                var setting_value = document.querySelector("#subject_setting_value").value;
                var order = document.querySelector("#display_order").value;

                if (subject_code == "" || setting_type == "" || setting_title == "" || setting_value == "" || order == "") {
                    swal("Error", "You need to fill all the fields!", "error");
                } else {
                    swal("Done!", "Your new setting added successfull!", "success")
                            .then((value) => {
                                document.getElementById("form-add").submit();
                            });
                }

            }
        </script>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

    </body>
</html>
