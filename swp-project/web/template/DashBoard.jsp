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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<!DOCTYPE html>
<html lang="vi">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Dashboard Page</title>
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
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />


    </head>
    <body>

        <div class="container-scroller">

            <c:if test="${user.getRole_id() == 1}">
                <jsp:include page="component/Sidebar_admin.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>

            </c:if>
            <c:if test="${user.getRole_id() == 2}">
                <jsp:include page="component/Sidebar_manager.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
            </c:if>
            <c:if test="${user.getRole_id() == 3}">
                <jsp:include page="component/Sidebar_teacher.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
            </c:if>
            <c:if test="${user.getRole_id() == 4}">
                <jsp:include page="component/Sidebar_student.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
            </c:if>


            <div class="main-panel">
                <div class="content-wrapper">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="home-tab">
                                <div class="d-sm-flex align-items-center justify-content-between border-bottom">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active ps-0" id="home-tab" data-bs-toggle="tab" href="#overview" role="tab" aria-controls="overview" aria-selected="true">Overview</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="subject-tab" data-bs-toggle="tab" href="#subject" role="tab" aria-selected="false">Subject</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="iteration-tab" data-bs-toggle="tab" href="#iteration" role="tab" aria-selected="false">Iteration</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="criteria-tab" data-bs-toggle="tab" href="#criteria" role="tab" aria-selected="false">Criteria</a>
                                        </li>
                                    </ul>
                                    <div>
                                        <div class="btn-wrapper">
                                            <a href="#" class="btn btn-otline-dark align-items-center"><i class="icon-share"></i> Share</a>
                                            <a href="#" class="btn btn-primary text-white me-0"><i class="icon-upload"></i> Import</a>
                                            <a href="#" class="btn btn-primary text-white me-0"><i class="icon-download"></i> Export</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-content tab-content-basic">
                                    <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="overview"> 
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div class="statistics-details d-flex align-items-center justify-content-between">
                                                    <div>
                                                        <p class="statistics-title">Subject Open</p>
                                                        <h3 class="text-success">27</h3>
                                                    </div>
                                                    <div>
                                                        <p class="statistics-title">Class Open</p>
                                                        <h3 class="text-success">46</h3>
                                                    </div>
                                                    <div>
                                                        <p class="statistics-title">Project Total</p>
                                                        <h3 class="text-light-green">567</h3>
                                                    </div>
                                                    <div>
                                                        <p class="statistics-title">Teacher</p>
                                                        <h3 class="text-light-green">128</h3>
                                                    </div>
                                                    <div>
                                                        <p class="statistics-title">User</p>
                                                        <h3 class="text-light-green">3062</h3>
                                                    </div>
                                                </div>
                                            </div>
                                        </div> 
                                        <div class="row">
                                            <div class="col-lg-8 d-flex flex-column">
                                                <div class="row flex-grow">
                                                    <div class="col-12 col-lg-4 col-lg-12 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="d-sm-flex justify-content-between align-items-start">
                                                                    <div>
                                                                        <h4 class="card-title card-title-dash" >Project Completion Performance</h4>
                                                                        <h5 class="card-subtitle card-subtitle-dash">Lorem Ipsum is simply dummy text of the printing</h5>

                                                                    </div>
                                                                    <div id="performance-line-legend"></div>
                                                                </div>
                                                                <div class="chartjs-wrapper mt-5">
                                                                    <canvas id="performaneLine"></canvas>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 d-flex flex-column">
                                                <div class="row flex-grow">
                                                    <div class="col-md-6 col-lg-12 grid-margin stretch-card">
                                                        <div class="card bg-primary card-rounded">
                                                            <div class="card-body pb-0">
                                                                <h4 class="card-title card-title-dash text-white mb-4">Project Status Total</h4>
                                                                <div class="row">
                                                                    <div class="col-sm-4">
                                                                        <p class="status-summary-ight-white mb-1">New Project</p>
                                                                        <h2 class="text-white">37</h2>
                                                                    </div>
                                                                    <div class="col-sm-8">
                                                                        <div class="status-summary-chart-wrapper pb-4">
                                                                            <canvas id="status-summary"></canvas>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 col-lg-12 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="col-sm-6">
                                                                        <div class="d-flex justify-content-between align-items-center mb-2 mb-sm-0">
                                                                            <div class="circle-progress-width">
                                                                                <div id="totalVisitors" class="progressbar-js-circle pr-2"></div>
                                                                            </div>
                                                                            <div>
                                                                                <p class="text-small mb-2">Progress</p>
                                                                                <h4 class="mb-0 fw-bold">335</h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-sm-6">
                                                                        <div class="d-flex justify-content-between align-items-center">
                                                                            <div class="circle-progress-width">
                                                                                <div id="visitperday" class="progressbar-js-circle pr-2"></div>
                                                                            </div>
                                                                            <div>
                                                                                <p class="text-small mb-2">Completed</p>
                                                                                <h4 class="mb-0 fw-bold">232</h4>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-8 d-flex flex-column">
                                                <div class="row flex-grow">
                                                    <div class="col-12 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="d-sm-flex justify-content-between align-items-start">
                                                                    <div>
                                                                        <h4 class="card-title card-title-dash">Overview Of Benefits For Members</h4>
                                                                        <p class="card-subtitle card-subtitle-dash">Lorem ipsum dolor sit amet consectetur adipisicing elit</p>
                                                                    </div>
                                                                    <div class="me-3"><div id="marketing-overview-legend"></div></div>
                                                                </div>
                                                                <div class="chartjs-bar-wrapper mt-3">
                                                                    <canvas id="marketingOverview"></canvas>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row flex-grow">
                                                    <div class="col-12 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="d-sm-flex justify-content-between align-items-start">
                                                                    <div>
                                                                        <h4 class="card-title card-title-dash">Project Progress Of Teams</h4>
                                                                        <p class="card-subtitle card-subtitle-dash">You have 50+ new requests</p>
                                                                    </div>
                                                                    <div>
                                                                        <button class="btn btn-primary btn-lg text-white mb-0 me-0" type="button"><i class="mdi mdi-account-plus"></i>Add new top</button>
                                                                    </div>
                                                                </div>
                                                                <div class="table-responsive  mt-1">
                                                                    <table class="table select-table">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    <div class="form-check form-check-flat mt-0">
                                                                                        <label class="form-check-label">
                                                                                            <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                                                                    </div>
                                                                                </th>
                                                                                <th>Team</th>
                                                                                <th>Class</th>
                                                                                <th>Progress</th>
                                                                                <th>Status</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td>
                                                                                    <div class="form-check form-check-flat mt-0">
                                                                                        <label class="form-check-label">
                                                                                            <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <div class="d-flex ">
                                                                                        <img src="https://cdn2.iconfinder.com/data/icons/audio-16/96/user_avatar_profile_login_button_account_member-512.png" alt="">
                                                                                        <div>
                                                                                            <h6>Team 1</h6>
                                                                                            <p>Head admin</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <h6>Class 1</h6>
                                                                                    <p>Lecturers 1</p>
                                                                                </td>
                                                                                <td>
                                                                                    <div>
                                                                                        <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                                                            <p class="text-success">95%</p>
                                                                                            <p>138/162</p>
                                                                                        </div>
                                                                                        <div class="progress progress-md">
                                                                                            <div class="progress-bar bg-success" role="progressbar" style="width: 95%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td><div class="badge badge-opacity-success">Done</div></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <div class="form-check form-check-flat mt-0">
                                                                                        <label class="form-check-label">
                                                                                            <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <div class="d-flex">
                                                                                        <img src="https://cdn2.iconfinder.com/data/icons/audio-16/96/user_avatar_profile_login_button_account_member-512.png" alt="">
                                                                                        <div>
                                                                                            <h6>Team 1</h6>
                                                                                            <p>Head admin</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <h6>Class 1</h6>
                                                                                    <p>Lecturers 1</p>
                                                                                <td>
                                                                                    <div>
                                                                                        <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                                                            <p class="text-success">55%</p>
                                                                                            <p>85/162</p>
                                                                                        </div>
                                                                                        <div class="progress progress-md">
                                                                                            <div class="progress-bar bg-warning" role="progressbar" style="width: 55%" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td><div class="badge badge-opacity-warning">Doing</div></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <div class="form-check form-check-flat mt-0">
                                                                                        <label class="form-check-label">
                                                                                            <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <div class="d-flex">
                                                                                        <img src="https://cdn2.iconfinder.com/data/icons/audio-16/96/user_avatar_profile_login_button_account_member-512.png" alt="">
                                                                                        <div>
                                                                                            <h6>Team 1</h6>
                                                                                            <p>Head admin</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <h6>Class 1</h6>
                                                                                    <p>Lecturers 1</p>
                                                                                </td>
                                                                                <td>
                                                                                    <div>
                                                                                        <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                                                            <p class="text-success">38%</p>
                                                                                            <p>85/162</p>
                                                                                        </div>
                                                                                        <div class="progress progress-md">
                                                                                            <div class="progress-bar bg-warning" role="progressbar" style="width: 38%" aria-valuenow="38" aria-valuemin="0" aria-valuemax="100"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td><div class="badge badge-opacity-warning">Doing</div></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <div class="form-check form-check-flat mt-0">
                                                                                        <label class="form-check-label">
                                                                                            <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <div class="d-flex">
                                                                                        <img src="https://cdn2.iconfinder.com/data/icons/audio-16/96/user_avatar_profile_login_button_account_member-512.png" alt="">
                                                                                        <div>
                                                                                            <h6>Team 1</h6>
                                                                                            <p>Head admin</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <h6>Class 1</h6>
                                                                                    <p>Lecturers 1</p>
                                                                                </td>
                                                                                <td>
                                                                                    <div>
                                                                                        <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                                                            <p class="text-success">10%</p>
                                                                                            <p>85/162</p>
                                                                                        </div>
                                                                                        <div class="progress progress-md">
                                                                                            <div class="progress-bar bg-danger" role="progressbar" style="width: 10%" aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td><div class="badge badge-opacity-danger">Todo</div></td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    <div class="form-check form-check-flat mt-0">
                                                                                        <label class="form-check-label">
                                                                                            <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <div class="d-flex">
                                                                                        <img src="https://cdn2.iconfinder.com/data/icons/audio-16/96/user_avatar_profile_login_button_account_member-512.png" alt="">
                                                                                        <div>
                                                                                            <h6>Team 1</h6>
                                                                                            <p>Head admin</p>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td>
                                                                                    <h6>Class 1</h6>
                                                                                    <p>Lecturers 1</p>
                                                                                </td>
                                                                                <td>
                                                                                    <div>
                                                                                        <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                                                            <p class="text-success">90%</p>
                                                                                            <p>85/162</p>
                                                                                        </div>
                                                                                        <div class="progress progress-md">
                                                                                            <div class="progress-bar bg-success" role="progressbar" style="width: 90%" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"></div>
                                                                                        </div>
                                                                                    </div>
                                                                                </td>
                                                                                <td><div class="badge badge-opacity-success">Done</div></td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row flex-grow">
                                                    <div class="col-md-6 col-lg-6 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body card-rounded">
                                                                <h4 class="card-title  card-title-dash">Recent Due</h4>
                                                                <div class="list align-items-center border-bottom py-2">
                                                                    <div class="wrapper w-100">
                                                                        <p class="mb-2 font-weight-medium">
                                                                            Change in Directors
                                                                        </p>
                                                                        <div class="d-flex justify-content-between align-items-center">
                                                                            <div class="d-flex align-items-center">
                                                                                <i class="mdi mdi-calendar text-muted me-1"></i>
                                                                                <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="list align-items-center border-bottom py-2">
                                                                    <div class="wrapper w-100">
                                                                        <p class="mb-2 font-weight-medium">
                                                                            Other Events
                                                                        </p>
                                                                        <div class="d-flex justify-content-between align-items-center">
                                                                            <div class="d-flex align-items-center">
                                                                                <i class="mdi mdi-calendar text-muted me-1"></i>
                                                                                <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="list align-items-center border-bottom py-2">
                                                                    <div class="wrapper w-100">
                                                                        <p class="mb-2 font-weight-medium">
                                                                            Quarterly Report
                                                                        </p>
                                                                        <div class="d-flex justify-content-between align-items-center">
                                                                            <div class="d-flex align-items-center">
                                                                                <i class="mdi mdi-calendar text-muted me-1"></i>
                                                                                <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="list align-items-center border-bottom py-2">
                                                                    <div class="wrapper w-100">
                                                                        <p class="mb-2 font-weight-medium">
                                                                            Change in Directors
                                                                        </p>
                                                                        <div class="d-flex justify-content-between align-items-center">
                                                                            <div class="d-flex align-items-center">
                                                                                <i class="mdi mdi-calendar text-muted me-1"></i>
                                                                                <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="list align-items-center pt-3">
                                                                    <div class="wrapper w-100">
                                                                        <p class="mb-0">
                                                                            <a href="#" class="fw-bold text-primary">Show all <i class="mdi mdi-arrow-right ms-2"></i></a>
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-6 col-lg-6 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="d-flex align-items-center justify-content-between mb-3">
                                                                    <h4 class="card-title card-title-dash">Activities</h4>
                                                                    <p class="mb-0">20 finished, 5 remaining</p>
                                                                </div>
                                                                <ul class="bullet-line-list">
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Ben Tossell</span> assign you a task</div>
                                                                            <p>Just now</p>
                                                                        </div>
                                                                    </li>
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Oliver Noah</span> assign you a task</div>
                                                                            <p>1h</p>
                                                                        </div>
                                                                    </li>
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Jack William</span> assign you a task</div>
                                                                            <p>1h</p>
                                                                        </div>
                                                                    </li>
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Leo Lucas</span> assign you a task</div>
                                                                            <p>1h</p>
                                                                        </div>
                                                                    </li>
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Thomas Henry</span> assign you a task</div>
                                                                            <p>1h</p>
                                                                        </div>
                                                                    </li>
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Ben Tossell</span> assign you a task</div>
                                                                            <p>1h</p>
                                                                        </div>
                                                                    </li>
                                                                    <li>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div><span class="text-light-green">Ben Tossell</span> assign you a task</div>
                                                                            <p>1h</p>
                                                                        </div>
                                                                    </li>
                                                                </ul>
                                                                <div class="list align-items-center pt-3">
                                                                    <div class="wrapper w-100">
                                                                        <p class="mb-0">
                                                                            <a href="#" class="fw-bold text-primary">Show all <i class="mdi mdi-arrow-right ms-2"></i></a>
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-4 d-flex flex-column">
                                                <div class="row flex-grow">
                                                    <div class="col-12 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="col-lg-12">
                                                                        <div class="d-flex justify-content-between align-items-center">
                                                                            <h4 class="card-title card-title-dash">Todo list this week</h4>
                                                                            <div class="add-items d-flex mb-0">
                                                                                <!-- <input type="text" class="form-control todo-list-input" placeholder="What do you need to do today?"> -->
                                                                                <button class="add btn btn-icons btn-rounded btn-primary todo-list-add-btn text-white me-0 pl-12p"><i class="mdi mdi-plus"></i></button>
                                                                            </div>
                                                                        </div>
                                                                        <div class="list-wrapper">
                                                                            <ul class="todo-list todo-list-rounded">
                                                                                <li class="d-block">
                                                                                    <div class="form-check w-100">
                                                                                        <label class="form-check-label">
                                                                                            <input class="checkbox" type="checkbox"> Lorem Ipsum is simply dummy text of the printing <i class="input-helper rounded"></i>
                                                                                        </label>
                                                                                        <div class="d-flex mt-2">
                                                                                            <div class="ps-4 text-small me-3">24 June 2020</div>
                                                                                            <div class="badge badge-opacity-warning me-3">Due tomorrow</div>
                                                                                            <i class="mdi mdi-flag ms-2 flag-color"></i>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                                <li class="d-block">
                                                                                    <div class="form-check w-100">
                                                                                        <label class="form-check-label">
                                                                                            <input class="checkbox" type="checkbox"> Lorem Ipsum is simply dummy text of the printing <i class="input-helper rounded"></i>
                                                                                        </label>
                                                                                        <div class="d-flex mt-2">
                                                                                            <div class="ps-4 text-small me-3">23 June 2020</div>
                                                                                            <div class="badge badge-opacity-success me-3">Done</div>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                                <li>
                                                                                    <div class="form-check w-100">
                                                                                        <label class="form-check-label">
                                                                                            <input class="checkbox" type="checkbox"> Lorem Ipsum is simply dummy text of the printing <i class="input-helper rounded"></i>
                                                                                        </label>
                                                                                        <div class="d-flex mt-2">
                                                                                            <div class="ps-4 text-small me-3">24 June 2020</div>
                                                                                            <div class="badge badge-opacity-success me-3">Done</div>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                                <li class="border-bottom-0">
                                                                                    <div class="form-check w-100">
                                                                                        <label class="form-check-label">
                                                                                            <input class="checkbox" type="checkbox"> Lorem Ipsum is simply dummy text of the printing <i class="input-helper rounded"></i>
                                                                                        </label>
                                                                                        <div class="d-flex mt-2">
                                                                                            <div class="ps-4 text-small me-3">24 June 2020</div>
                                                                                            <div class="badge badge-opacity-danger me-3">Expired</div>
                                                                                        </div>
                                                                                    </div>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row flex-grow">
                                                    <div class="col-12 grid-margin stretch-card">
                                                        <div class="card card-rounded">
                                                            <div class="card-body">
                                                                <div class="row">
                                                                    <div class="col-lg-12">
                                                                        <div class="d-flex justify-content-between align-items-center mb-3">
                                                                            <div>
                                                                                <h4 class="card-title card-title-dash">Active Users</h4>
                                                                            </div>
                                                                        </div>
                                                                        <div class="card card-rounded">
                                                                            <div class="card-body">
                                                                                <div class="table-responsive  mt-1">
                                                                                    <table class="table select-table">
                                                                                        <thead>
                                                                                            <tr>
                                                                                                <th>User</th>
                                                                                                <th>Status</th>
                                                                                            </tr>
                                                                                        </thead>
                                                                                        <tbody>
                                                                                            <c:forEach items="${requestScope.list}" var="temp">
                                                                                                <tr>
                                                                                                    <td>
                                                                                                        <div class="d-flex">
                                                                                                            <a href="#">
                                                                                                                <img src="${temp.getAvatar_link()}" style=" border-radius: 50%; width: 50px; height: 50px; object-fit: cover;" alt="">
                                                                                                            </a>
                                                                                                            <div>
                                                                                                                <a href="#">
                                                                                                                    <h6>${temp.getFull_name()}</h6>
                                                                                                                </a>
                                                                                                                <span>
                                                                                                                    <c:if test = "${temp.getRole_id() == 1}">
                                                                                                                        <p>Admin</p>
                                                                                                                    </c:if>
                                                                                                                    <c:if test = "${temp.getRole_id() == 2}">
                                                                                                                        <p>Manager</p>
                                                                                                                    </c:if>
                                                                                                                    <c:if test = "${temp.getRole_id() == 3}">
                                                                                                                        <p>Teacher</p>
                                                                                                                    </c:if>
                                                                                                                    <c:if test = "${temp.getRole_id() == 4}">
                                                                                                                        <p>Student</p>
                                                                                                                    </c:if>
                                                                                                                </span>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                    <td><c:if test="${temp.isOnline() == true}">
                                                                                                            <div class="badge badge-opacity-success">Online</div>
                                                                                                        </c:if>
                                                                                                        <c:if test="${temp.isOnline() == false}">
                                                                                                            <div class="badge badge-opacity-danger">Offline</div>
                                                                                                        </c:if>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </c:forEach>
                                                                                        </tbody>
                                                                                    </table>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- content-wrapper ends -->
                <!-- partial:partials/_footer.html -->
                <jsp:include page="component/Footer.jsp"/>
                <!-- partial -->
            </div>
            <!-- main-panel ends -->
        </div>
        <!-- container-scroller -->
        <%
            //                } catch (Exception e) {
            //                    request.getRequestDispatcher("HomePage.jsp").forward(request, response);
            //                }
            //            }
        %>


        <%--<jsp:include page="dashboard_JS.jsp"/>--%> 

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


