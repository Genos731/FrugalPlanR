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

        input[type=submit],
        .btn,
        .close {
            cursor: pointer;
        }

        .alert-dismissible .close {
            top: -1rem;
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

        .transaction-type {
            background-color: #9CCEFF;
            border-color: #9CCEFF;
        }

        .transaction-type.active {
            background-color: #007bff;
            border-color: #007bff;
        }

        .transaction-type:hover:not(.active) {
            background-color: #4ea3ff;
            border-color: #4ea3ff;
        }

        .form-control {
            line-height: 1.5
        }

        .form-control#Repeating, .form-control#Category, .form-control#edit-repeating, .form-control#edit-category {
            height: 45px;
        }

        .new-category {
            margin-top: 15px;
        }
        
        #EditTransaction .modal-header {
            margin-bottom: 15px;
        }

        .datepicker {
            margin: 0 auto;
        }

        #transaction-datepicker tfoot,
        #transaction-date,
        #edit-transaction-datepicker tfoot,
        #edit-transaction-date {
            display: none;
        }

        .datepicker table tr td.active.active,
        .datepicker table tr td.active.highlighted.active,
        .datepicker table tr td.active.highlighted:active,
        .datepicker table tr td.active:active,
        .datepicker table tr td span.active.active,
        .datepicker table tr td span.active.disabled.active,
        .datepicker table tr td span.active.disabled:active,
        .datepicker table tr td span.active.disabled.active:hover,
        .datepicker table tr td span.active.disabled:hover:active,
        .datepicker table tr td span.active:active,
        .datepicker table tr td span.active.active:hover,
        .datepicker table tr td span.active:hover:active {
            background-color: #007bff;
            border-color: #007bff;
        }

        .datepicker table tr td.active.active.focus,
        .datepicker table tr td.active.active:focus,
        .datepicker table tr td.active.active:hover,
        .datepicker table tr td.active.highlighted.active.focus,
        .datepicker table tr td.active.highlighted.active:focus,
        .datepicker table tr td.active.highlighted.active:hover,
        .datepicker table tr td.active.highlighted.focus:active,
        .datepicker table tr td.active.highlighted:active:focus,
        .datepicker table tr td.active.highlighted:active:hover,
        .datepicker table tr td.active.focus:active,
        .datepicker table tr td.active:active:focus,
        .datepicker table tr td.active:active:hover,
        .datepicker table tr td span.active.active.focus,
        .datepicker table tr td span.active.active:focus,
        .datepicker table tr td span.active.active:hover,
        .datepicker table tr td span.active.disabled.active.focus,
        .datepicker table tr td span.active.disabled.active:focus,
        .datepicker table tr td span.active.disabled.active:hover,
        .datepicker table tr td span.active.disabled.focus:active,
        .datepicker table tr td span.active.disabled:active:focus,
        .datepicker table tr td span.active.disabled:active:hover,
        .datepicker table tr td span.active.disabled.active.focus:hover,
        .datepicker table tr td span.active.disabled.active:hover:focus,
        .datepicker table tr td span.active.disabled.active:hover:hover,
        .datepicker table tr td span.active.disabled.focus:hover:active,
        .datepicker table tr td span.active.disabled:hover:active:focus,
        .datepicker table tr td span.active.disabled:hover:active:hover,
        .datepicker table tr td span.active.focus:active,
        .datepicker table tr td span.active:active:focus,
        .datepicker table tr td span.active:active:hover,
        .datepicker table tr td span.active.active.focus:hover,
        .datepicker table tr td span.active.active:hover:focus,
        .datepicker table tr td span.active.active:hover:hover,
        .datepicker table tr td span.active.focus:hover:active,
        .datepicker table tr td span.active:hover:active:focus,
        .datepicker table tr td span.active:hover:active:hover {
            background-color: #4ea3ff;
            border-color: #4ea3ff;
        }

        .more-options {
            margin-bottom: 15px;
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
        
        tr .edit {
        	opacity: 0;
        }
        
        tr:hover .edit {
        	opacity: 1;
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
    
    <%@include file="Presets/Header.jsp" %>
    
    <div class="container-fluid">
        <div class="row">
            <!-- SIDEBAR -->
            <nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
                <ul class="nav nav-pills flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="Overview">Overview</a>
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

            <!-- MAIN -->
            
            <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
            
                <!-- EXPLANATION -->
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button> This is the Overview page where you can see all your transactions
                </div>

                <!-- DATE PICKER -->
                <div class="card date-picker text-center">
                    <div>
                        <a class="text-secondary" href="#">&lt;</a> 
                        <a class="text-secondary" href="#">All time</a>
                        <a class="text-secondary" href="#">&gt;</a>
                    </div>
                </div>

                <!-- SUMMARY -->
                <div class="card summary">
                    <div class="card-body">
                        <div class="row text-center">
                            <span class="col text-primary">$<c:out value="${balance }" /> Balance</span>
                            <span class="col text-success">+ $<c:out value="${totalIncome }" /> Income</span>
                            <span class="col text-danger">- $<c:out value="${totalExpenses }" /> Expenses</span>
                            <span class="col text-warning"></span>
                        </div>
                    </div>
                </div>

                <!-- GRAPH OF TRANSACTION -->
                <div class="row">
                	<canvas id="graph-income" width="500" height="300" style="display: block; width: 500px; height: 300px; margin: 0 auto;"></canvas>
                	<canvas id="graph-expenses" width="500" height="300" style="display: block; width: 500px; height: 300px; margin: 0 auto;"></canvas>
				</div>
                <!-- TABLE OF TRANSACTION -->
                <section>
                    <h2>Transactions</h2>
                    <%@include file="Presets/Table.jsp" %>
                    
                    <!-- EDIT TRANSACTION MODAL -->
		            <%@include file="Presets/EditTransaction.jsp" %>
                </section>
            </main>
        </div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
	<script src="https://codepen.io/anon/pen/aWapBE.js"></script> <!-- http://google.github.io/palette.js/ -->
	
    <%@include file="Presets/TransactionFunctions.jsp" %>
    
    <script>
	    var graphIncome = document.getElementById('graph-income').getContext('2d');
	    var graphExpenses = document.getElementById('graph-expenses').getContext('2d');

    	var incomeDataSet = <c:out value="${incomeTotals}" />;
       	var expensesDataSet = <c:out value="${expensesTotals}" />;
       	
    	var incomeData = {
    	    datasets: [{
    	        data: incomeDataSet,
    	        backgroundColor: palette('cb-Spectral', incomeDataSet.length).map(function(hex) {
    	            return '#' + hex;
    	          })
    	    }],

    	    labels: [<c:forEach var= "category" items="${incomeCategories}" >"<c:out value='${category}' />",</c:forEach>]
    	};
    	
    	var expensesData = {
       	    datasets: [{
       	        data: expensesDataSet,
       	        backgroundColor: palette('cb-Spectral', expensesDataSet.length).map(function(hex) {
       	            return '#' + hex;
       	          })
       	    }],

       	    labels: [<c:forEach var= "category" items="${expensesCategories}" >"<c:out value='${category}' />",</c:forEach>]
       	};

    	Chart.defaults.global.responsive = false;
    	Chart.defaults.global.title.display = true;
    	Chart.defaults.global.title.position = 'top';
    	Chart.defaults.global.title.fontSize = 30;
    	Chart.defaults.global.title.fontFamily = '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
    	Chart.defaults.global.title.fontStyle = 'normal';
    	Chart.defaults.global.title.fontColor = '#212529';
    	Chart.defaults.global.legend.position = 'right';
	    
	    var income = new Chart(graphIncome, {
	        type: 'doughnut',
	        data: incomeData,
	        options: {
	   	        title: {
	   	            text: 'Income'
	   	        }
	    	}
	    });
	    
	    var expenses = new Chart(graphExpenses, {
	        type: 'doughnut',
	        data: expensesData,
	        options: {
	   	        title: {
	   	            text: 'Expenses'
	   	        }
	    	}
	    });
    </script>
</body>
</html>