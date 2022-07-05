
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Class List</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
        <!-- plugins:css -->
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
                            <form action="class" method="post" id="form-search">
                                <input type="hidden" name="go" value="view">
                                <div class="col-md-12 grid-margin stretch-card" id="mySearch">

                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="class_code" id="class_code" style="height: 48px">
                                                <option value=""  ${class_code eq null?"selected":""}>
                                                    All Class Code
                                                </option>
                                                <c:forEach items="${requestScope.filterC}" var="temp">
                                                    <option value="${temp.getClass_code()}"  ${temp.getClass_code() eq class_code?"selected":""}>${temp.getClass_code()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="subject_code" id="subject_code" style="height: 48px">
                                                <option value="" ${subject_code eq null?"selected":""} >

                                                    All Subject Code
                                                </option>
                                                <c:forEach items="${requestScope.filterS}" var="temp">
                                                    <option value="${temp.getSubject_id().getSubject_code()}" ${subject_code eq temp.getSubject_id().getSubject_code()?"selected":""} >${temp.getSubject_id().getSubject_code()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2" style="margin-right: 10px;">
                                        <div>
                                            <select class="form-control shadow-sm" name="status" id="status" style="height: 48px" > 
                                                <option value="" ${status eq null?"selected":""}>
                                                    All Status</option>
                                                <option value="0"${status eq 0?"selected":""}>Inactive</option>
                                                <option value="1"${status eq 1?"selected":""}>Active</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div style="margin-right: 10px">
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="submit" id="btn-search-name">
                                            <i class="ti-search" style="height: 20px"></i>
                                        </button>
                                    </div>              
                                    <div style="margin-right: 10px">
                                        <button class="btn btn-outline-secondary text-dark shadow-sm" type="button" onclick="callBack()" data-modal-toggle="subject-add" id="btn-add-setting" >
                                            <i class="mdi mdi-filter-remove-outline"></i>
                                        </button>
                                    </div>

                                    <input type="hidden" id="page" name="page" value="1">     
                                    <input type="hidden" id="sort_code" name="sort_code" value="asc">   
                                    <input type="hidden" id="sort_year" name="sort_year" value="asc">   
                                    <input type="hidden" id="sort_term" name="sort_term" value="asc">
                                    <input type="hidden" id="string_sort" name="string_sort" value=" c.class_code asc, c.class_year asc , c.class_term asc"> 

                                </div>
                            </form>
                            <div class="col-md-12 grid-margin stretch-card" id="myList">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-9">
                                                <h4 class="card-title">All Class</h4>
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

                                                        <th style="text-align: center;">Class Code
                                                            <c:if test="${sort_code == 'asc'}">
                                                                <i onclick="sortCode()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_code == 'desc'}">
                                                                <i onclick="sortCode()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Teacher</th>
                                                        <th style="text-align: center;">Subject</th>
                                                        <th style="text-align: center;">Class Year
                                                            <c:if test="${sort_year == 'asc'}">
                                                                <i onclick="sortYear()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_year == 'desc'}">
                                                                <i onclick="sortYear()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Class Term
                                                            <c:if test="${sort_term == 'asc'}">
                                                                <i onclick="sortTerm()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_term == 'desc'}">
                                                                <i onclick="sortTerm()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Block 5</th>
                                                        <th style="text-align: center;">Status</th>

                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                           
                                                            <c:if test="${empty temp.getClass_code()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>  
                                                            <c:if test="${not empty temp.getClass_code()}">
                                                                <td style="text-align: center;" >${temp.getClass_code()}</td>
                                                            </c:if>   

                                                            <c:if test="${empty temp.getTrainer_id().getFull_name()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>

                                                            <c:if test="${not empty temp.getTrainer_id().getFull_name()}">
                                                                <td style="text-align: center;" >${temp.getTrainer_id().getFull_name()}</td>
                                                            </c:if> 

                                                            <c:if test="${empty temp.getSubject_id().getSubject_code()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${not empty temp.getSubject_id().getSubject_code()}">
                                                                <td style="text-align: center;" >${temp.getSubject_id().getSubject_code()}</td>
                                                            </c:if> 

                                                            <c:if test="${empty temp.getClass_year()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>

                                                            <c:if test="${not empty temp.getClass_year()}">
                                                                <td style="text-align: center;" >${temp.getClass_year()}</td>
                                                            </c:if> 
                                                            <c:if test="${empty temp.getClass_term()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${not empty temp.getClass_term() && temp.getClass_term() == 0}">
                                                                <td style="text-align: center;" >Spring</td>
                                                            </c:if> 
                                                            <c:if test="${not empty temp.getClass_term() && temp.getClass_term() == 1}">
                                                                <td style="text-align: center;" >Summer</td>
                                                            </c:if> 
                                                            <c:if test="${not empty temp.getClass_term() && temp.getClass_term() == 2}">
                                                                <td style="text-align: center;" >Fall</td>
                                                            </c:if> 
                                                            <c:if test="${empty temp.isBlock5_class()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${ temp.isBlock5_class()==true}">
                                                                <td style="text-align: center;" >True</td>
                                                            </c:if> 
                                                            <c:if test="${ temp.isBlock5_class()==false}">
                                                                <td style="text-align: center;" >False</td>
                                                            </c:if> 
                                                            <!-- STATUS  -->
                                                            <c:if test="${ temp.getStatus()==0}">
                                                                <td style="text-align: center;">
                                                                    <label class="switch">
                                                                        <input type="checkbox" onchange="active(${temp.getId()})">
                                                                        <span class="slider round"></span>
                                                                    </label>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${ temp.getStatus()==1}">
                                                                <td style="text-align: center;">
                                                                    <label class="switch">
                                                                        <input type="checkbox" checked onchange="inactive(${temp.getId()})">
                                                                        <span class="slider round"></span>
                                                                    </label>
                                                                </td>
                                                            </c:if>



                                                            <!--Detail-->


                                                        </tr>  
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row" style="margin-top: 20px">
                                            <div class="col-md-12 ml-4">
                                                <div class="btn-group" role="group" aria-label="Basic example" >
                                                    <c:if test="${page>1}" >
                                                        <button type="button" class="btn btn-outline-secondary text-dark" onclick="paging(1)">First</button>
                                                    </c:if>
                                                    <c:forEach begin="1" end="${pageEnd}" var="i">
                                                        <button type="button" class="${page == i?"btn btn-primary":"btn btn-outline-secondary text-dark"}" onclick="paging(${i})">${i}</button>
                                                    </c:forEach>
                                                    <c:if test="${page<pageEnd}">
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script>
                                                   
                                                            

                                                            function callBack() {
                                                                window.location.href = "/swp-project/teacher_manager/class"
                                                            }

                                                            function submitForm() {
                                                                document.getElementById("form-search").submit();
                                                            }
                                                            function paging(e) {
                                                                $('#page').val(e)
                                                                $('#form-search').submit()
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
                                                            $(document).ready(function () {
                                                                $('#sort_code').val('${sort_code}')
                                                                $('#sort_year').val('${sort_year}')
                                                                $('#sort_term').val('${sort_term}')
                                                                $('#string_sort').val('${string_sql}')

                                                            });
                                                            function sortCode() {
                                                                //bindding
                                                                if ($('#sort_code').val() == 'asc')
                                                                {
                                                                    $('#sort_code').val('desc')
                                                                } else {
                                                                    $('#sort_code').val('asc')
                                                                }
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_code').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_code asc,'
                                                                } else
                                                                {
                                                                    sql += 'c.class_code desc,'
                                                                }
                                                                if ($('#sort_term').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_term asc,'
                                                                } else
                                                                {
                                                                    sql += 'c.class_term desc,'
                                                                }
                                                                if ($('#sort_year').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_year asc'
                                                                } else
                                                                {
                                                                    sql += 'c.class_year desc'
                                                                }
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-search').submit()
                                                            }
                                                            function sortYear() {
                                                                //bindding
                                                                if ($('#sort_year').val() == 'asc')
                                                                    $('#sort_year').val('desc')
                                                                else
                                                                    $('#sort_year').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_code').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_code asc,'
                                                                } else
                                                                {
                                                                    sql += 'c.class_code desc,'
                                                                }
                                                                if ($('#sort_term').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_term asc,'
                                                                } else
                                                                {
                                                                    sql += 'c.class_term desc,'
                                                                }
                                                                if ($('#sort_year').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_year asc'
                                                                } else
                                                                {
                                                                    sql += 'c.class_year desc'
                                                                }
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-search').submit()
                                                            }
                                                            function sortTerm() {
                                                                //bindding
                                                                if ($('#sort_term').val() == 'asc')
                                                                    $('#sort_term').val('desc')
                                                                else
                                                                    $('#sort_term').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_code').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_code asc,'
                                                                } else
                                                                {
                                                                    sql += 'c.class_code desc,'
                                                                }
                                                                if ($('#sort_term').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_term asc,'
                                                                } else
                                                                {
                                                                    sql += 'c.class_term desc,'
                                                                }
                                                                if ($('#sort_year').val() == 'asc')
                                                                {
                                                                    sql += 'c.class_year asc'
                                                                } else
                                                                {
                                                                    sql += 'c.class_year desc'
                                                                }
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-search').submit()
                                                            }
                                                            function active(id)
                                                            {
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "You want update action ACTIVE Class (ID " + id + ")!",
                                                                    icon: "warning",
                                                                    buttons: true,
                                                                    dangerMode: true,
                                                                })
                                                                        .then((willDelete) => {
                                                                            if (willDelete) {
                                                                                alert("success", "Success!", "Update Inactivate Successfully!")
                                                                                setTimeout(function () {
                                                                                    window.location.href = "/swp-project/teacher_manager/class?go=active&id=" + id;
                                                                                }, 3000)
                                                                            } else {
                                                                                window.location.href = "/swp-project/teacher_manager/class";
                                                                            }
                                                                        });
                                                            }

                                                            function inactive(id)
                                                            {
                                                                swal({
                                                                    title: "Are you sure?",
                                                                    text: "You want update action INACTIVE Class (ID " + id + ")!",
                                                                    icon: "warning",
                                                                    buttons: true,
                                                                    dangerMode: true,
                                                                })
                                                                        .then((willDelete) => {
                                                                            if (willDelete) {
                                                                                alert("success", "Success!", "Update Activate Successfully!")
                                                                                setTimeout(function () {
                                                                                    window.location.href = "/swp-project/teacher_manager/class?go=inactive&id=" + id;
                                                                                }, 3000)
                                                                            } else {
                                                                                window.location.href = "/swp-projectteacher_manager/class";
                                                                            }
                                                                        });
                                                            }
        </script>
    </body>
</html>

