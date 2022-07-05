
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
        <title>Issue List</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
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
                            <form action="/swp-project/student/issue?go=list" method="post" id="form-filter">
                                <div class="col-md-12 grid-margin stretch-card" id="mySearch">

                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="class_id" id="class_id" style="height: 48px" onchange="loadTeam()">
                                                <option value="">All Class</option>
                                                <c:forEach items="${requestScope.listClassCode}" var="item">
                                                    <option value="${item.id}">${item.class_code}-${item.subject_id.subject_code}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>
                                     <div class="col-md-2" style="margin-right: 10px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="team_id" id="team_id" style="height: 48px" > 
                                                <option value="">All Team</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="status" id="status" style="height: 48px" > 
                                                <option value="">All status</option>
                                            </select>
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
                                       
                                    </div>
                                    <div class="col-md-2 d-flex flex-row-reverse px-2">
                                        <div style="margin-right: 10px" >
                                            <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="export_file()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                                <i class="ti-export"></i>
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
                                                        <th style="text-align: center;" >Issue Titles
                                                           
                                                        </th>
                      
                                                        <th style="text-align: center;">Assigness</th>
                                                        <th style="text-align: center;">Desctription</th>
                                                        <th style="text-align: center;">Due date</th>
                                                        <th style="text-align: center;">Milestion</th>
                                                        <th style="text-align: center;">Function</th>
                                                        <th style="text-align: center;">Status</th>
                                                        <th style="text-align: center;">Label</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                            <td style="text-align: center;" >${temp.issue_title}</td>
                                                            <td style="text-align: center;" >${temp.assignee_name}</td>
                                                            <td style="text-align: center;" >${temp.desciption}</td>
                                                            <td style="text-align: center;" >${temp.due_date}</td>
                                                            <td style="text-align: center;" >${temp.milestone_name}</td>
                                                            <td style="text-align: center;" >${temp.function_name}</td>
                                                            <td style="text-align: center;" >${temp.status}</td>
                                                            <td style="text-align: center;" >${temp.labels}</td>               
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
                                        
                                                            function clean() {
                                                                window.location.href = '/swp-project/student/issue?go=list'
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
                                                                    function loadTeam() {
                                                        let value = $('#class_id').val();
                                                        $.ajax({
                                                            url: '/swp-project/teacher/class-user?go=load-team',
                                                            type: 'POST',
                                                            data: {
                                                                class_id: value
                                                            }
                                                        }).done(function (res) {
                                                            console.log(res);
                                                            $('#team_id').empty();
                                                            $('#team_id').append(`<option value="">All Team</option>`);
                                                            for (let i = 0; i < res.length; i++) {
                                                                $('#team_id').append(`<option value=\"\${res[i].id}\">\${res[i].team_name}</option>`);
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
