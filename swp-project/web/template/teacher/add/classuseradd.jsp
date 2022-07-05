

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
                            <div class="col-md-6 grid-margin stretch-card" id="add-subject" >
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <p class="card-title">Class user</p>
                                            </div>
                                            <div class="col-md-8"></div>

                                        </div>
                                        <div class="row d-flex flex-row">
                                            <div class="form-group col-md-4 p-1 ">
                                                <label for="class_id">Class</label>
                                                <select class="form-control shadow-sm" name="class_id" id="class_id" onchange="loadTeam()">
                                                    <option value="">--Choose--</option>
                                                    <c:forEach items="${requestScope.listClassCode}" var="item">
                                                        <option value="${item.id}">${item.class_code}-${item.subject_id.subject_code}</option>
                                                    </c:forEach>
                                                </select>

                                            </div>
                                            <div class="form-group col-md-3 p-1">
                                                <label for="team_id">Team</label>
                                                <select class="form-control shadow-sm" name="team_id" id="team_id" onchange="loadStudent()">
                                                </select>
                                            </div>
                                        </div>
                                        <div class="">
                                            <table class="table table-striped">
                                                <tbody id="content-1">
                                                    <tr class="text-center" >

                                                        <td>Please, choose class and team to add .....</td>
                                                    </tr>

                                            </table>
                                            <div class="p-2 d-flex" id="two-btn">

                                            </div>

                                        </div>


                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6  grid-margin stretch-card">

                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <p class="card-title">Find Student</p>
                                            </div>
                                            <div class="col-md-8"></div>

                                        </div>
                                        <div class="d-flex flex-row">
                                            <div class="form-group w-25 p-1 ">
                                                <label for="roll_number">Roll Number</label>
                                                <input type="text" class="form-control" value="" id="roll-number-table-2" onkeyup="findStudentToAdd()">
                                            </div>
                                            <div class="form-group w-50 p-1">
                                                <label for="email">Email</label>
                                                <input type="text" class="form-control" value="" id="email-table-2" onkeyup="findStudentToAdd()">
                                            </div>
                                            <div class="form-group w-25 p-1">
                                                <label for="name">Name</label>
                                                <input type="text" class="form-control" value="" id="name-table-2" onkeyup="findStudentToAdd()">
                                            </div>
                                        </div>
                                        <div class="">
                                            <table class="table table-striped" >
                                                <tbody id="content-2" >
                                                    <tr class="text-center" >

                                                        <td>Please, choose class and team to add .....</td>
                                                    </tr>

                                            </table>



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

        <!--validate input-->
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-notify@0.5.4/dist/simple-notify.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
                                                    localStorage.clear();
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
                                                            $('#team_id').append(`<option value="">--Choose--</option>`);
                                                            for (let i = 0; i < res.length; i++) {
                                                                $('#team_id').append(`<option value=\"\${res[i].id}\">\${res[i].team_name}</option>`);
                                                            }
                                                        });

                                                    }
                                                    function loadStudent() {
                                                        localStorage.removeItem('listAdd');
                                                        let value1 = $('#class_id').val();
                                                        let value2 = $('#team_id').val();
                                                        $.ajax({
                                                            url: '/swp-project/teacher/class-user?go=load-student',
                                                            type: 'POST',
                                                            data: {
                                                                class_id: value1,
                                                                team_id: value2
                                                            }
                                                        }).done(function (res) {
                                                            $('#content-1').empty();
                                                            $('#two-btn').empty();
                                                            localStorage.setItem("listTable1", JSON.stringify(res));
                                                            localStorage.setItem("listTable1-root", JSON.stringify(res));
                                                            renderTable1(res)


                                                        });

                                                    }

                                                    function findStudentToAdd() {
                                                        let _name = $('#name-table-2').val();
                                                        let _email = $('#email-table-2').val();
                                                        let _roll_number = $('#roll-number-table-2').val();
                                                        $.ajax({
                                                            url: '/swp-project/teacher/class-user?go=find-student-to-add',
                                                            type: 'POST',
                                                            data: {
                                                                name: _name,
                                                                email: _email,
                                                                roll_number: _roll_number,
                                                                class_id: $('#class_id').val() 
                                                            }
                                                        }).done(function (res) {
                                                            $('#content-2').empty();
                                                            localStorage.setItem("listTable2", JSON.stringify(res));
                                                            localStorage.setItem("listTable2-root", JSON.stringify(res));
                                                            renderTable2(res)
                                                        });
                                                    }

                                                    function renderTwoButton() {
                                                        $('#two-btn').append(`<button type="button" class="btn btn-danger btn-sm m-2" onclick="resetAll()">Reset All</button>
                                          <button type="button" class="btn btn-primary btn-sm m-2" onclick="saveAll()">Confirm Add</button>
                                                `);

                                                    }
                                                    function renderTable1(res) {
                                                        for (let i = 0; i < res.length; i++) {
                                                            let content = `<tr>
                                        <td>\${res[i].full_name}</td>
                                        <td>\${res[i].roll_number}</td>
                                        <td>\${res[i].email.substring(0, res[i].email.length - 11)}@...</td>
                                        </tr>`
                                                            $('#content-1').append(content);
                                                            console.log(res[i].full_name)
                                                        }

                                                    }
                                                    function renderTable2(res) {
                                                        for (let i = 0; i < res.length; i++) {
                                                            let content = `<tr>
                                        <td>\${res[i].full_name}</td>
                                        <td>\${res[i].roll_number}</td>
                                        <td>\${res[i].email.substring(0, res[i].email.length - 11)}@...</td>
                                        <td class="p-2" ><button type="button" class="btn btn-primary btn-sm" onclick="add(\${res[i].id},'\${res[i].full_name}','\${res[i].email}','\${res[i].roll_number}')">Add</button></td>
                                        </tr>`
                                                            $('#content-2').append(content);
                                                            console.log(res[i])
                                                        }
                                                    }

                                                    function add(user_id, name, email, roll_number) {
                                                        let class_id = $('#class_id').val()
                                                        let team_id = $('#team_id').val()
                                                        if (!class_id || !team_id)
                                                            return swal("Oops", "You need slect CLASS and TEAM befor add!", "error")
                                                        //list data add
                                                        let list = []
                                                        if (localStorage.getItem("listAdd"))
                                                            list = list.concat(JSON.parse(localStorage.getItem("listAdd")))
                                                        if (list.find(e => e == user_id))
                                                            return swal("Oops", "User selected!", "error")
                                                        else
                                                            list.push(user_id)
                                                        //list data table2
                                                        let listTable2 = []
                                                        listTable2 = JSON.parse(localStorage.getItem("listTable2"))
                                                        listTable2 = listTable2.filter(e => e.id != user_id)
                                                        $('#content-2').empty()
                                                        renderTable2(listTable2)
                                                        //list data table1
                                                        let listTable1 = []
                                                        listTable1 = JSON.parse(localStorage.getItem("listTable1"))
                                                        listTable1.push({email: email, full_name: name, roll_number: roll_number})
                                                        $('#content-1').empty()
                                                        renderTable1(listTable1)
                                                        $('#two-btn').empty()
                                                        renderTwoButton()
                                                        //save data
                                                        localStorage.setItem("listAdd", JSON.stringify(list));
                                                        localStorage.setItem("listTable2", JSON.stringify(listTable2));
                                                        localStorage.setItem("listTable1", JSON.stringify(listTable1));
                                                    }


                                                    function resetAll() {
                                                        localStorage.removeItem('listAdd');
                                                        //list data table2
                                                        let listTable2 = []
                                                        listTable2 = JSON.parse(localStorage.getItem("listTable2-root"))
                                                        $('#content-2').empty()
                                                        renderTable2(listTable2)
                                                        //list data table1
                                                        let listTable1 = []
                                                        listTable1 = JSON.parse(localStorage.getItem("listTable1-root"))
                                                        $('#content-1').empty()
                                                        renderTable1(listTable1)
                                                        $('#two-btn').empty()
                                                        localStorage.setItem("listTable2", JSON.stringify(listTable2));
                                                        localStorage.setItem("listTable1", JSON.stringify(listTable1));
                                                    }

                                                    function saveAll() {
                                                        let class_id = $('#class_id').val();
                                                        let team_id = $('#team_id').val();
                                                        let listTable1 = []
                                                        listTable1 = JSON.parse(localStorage.getItem("listTable1"))
                                                        let team_lead = listTable1[0].team_lead;
                                                        let list = []
                                                        if (localStorage.getItem("listAdd"))
                                                            list = list.concat(JSON.parse(localStorage.getItem("listAdd")))
                                                        let size = list.length
                                                       
                                                            $.ajax({
                                                                url: '/swp-project/teacher/class-user?go=insert-data',
                                                                type: 'POST',
                                                                data: {
                                                                    list_user_id: JSON.stringify(list),
                                                                    class_id: class_id,
                                                                    team_id: team_id,
                                                                    team_lead: team_lead
                                                                }
                                                            }).done(function (res) {
                                                        console.log(res)
                                                        if (res.done == 0) {
                                                            return alert("error", "", `Add sucess \${res.done} student into your class, \${res.error} error`)

                                                        }
                                                        localStorage.removeItem('listTable2');
                                                        loadStudent()
                                                        //$('#content-2').empty()
                                                        findStudentToAdd()
                                                        alert("success", "", `Add sucess \${res.done} student into your class, \${res.error} error`)

                                                    })


                                                                   
                                                               

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
