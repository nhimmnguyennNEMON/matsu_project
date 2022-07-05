<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
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
        <title>Class User Add</title>
        <link href="../assets/style/validator.css" rel="stylesheet" type="text/css"/>
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
                    <div class="content-wrapper" id="list">
                        <div class="row">

                            <div class="col-md-12 grid-margin stretch-card" id="card-table" >
                                <div class="card">
                                    <div class="card-body">
                                        <h4 class="card-title">Issue Add</h4>
                                        <form id="form-detail" class="forms-sample" method="POST" action="">


                                            <div class="d-flex flex-row">  
                                                <div class="form-group w-50 mr-2">
                                                    <label for="class_id">Class</label>
                                                    <select class="form-control shadow-sm" name="class_id" id="class_id" onchange="loadTeam()">
                                                        <option value="">-----Choose-----</option>
                                                        <c:forEach items="${requestScope.listClassCode}" var="item">
                                                            <option value="${item.id}">${item.class_code}-${item.subject_id.subject_code}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group w-50">
                                                    <label for="team_id">Team</label>
                                                    <select class="form-control shadow-sm" name="team_id" id="team_id" onchange="loadStudent()">
                                                </select>
                                                </div>
                                            </div>
                                            <div class="d-flex flex-row">
                                                <div class="form-group w-50 mr-2">
                                                    <label for="name">Milestone</label>
                                                      <select class="form-control shadow-sm" name="team_id" id="milestone_name">
                                                </select>
                                                </div>
                                                <div class="form-group w-50 ">
                                                    <label for="assigness_id">Assignee</label>
                                                    <select class="form-control shadow-sm" name="assigness_id" id="assigness_id" onchange="">
                                                        </select>
                                                </div>

                                            </div>
                                            <div class="d-flex flex-row">  
                                                <div class="form-group w-50 mr-2">
                                                    <label for="name">Issue Titles</label>
                                                    <input type="text" class="form-control"
                                                           value="${item.roll_number}" >
                                                </div>
                                                <div class="form-group w-50">
                                                    <label for="topic">Due date<span style="color: red">(*)</span></label>
                                                    <input type="number" class="form-control"  
                                                           name="team_lead" min="0" max="1"
                                                           value="${item.team_lead}">
                                                </div>
                                            </div>

                                            <div class="d-flex flex-row">  
                                                <div class="form-group w-50 mr-2">
                                                    <label for="topic">Label</label>
                                                    <input type="number" class="form-control" id="topic" name="final_pres_eval"
                                                           min="0" max="100"
                                                           value="${item.final_pres__eval}">
                                                </div>
                                                <div class="form-group w-50">
                                                    <label for="name">Git lab url</label>
                                                    <input type="text" class="form-control"
                                                           value="${item.email}" >
                                                </div>

                                            </div>



                                            <div class="form-group">
                                                <label for="desc">Description</label>
                                                <textarea class="form-control" name="user_notes" id="desc" maxlength="100" style="height: 48px">${item.user_notes}</textarea>
                                            </div>

                                            <div class="row">
                                                <div class="col-md-4">
                                                    <button type="button" class="btn btn-light me-2" onclick="reload()" >Clean</button>
                                                    <button type="submit" class="btn btn-primary me-2" >Update</button>
                                                </div>
                                            </div>

                                        </form>




                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <jsp:include page="../../component/Footer.jsp"/>
                </div>

            </c:if>
        </div>

        <!--validate input-->
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>




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
                                                                autotimeout: 4000,
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
