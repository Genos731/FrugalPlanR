<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
        crossorigin="anonymous">
    <title>Signup</title>
    <%@include file="Presets/Style.jsp" %>
</head>
<body>
    <!-- NAVBAR/HEADER -->
    <nav class="navbar fixed-top navbar-light">
        <a href="#"><img class="logo" src="https://i.imgur.com/XTgvOoN.png" alt="FrugalPlanr" /></a>
        <span align="right" class="row">
            <form action="Login">
                <input type="submit" class="btn btn-danger" value="Login">
            </form>
        </span>
    </nav>
    <%@include file="Presets/Error.jsp" %>
    
    <div class="container-fluid">
        <div class="row">
		    
		    <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
			<!-- LOGIN -->
		    	<!-- <form class="form-horizontal" method="post" action="SignupServlet">-->
		    	<form class="form-horizontal" method="post" action ="SignUp">
					<div class="form-group">
						<label class="control-label col-sm-2" for="username">Username:</label>
						<div class="col-sm-10">
			 				<input type="text" class="form-control" name="user" placeholder="Enter username" required>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="pwd">Password:</label>
						<div class="col-sm-10"> 
			  				<input type="password" class="form-control" name="pwd" placeholder="Enter password" required>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">Email:</label>
						<div class="col-sm-10">
			 				<input type="email" class="form-control" name="email" placeholder="Enter email" required>
						</div>
					</div>
					<div class="form-group"> 
						<div class="col-sm-offset-2 col-sm-10">
			  				<button type="submit" class="btn btn-default" value="login">Signup</button>
						</div>
					</div>
				</form>			
			</main>
		</div>
    <%@include file="Presets/Scripts.jsp" %>
</body>
</html>
