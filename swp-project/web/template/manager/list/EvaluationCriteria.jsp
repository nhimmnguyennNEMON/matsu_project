
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
        <title>Criteria List</title>
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
                <jsp:include page="../../component/Sidebar_manager.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>

                <div class="main-panel">        
                    <div class="content-wrapper" id="content">
                        
                        <div class="row" id="list">
                            
                            <form action="evaluation_criteria" method="post" id="form-search">
                                <input type="hidden" name="go" value="view">
                                
                                <div class="col-md-12 grid-margin stretch-card" id="mySearch">

                                    <div class="col-md-2" style="margin-right: 10px">
                                        <div>
                                            <select class="form-control shadow-sm" name="subject_code" id="subject_code" style="height: 48px">
                                                <option value="" ${subject_code eq null?"selected":""} >
                                                    
                                                    All Subject Code
                                                </option>
                                                <c:forEach items="${requestScope.listS}" var="temp">
                                                    <option value="${temp.getIteration_id().getSubject_code()}" ${subject_code eq temp.getIteration_id().getSubject_code()?"selected":""} >${temp.getIteration_id().getSubject_code()}</option>
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
                                    <input type="hidden" id="sort_name" name="sort_name" value="asc">   
                                    <input type="hidden" id="sort_order" name="sort_order" value="asc">   
                                    <input type="hidden" id="sort_weight" name="sort_weight" value="asc">
                                    <input type="hidden" id="string_sort" name="string_sort" value=" c.criteria_order asc, i.iteration_name asc , c.evaluation_weight asc"> 

                                </div>
                            </form> 
                            <div class="col-md-12 grid-margin stretch-card" id="myList">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-9">
                                                <h4 class="card-title">All Evalution Criteria</h4>
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
                                                        
                                                        <th style="text-align: center;">Iteration Name
                                                        <c:if test="${sort_name == 'asc'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_name == 'desc'}">
                                                                <i onclick="sortName()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Subject Code</th>
                                                        <th style="text-align: center;">Evaluation Weight ( % )
                                                        <c:if test="${sort_weight == 'asc'}">
                                                                <i onclick="sortWeight()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_weight == 'desc'}">
                                                                <i onclick="sortWeight()" class="mdi mdi-sort-descending"></i>
                                                            </c:if>
                                                        </th>
                                                        <th style="text-align: center;">Team Evaluation </th>
                                                        <th style="text-align: center;">Criteria Order
                                                        <c:if test="${sort_order == 'asc'}">
                                                                <i onclick="sortOrder()" class="mdi mdi-sort-ascending"></i>
                                                            </c:if>
                                                            <c:if test="${sort_order == 'desc'}">
                                                                <i onclick="sortOrder()" class="mdi mdi-sort-descending"></i>
                                                            </c:if></th>
                                                        <th style="text-align: center;">Max Log</th>
                                                        <th style="text-align: center;">Status</th>
                                                        
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="temp">
                                                        <tr>
                                                            
                                                            
                                                            <c:if test="${empty temp.getIteration_id().getIteration_name()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>  
                                                            <c:if test="${not empty temp.getIteration_id().getIteration_name()}">
                                                                <td style="text-align: center;" >${temp.getIteration_id().getIteration_name()}</td>
                                                            </c:if>   
                                                            <c:if test="${empty temp.getIteration_id().getSubject_code()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>

                                                            <c:if test="${not empty temp.getIteration_id().getSubject_code()}">
                                                                <td style="text-align: center;" >${temp.getIteration_id().getSubject_code()}</td>
                                                            </c:if> 
                                                                
                                                            <c:if test="${empty temp.getEvaluation_weight()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${not empty temp.getEvaluation_weight()}">
                                                                <td style="text-align: center;" >${temp.getEvaluation_weight()}</td>
                                                            </c:if> 
                                                                
                                                            <c:if test="${empty temp.getTeam_evaluation()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${ temp.getTeam_evaluation()==true}">
                                                                <td style="text-align: center;" >True</td>
                                                            </c:if> 
                                                            <c:if test="${ temp.getTeam_evaluation()==false}">
                                                                <td style="text-align: center;" >False</td>
                                                            </c:if> 
                                                            
                                                            <c:if test="${empty temp.getCriteria_order()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${not empty temp.getCriteria_order()}">
                                                                <td style="text-align: center;" >${temp.getCriteria_order()}</td>
                                                            </c:if> 
                                                            <c:if test="${empty temp.getMax_loc()}">
                                                                <td style="text-align: center;" > - </td>
                                                            </c:if>
                                                            <c:if test="${not empty temp.getMax_loc()}">
                                                                <td style="text-align: center;" >${temp.getMax_loc()}</td>
                                                            </c:if>
                                                                
                                                            <!-- STATUS  -->
                                                            <c:if test="${ temp.isStatus()==false}">
                                                                <td style="text-align: center;">
                                                                    <label class="switch">
                                                                        <input disabled type="checkbox" onchange="active(${temp.getCriteria_id()})">
                                                                        <span class="slider round"></span>
                                                                    </label>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${ temp.isStatus()==true}">
                                                                <td style="text-align: center;">
                                                                    <label class="switch">
                                                                        <input disabled type="checkbox" checked onchange="inactive(${temp.getCriteria_id()})">
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>
        <script>
                                                           
                                                            function callBack() {
                                                                window.location.href = "/swp-project/manager/evaluation_criteria"
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
                                                                $('#sort_name').val('${sort_name}')
                                                                $('#sort_weight').val('${sort_weight}')
                                                                $('#sort_order').val('${sort_order}')
                                                                $('#string_sort').val('${string_sql}')

                                                            });
                                                            function sortName() {
                                                                //bindding
                                                                if ($('#sort_name').val() == 'asc')
                                                                {
                                                                    $('#sort_name').val('desc')
                                                                }
                                                                else{
                                                                    $('#sort_name').val('asc')
                                                                }
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_name').val() == 'asc')
                                                                {
                                                                    sql += 'i.iteration_name asc,'
                                                                }
                                                                else
                                                                {    
                                                                    sql += 'i.iteration_name desc,'
                                                                }
                                                                if ($('#sort_weight').val() == 'asc')
                                                                {
                                                                    sql += 'c.evaluation_weight asc,'
                                                                }    
                                                                else
                                                                {
                                                                    sql += 'c.evaluation_weight desc,'
                                                                }
                                                                if ($('#sort_order').val() == 'asc')
                                                                {
                                                                    sql += 'c.criteria_order asc'
                                                                }
                                                                else
                                                                {
                                                                    sql += 'c.criteria_order desc'
                                                                }
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-search').submit()
                                                            }
                                                            function sortWeight() {
                                                                //bindding
                                                                if ($('#sort_weight').val() == 'asc')
                                                                    $('#sort_weight').val('desc')
                                                                else
                                                                    $('#sort_weight').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_name').val() == 'asc')
                                                                {
                                                                    sql += 'i.iteration_name asc,'
                                                                }
                                                                else
                                                                {    
                                                                    sql += 'i.iteration_name desc,'
                                                                }
                                                                if ($('#sort_weight').val() == 'asc')
                                                                {
                                                                    sql += 'c.evaluation_weight asc,'
                                                                }    
                                                                else
                                                                {
                                                                    sql += 'c.evaluation_weight desc,'
                                                                }
                                                                if ($('#sort_order').val() == 'asc')
                                                                {
                                                                    sql += 'c.criteria_order asc'
                                                                }
                                                                else
                                                                {
                                                                    sql += 'c.criteria_order desc'
                                                                }
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-search').submit()
                                                            }
                                                            function sortOrder() {
                                                                //bindding
                                                                if ($('#sort_order').val() == 'asc')
                                                                    $('#sort_order').val('desc')
                                                                else
                                                                    $('#sort_order').val('asc')
                                                                let sql = '';

                                                                // tao chuoi sql
                                                                if ($('#sort_name').val() == 'asc')
                                                                {
                                                                    sql += 'i.iteration_name asc,'
                                                                }
                                                                else
                                                                {    
                                                                    sql += 'i.iteration_name desc,'
                                                                }
                                                                if ($('#sort_weight').val() == 'asc')
                                                                {
                                                                    sql += 'c.evaluation_weight asc,'
                                                                }    
                                                                else
                                                                {
                                                                    sql += 'c.evaluation_weight desc,'
                                                                }
                                                                if ($('#sort_order').val() == 'asc')
                                                                {
                                                                    sql += 'c.criteria_order asc'
                                                                }
                                                                else
                                                                {
                                                                    sql += 'c.criteria_order desc'
                                                                }
                                                                // gan gia tri ve form
                                                                $('#string_sort').val(sql)

                                                                $('#form-search').submit()
                                                            }
                                                            
        </script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
                <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>




    </body>
</html>
