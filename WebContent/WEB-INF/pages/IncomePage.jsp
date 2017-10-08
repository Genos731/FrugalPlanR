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
    <title>Overview</title>
    <style>
        .navbar {
            background: #4ea3ff;
            height: 100px;
        }

        .navbar .btn {
            margin-right: 15px;
        }

        .logo {
            width: 400px;
        }

        .container-fluid {
            margin-top: 100px;
        }

        .sidebar {
            position: fixed;
            top: 90px;
            bottom: 0;
            left: 0;
            z-index: 1;
            padding: 20px;
            overflow-x: hidden;
            overflow-y: auto;
            border-right: 1px solid #eee;
        }

        .sidebar {
            padding-left: 0;
            padding-right: 0;
        }

        .sidebar .nav-item {
            width: 100%;
        }

        .sidebar .nav-link {
            border-radius: 0;
            height: 50px;
            font-size: 16pt;
        }

        .nav-pills .nav-link.active {
            background-color: #4ea3ff;
        }

        .sidebar .nav-link:hover:not(.active) {
            background: #9CCEFF;
        }

        input[type=submit] {
            cursor: pointer;
        }

        .btn {
            cursor: pointer;
        }

        .alert-dismissible .close {
            top: -1rem;
            cursor: pointer;
        }

        .summary {
            font-size: 1.5em;
        }

        .add-transaction {
            margin: 50px auto 0;
            width: 75%;
        }

        .add-transaction .btn {
            font-size: 16pt;
        }

        .date-picker {
            width: 20rem;
            font-size: 20pt;
            margin: 0 auto 20px;
            padding-bottom: 3px;
        }

        .date-picker a {
            margin: 0 15px;
        }

        section {
            margin-top: 10px;
        }

        @media only screen and (max-width: 750px) {
            .summary {
                font-size: 1em;
            }
        }
    </style>
</head>
<body>
    <!-- NAVBAR/HEADER -->
    <nav class="navbar fixed-top navbar-light">
        <a href="#"><img class="logo" src="https://i.imgur.com/XTgvOoN.png" alt="FrugalPlanr" /></a>
        <span align="right" class="row">
        	<form action="SignUp">
                <input type="submit" class="btn btn-light" value="Sign Up">
            </form>
            <form action="EditProfile">
                <input type="submit" class="btn btn-light" value="Edit Profile">
            </form>
            <form action="Logout">
                <input type="submit" class="btn btn-danger" value="Logout">
            </form>
        </span>
    </nav>
    
    <div class="container-fluid">
        <div class="row">
    		<!-- SIDEBAR -->
		    <nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
		        <ul class="nav nav-pills flex-column">
		            <li class="nav-item">
		                <a class="nav-link active" href="/">Overview</span></a>
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
		            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addTransaction">
		                Add transaction
		            </button>
		        </ul>
		    </nav>
		    
		    <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
			</main>
		</div>
	</div>
</body>
</html>