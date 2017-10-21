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
                    <li class="nav-item active">
                        <a class="nav-link active" href="Budget active">Budget</a>
                    </li>
                </ul>

                <!-- TRIGGERS ADD BUDGET MODAL -->
                <ul class="nav nav-pills flex-column add-budget">
                    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addBudget" id="add-budget">
	                    Add budget
	                </button>
                </ul>
            </nav>

            <!-- ADD BUDGET MODAL -->
			<div class="modal fade" id="addBudget" tabindex="-1" role="dialog" aria-labelledby="addBudgetLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <form id="AddBudget" action="AddBudget" method="post">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="AddBudgetLabel">Add budget</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                            </div>
                            <div class="alert alert-danger" id="add-budget-error" role="alert">
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Description" class="col-sm-3 col-form-label">Description</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="description" class="form-control" id="Description" placeholder="Description of your budget" maxlength="255">
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Amount" class="col-sm-3 col-form-label">Amount</label>
                                    <div class="col-sm-9">
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input type="number" name="amount" min="0.00" step="0.01" class="form-control" id="Amount" placeholder="0.00">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Categories" class="col-sm-3 col-form-label">Select categories</label>
                                    <div class="col-sm-9">
                                        <select multiple class="form-control" name="categories" id="Categories">
                                            <c:forEach items="${categories}" var="category">
                                            	<option value="<c:out value="${category}"/>"><c:out value="${category}"/></option>
											</c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Dates" class="col-sm-3 col-form-label">Date range</label>
                                    <div class="col-sm-9">
                                        <div class="input-daterange input-group" id="budget-datepicker">
									        <input type="text" class="input-sm form-control" name="start" id="budget-start" />
									        <span class="input-group-addon">to</span>
									        <input type="text" class="input-sm form-control" name="end" />
									    </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-primary" value="Add budget">
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- MAIN -->
            <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
                <!-- EXPLANATION -->
                <div class="alert alert-info alert-dismissible fade show" role="alert" id="explanation" style="display: none">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="hideExplanation()"><span aria-hidden="true">&times;</span></button>
                    This is the Budget page where you can organise your budgets.
                </div>

                <!-- DATE PICKER -->
                <div class="card date-picker text-center">
                    <div>
                        <a class="text-secondary" href="#">&lt;</a> <a class="text-secondary" href="#">All time</a>
                        <a class="text-secondary" href="#">&gt;</a>
                    </div>
                </div>

                <!-- SUMMARY -->
                <div class="card summary">
                    <div class="card-body">
                        <div class="row text-center">
                            <span class="col text-primary">start Date <c:out value="${startDate }" /></span>
                            <span class="col text-success">Goal $<c:out value="${budgetGoal }" /> current <c:out value="${currentTotal }" /></span>
                            <span class="col text-danger">Days Remaining <c:out value="${days }" /></span>
                            <span class="col text-warning"></span>
                        </div>
                    </div>
                </div>
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
    	<script>
        $('#budget-datepicker').datepicker({
            format: "dd/mm/yyyy",
            weekStart: 1,
            maxViewMode: 2,
            todayBtn: "linked"
        });

        $('#add-budget-error').hide();
        $('#AddBudget').submit(function (e) {
            // form validation
            var error = "";
            $('#add-budget-error').hide();
            if (!$('#Amount').val()) error += "Please enter an amount.\n";
            if (!$('#category').val()) error += "Please select at least one category.\n";
            if (!$('#budget-start').val()) error += "Please select a date range.\n";
            if (error.length > 0) {
                $('#add-budget-error').text(error);
                $('#add-budget-error').show();
                return false;
            }
        });
    </script>
    <script>    
    	function hideExplanation() {
    		localStorage.setItem('<c:out value="${userName}" /> budget', true);
    	}
    	
    	if (!localStorage.getItem('<c:out value="${userName}" /> budget')) $('#explanation').show();
    </script>
</body>

</html>
