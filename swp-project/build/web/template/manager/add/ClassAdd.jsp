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
        <title>Class Add</title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="assets/assets_template/vendors/feather/feather.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/typicons/typicons.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="assets/assets_template/vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="assets/assets_template/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
        <link rel="stylesheet" href="assets/assets_template/js/select.dataTables.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link href="../assets/style/validator.css" rel="stylesheet" type="text/css"/>
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
    </head>
    <body>
        <div class="container-scroller">
            <c:if test="${user != null}">
                <c:if test="${user.getRole_id()==1}">
                <jsp:include page="../../component/Sidebar_admin.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
                </c:if>
               <c:if test="${user.getRole_id()==2}">
                <jsp:include page="../../component/Sidebar_manager.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
                </c:if>

                <div class="main-panel">        
                    <div class="content-wrapper" id="list">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="home-tab">
                                    <div class="d-sm-flex align-items-center justify-content-between border-bottom" style="margin-bottom: 30px">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link"  href="class" role="tab">Class List</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"  href="class?go=add" role="tab">Add Class</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="add-subject" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <p class="card-title">Add Class</p>
                                            </div>
                                            <div class="col-md-9"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                </button>
                                            </div>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="class" method="POST">
                                            <input type="hidden" name="go" value="add">
                                            <div class="form-group">
                                                <label for="class_code">Class code</label>
                                                <input type="text" class="form-control" id="class_code" name="class_code" placeholder="Class code">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="subject_id">Subject Code</label>
                                                <select class="form-control" id="subject_id" name="subject_id">
                                                    <option value="">--- Choose Subject Code ---</option>
                                                     <c:forEach items="${requestScope.listS}" var="temp">
                                                    <option value="${temp.getId()}">${temp.getSubject_code()}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="trainer_id"> Trainer</label>
                                                <select class="form-control" id="trainer_id" name="trainer_id">
                                                    <option value="">--- Choose Trainer ---</option>
                                                     <c:forEach items="${requestScope.filterU}" var="temp">
                                                    <option value="${temp.getUser_id()}">${temp.getFull_name()}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="class_year">Class Year</label>
                                                <input type="number" class="form-control" id="class_year" name="class_year" placeholder="Class year">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="row">
                                            <div class="form-group col-md-4">
                                                <label for="class_term">Class Term</label>
                                                <select class="form-control" id="class_term" name="class_term">
                                                    <option value="">--- Choose Class Term ---</option>
                                                    <option value="0">Spring</option>
                                                    <option value="1">Summer</option>
                                                    <option value="2">Fall</option>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group col-md-8" style="text-align: center;">
                                                <div class="row text-center align-items-center" >
                                                    <label for="block5">Block 5</label>
                                                    <div class="col-md-3">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="block5" id="optionsRadios1" value="true">
                                                            True
                                                        </label>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="block5" id="optionsRadios2" value="false" >
                                                            False
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            </div>    
                                            <div class="form-group">
                                                <div class="row">
                                                    <label class="col-md-2" for="class_status">Class Status</label>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="class_status" id="optionsRadios1" value="1">
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" style="margin-right: 20px" name="class_status" id="optionsRadios2" value="0" >
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-9"></div>
                                                <div class="col-md-3">
                                                    <button type="reset" class="btn btn-light me-2">Reset</button>
                                                    <button class="btn btn-primary me-2" onclick="submitForm()" >Add</button>
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
        <script src="../assets/js/validator.js" type="text/javascript"></script>

        <!--validate input-->
        <script>
                                                        document.addEventListener('DOMContentLoaded', function () {
                                                            Validator({
                                                                form: '#form-add',
                                                                formGroupSelector: '.form-group',
                                                                errorSelector: '.form-message',
                                                                rules: [
                                                                    Validator.isRequired('#class_code', 'Vui lòng nhập Class Code!'),
                                                                    Validator.isCharacterSpecial('#class_code'),
                                                                    Validator.isRequired('#class_year', 'Vui lòng nhập Class Year'),
                                                                    Validator.isRequired('input[name="class_status"]', 'Vui lòng chọn Status Class!'),
                                                                    Validator.Year('#class_year')
                                                                ]
                                                            });
                                                        });

                                                        function callBack() {
                                                            window.location.href = "/swp-project/manager/class"
                                                        }

                                                        function submitForm() {
                                                            var elem = document.querySelector("#form-add");
                                                            var error = elem.classList.contains("invalid");
                                                            var class_status;
                                                            var checkstatus = document.getElementsByName("class_status");
                                                            for (var i = 0; i < checkstatus.length; i++) {
                                                                if (checkstatus[i].checked === true) {
                                                                    class_status = checkstatus[i].value;
                                                                }
                                                            }
                                                            var block5;
                                                            var checkblock5 = document.getElementsByName("block5");
                                                            for (var i = 0; i < checkblock5.length; i++) {
                                                                if (checkblock5[i].checked === true) {
                                                                    block5 = checkstatus[i].value;
                                                                }
                                                            }
                                                            var trainer_id = document.getElementById("trainer_id").value;
                                                            var class_code = document.getElementById("class_code").value;
                                                            var subject_id = document.getElementById("subject_id").value;
                                                            var class_year = document.getElementById("class_year").value;
                                                            var class_term = document.getElementById("class_term").value;
                                                           
                                                            if (!class_code)
                                                            {
                                                                swal("OOPS!", "You need to fill all the fields or the fields invalid.", "warning");
                                                            }
                                                        }
        </script>

        <!-- End custom js for this page-->
    </body>
</html>
