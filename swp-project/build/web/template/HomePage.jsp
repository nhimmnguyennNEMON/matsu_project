<%-- 
    Document   : DashBoard
    Created on : May 12, 2022, 10:00:59 PM
    Author     : SY NGUYEN
--%>

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
        <title>Home Page</title>
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
        <link href="assets/style/validator.css" rel="stylesheet" type="text/css"/>
        <link href="assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
    </head>
    <body>

        <!-- container-scroller -->
        <div class="container-scroller">

            <!-- navbar chua login -->
            <c:if test="${user == null}">
                <jsp:include page="component/Sidebar_home.jsp"/>  
            </c:if>

            <!-- navbar da login -->
            <c:if test="${user != null}">
                <jsp:include page="component/Sidebar_home_login.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
            </c:if>

            <div class="main-panel">
                <div class="content-wrapper">
                    <div class="row">

                        <div class="col-md-12 d-flex align-items-stretch">
                            <div class="row">
                                <div class="col-md-12 grid-margin stretch-card">
                                    <section id="about">
                                        <div class="card">
                                            <div class="card-body">
                                                <h4 class="card-title">About</h4>
                                                <div class="row">
                                                    <div class="col-md-3 grid-margin stretch-card">
                                                        <img src="assets/assets_template/images/faces/about.svg" alt="">
                                                    </div>
                                                    <div class="col-md-2"></div>
                                                    <div class="col-md-7 grid-margin stretch-card">
                                                        <p>
                                                            <b> MATSU</b> is a software project built to help organizations, students, students as well as teachers manage their teaching and learning.
                                                            <b> MATSU</b> relies on 4 objects to manage: Manager, Admin, Teacher, Student and especially User Authentication. The main advantages of this software are:
                                                            <br><br>    
                                                            - Any change when it is User Authentication ie after successful login, you can edit your profile.
                                                            <br><br>
                                                            - For the Manager, you can manage and edit the list of subjects, check the interaction as well as set the appropriate criteria.
                                                            <br><br>
                                                            - For Admin can install lists, Check user list and edit user functions (for example, 
                                                            for each student this semester, they take this class but next semester take another class, 
                                                            and for teachers students can teach 1 more subject compared to the previous term), in addition, they can view the Class list.
                                                            <br><br>
                                                            - For teachers it is possible to check the list of classes and milestones as well as check if the list is imported or exported.
                                                            <br><br>
                                                            - For Student can view the list of features, track functions, track list, import or export functions, in addition students can also perform loc, team and user evaluation.
                                                            <br><br>
                                                            <b><i>MATSU will help users have a good experience, quick access, diverse features to help users monitor, manage and edit quickly.</i></b>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </section>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 grid-margin stretch-card">
                            <div class="card">
                                <section id="contact">
                                    <div class="card-body">
                                        <h4 class="card-title">Contact</h4>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <address>
                                                    <p class="fw-bold">Admin Management.</p>
                                                    <p>
                                                        Khu Giao Duc va Dao Tao,
                                                    </p>
                                                    <p>
                                                        Khu Cong Nghe Cao Hoa La
                                                    </p>
                                                    <p>
                                                        Km29 DLTL, Thach That, Ha Noi
                                                    </p>
                                                </address>
                                            </div>
                                            <div class="col-md-6">
                                                <address class="text-primary">
                                                    <p class="fw-bold">
                                                        Tel
                                                    </p>
                                                    <p class="mb-2">
                                                        0123456789
                                                    </p>
                                                    <p class="fw-bold">
                                                        E-mail
                                                    </p>
                                                    <p class="mb-2">
                                                        admin@examplemeail.com
                                                    </p>
                                                    <p class="fw-bold">
                                                        Web Address
                                                    </p>
                                                    <p>
                                                        www.admin.com
                                                    </p>
                                                </address>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>
                        <div class="col-md-6 grid-margin stretch-card">
                            <div class="card">
                                <section id="infosupport">
                                    <div class="card-body">
                                        <h4 class="card-title">Information & Supporting</h4>

                                        <c:if test="${user != null}">
                                            <form class="forms-sample" id="form-add" action="home" method="post" onsubmit="return false">
                                                <input type="hidden" name="go" value="addContact" >
                                                <div class="form-group">
                                                    <label for="exampleInputUsername1">Full Name(*)</label>
                                                    <input type="text" class="form-control" id="name_unlog" name="fullname" value="${user.getFull_name()}" placeholder="Name">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Email address(*)</label>
                                                    <input type="email" class="form-control" id="email_unlog" name="email" value="${user.getEmail()}" placeholder="Email">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputPassword1">Phone(*)</label>
                                                    <input type="text" class="form-control" id="phone_unlog" name="phone" maxlength="10"a placeholder="Phone">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputConfirmPassword1">Message(*)</label>
                                                    <input type="text" class="form-control" id="mess_unlog" name="message" placeholder="Message">
                                                    <span class="form-message"></span>
                                                </div>
                                                <button class="btn btn-primary me-2" onclick="submitForm()">Send</button>
                                                <button type="reset" class="btn btn-light">Reset</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${user == null}">
                                            <form class="forms-sample" id="form-add" action="home" method="post" onsubmit="return false">
                                                <input type="hidden" name="go" value="addContact" >
                                                <div class="form-group">
                                                    <label for="exampleInputUsername1">Full Name(*)</label>
                                                    <input type="text" class="form-control" id="name_unlog" name="fullname" placeholder="Name">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputEmail1">Email address(*)</label>
                                                    <input type="email" class="form-control" id="email_unlog" name="email" placeholder="Email">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputPassword1">Phone(*)</label>
                                                    <input type="text" class="form-control" id="phone_unlog" name="phone" maxlength="10"a placeholder="Phone">
                                                    <span class="form-message"></span>
                                                </div>
                                                <div class="form-group">
                                                    <label for="exampleInputConfirmPassword1">Message(*)</label>
                                                    <input type="text" class="form-control" id="mess_unlog" name="message" placeholder="Message">
                                                    <span class="form-message"></span>
                                                </div>
                                                <button class="btn btn-primary me-2" onclick="submitForm()" >Send</button>
                                                <button type="reset" class="btn btn-light">Reset</button>
                                            </form>
                                        </c:if>

                                    </div>
                                </section>
                            </div>
                        </div>
                    </div>
                </div>
                <script src="assets/js/validator.js" type="text/javascript"></script>
                <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
                <!--validate input-->
                <script>
                                                            document.addEventListener('DOMContentLoaded', function () {
                                                                Validator({
                                                                    form: '#form-add',
                                                                    formGroupSelector: '.form-group',
                                                                    errorSelector: '.form-message',
                                                                    rules: [
                                                                        Validator.isRequired('#name_unlog'),
                                                                        Validator.isRequired('#email_unlog'),
                                                                        Validator.isRequired('#phone_unlog'),
                                                                        Validator.isRequired('#mess_unlog'),
                                                                        Validator.maxLength('#mess_unlog', 200)
                                                                    ]
        //                                                                onSubmit: function (data) {
        //                                                                    // Call API
        //                                                                    console.log(data);
        //                                                                }
                                                                });
                                                            });
                </script>
                <script>
                    function submitForm() {
                        var name_unlog = document.getElementById("name_unlog").value;
                        var email_unlog = document.getElementById("email_unlog").value;
                        var phone_unlog = document.getElementById("phone_unlog").value;
                        var mess_unlog = document.getElementById("mess_unlog").value;
                        if (!name_unlog || !email_unlog || !phone_unlog || !mess_unlog)
                        {
                            swal("OOPS!", "You need to fill all the fields.", "warning");
                        } else {
                            swal("Done!", "Your new subject added successfull!", "success")
                                    .then((value) => {
                                        document.getElementById("form-add").submit();
                                    });
                        }
                    }

                    function alrtLogin1() {
                        swal({
                            title: "${titles}",
                            text: "${mess}",
                            button: "Scan QR",
                        }).then(
                                () => {
                            swal({
                                title: "Please scan me!!!",
                                icon: "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/MATSU:${requestScope.email}?secret=${requestScope.secret_key}&algorithm=SHA-1",
                                button: "Done!",
                            });

                        }
                        );
                    }
                    function alrtLogin2() {
                        swal({
                            title: "${titles}",
                            text: "${mess}",
                            button: "Agree",
                        });
                    }


                    function alrtFilter1() {
                        swal({
                            title: "Warning!",
                            text: "You do not have permission, we will block you if you continue",
                            button: "Agree",
                        });
                    }
                    function alrtFilter2() {
                        swal({
                            title: "So sorry!",
                            text: "The page you just clicked on is currently under maintenance, please come back later!",
                            button: "Agree",
                        });
                    }
                    function alrtFilter3() {
                        swal({
                            title: "So Sorry!",
                            text: "Phiên đăng nhập đã kết thúc! Vui lòng re-login.",
                            button: "Okay",
                        });
                    }


                    if (${statusFilter} == 1) {
                        alrtFilter1();
                    }
                    if (${statusFilter} == 3) {
                        alrtFilter3();
                    }
                    if (${statusFilter} == 2) {
                        alrtFilter2();
                    }
                    if (${statusLogin} == 1) {
                        alrtLogin1();
                    }
                    if (${statusLogin} == 2) {
                        alrtLogin2();
                    }

                </script>
                <!-- content-wrapper ends -->
                <jsp:include page="component/Footer.jsp"/>
                <!-- partial:../../partials/_footer.html -->
                <!-- partial -->
            </div>
            <!-- main-panel ends -->
        </div>
        <!-- page-body-wrapper ends -->    
        <!-- end container-scroller -->
    </div>

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


