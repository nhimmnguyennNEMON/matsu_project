
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Milestones List</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>

        <style>
            .hinhanh {
                margin-left: 58px;
            }

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
        <div class="container-scroller">

            <c:if test="${user != null}">
                <jsp:include page="../../component/Sidebar_teacher.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>

                <div class="main-panel">        
                    <div class="content-wrapper" id="content">
                        <div class="row" id="list">
                            <form action="/swp-project/teacher/milestones" method="post" id="form-filter">
                                <div class="col-md-12 grid-margin stretch-card " id="mySearch">
                                    <div class="col-md-2" style="margin-right: 30px">
                                        <div>
                                            <select class="form-control shadow-sm" name="iter_id" id="iter_id" style="height: 48px">
                                                <option value="">All Iteration</option>
                                                <c:forEach items="${requestScope.iterList}" var="temp" >
                                                    <option value="${temp.id}">${temp.iteration_name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 30px">
                                        <div>
                                            <select class="form-control shadow-sm" name="class_code" id="class_code" style="height: 48px">
                                                <option value="">All Class</option>
                                                <c:forEach items="${requestScope.classList}" var="temp" >
                                                    <option value="${temp.id}">${temp.class_code}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 30px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="status" id="status" style="height: 48px" > 
                                                <option value="">All status</option>
                                                <option value="0">Closed</option>
                                                <option value="1">Open</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-3" style="margin-right: 10px">
                                        <div class="form-group" style="height: 20px">
                                            <div class="input-group">
                                                <input type="text" class="form-control shadow-sm" name="milestone_name" 
                                                       value="${milestone_name}" style="height: 48px;" placeholder="Search Milestone Name...">
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin-right: 10px">
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="submit" id="btn-search-name">
                                            <i class="ti-search" style="height: 20px"></i>
                                        </button>
                                    </div>              
                                    <div style="margin-right: 10px">
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="clean()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                            <i class="mdi mdi-filter-remove-outline"></i>
                                        </button>
                                    </div>
                                    <div>
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="add()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                            <i class="ti-plus"></i>
                                        </button>
                                    </div> 

                                    <input type="hidden" id="page" name="page" value="1">
                                    <input type="hidden" id="sort_name" name="sort_name" value="asc">   
                                    <input type="hidden" id="sort_class" name="sort_class" value="asc">   
                                    <input type="hidden" id="sort_iter" name="sort_iter" value="asc">   
                                    <input type="hidden" id="string_sort" name="string_sort" 
                                           value="m.milestone_name asc, c.class_code asc, i.iteration_name asc"> 
                                </div>
                            </form>
                            <div class="col-md-12 grid-margin stretch-card" id="myList">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-9">
                                                <h4 class="card-title">Milestones List</h4>
                                            </div>
                                            <div class="col-md-3">
                                                <p class="card-description">
                                                    There were ${count} matches for filter.
                                                </p>
                                            </div>
                                        </div>
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th style="text-align: center;">Milestone Name
                                                            <c:if test="${sort_name == 'asc'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-ascending" style="cursor: pointer"></i>
                                                            </c:if>
                                                            <c:if test="${sort_name == 'decs'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-descending" style="cursor: pointer"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Iteration Name
                                                            <c:if test="${sort_iter == 'asc'}">
                                                                <i onclick="sortIter()" class="mdi mdi-sort-ascending" style="cursor: pointer"></i>
                                                            </c:if>
                                                            <c:if test="${sort_iter == 'decs'}">
                                                                <i onclick="sortIter()" class="mdi mdi-sort-descending" style="cursor: pointer"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Class Code
                                                            <c:if test="${sort_class == 'asc'}">
                                                                <i onclick="sortClass()" class="mdi mdi-sort-ascending" style="cursor: pointer"></i>
                                                            </c:if>
                                                            <c:if test="${sort_class == 'decs'}">
                                                                <i onclick="sortClass()" class="mdi mdi-sort-descending" style="cursor: pointer"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">From Date</th>
                                                        <th style="text-align: center;">To Date</th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Detail</th>
                                                    </tr>
                                                </thead>
                                                <c:if test="${list.size() > 0}">
                                                    <tbody>
                                                        <c:forEach items="${requestScope.list}" var="temp">
                                                            <tr>
                                                                <td style="text-align: center;">${temp.milestone_name}</td>
                                                                <td style="text-align: center;">${temp.iter.iteration_name}</td>
                                                                <td style="text-align: center;">${temp.class_id.class_code}</td>
                                                                <td style="text-align: center;">${temp.from_date}</td>
                                                                <td style="text-align: center;">${temp.to_date}</td>
                                                                <td style="text-align: center;">
                                                                    <c:if test="${temp.status == 1}">
                                                                        <label class="switch">
                                                                            <input type="checkbox" checked onchange="inactive(${temp.id})">
                                                                            <span class="slider round"></span>
                                                                        </label>
                                                                        <!--<button class="btn btn-inverse-danger btn-fw" onclick="inactive(${temp.id})" style="text-align: center; border-radius: 20px; width: 100px">In Active</button>-->
                                                                    </c:if>
                                                                    <c:if test="${temp.status == 0}">
                                                                        <label class="switch">
                                                                            <input type="checkbox" onchange="active(${temp.id})">
                                                                            <span class="slider round"></span>
                                                                        </label>
                                                                        <!--<button class="btn btn-inverse-success btn-fw" onclick="active(${temp.id})" style="text-align: center; border-radius: 20px; width: 100px">Active</button>-->
                                                                    </c:if>
                                                                </td>
                                                                <td style="text-align: center;">
                                                                    <a type="button" class="btn btn-icon" style="margin-top: 15px" href="milestones?go=detail&id=${temp.id}" id="btn-add-setting">
                                                                        <i class="mdi mdi-format-list-bulleted"></i>
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </c:if>

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
                                        <c:if test="${list.size() == 0}">
                                            <span style="color: red">No record to display!</span>
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

        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        
        <c:if test="${mess ne null}">
            <script>
                window.onload = function ()
                {
                    alert("success", "Success!", "${mess}!");
                };
            </script>
        </c:if>
        
        <script>
                                                            $(document).ready(function () {
                                                                $('#class_code').val('${class_code}')
                                                                $('#status').val('${status}')
                                                                $('#iter_id').val('${iter_id}')
                                                                $('#sort_name').val('${sort_name}')
                                                                $('#sort_class').val('${sort_class}')
                                                                $('#sort_iter').val('${sort_iter}')
                                                                $('#string_sort').val('${string_sql}')
                                                            });

                                                            function sortName() {
                                                                //bindding
                                                                if ($('#sort_name').val() == 'asc')
                                                                    $('#sort_name').val('decs')
                                                                else
                                                                    $('#sort_name').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_name').val() == 'asc')
                                                                    sql += 'm.milestone_name asc,'
                                                                else
                                                                    sql += 'm.milestone_name desc,'
                                                                if ($('#sort_class').val() == 'asc')
                                                                    sql += 'c.class_code asc,'
                                                                else
                                                                    sql += 'c.class_code desc,'
                                                                if ($('#sort_iter').val() == 'asc')
                                                                    sql += 'i.iteration_name asc'
                                                                else
                                                                    sql += 'i.iteration_name desc'
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-filter').submit()
                                                            }
                                                            function sortClass() {
                                                                //bindding
                                                                if ($('#sort_class').val() == 'asc')
                                                                    $('#sort_class').val('decs')
                                                                else
                                                                    $('#sort_class').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_class').val() == 'asc')
                                                                    sql += 'c.class_code asc,'
                                                                else
                                                                    sql += 'c.class_code desc,'
                                                                if ($('#sort_name').val() == 'asc')
                                                                    sql += 'm.milestone_name asc,'
                                                                else
                                                                    sql += 'm.milestone_name desc,'
                                                                if ($('#sort_iter').val() == 'asc')
                                                                    sql += 'i.iteration_name asc'
                                                                else
                                                                    sql += 'i.iteration_name desc'
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-filter').submit()
                                                            }
                                                            function sortIter() {
                                                                //bindding
                                                                if ($('#sort_iter').val() == 'asc')
                                                                    $('#sort_iter').val('decs')
                                                                else
                                                                    $('#sort_iter').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_iter').val() == 'asc')
                                                                    sql += 'i.iteration_name asc,'
                                                                else
                                                                    sql += 'i.iteration_name desc,'
                                                                if ($('#sort_class').val() == 'asc')
                                                                    sql += 'c.class_code asc,'
                                                                else
                                                                    sql += 'c.class_code desc,'
                                                                if ($('#sort_name').val() == 'asc')
                                                                    sql += 'm.milestone_name asc'
                                                                else
                                                                    sql += 'm.milestone_name desc'

                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-filter').submit()
                                                            }

                                                            function add() {
                                                                window.location.href = "/swp-project/teacher/milestones?go=add";
                                                            }

                                                            function clean() {
                                                                window.location.href = '/swp-project/teacher/milestones'
                                                            }
                                                            function paging(e) {
                                                                $('#page').val(e)
                                                                $('#form-filter').submit()
                                                            }

                                                            function active(id)
                                                            {
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "You want update action ACTIVE feature!",
                                                                    icon: "warning",
                                                                    buttons: true,
                                                                    dangerMode: true,
                                                                })
                                                                        .then((willDelete) => {
                                                                            if (willDelete) {
//                                                                                swal("Done!", "Activate Successfully!", "success")
//                                                                                        .then((value) => {
//                                                                                            window.location.href = "/swp-project/teacher/team?go=active&id=" + id;
//                                                                                        });
                                                                                alert("success", "", "Activate successfully!")
                                                                                setTimeout(function () {
                                                                                    window.location.href = "/swp-project/teacher/milestones?go=active&id=" + id;
                                                                                }, 2000)
                                                                            } else {
                                                                                window.location.href = "/swp-project/teacher/milestones";
                                                                            }
                                                                        });
                                                            }

                                                            function inactive(id)
                                                            {
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "You want update action INACTIVE feature!",
                                                                    icon: "warning",
                                                                    buttons: true,
                                                                    dangerMode: true,
                                                                })
                                                                        .then((willDelete) => {
                                                                            if (willDelete) {
//                                                                                swal("Done!", "Update Inactive successfull!", "success")
//                                                                                        .then((value) => {
//                                                                                            window.location.href = "/swp-project/teacher/team?go=inactive&id=" + id;
//                                                                                        });
                                                                                alert("success", "", "Inactivate successfully!")
                                                                                setTimeout(function () {
                                                                                    window.location.href = "/swp-project/teacher/milestones?go=inactive&id=" + id;
                                                                                }, 2000)
                                                                            } else {
                                                                                window.location.href = "/swp-project/teacher/milestones";
                                                                            }
                                                                        });
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
        </script>

        <script src="https://unpkg.com/flowbite@1.4.7/dist/flowbite.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    </body>
</html>
