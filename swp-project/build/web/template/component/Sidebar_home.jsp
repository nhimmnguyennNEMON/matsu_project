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
                            <h1 class="welcome-text">Welcome to the project management systems<span class="text-black fw-bold"></span></h1>
                        </li>
                    </ul>
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a href="auth?go=login" class="btn btn-outline-dark btn-fw">Login</a>
                        </li>
                        <li class="nav-item">
                            <a href="auth?go=register" class="btn btn-outline-dark btn-fw">Register</a>
                        </li>
                    </ul>
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
                            <a class="nav-link" href="home">
                                <i class="menu-icon mdi mdi-home"></i>
                                <span class="menu-title">Home</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#about">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">About</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#contact">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">Contact</span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#infosupport">
                                <i class="menu-icon mdi mdi-layers-outline"></i>
                                <span class="menu-title">Information & Supporting</span>
                            </a>
                        </li>
                    </ul>
                </nav>
                <!-- partial: end siber -->
                <!-- plugins:js -->
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


