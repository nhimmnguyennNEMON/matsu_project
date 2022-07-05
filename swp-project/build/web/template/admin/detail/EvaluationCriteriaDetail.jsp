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
        <title>Evaluation Criteria Add</title>
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
                                                <a class="nav-link"  href="Evaluation Criteria" role="tab">Evaluation Criteria List</a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"  href="evaluation_criteria?go=add" role="tab">Add Evaluation Criteria</a>
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
                                                <p class="card-title">Add Evaluation Criteria</p>
                                            </div>
                                            <div class="col-md-9"></div>
                                            <div class=" col-md-1 ">
                                                <button class="btn btn-close" style="padding-left: 50px" type="button" onclick="callBack()" id="btn-add-setting" >
                                                </button>
                                            </div>
                                        </div>
                                        <form class="forms-sample" id="form-add" onsubmit="return false" action="evaluation_criteria" method="POST">
                                            <input type="hidden" name="go" value="detail">
                                            <input type="hidden" name="criteria_id" value="${c.getCriteria_id()}">
                                            <input type="hidden" name="iteration_id" value="${c.getIteration_id().getId()}">
                                             <div class="form-group">
                                                <label for="iteration_id">Iteration<code>(*)</code></label>
                                                 <select class="form-control" id="iteration_id" disabled name="iteration_id">
                                                     <option  value="${c.getIteration_id().getId()}" selected>${c.getIteration_id().getIteration_name()}- ${c.getIteration_id().getSubject_code()}</option>
                                                </select>
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="evaluarion_weight">Evaluation Weight</label>
                                                <input type="number" class="form-control" disabled id="evaluarion_weight" value="${c.getEvaluation_weight()}" name="evaluarion_weight" >
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <label for="criteria_order">Order</label>
                                                <input type="number" class="form-control" id="criteria_order" value="${c.getCriteria_order()}" name="criteria_order" placeholder="Criteria Order">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="row">
                                            <div class="form-group col-md-8" style="text-align: center;">
                                                <div class="row text-center align-items-center" >
                                                    <label for="team_evaluation">Team Evaluation</label>
                                                    <div class="col-md-3">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" ${c.getTeam_evaluation()==true ?"checked":""}  style="margin-right: 20px" name="team_evaluation" id="optionsRadios1" value="true">
                                                            True
                                                        </label>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" ${c.getTeam_evaluation()==false ?"checked":""} style="margin-right: 20px" name="team_evaluation" id="optionsRadios2" value="false" >
                                                            False
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            </div>    
                                            <div class="form-group">
                                                <label for="max_loc">Max Loc</label>
                                                <input type="number" class="form-control" value="${c.getMax_loc()}" id="max_loc" name="max_loc" placeholder="Max Loc">
                                                <span class="form-message"></span>
                                            </div>
                                            <div class="form-group">
                                                <div class="row">
                                                    <label class="col-md-2" for="status">Class Status<code>(*)</code></label>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" ${c.isStatus()==true ?"checked":""} style="margin-right: 20px" name="status" id="optionsRadios1" value="1">
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" ${c.isStatus()==false ?"checked":""} style="margin-right: 20px" name="status" id="optionsRadios2" value="0" >
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <span class="form-message"></span>
                                                </div>
                                            </div>
                                            <div >
                                                
                                                
                                                    <button type="reset" class="btn btn-light me-2">Reset</button>
                                                    <button class="btn btn-primary me-2" onclick="submitForm()" >Update</button>
                                                
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
                                                                    Validator.isPositiveNumbers('#criteria_order'),
                                                                    Validator.isPositiveNumbers('#max_loc'),
                                                                    Validator.isRequired('#iteration_id', 'Vui lòng chọn Iteration'),
                                                                    Validator.isRequired('input[name="status"]', 'Vui lòng chọn Status !'),
                                                                    
                                                                ]
                                                            });
                                                        });

                                                        function callBack() {
                                                            window.location.href = "/swp-project/manager/evaluation_criteria"
                                                        }

                                                        function submitForm() {
                                                            var elem = document.querySelector("#form-add");
                                                            var error = elem.classList.contains("invalid");
                                                            var status;
                                                            var checkstatus = document.getElementsByName("status");
                                                            for (var i = 0; i < checkstatus.length; i++) {
                                                                if (checkstatus[i].checked === true) {
                                                                    status = checkstatus[i].value;
                                                                }
                                                            }
                                                            var team_evaluation;
                                                            var checkteam = document.getElementsByName("team_evaluation");
                                                            for (var i = 0; i < checkteam.length; i++) {
                                                                if (checkteam[i].checked === true) {
                                                                    team_evaluation = checkstatus[i].value;
                                                                }
                                                            }
                                                            var criteria_order = document.getElementById("criteria_order").value;
                                                            var max_loc  = document.getElementById("max_loc").value;
                                                            
                                                            
                                                            
                                                                 document.getElementById("form-add").submit();
                                                            }
                                                        
        </script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

        <!-- plugins:js -->
        <script src="assets/assets_template/vendors/js/vendor.bundle.base.js"></script>
        <!-- endinject -->
        <!-- Plugin js for this page -->
        <script src="assets/assets_template/vendors/chart.js/Chart.min.js"></script>
        <script src="assets/assets_template/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
        <script src="assets/assets_template/vendors/progressbar.js/progressbar.min.js"></script>

        <!-- End plugin js for this page -->
        <!-- inject:js -->
        <script src="assets/assets_template/js/off-canvas.js"></script>
        <script src="assets/assets_template/js/hoverable-collapse.js"></script>
        <script src="assets/assets_template/js/template.js"></script>
        <script src="assets/assets_template/js/settings.js"></script>
        <script src="assets/assets_template/js/todolist.js"></script>
        <!-- endinject -->
        <!-- Custom js for this page-->
        <script src="assets/assets_template/js/jquery.cookie.js" type="text/javascript"></script>
        <script src="assets/assets_template/js/dashboard.js"></script>
        <script src="assets/assets_template/js/Chart.roundedBarCharts.js"></script>
        <!-- End custom js for this page-->
    </body>
</html>
