<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="Juan Carlos Hernández">
        <title>Exam Application System</title>
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="css/sb-admin.css" rel="stylesheet">
    </head>

    <body class="bg-dark">
        <%
            String msg = (String) request.getAttribute("msg");

            if (msg != null) {

        %>
        <div class="container">
            <div class="card card-login bg-light mx-auto mt-5">
                <div class="card-header">Warning</div>
                <div class="card-body">
                    <div class="text-center">
                        <p class="card-text">${msg}</p>
                        <a class="d-block small mt-3" href="register.jsp">Register an Account</a>
                    </div>
                </div>
            </div>
        </div>
        <%} else { %>

        <div class="container">            
            <div class="card card-login mx-auto mt-5">
                <div class="card-header">Login</div>
                <div class="card-body">
                    <form action="servletLogin" method="post">
                        <div class="form-group">
                            <label for="exampleInputEmail1">Email address</label>
                            <input class="form-control" id="exampleInputEmail1" name="email" type="email" aria-describedby="emailHelp" required placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label>
                            <input class="form-control" id="exampleInputPassword1" name="password" type="password" placeholder="Password" required>
                        </div>
                        <div class="form-group">
                            <div class="form-check">
                                <label class="form-check-label">
                                    <input class="form-check-input" type="checkbox"> Remember Password</label>
                            </div>
                        </div>
                        <input type="submit" class="btn btn-primary btn-block" name="btnLogin" value="Login">
                        <input type="hidden" name="action" value="login">
                    </form>
                    <div class="text-center">
                        <a class="d-block small mt-3" href="register.jsp">Register an Account</a>
                        <a class="d-block small" href="forgot-password.jsp">Forgot Password?</a>
                    </div>
                </div>
            </div>
        </div>

        <% }%>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    </body>

</html>

