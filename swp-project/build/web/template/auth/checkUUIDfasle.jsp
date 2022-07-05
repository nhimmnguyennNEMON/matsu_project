<%-- 
    Document   : checkUUIDfasle
    Created on : Jun 4, 2022, 8:25:39 AM
    Author     : pallgree
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <a href="/swp-project/auth?go=login">Login</a>
         <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    </body>
    <script>
          function noti(){
              swal("Sorry!", "We dont confirm you are the account owner!", "error");
          }
          noti();
    </script>
</html>
