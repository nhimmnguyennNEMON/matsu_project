<%-- 
    Document   : UpdateUserProfile
    Created on : May 17, 2022, 6:00:50 PM
    Author     : SY NGUYEN
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Setting Detail</title>
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
                                    <div class="d-sm-flex align-items-center justify-content-between border-bottom" 
                                         style="margin-bottom: 30px">
                                        <ul class="nav nav-tabs" role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link" href="subject?go=detail" role="tab" 
                                                   aria-selected="false">Setting Detail</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Setting Detail</h4>
                                        <c:if test="${requestScope.setting != null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="setting">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${setting.getSetting_Id()}"> 
                                                <div class="form-group">
                                                    <div class="form-group">
                                                        <label for="setting_name">Setting Name</label>
                                                        <input type="text" style="height: 48px" class="form-control" id="setting_name" name="setting_name" placeholder="Setting Name" value="${setting.getSetting_Name()}">
                                                        <span class="form-message"></span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="setting_order">Setting Order</label>
                                                    <input type="number" style="height: 48px" class="form-control" id="setting_order" name="setting_order" value="${setting.getOrder()}" placeholder="Setting Order">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="setting_value">Setting Value</label>
                                                    <input type="text" style="height: 48px" class="form-control" id="setting_value" name="setting_value" placeholder="Setting Value" value="${setting.getSetting_Value()}">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="setting_type">Setting Type</label>
                                                    <select class="form-control" id="setting_type" name="setting_type" 
                                                            style="height: 48px">
                                                        <option value="">--- Choose Setting Type ---</option>
                                                        <c:forEach items="${requestScope.listSettingType}" var="temp" >
                                                            <fmt:parseNumber var = "type_id" type = "number" value = "${temp}" />
                                                            <c:if test ="${setting.getType_Id() == type_id}">
                                                             <option value="${temp}" selected ><c:out value="${sessionScope[temp]}"/></option>
                                                            </c:if>
                                                            <option value="${temp}"><c:out value="${sessionScope[temp]}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="form-message"></span>
                                                </div>    
                                                <div class="form-group">
                                                    <label for="setting_note">Description</label>
                                                    <textarea rows="4" class="form-control" id="setting_note" name="setting_note">
                                                        <c:out value="${setting.getNote()}"/>
                                                    </textarea>
                                                    <span class="form-message"></span>
                                                </div>

                                                <div class="form-group row">
                                                    <label class="col-md-2" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="setting_status" id="optionsRadios1" value="1" 
                                                                   ${setting.getStatus() eq 1?"checked":""}>
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="setting_status" id="optionsRadios2" value="0" 
                                                                   ${setting.getStatus() eq 0?"checked":""}>
                                                            Inactive
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios3">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="setting_status" id="optionsRadios3" value="2" 
                                                                   ${setting.getStatus() eq 2?"checked":""}>
                                                            Inactive
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-8"></div>
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancelDelete()">Cancel</button>
                                                        <button type="button" class="btn btn-primary me-2" onclick="updateSetting(${setting.getSetting_Id()})">Update</button>
                                                        <button class="btn btn-danger me-2" type="button"
                                                                onclick="deleteSetting(${Setting.id})">Delete</button>
                                                    </div>
                                                </div>
                                            </form>

                                        </c:if>

                                        <c:if test="${setting == null}">
                                            No record to display
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

        <script>

                                                                    function updateSetting(id) {
                                                                        swal("Done!", "Update setting successfull!", "success")
                                                                                .then((value) => {
                                                                                    document.getElementById("form-detail").submit();
                                                                                });
                                                                    }

                                                                    function deleteSetting(id)
                                                                    {
                                                                        swal({
                                                                            title: "Are you sure?",
                                                                            text: "Once deleted, you will not be able to recover this setting!",
                                                                            icon: "warning",
                                                                            buttons: true,
                                                                            dangerMode: true,
                                                                        })
                                                                                .then((willDelete) => {
                                                                                    if (willDelete) {
                                                                                        swal("Done!", "Your status has been deleted!", "success")
                                                                                                .then((value) => {
                                                                                                    window.location.href = "/swp-project/admin/subject-setting?go=delete&id=" + id;
                                                                                                });
                                                                                    } else {
                                                                                        swal("Your setting is still not deleted!");
                                                                                    }
                                                                                });
                                                                    }

                                                                    function cancelDelete() {
                                                                        window.location.href = "/swp-project/admin/subject-setting";
                                                                    }
        </script>

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
