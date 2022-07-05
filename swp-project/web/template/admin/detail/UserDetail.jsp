
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
        <title>User Detail</title>
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
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />

        <style>
            .hinhanh{
                text-align: center;
            }
        </style>

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
                                                <a class="nav-link"  href="user?go=detail" role="tab" aria-selected="false">User Detail</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">User Detail</h4>
                                        <c:if test="${u != null}">
                                             <form id="form-detail" class="forms-sample row" method="POST" action="user">
                                                <input  type="hidden" name="go" value="edit"> 
                                                <input  type="hidden" name="id" value="${u.getUser_id()}"> 
                                                <div class="col-md-8">
                                                <div class="form-group">
                                                    <label for="name">Full name</label>
                                                    <input type="text" class="form-control" id="name" name="name" value="${u.getFull_name()}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="user_email">Email</label>
                                                    <input type="text" class="form-control" id="user_email" name="user_email" disabled value="${u.getEmail()}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="mobile">Phone number</label>
                                                    <input type="tel" class="form-control" id="mobile" name="mobile" pattern="[0-9]{3} [0-9]{3} [0-9]{4}" maxlength="12" value="${u.getPhone_number()}">
                                                </div>
                                                <div class="form-group">
                                                    <label for="date_of_birth">Date of birth</label>
                                                    <input type="date" class="form-control" id="date_of_birth" name="date_of_birth"  value="${u.getDate_of_birth() }">
                                                </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <img src="${u.getAvatar_link()}" style=" border-radius: 50%; width: 250px; height: 250px; object-fit: cover;    display: inline-flex;" disabled  alt="" >
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-2" for="optionsRadios0"> Gender</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" name="gender" id="optionsRadios0" value="0"
                                                                   ${u.getGender() eq 0?"checked":""}>
                                                            Orthers
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios1">
                                                            <input type="radio" class="form-check-input" name="gender" id="optionsRadios1" value="1" 
                                                                   ${u.getGender() eq 1?"checked":""}>
                                                            Male
                                                        </label>
                                                    </div>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" name="gender" id="optionsRadios2" value="2" 
                                                                   ${u.getGender() eq 2?"checked":""}>
                                                            Female
                                                        </label>
                                                    </div>

                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="role">User Role</label>
                                                    <select class="form-control" id="role" name="role" 
                                                            style="height: 48px">
                                                        <option value="">--- Choose User Role ---</option>
                                                        <c:forEach items="${ requestScope.listRole}" var="temp" >
                                                            <fmt:parseNumber var = "role_id" type = "number" value = "${temp}" />
                                                            <c:if test ="${u.getRole_id() == role_id}">
                                                                <option value="${temp}" selected ><c:out value="${sessionScope[temp]}"/></option>
                                                            </c:if>
                                                            <option value="${temp}"><c:out value="${sessionScope[temp]}"/></option>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="form-message"></span>
                                                </div>    
                                                <div class="form-group col-md-6">
                                                    <label for="status">Status</label>
                                                    <select class="form-control" id="status" name="status" 
                                                            style="height: 48px">
                                                        <option value="">--- Choose Status ---</option>
                                                        <c:if test ="${u.isStatus() == true}">
                                                            <option  value="true"  selected="" >Active</option>
                                                            <option  value="false"  >Deactive</option>
                                                        </c:if>
                                                        <c:if test ="${u.isStatus() == false}">
                                                            <option  value="true"   >Active</option>
                                                            <option  value="false"  selected="" >Deactive</option>
                                                        </c:if>                                                    </select>
                                                    <span class="form-message"></span>
                                                </div>    
                                                <div class="form-group">
                                                    <label for="address">Address</label>
                                                    <input type="text" class="form-control" id="address" name="address"  value="${u.getAddress()}">
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-8"></div>
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="cancelDelete()">Cancel</button>
                                                        <button type="button" class="btn btn-primary me-2" onclick="updateUser()">Update</button>
                                                        <button class="btn btn-danger me-2" type="button"
                                                                onclick="deleteSubjectSetting(${subjectSetting.id})">Delete</button>
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

        <script>

            function tai_lai_trang() {
                location.reload();
            }
            function cancelDelete() {
                window.location.href = "/swp-project/admin/user";
            }
            function updateUser() {
                
                swal("Done!", "Update setting successfull!", "success")
                    .then((value) => {
                        console.log(value);
                        document.getElementById("form-detail").submit();
                    });
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
                <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>





        <!-- End custom js for this page-->
    </body>
</html>
