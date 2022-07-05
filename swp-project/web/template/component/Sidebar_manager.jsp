<%-- 
    Document   : DashBoard
    Created on : May 12, 2022, 10:00:59 PM
    Author     : SY NGUYEN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Sidebar </title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="../assets/assets_template/vendors/feather/feather.css">
        <link rel="stylesheet" href="../assets/assets_template/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="../assets/assets_template/vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="../assets/assets_template/vendors/typicons/typicons.css">
        <link rel="stylesheet" href="../assets/assets_template/vendors/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="../assets/assets_template/vendors/css/vendor.bundle.base.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <link rel="stylesheet" href="../assets/assets_template/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
        <link rel="stylesheet" href="../assets/assets_template/js/select.dataTables.min.css">
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link href="../assets/assets_template/css/vertical-layout-light/style.css" rel="stylesheet" type="text/css"/>
        <!-- endinject -->
        <link rel="shortcut icon" href="assets/assets_template/images/icon.png" />
    </head>
    <body>

        <%
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String avatar = request.getParameter("avatar");
        %>

        <div class="container-scroller">
            <!-- partial:partials/_navbar.html -->
            <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
                <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
                    <div class="me-3">
                        <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-bs-toggle="minimize">
                            <span class="icon-menu"></span>
                        </button>
                    </div>
                    <div>
                        <a class="navbar-brand brand-logo" href="#">
                            <img src="assets/assets_template/images/logo.png" alt="logo" />
                        </a>
                        <a class="navbar-brand brand-logo-mini" href="#">
                            <img src="assets/assets_template/images/icon.png" alt="logo" />
                        </a>
                    </div>
                </div>
                <div class="navbar-menu-wrapper d-flex align-items-top"> 
                    <ul class="navbar-nav">
                        <li class="nav-item font-weight-semibold d-none d-lg-block ms-0">
                            <h1 class="welcome-text">Halo, <span class="text-black fw-bold"><%=name%></span></h1>
                            <h3 class="welcome-sub-text">Your project performance summary</h3>
                        </li>
                    </ul>
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item dropdown">
                            <a class="nav-link count-indicator" id="notificationDropdown" href="#" data-bs-toggle="dropdown">
                                <i class="mdi mdi-facebook-messenger"></i>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list pb-0" aria-labelledby="notificationDropdown">
                                <a class="dropdown-item py-3 border-bottom">
                                    <p class="mb-0 font-weight-medium float-left">You have 4 new notifications </p>
                                    <span class="badge badge-pill badge-primary float-right">View all</span>
                                </a>
                                <a class="dropdown-item preview-item py-3">
                                    <div class="preview-thumbnail">
                                        <i class="mdi mdi-email-outline m-auto text-primary"></i>
                                    </div>
                                    <div class="preview-item-content">
                                        <h6 class="preview-subject fw-normal text-dark mb-1">Feedback from the instructor</h6>
                                        <p class="fw-light small-text mb-0"> Just now </p>
                                    </div>
                                </a>
                                <a class="dropdown-item preview-item py-3">
                                    <div class="preview-thumbnail">
                                        <i class="mdi mdi-email-outline m-auto text-primary"></i>
                                    </div>
                                    <div class="preview-item-content">
                                        <h6 class="preview-subject fw-normal text-dark mb-1">Request a new class</h6>
                                        <p class="fw-light small-text mb-0"> 1 days ago</p>
                                    </div>
                                </a>
                                <a class="dropdown-item preview-item py-3">
                                    <div class="preview-thumbnail">
                                        <i class="mdi mdi-email-outline m-auto text-primary"></i>
                                    </div>
                                    <div class="preview-item-content">
                                        <h6 class="preview-subject fw-normal text-dark mb-1">New user registration</h6>
                                        <p class="fw-light small-text mb-0"> 2 days ago </p>
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li class="nav-item dropdown"> 
                            <a class="nav-link count-indicator" id="countDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="icon-bell"></i>
                                <span class="count"></span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list pb-0" aria-labelledby="countDropdown">
                                <a class="dropdown-item py-3">
                                    <p class="mb-0 font-weight-medium float-left">You have 7 unread mails </p>
                                    <span class="badge badge-pill badge-primary float-right">View all</span>
                                </a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item preview-item">
                                    <div class="preview-thumbnail">
                                        <img src="assets/assets_template/images/faces/face10.jpg" alt="image" class="img-sm profile-pic">
                                    </div>
                                    <div class="preview-item-content flex-grow py-2">
                                        <p class="preview-subject ellipsis font-weight-medium text-dark">Marian Garner </p>
                                        <p class="fw-light small-text mb-0"> The meeting is cancelled </p>
                                    </div>
                                </a>
                                <a class="dropdown-item preview-item">
                                    <div class="preview-thumbnail">
                                        <img src="assets/assets_template/images/faces/face12.jpg" alt="image" class="img-sm profile-pic">
                                    </div>
                                    <div class="preview-item-content flex-grow py-2">
                                        <p class="preview-subject ellipsis font-weight-medium text-dark">David Grey </p>
                                        <p class="fw-light small-text mb-0"> The meeting is cancelled </p>
                                    </div>
                                </a>
                                <a class="dropdown-item preview-item">
                                    <div class="preview-thumbnail">
                                        <img src="assets/assets_template/images/faces/face1.jpg" alt="image" class="img-sm profile-pic">
                                    </div>
                                    <div class="preview-item-content flex-grow py-2">
                                        <p class="preview-subject ellipsis font-weight-medium text-dark">Travis Jenkins </p>
                                        <p class="fw-light small-text mb-0"> The meeting is cancelled </p>
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li class="nav-item dropdown d-none d-lg-block user-dropdown">
                            <a class="nav-link" id="UserDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                                <img class="img-xs rounded-circle" src="<%=avatar%>" alt="Profile image"> </a>
                            <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="UserDropdown">
                                <div class="dropdown-header text-center">
                                    <img style=" border-radius: 50%; width: 50px; height: 50px; object-fit: cover;" src="<%=avatar%>" alt="Profile image">
                                    <p class="mb-1 mt-3 font-weight-semibold"><%=name%></p>
                                    <p class="fw-light text-muted mb-0"><%=email%></p>
                                </div>
                                <a class="dropdown-item" onclick="callProfile()" ><i class="dropdown-item-icon mdi mdi-account-outline text-primary me-2"></i> My Profile <span class="badge badge-pill badge-danger"></span></a>
                                <a class="dropdown-item" onclick="callChangePass()" ><i class="dropdown-item-icon mdi mdi mdi-key-minus text-primary me-2"></i> Change Password</a>
                                <a class="dropdown-item" onclick="callSignOut()"><i class="dropdown-item-icon mdi mdi-power text-primary me-2"></i>Sign Out</a>
                            </div>
                        </li>
                    </ul>
                    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-bs-toggle="offcanvas">
                        <span class="mdi mdi-menu"></span>
                    </button>
                </div>
            </nav>
            <!-- partial -->
            <div class="container-fluid page-body-wrapper">
                <!-- partial:partials/_settings-panel.html -->
                <div class="theme-setting-wrapper">
                    <div id="settings-trigger"><i class="ti-settings"></i></div>
                    <div id="theme-settings" class="settings-panel">
                        <i class="settings-close ti-close"></i>
                        <p class="settings-heading">SIDEBAR SKINS</p>
                        <div class="sidebar-bg-options selected" id="sidebar-light-theme"><div class="img-ss rounded-circle bg-light border me-3"></div>Light</div>
                        <div class="sidebar-bg-options" id="sidebar-dark-theme"><div class="img-ss rounded-circle bg-dark border me-3"></div>Dark</div>
                        <p class="settings-heading mt-2">HEADER SKINS</p>
                        <div class="color-tiles mx-0 px-4">
                            <div class="tiles success"></div>
                            <div class="tiles warning"></div>
                            <div class="tiles danger"></div>
                            <div class="tiles info"></div>
                            <div class="tiles dark"></div>
                            <div class="tiles default"></div>
                        </div>
                    </div>
                </div>
                <!-- partial -->
                <!-- partial: sibar -->
                <nav class="sidebar sidebar-offcanvas" id="sidebar">
                    <ul class="nav">
                        <li class="nav-item">
                            <a class="nav-link" onclick="callHome()">
                                <i class="menu-icon mdi mdi-home"></i>
                                <span class="menu-title">Home</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" onclick="callDashboard()">
                                <i class="mdi mdi-grid-large menu-icon"></i>
                                <span class="menu-title">Dashboard</span>
                            </a>
                        </li>
                        <li class="nav-item nav-category">Content Management</li>
                        <li class="nav-item">
                            <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">Subject Setting</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="collapse" id="auth">
                                <ul class="nav flex-column sub-menu">
                                    <li class="nav-item"> <a class="nav-link" href="#">Subject Setting List</a></li>
                                    <li class="nav-item"> <a class="nav-link" href="#">Subject Setting Detail</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-bs-toggle="collapse" href="#auth1" aria-expanded="false" aria-controls="auth1">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">Class</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="collapse" id="auth1">
                                <ul class="nav flex-column sub-menu">
                                    <li class="nav-item"> <a class="nav-link" href="#">Class List</a></li>
                                    <li class="nav-item"> <a class="nav-link" href="#">Class Detail</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-bs-toggle="collapse" href="#auth2" aria-expanded="false" aria-controls="auth2">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">Iteration</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="collapse" id="auth2">
                                <ul class="nav flex-column sub-menu">
                                    <li class="nav-item"> <a class="nav-link" href="#">Iteration List</a></li>
                                    <li class="nav-item"> <a class="nav-link" href="#">Iteration Details</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-bs-toggle="collapse" href="#auth3" aria-expanded="false" aria-controls="auth2">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">Criteria</span>
                                <i class="menu-arrow"></i>
                            </a>
                            <div class="collapse" id="auth3">
                                <ul class="nav flex-column sub-menu">
                                    <li class="nav-item"> <a class="nav-link" href="/swp-project/manager/evaluation_criteria">Criteria List</a></li>
                                    <li class="nav-item"> <a class="nav-link" href="#">Criteria Details</a></li>
                                </ul>
                            </div>
                        </li>
                        <li class="nav-item nav-category">help</li>
                        <li class="nav-item">
                            <a class="nav-link" href="template/404_Page.jsp">
                                <i class="menu-icon mdi mdi-help-circle-outline"></i>
                                <span class="menu-title">Contact With Admin</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- partial: end siber -->
                <!-- plugins:js -->
                <script>
                    function callHome(url) {
                        window.location.href = "/swp-project/home"
                    }
                    function callDashboard(url) {
                        window.location.href = "/swp-project/dash-board"
                    }
                    function callSignOut(url) {
                        window.location.href = "/swp-project/auth?go=logout"
                    }
                    function callChangePass(url) {
                        window.location.href = "/swp-project/auth?go=change-password"
                    }
                    function callProfile(url) {
                        window.location.href = "/swp-project/auth?go=over-view"
                    }
                </script>
                <script src="../assets/assets_template/vendors/js/vendor.bundle.base.js"></script>
                <!-- endinject -->
                <!-- Plugin js for this page -->
                <script src="../assets/assets_template/vendors/chart.js/Chart.min.js"></script>
                <script src="../assets/assets_template/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
                <script src="../assets/assets_template/vendors/progressbar.js/progressbar.min.js"></script>

                <!-- End plugin js for this page -->
                <!-- inject:js -->
                <script src="../assets/assets_template/js/off-canvas.js"></script>
                <script src="../assets/assets_template/js/hoverable-collapse.js"></script>
                <script src="../assets/assets_template/js/template.js"></script>
                <script src="../assets/assets_template/js/settings.js"></script>
                <script src="../assets/assets_template/js/todolist.js"></script>
                <!-- endinject -->
                <!-- Custom js for this page-->
                <script src="../assets/assets_template/js/jquery.cookie.js" type="text/javascript"></script>
                <script src="../assets/assets_template/js/dashboard.js"></script>
                <script src="../assets/assets_template/js/Chart.roundedBarCharts.js"></script>
                <!-- End custom js for this page-->
                </body>

                </html>


