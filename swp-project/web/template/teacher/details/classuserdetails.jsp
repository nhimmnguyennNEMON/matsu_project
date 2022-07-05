
<%@page import="dal.UserDao"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Class User Detail</title>
        <link rel="shortcut icon" href="../assets/assets_template/images/icon.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.4.7/dist/flowbite.min.css" />
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
                    <div class="content-wrapper" id="list">
                        <div class="row">

                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Class User Detail</h4>
                                        <c:if test="${item != null}">
                                            <form id="form-detail" class="forms-sample" method="POST" action="/swp-project/teacher/class-user?go=details">
                                                <input  type="hidden" name="class_id" value="${item.class_id}"> 
                                                <input  type="hidden" name="team_id" value="${item.team_id}"> 
                                                <input  type="hidden" name="user_id" value="${item.user_id}">
                                                 <div class="row">
                                                    <div class="col-md-12">
                                                       <p class="card-description " style="color: red">
                                                    Note: -If a student dropout from a class subject, the teacher records that date in fiels drop out date and choose inactive
                                                   </p>
                                                    </div>
                                                </div>
                                                <div class="d-flex flex-row">
                                                    <div class="form-group w-50 mr-2">
                                                        <label for="name">Full Name</label>
                                                        <input type="text" class="form-control" id="name"
                                                               value="${item.full_name}" readonly>
                                                    </div>
                                                    <div class="form-group w-50">
                                                        <label for="name">Roll Number</label>
                                                        <input type="text" class="form-control"
                                                               value="${item.roll_number}" readonly>
                                                    </div>
                                                </div>
                                                <div class="d-flex flex-row">  
                                                    <div class="form-group w-50 mr-2">
                                                        <label for="name">Email</label>
                                                        <input type="text" class="form-control"
                                                               value="${item.email}" readonly>
                                                    </div>
                                                    <div class="form-group w-50">
                                                        <label for="topic">Team Lead<span style="color: red">(*)</span></label>
                                                        <input type="number" class="form-control"  
                                                               name="team_lead" min="0" max="1"
                                                               value="${item.team_lead}">
                                                    </div>
                                                </div>
                                                <div class="d-flex flex-row">  
                                                    <div class="form-group w-50 mr-2">
                                                        <label for="name">On Going Evalution</label>
                                                        <input type="number" class="form-control" name="ongoing_eval"
                                                               min="0" max="100"
                                                               value="${item.ongoing_eval}">
                                                    </div>
                                                    <div class="form-group w-50">
                                                        <label for="topic">Final Topic Evalution</label>
                                                        <input type="number" class="form-control" id="topic" name="final_topic_eval"
                                                               min="0" max="100"
                                                               value="${item.final_topic_eval}">
                                                    </div>
                                                </div>
                                                <div class="d-flex flex-row">  
                                                    <div class="form-group w-50 mr-2">
                                                        <label for="topic">Final Present Evalution</label>
                                                        <input type="number" class="form-control" id="topic" name="final_pres_eval"
                                                               min="0" max="100"
                                                               value="${item.final_pres__eval}">
                                                    </div>
                                                    <div class="form-group w-50">
                                                        <label for="topic">Drop Out Date</label>
                                                        <input type="date" class="form-control" id="datefield" name="dropout_date"
                                                               value="${item.dropout_date}">
                                                    </div>
                                                </div>



                                                <div class="form-group">
                                                    <label for="desc">User Note</label>
                                                    <textarea class="form-control" name="user_notes" id="desc" maxlength="100" style="height: 48px">${item.user_notes}</textarea>
                                                </div>
                                                <div class="form-group row">
                                                    <label class="col-md-1" for="optionsRadios1">Status</label>
                                                    <div class="col-md-1">
                                                        <label class="form-check-label">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios1" value="1" 
                                                                   ${item.status eq 1?"checked":""}>
                                                            Active
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label class="form-check-label" for="optionsRadios2">
                                                            <input type="radio" class="form-check-input" 
                                                                   name="status" id="optionsRadios2" value="0" 
                                                                   ${item.status eq 0?"checked":""}>
                                                            Inactive
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <button type="button" class="btn btn-light me-2" onclick="reload()" >Clean</button>
                                                        <button type="submit" class="btn btn-primary me-2" >Update</button>
                                                    </div>
                                                </div>
                                                 
                                            </form>

                                        </c:if>

                                        <c:if test="${item == null}">
                                            No record to display
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script>
                                                            console.log('${item}');
                                                            var today = new Date();
                                                            var dd = today.getDate();
                                                            var mm = today.getMonth() + 1; //January is 0!
                                                            var yyyy = today.getFullYear();
                                                            if (dd < 10) {
                                                                dd = '0' + dd;
                                                            }
                                                            if (mm < 10) {
                                                                mm = '0' + mm;
                                                            }
                                                            today = yyyy + '-' + mm + '-' + dd;
                                                            document.getElementById("datefield").setAttribute("max", today);
                                                            function reload() {
                                                                window.location.reload()
                                                            }

                                                            if (${statusUpdate} == 1) {
                                                                alert("success","","Update success and 0 error");
                                                                alert("warning", "", `Leave the page after 3 seconds`)
                                                                let myTimeout = setTimeout(_redirect, 3000);

                                                            }
                                                            if (${statusUpdate} == -1) {
                                                                alert("error","","Update not success");
                                                                

                                                            }
                                                            function _redirect() {
                                                                window.location.href = '/swp-project/teacher/class-user?go=view';
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
                                                                    autotimeout: 2500,
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
