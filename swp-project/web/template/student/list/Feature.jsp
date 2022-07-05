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
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <title>Feature List</title>
        <style>
            .switch {
                position: relative;
                display: inline-block;
                width: 45px;
                height: 22px;
            }

            .switch input { 
                opacity: 0;
                width: 0;
                height: 0;
            }

            .slider {
                position: absolute;
                cursor: pointer;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: #ccc;
                -webkit-transition: .4s;
                transition: .4s;
            }

            .slider:before {
                position: absolute;
                content: "";
                height: 18px;
                width: 18px;
                left: 2px;
                bottom: 2px;
                background-color: white;
                -webkit-transition: .4s;
                transition: .4s;
            }

            input:checked + .slider {
                background-color: #1F3BB3;
            }

            input:focus + .slider {
                box-shadow: 0 0 1px #2196F3;
            }

            input:checked + .slider:before {
                -webkit-transform: translateX(23px);
                -ms-transform: translateX(23px);
                transform: translateX(23px);
            }

            /* Rounded sliders */
            .slider.round {
                border-radius: 34px;
            }

            .slider.round:before {
                border-radius: 50%;
            }
        </style>
    </head>
    <body>

        <!--UI role admin-------------------------------------------------->
        <c:if test="${user.getRole_id() == 1 || user.getRole_id() == 2}">
            <div class="container-scroller ">
                <jsp:include page="../../component/Sidebar_admin.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
                <div class="main-panel">        
                    <div class="content-wrapper">
                        <div class="row" id="list">
                            <form action="feature" method="POST" id="form-search">
                                <input type="hidden" name="go" value="view">
                                <div class="col-md-12 grid-margin stretch-card ">
                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="class_code" id="search-type" style="height: 48px">
                                                <option value="">
                                                    <c:if test="${class_code != null}">${class_code}</c:if>
                                                    <c:if test="${class_code == null}">All Class Code</c:if>
                                                    </option>
                                                <c:forEach items="${requestScope.classCode}" var="temp">
                                                    <option value="${temp.getClass_code()}">${temp.getClass_code()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="subject_code" id="search-type" style="height: 48px">
                                                <option value="">
                                                    <c:if test="${subject_code != null}">${subject_code}</c:if>
                                                    <c:if test="${subject_code == null}">All Subject Code</c:if>
                                                    </option>
                                                <c:forEach items="${requestScope.filter}" var="temp">
                                                    <option value="${temp.getSubject_code()}">${temp.getSubject_code()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="feature_status" id="search-status" style="height: 48px"> 
                                                <option value="">
                                                    <c:if test="${feature_status != null}">${feature_status}</c:if>
                                                    <c:if test="${feature_status == null}">All Status</c:if>
                                                    </option>
                                                    <option value="0">Inactive</option>
                                                    <option value="1">Active</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-3" style="margin-right: 10px">
                                            <div class="form-group" style="height: 20px">
                                                <input type="text" class="form-control shadow-sm" name="feature_name" id="input-search-title"
                                                <c:if test="${iteration_name != null}">value="${iteration_name}"</c:if>
                                                <c:if test="${iteration_name == null}">placeholder="Search..."</c:if> 
                                                    style="height: 48px;" >
                                            </div>
                                        </div>
                                        <div style="margin-right: 10px">
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="submit" id="btn-search-name">
                                                <i class="ti-search" style="height: 20px"></i>
                                            </button>
                                        </div>              
                                        <div style="margin-right: 10px" shadow-sm>
                                            <button class="btn btn-outline-secondary text-dark" type="button" onclick="callBack()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="mdi mdi-filter-remove-outline"></i>
                                            </button>
                                        </div>
                                        <div>
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="callAdd()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="ti-plus"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <input type="hidden" id="sort_name" name="sort_name" value="asc">   
                                    <input type="hidden" id="sort_team" name="sort_team" value="asc">   
                                    <input type="hidden" id="sort_code" name="sort_code" value="asc">
                                    <input type="hidden" id="string_sort" name="string_sort" value="c.class_code asc, t.team_name asc, f.feature_name asc"> 
                                    <input type="hidden" id="page" name="page" value="1"> 
                                </form>
                                <div class="col-md-12 grid-margin stretch-card" id="myList" >
                                    <div class="card">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-md-10">
                                                    <h4 class="card-title">Feature List</h4>
                                                </div>
                                                <div class="col-md-2">
                                                    <p class="card-description">
                                                        There were ${count} matches.
                                                </p>
                                            </div>
                                        </div>
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th style="text-align: center;" >Class Code
                                                            <c:if test="${sort_code == 'asc'}">
                                                                <i onclick="sortCode()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_code == 'decs'}">
                                                                <i onclick="sortCode()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Subject Code</th>
                                                        <th style="text-align: center;" >Team
                                                            <c:if test="${sort_team == 'asc'}">
                                                                <i onclick="sortTeam()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_team == 'decs'}">
                                                                <i onclick="sortTeam()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Topic Name</th>
                                                        <th style="text-align: center;">Feature Name
                                                            <c:if test="${sort_name == 'asc'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_name == 'decs'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Detail</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                            <td style="text-align: center;" >${temp.getClass_code()}</td>
                                                            <td style="text-align: center;" >${temp.getSubject_code()}</td>
                                                            <td style="text-align: center;" >${temp.getTeam_name()}</td>
                                                            <td style="text-align: center;" >${temp.getTopic_name()}</td>
                                                            <td style="text-align: center;" >${temp.getFeature_name()}</td>
                                                            <td style="text-align: center;">
                                                                <c:if test="${temp.getStatus() == 1}">
                                                                    <label class="switch">
                                                                        <input type="checkbox" checked onchange="inactive(${temp.getId()})">
                                                                        <span class="slider round"></span>
                                                                    </label>
                                                                </c:if>
                                                                <c:if test="${temp.getStatus() == 0}">
                                                                    <label class="switch">
                                                                        <input type="checkbox" onchange="active(${temp.getId()})">
                                                                        <span class="slider round"></span>
                                                                    </label>
                                                                </c:if>
                                                            </td>
                                                            <td style="text-align: center;">
                                                                <a type="button" class="btn btn-icon" style="margin-top: 15px" href="feature?go=detail&id=${temp.getId()}" id="btn-add-setting">
                                                                    <i class="mdi mdi-format-list-bulleted"></i>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row" style="margin-top: 20px">
                                            <div class="col-md-12 ml-4">
                                                <div class="btn-group" role="group" aria-label="Basic example" >
                                                    <c:if test="${(page-1)>1}" >
                                                        <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(1)">First</button>
                                                    </c:if>

                                                    <c:forEach begin="${page-1}" end="${page+1}" var="i">
                                                        <c:if test="${i>=1 && i <= pageEnd}">
                                                            <c:if test="${i == page}">
                                                                <button type="button" class="btn btn-primary text-light-green" onclick="paging(${i})"><c:out value="${i}"></c:out></button>
                                                            </c:if>
                                                            <c:if test="${i!= page}">
                                                                <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${i})"><c:out value="${i}"></c:out></button>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:if test="${page+1<pageEnd}">
                                                        <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${pageEnd})">Last</button>
                                                    </c:if>  
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <jsp:include page="../../component/Footer.jsp"/>
                    </div>
                </div>
            </div>
        </c:if>

        <!--End UI role admin------------------------------------------>

        <!--UI role student-------------------------------------------->

        <c:if test="${user.getRole_id() == 4}">
            <jsp:include page="../../component/Sidebar_student.jsp">
                <jsp:param name="email" value="${user.getEmail()}"/>
                <jsp:param name="name" value="${user.getFull_name()}"/>
                <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
            </jsp:include>

            <div class="main-panel" style="width: 100%">        
                <div class="content-wrapper">
                    <div class="row" id="list">
                        <div class="col-sm-12">
                            <div class="home-tab">
                                <div class="d-sm-flex align-items-center justify-content-between border-bottom" style="margin-bottom: 30px">
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link text-primary" href="feature" role="tab">Feature List</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link"  href="feature?go=add" role="tab">Add Feature</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <form action="feature" method="POST" id="form-search">
                            <input type="hidden" name="go" value="view">

                            <input type="hidden" id="page" name="page" value="1"> 
                        </form>
                        <div class="col-md-12 grid-margin stretch-card" id="myList" >
                            <div class="row">
                                <c:forEach items="${requestScope.list}" var="temp">
                                    <div class="col-md-4 grid-margin grid-margin stretch-card">
                                        <div class="card">
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="col-md-9">
                                                        <h4 class="card-title">${temp.getSubject_code()} - ${temp.getSubject_name()}</h4>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <a type="button" class="btn btn-icon" style="margin-left: 30px" href="feature?go=detail&id=${temp.getId()}" id="btn-add-setting">
                                                            <i class="mdi mdi-format-list-bulleted"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                                <p class="card-description">
                                                    <b>Class: </b><span class="text-primary" style="margin-right: 210px">${temp.getClass_code()}</span>
                                                    <span>
                                                        <c:if test="${temp.getStatus() == 1}">
                                                            <span class="btn btn-inverse-success btn-fw" style="text-align: center; border-radius: 20px; width: 110px">Active</span>
                                                        </c:if>
                                                        <c:if test="${temp.getStatus() == 0}">
                                                            <span class="btn btn-inverse-danger btn-fw" style="text-align: center; border-radius: 20px; width: 110px">In Active</span>
                                                        </c:if>
                                                    </span>
                                                </p>
                                                <ul class="list-arrow">
                                                    <li><b>Feature Name: </b>${temp.getFeature_name()}</li>
                                                    <li><b>Topic Name: </b>${temp.getTopic_name()}</li>
                                                    <li><b>Team: </b>${temp.getTeam_name()}</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="../../component/Footer.jsp"/>
                </div>
            </div>
        </c:if>

        <!--End UI role student---------------------------------------->

        <!--UI role teacher-------------------------------------------->
        <c:if test="${user.getRole_id() == 3}">
            <jsp:include page="../../component/Sidebar_admin.jsp">
                <jsp:param name="email" value="${user.getEmail()}"/>
                <jsp:param name="name" value="${user.getFull_name()}"/>
                <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
            </jsp:include>
            <div class="main-panel">        
                <div class="content-wrapper">
                    <div class="row" id="list">
                        <form action="feature" method="POST" id="form-search">
                            <input type="hidden" name="go" value="view">
                            <div class="col-md-12 grid-margin stretch-card ">
                                <div class="col-md-2" style="margin-right: 10px">
                                    <div>
                                        <select class="form-control shadow-sm" name="class_code" id="search-type" style="height: 48px">
                                            <option value="">
                                                <c:if test="${class_code != null}">${class_code}</c:if>
                                                <c:if test="${class_code == null}">All Class Code</c:if>
                                                </option>
                                            <c:forEach items="${requestScope.classCode}" var="temp">
                                                <option value="${temp.getClass_code()}">${temp.getClass_code()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2" style="margin-right: 10px">
                                    <div>
                                        <select class="form-control shadow-sm" name="subject_code" id="search-type" style="height: 48px">
                                            <option value="">
                                                <c:if test="${subject_code != null}">${subject_code}</c:if>
                                                <c:if test="${subject_code == null}">All Subject Code</c:if>
                                                </option>
                                            <c:forEach items="${requestScope.filter}" var="temp">
                                                <option value="${temp.getSubject_code()}">${temp.getSubject_code()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-2" style="margin-right: 10px;">
                                    <div>
                                        <select class="form-control shadow-sm" name="feature_status" id="search-status" style="height: 48px"> 
                                            <option value="">
                                                <c:if test="${feature_status != null}">${feature_status}</c:if>
                                                <c:if test="${feature_status == null}">All Status</c:if>
                                                </option>
                                                <option value="0">Inactive</option>
                                                <option value="1">Active</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-3" style="margin-right: 10px">
                                        <div class="form-group" style="height: 20px">
                                            <input type="text" class="form-control shadow-sm" name="feature_name" id="input-search-title"
                                            <c:if test="${iteration_name != null}">value="${iteration_name}"</c:if>
                                            <c:if test="${iteration_name == null}">placeholder="Search..."</c:if> 
                                                style="height: 48px;" >
                                        </div>
                                    </div>
                                    <div style="margin-right: 10px">
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="submit" id="btn-search-name">
                                            <i class="ti-search" style="height: 20px"></i>
                                        </button>
                                    </div>              
                                    <div style="margin-right: 10px" shadow-sm>
                                        <button class="btn btn-outline-secondary text-dark" type="button" onclick="callBack()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                            <i class="mdi mdi-filter-remove-outline"></i>
                                        </button>
                                    </div>
                                    <div>
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="callAdd()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                            <i class="ti-plus"></i>
                                        </button>
                                    </div>
                                </div>
                                <input type="hidden" id="sort_name" name="sort_name" value="asc">   
                                <input type="hidden" id="sort_team" name="sort_team" value="asc">   
                                <input type="hidden" id="sort_code" name="sort_code" value="asc">
                                <input type="hidden" id="string_sort" name="string_sort" value="c.class_code asc, t.team_name asc, f.feature_name asc"> 
                                <input type="hidden" id="page" name="page" value="1"> 
                            </form>
                            <div class="col-md-12 grid-margin stretch-card" id="myList" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-10">
                                                <h4 class="card-title">Feature List</h4>
                                            </div>
                                            <div class="col-md-2">
                                                <p class="card-description">
                                                    There were ${count} matches.
                                            </p>
                                        </div>
                                    </div>
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th style="text-align: center;" >Class Code
                                                        <c:if test="${sort_code == 'asc'}">
                                                            <i onclick="sortCode()" class="mdi mdi-sort-ascending"></i>
                                                        </c:if>
                                                        <c:if test="${sort_code == 'decs'}">
                                                            <i onclick="sortCode()" class="mdi mdi-sort-descending"></i>
                                                        </c:if>
                                                    </th>
                                                    <th style="text-align: center;">Subject Code</th>
                                                    <th style="text-align: center;" >Team
                                                        <c:if test="${sort_team == 'asc'}">
                                                            <i onclick="sortTeam()" class="mdi mdi-sort-ascending"></i>
                                                        </c:if>
                                                        <c:if test="${sort_team == 'decs'}">
                                                            <i onclick="sortTeam()" class="mdi mdi-sort-descending"></i>
                                                        </c:if>
                                                    </th>
                                                    <th style="text-align: center;">Topic Name</th>
                                                    <th style="text-align: center;">Feature Name
                                                        <c:if test="${sort_name == 'asc'}">
                                                            <i onclick="sortName()" class="mdi mdi-sort-ascending"></i>
                                                        </c:if>
                                                        <c:if test="${sort_name == 'decs'}">
                                                            <i onclick="sortName()" class="mdi mdi-sort-descending"></i>
                                                        </c:if>
                                                    </th>
                                                    <th style="text-align: center;">Status</th>
                                                    <th style="text-align: center;">Detail</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${requestScope.list}" var="temp">
                                                    <tr>
                                                        <td style="text-align: center;" >${temp.getClass_code()}</td>
                                                        <td style="text-align: center;" >${temp.getSubject_code()}</td>
                                                        <td style="text-align: center;" >${temp.getTeam_name()}</td>
                                                        <td style="text-align: center;" >${temp.getTopic_name()}</td>
                                                        <td style="text-align: center;" >${temp.getFeature_name()}</td>
                                                        <td style="text-align: center;">
                                                            <c:if test="${temp.getStatus() == 1}">
                                                                <label class="switch">
                                                                    <input type="checkbox" checked onchange="inactive(${temp.getId()})">
                                                                    <span class="slider round"></span>
                                                                </label>
                                                            </c:if>
                                                            <c:if test="${temp.getStatus() == 0}">
                                                                <label class="switch">
                                                                    <input type="checkbox" onchange="active(${temp.getId()})">
                                                                    <span class="slider round"></span>
                                                                </label>
                                                            </c:if>
                                                        </td>
                                                        <td style="text-align: center;">
                                                            <a type="button" class="btn btn-icon" style="margin-top: 15px" href="feature?go=detail&id=${temp.getId()}" id="btn-add-setting">
                                                                <i class="mdi mdi-format-list-bulleted"></i>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="row" style="margin-top: 20px">
                                        <div class="col-md-12 ml-4">
                                            <div class="btn-group" role="group" aria-label="Basic example" >
                                                <c:if test="${(page-1)>1}" >
                                                    <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(1)">First</button>
                                                </c:if>

                                                <c:forEach begin="${page-1}" end="${page+1}" var="i">
                                                    <c:if test="${i>=1 && i <= pageEnd}">
                                                        <c:if test="${i == page}">
                                                            <button type="button" class="btn btn-primary text-light-green" onclick="paging(${i})"><c:out value="${i}"></c:out></button>
                                                        </c:if>
                                                        <c:if test="${i!= page}">
                                                            <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${i})"><c:out value="${i}"></c:out></button>
                                                        </c:if>
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${page+1<pageEnd}">
                                                    <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(${pageEnd})">Last</button>
                                                </c:if>  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="../../component/Footer.jsp"/>
                </div>
            </c:if>
            <!--End UI role teacher---------------------------------------->

            <!--Xu li alert and dong mo form-->
            <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
            <c:if test="${success != null}">
                <script>
                                                        window.onload = function ()
                                                        {
                                                            alert("success", "Success!", "${success}!")
                                                        };
                </script>
            </c:if>

            <c:if test="${error != null}">
                <script>
                    window.onload = function ()
                    {
                        alert("error", "Fail!", "${error}!")
                    };
                </script>
            </c:if>
            <script>

                $(document).ready(function () {
                    $('#sort_name').val('${sort_name}')
                    $('#sort_team').val('${sort_team}')
                    $('#sort_code').val('${sort_code}')
                    $('#string_sort').val('${string_sql}')

                });
                function status() {
                    var status;
                    var checkstatus = document.getElementsByName("status");
                    for (var i = 0; i < checkstatus.length; i++) {
                        if (checkstatus[i].checked === true) {
                            status = 1
                        } else if (checkstatus[i].checked === false) {
                            status = 0
                        }
                    }
                    console.log(status);
                }

                function paging(e) {
                    $('#page').val(e)
                    $('#form-search').submit()
                }

                function sortName() {
                    //bindding
                    if ($('#sort_name').val() == 'asc')
                        $('#sort_name').val('decs')
                    else
                        $('#sort_name').val('asc')
                    let sql = '';

                    // tao chuoi sql
                    if ($('#sort_name').val() == 'asc')
                        sql += 'f.feature_name asc,'
                    else
                        sql += 'f.feature_name desc,'
                    if ($('#sort_team').val() == 'asc')
                        sql += 't.team_name asc,'
                    else
                        sql += 't.team_name desc,'
                    if ($('#sort_code').val() == 'asc')
                        sql += 'c.class_code asc'
                    else
                        sql += 'c.class_code desc'
                    // gan gia tri ve form
                    $('#string_sort').val(sql)

                    $('#form-search').submit()
                }
                function sortTeam() {
                    //bindding
                    if ($('#sort_team').val() == 'asc')
                        $('#sort_team').val('decs')
                    else
                        $('#sort_team').val('asc')
                    let sql = '';

                    // tao chuoi sql
                    if ($('#sort_team').val() == 'asc')
                        sql += 't.team_name asc,'
                    else
                        sql += 't.team_name desc,'
                    if ($('#sort_name').val() == 'asc')
                        sql += 'f.feature_name asc,'
                    else
                        sql += 'f.feature_name desc,'
                    if ($('#sort_code').val() == 'asc')
                        sql += 'c.class_code asc'
                    else
                        sql += 'c.class_code desc'
                    // gan gia tri ve form
                    $('#string_sort').val(sql)

                    $('#form-search').submit()
                }
                function sortCode() {
                    //bindding
                    if ($('#sort_code').val() == 'asc')
                        $('#sort_code').val('decs')
                    else
                        $('#sort_code').val('asc')
                    let sql = '';

                    // tao chuoi sql
                    if ($('#sort_code').val() == 'asc')
                        sql += 'c.class_code asc,'
                    else
                        sql += 'c.class_code desc,'
                    if ($('#sort_team').val() == 'asc')
                        sql += 't.team_name asc,'
                    else
                        sql += 't.team_name desc,'
                    if ($('#sort_name').val() == 'asc')
                        sql += 'f.feature_name asc'
                    else
                        sql += 'f.feature_name desc'

                    // gan gia tri ve form
                    $('#string_sort').val(sql)

                    $('#form-search').submit()
                }

                function callAdd() {
                    window.location.href = "/swp-project/student/feature?go=add"
                }

                function callBack() {
                    window.location.href = "/swp-project/student/feature"
                }

                function submitForm() {
                    document.getElementById("form-search").submit();
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

                function active(id)
                {
                    swal({
                        title: "Are you sure?",
                        text: "You want update action ACTIVE Feature!",
                        icon: "warning",
                        buttons: true,
                        dangerMode: true,
                    })
                            .then((willDelete) => {
                                if (willDelete) {
                                    alert("success", "Success!", "Update Inactivate Successfully!")
                                    setTimeout(function () {
                                        window.location.href = "/swp-project/student/feature?go=active&id=" + id;
                                    }, 3000)
                                } else {
                                    window.location.href = "/swp-project/student/feature";
                                }
                            });
                }

                function inactive(id)
                {
                    swal({
                        title: "Are you sure?",
                        text: "You want update action INACTIVE Feature!",
                        icon: "warning",
                        buttons: true,
                        dangerMode: true,
                    })
                            .then((willDelete) => {
                                if (willDelete) {
                                    alert("success", "Success!", "Update Activate Successfully!")
                                    setTimeout(function () {
                                        window.location.href = "/swp-project/student/feature?go=inactive&id=" + id;
                                    }, 3000)
                                } else {
                                    window.location.href = "/swp-project/student/feature";
                                }
                            });
                }
            </script>
            <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    </body>
</html>
