<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
    crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css">
<title>Overview</title>
<%@include file="Presets/Style.jsp" %>
</head>

<body>
    <!-- NAVBAR/HEADER -->
    <%@include file="Presets/Header.jsp" %>

	 <div class="container-fluid">
        <div class="row">
		    <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
			<!-- EDIT PROFILE -->
		    	<form class="form-horizontal" method="post" action ="EditProfile">
		    		<div class="form-group">
						<label class="control-label col-sm-2" for="username">Current Password</label>
						<div class="col-sm-10">
			 				<input type="password" class="form-control" name="currpwd" placeholder="Enter current password">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="pwd">New Password:</label>
						<div class="col-sm-10"> 
			  				<input type="password" class="form-control" name="newpwd" placeholder="Enter new password">
						</div>
					</div>
					<div id = "email" class="form-group" >
						<label class="control-label col-sm-2" for="email">New Email:</label>
						<div class="col-sm-10">
			 				<input type="text" class="form-control" name="email" placeholder="Enter new email">
						</div>
					</div>
					<div class="form-group"> 
						<div class="col-sm-offset-2 col-sm-10">
		  					 <button type="submit" class="btn btn-default" value="submit">Submit</button>
						</div>
					</div>
				</form>
			</main>
		</div>
	</div>

    <div class="container-fluid">
        <div class="row">
            <!-- SIDEBAR -->
            <nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
                <ul class="nav nav-pills flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="Overview">Overview</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Expenses">Expenses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Income">Income</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Budget">Budget</a>
                    </li>
                </ul>

                <!-- TRIGGERS ADD TRANSACTION MODAL -->
                <ul class="nav nav-pills flex-column add-transaction">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addTransaction" id="add-transaction">
                        Add transaction
                    </button>
                </ul>
            </nav>

            <!-- ADD TRANSACTION MODAL -->
            <%@include file="Presets/AddTransaction.jsp" %>
        </div>
    </div>
    
    <script>
		if (${message != null}) {
 			alert("${message}");
		}
	</script>
    
</body>

</html>