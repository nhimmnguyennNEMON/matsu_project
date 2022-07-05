
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Class User List</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
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
                            <form action="/swp-project/teacher/class-user?go=view" method="post" id="form-filter">
                                <div class="col-md-12 grid-margin stretch-card" id="mySearch">

                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="class_id" id="class_id" style="height: 48px">
                                                <option value="">All Class</option>
                                                <c:forEach items="${requestScope.listClassCode}" var="item">
                                                    <option value="${item.id}">${item.class_code}-${item.subject_id.subject_code}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="status" id="status" style="height: 48px" > 
                                                <option value="">All status</option>
                                                <option value="0">Inactive</option>
                                                <option value="1">Active</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-3" style="margin-right: 10px">
                                        <div class="form-group" style="height: 20px">
                                            <div class="input-group">
                                                <input type="text" class="form-control shadow-sm" name="key" value="${key}" style="height: 48px;" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3 d-flex flex-row">
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
                                        <div style="margin-right: 10px">
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="add()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="ti-plus"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-2 d-flex flex-row-reverse px-2">
                                        <div style="margin-right: 10px" >
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="export_file()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="ti-export"></i>
                                            </button>
                                        </div>
                                        <div  style="margin-right: 10px" >
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="import_file()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="ti-import"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <input type="hidden" id="page" name="page" value="1">    
                                    <input type="hidden" id="sort_name" name="sort_name" value="asc">   
                                    <input type="hidden" id="sort_team" name="sort_team" value="asc">   
                                    <input type="hidden" id="sort_loc" name="sort_loc" value="asc">   
                                    <input type="hidden" id="string_sort" name="string_sort" value="d.team_name asc,b.full_name asc"> 
                                </div>
                            </form>
                            <div class="col-md-12 grid-margin stretch-card" id="myList">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-9">
                                                <h4 class="card-title">All Class User</h4>
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
                                                        <th style="text-align: center;" >Full Name
                                                            <c:if test="${sort_name == 'asc'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_name == 'decs'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Team
                                                            <c:if test="${sort_team == 'asc'}">
                                                                <i onclick="sortTeam()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_team == 'decs'}">
                                                                <i onclick="sortTeam()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;"> Leader</th>
                                                        <th style="text-align: center;">On Going Eval</th>
                                                        <th style="text-align: center;">Final Pres Eval  </th>
                                                        <th style="text-align: center;">Final Topic Eval
                                                            <c:if test="${sort_loc == 'asc'}">
                                                                <i onclick="sortLoc()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_loc == 'decs'}">
                                                                <i onclick="sortLoc()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Detail</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                            <td style="text-align: center;" >${temp.getFull_name()}</td>
                                                            <td style="text-align: center;" >${temp.getTeam_name()}</td>
                                                            <c:if test="${temp.getTeam_lead() == 1}">
                                                                <td style="text-align: center;" >True</td>
                                                            </c:if>
                                                            <c:if test="${temp.getTeam_lead() != 1}">
                                                                <td style="text-align: center;" >False</td>
                                                            </c:if>
                                                            <td style="text-align: center;" >${temp.ongoing_eval == null?"-":temp.ongoing_eval}</td>
                                                            <td style="text-align: center;" >${temp.final_pres__eval == null?"-":temp.final_pres__eval}</td>

                                                            <td style="text-align: center;" >${temp.final_topic_eval ==null?"-":temp.final_topic_eval}</td>

                                                            <!-- STATUS  -->
                                                            <td style="text-align: center;">
                                                                <c:if test="${temp.status == 1}">
                                                                    <label class="switch ">
                                                                        <input  type="checkbox" id="check-box"
                                                                                checked onchange="turnStatus(${temp.user_id}, ${temp.team_id}, ${temp.class_id}, '${temp.full_name}',${temp.status})">
                                                                        <span class="slider round"></span>
                                                                    </label>

                                                                </c:if>
                                                                <c:if test="${temp.status == 0}">
                                                                    <label class="switch ">
                                                                        <input  type="checkbox" id="check-box"
                                                                                onchange="turnStatus(${temp.user_id}, ${temp.team_id}, ${temp.class_id}, '${temp.full_name}',${temp.status})">
                                                                        <span class="slider round"></span>
                                                                    </label>     
                                                                </c:if>
                                                            </td>
                                                            <!--Detail-->
                                                            <td style="text-align: center;">
                                                                <a type="button" class="btn btn-icon"  href="/swp-project/teacher/class-user?go=details&class_id=${temp.class_id}&team_id=${temp.team_id}&user_id=${temp.user_id}" id="btn-add-setting">
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
                    </div>
                    <jsp:include page="../../component/Footer.jsp"/>
                </div>
            </c:if>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script>
                                                            
                                                            $(document).ready(function () {
                                                                $('#class_id').val('${class_id}')
                                                                $('#status').val('${status}')
                                                                $('#sort_name').val('${sort_name}')
                                                                $('#sort_team').val('${sort_team}')
                                                                $('#sort_loc').val('${sort_loc}')
                                                                $('#string_sort').val('${string_sql}')

                                                            });
                                                            function add(){
                                                                window.location.href = '/swp-project/teacher/class-user?go=add'
                                                            }    

                                                            function clean() {
                                                                window.location.href = '/swp-project/teacher/class-user?go=view'
                                                            }
                                                            function paging(e) {
                                                                $('#page').val(e)
                                                                $('#form-filter').submit()
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
                                                                    sql += 'b.full_name asc,'
                                                                else
                                                                    sql += 'b.full_name desc,'
                                                                if ($('#sort_team').val() == 'asc')
                                                                    sql += 'd.team_name asc,'
                                                                else
                                                                    sql += 'd.team_name desc,'
                                                                if ($('#sort_loc').val() == 'asc')
                                                                    sql += 'a.final_topic_eval asc'
                                                                else
                                                                    sql += 'a.final_topic_eval desc'
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-filter').submit()
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
                                                                    sql += 'd.team_name asc,'
                                                                else
                                                                    sql += 'd.team_name desc,'
                                                                if ($('#sort_name').val() == 'asc')
                                                                    sql += 'b.full_name asc,'
                                                                else
                                                                    sql += 'b.full_name desc,'
                                                                if ($('#sort_loc').val() == 'asc')
                                                                    sql += 'a.final_topic_eval asc'
                                                                else
                                                                    sql += 'a.final_topic_eval desc'
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-filter').submit()
                                                            }
                                                            function sortLoc() {
                                                                //bindding
                                                                if ($('#sort_loc').val() == 'asc')
                                                                    $('#sort_loc').val('decs')
                                                                else
                                                                    $('#sort_loc').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_loc').val() == 'asc')
                                                                    sql += 'a.final_topic_eval asc,'
                                                                else
                                                                    sql += 'a.final_topic_eval desc,'
                                                                if ($('#sort_team').val() == 'asc')
                                                                    sql += 'd.team_name asc,'
                                                                else
                                                                    sql += 'd.team_name desc,'
                                                                if ($('#sort_name').val() == 'asc')
                                                                    sql += 'b.full_name asc'
                                                                else
                                                                    sql += 'b.full_name desc'

                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-filter').submit()
                                                            }
                                                           

                                                            function uncheck() {
                                                                console.log('un-check-box');
                                                                 $("#check-box").checked = false;
                                                            }
                                                            function export_file() {
                                                                let class_code = $('#class_id option:selected').html()
                                                                let class_id = $('#class_id').val()
                                                                if (class_id == "")
                                                                    return alert("warning", "", "Please choose class before export")
                                                                else {
                                                                    alert("success", "", "Your request is being processed by the system")
                                                                    window.location.href = `/swp-project/teacher/class-user?go=write-file&class_code=\${class_code}&class_id=\${class_id}`
                                                                }

                                                            }
                                                            function import_file() {

                                                                window.location.href = `/swp-project/teacher/class-user?go=import-file-view`


                                                            }
                                                            function turnStatus(user_id, team_id, class_id, full_name, status) {
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "Change status for student " + full_name,
                                                                    buttons: true
                                                                })
                                                                        .then((willDelete) => {
                                                                            if (willDelete) {
                                                                                let _status;
                                                                                if (status == 1)
                                                                                    _status = 0;
                                                                                else
                                                                                    _status = 1;
                                                                                $.ajax({
                                                                                    url: '/swp-project/teacher/class-user?go=update-status',
                                                                                    type: 'GET',
                                                                                    data: {
                                                                                        class_id: class_id,
                                                                                        team_id: team_id,
                                                                                        user_id: user_id,
                                                                                        status: _status
                                                                                    }
                                                                                }).done(function (res) {
                                                                                    if (res.n == 1)
                                                                                        alert("success", "", `Change status success for \${full_name}`)
                                                                                    else
                                                                                        alert("erorr", "", "Change status not success")
                                                                                });
                                                                            }else {
                                                                               window.location.href = `/swp-project/teacher/class-user?go=view`
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
    </body>
</html>
