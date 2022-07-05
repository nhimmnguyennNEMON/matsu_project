

<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
<%@page import="dal.UserDao"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Class User Import</title>
        <link rel="shortcut icon" href="https://i.imgur.com/CilLEiG.png" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.css"/>
        <link href="../assets/style/validator.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-scroller">
            <c:if test="${user != null}">
                <jsp:include page="../../component/Sidebar_teacher.jsp">
                    <jsp:param name="email" value="${user.getEmail()}"/>
                    <jsp:param name="name" value="${user.getFull_name()}"/>
                    <jsp:param name="avatar" value="${user.getAvatar_link()}"/>
                </jsp:include>
            </c:if>       
            <div class="main-panel">        
                <div class="content-wrapper" id="list">
                    <div class="row">
                        <c:if test="${table == -1}">
                            <div class="col-12 grid-margin stretch-card">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex flex-row">
                                            <h4 class="card-title">Import class user</h4>
                                            <a style="font-size: 14px ;margin-left: 5px" href="/swp-project/teacher/class-user?go=down-load-teamplate">Download template </a>
                                        </div>
                                        <form class="forms-sample" id="form-add" enctype="multipart/form-data" method="post" action="/swp-project/teacher/class-user?go=import-file">   
                                            <div class="input-group col-md-12 d-flex flex-column">
                                                <span class="form-message" style="font-size: 12px; color: red"></span>
                                                <input id="input2"  name="file" type="file" class="btn-sm"  >
                                                <span class="input-group-append mx-2">
                                                    <button class="btn btn-primary btn-sm" type="submit">Load File</button>
                                                </span>
                                            </div>
                                        </form>
                                         <h6 class="mx-3 mt-2" style="font-size: 12px; color: gray">**guide: first you download the template and fill in the format like that, then upload the file xlsx</h6>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${table == 1}">
                            <div class="col-md-12 grid-margin stretch-card" id="myList">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row d-flex flex-row">
                                            <div class="col-md-4 form-groupp-1 ">
                                                <label for="class_code" class="mb-1 mx-4" >Class</label>
                                                <select class=" mx-4 w-50 form-control shadow-sm" name="class_code" id="class_id">
                                                    <option value="">--Choose--</option>
                                                    <c:forEach items="${requestScope.listClassCode}" var="item">
                                                        <option value="${item.id}">${item.class_code}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>
                                            <div class="col-md-5">

                                            </div>
                                            <div class="col-md-3 p-2">
                                                <p class="card-description ">
                                                    There were ${list.size()} student form file xlsx.
                                                </p>
                                            </div>
                                        </div>
                                        <div class="table-responsive">
                                            <table class="table table-striped">
                                                <thead>
                                                    <tr>
                                                        <th style="text-align: center;" >Team</th>
                                                        <th style="text-align: center;" >Full Name</th>
                                                        <th style="text-align: center;" >Roll Number</th>
                                                        <th style="text-align: center;" >Email</th>
                                                        <th style="text-align: center;">Leader</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${requestScope.list}" var="item">
                                                        <tr>
                                                            <td style="text-align: center;">${item.team}</td>
                                                            <td style="text-align: center;">${item.full_name}</td>
                                                            <td style="text-align: center;">${item.roll_number}</td>
                                                            <td style="text-align: center;">${item.email}</td>
                                                            <td style="text-align: center;">${item.leader}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="m-4">
                                            <button type="button" class="btn btn-primary btn-sm" onclick="import_now()" >Insert Now</button>
                                        </div>
                                    </div>                                        
                                </div>                                      
                            </div>
                        </c:if>
                    </div>
                </div>
                <jsp:include page="../../component/Footer.jsp"/>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </body>
    <script>
                                                document.addEventListener('DOMContentLoaded', function () {
                                                    Validator({
                                                        form: '#form-add',
                                                        formGroupSelector: '.input-group',
                                                        errorSelector: '.form-message',
                                                        rules: [

                                                            Validator.isFiles('#input2')

                                                        ]
                                                    });
                                                });
                                                function import_now() {
                                                    let class_id = $('#class_id').val();
                                                    let class_code = $('#class_id option:selected').html();
                                                    if (!class_id)
                                                        return  alert("warning", "", "You need choose CLASS befor insert")
                                                  
                                                                

                                                                    $.when(
                                                                            $.ajax({
                                                                                url: '/swp-project/teacher/class-user?go=import-insert-xlsx',
                                                                                type: 'POST',
                                                                                data: {
                                                                                    file_name: '${filename}',
                                                                                    class_id: class_id
                                                                                }
                                                                            }).done(function (res) {
                                                                        console.log(res);
                                                                        if (res.error == 0)
                                                                            alert("success", "", `Add sucess \${res.done} student into your class, \${res.error} error`)
                                                                        else
                                                                            alert("warning", "", `Add sucess \${res.done} student into your class, \${res.error} error`)
                                                                        alert("warning", "", `Leave the page after 5 seconds`)
                                                                        let myTimeout = setTimeout(_redirect, 4500);

                                                                    })
                                                                            )
                                                                            .done(
                                                                                    alert("success", "", `Your request is being processed by the system`)
                                                                                    )


                                                                
                                                           
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
                                                        autotimeout: 4000,
                                                        gap: 20,
                                                        distance: 20,
                                                        type: 1,
                                                        position: "right top",
                                                        customWrapper: "",
                                                    });
                                                }

    </script>
</html>
