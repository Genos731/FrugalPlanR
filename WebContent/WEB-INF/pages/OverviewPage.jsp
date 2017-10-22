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
    <%@include file="Presets/Error.jsp" %>
    
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
                <div class="alert alert-info alert-dismissible fade show" role="alert" id="explanation" style="display: none">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="hideExplanation()"><span aria-hidden="true">&times;</span></button>
                    <h4 class="alert-heading">Welcome to <strong>FrugalPlanR</strong>!</h4>
                    This is the Overview page where you can see all of your transactions for both expenses and income.<br>
                    Here are a few tips that might help you out:
                    <ul>
	                    <li>The sortable table shows a list of transactions for the time range you've selected.</li>
	                    <li>The pie charts show the proportion of your spending in each category.</li>
	                    <li>To add a transaction, click the <em>Add transaction</em> button in the sidebar.</li>
	                    <li>To edit or delete a transaction, hover over the relevant row in the table and click the <em>Edit</em> button.</li>
	                    <li>Repeating transactions repeat regularly beginning on the date entered and continuing until one repetition in the future.</li>
                    </ul>
                </div>

                <!-- DATE PICKER -->
                <%@include file="Presets/DatePicker.jsp" %>

                <!-- SUMMARY -->
                <div class="card summary">
                    <div class="card-body">
                        <div class="row text-center">
                            <span class="col text-primary"><c:out value="${balance}" /> Balance</span>
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
    <%@include file="Presets/Scripts.jsp" %>
    <%@include file="Presets/TransactionFunctions.jsp" %>
    <script>
	    var graphIncome = document.getElementById('graph-income').getContext('2d');
	    var graphExpenses = document.getElementById('graph-expenses').getContext('2d');

    	var incomeDataSet = <c:out value="${incomeTotals}" />;
    	var incomeCategorySet = [<c:forEach var= "category" items="${incomeCategories}" >"<c:out value='${category}' />",</c:forEach>];
       	var expensesDataSet = <c:out value="${expensesTotals}" />;
       	var expensesCategorySet = [<c:forEach var= "category" items="${expensesCategories}" >"<c:out value='${category}' />",</c:forEach>];
       	
       	if (incomeDataSet.length === 0) {
       		incomeDataSet = [1];
       		incomeCategorySet = ["No income entered"]
       	}
       	
       	if (expensesDataSet.length === 0) {
       		expensesDataSet = [1];
       		expensesCategorySet = ["No expenses entered"]
       	}
       	
    	var incomeData = {
    	    datasets: [{
    	        data: incomeDataSet,
    	        backgroundColor: palette('cb-Spectral', incomeDataSet.length).map(function(hex) {
    	            return '#' + hex;
    	          })
    	    }],
    	    labels: incomeCategorySet
    	};
    	
    	var expensesData = {
       	    datasets: [{
       	        data: expensesDataSet,
       	        backgroundColor: palette('cb-Spectral', expensesDataSet.length).map(function(hex) {
       	            return '#' + hex;
       	          })
       	    }],
       	    labels: expensesCategorySet
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
    <script>    
    	function hideExplanation() {
    		localStorage.setItem('<c:out value="${userName}" /> overview', true);
    	}
    	
    	if (!localStorage.getItem('<c:out value="${userName}" /> overview')) $('#explanation').show();
    </script>
    
    
</body>
</html>