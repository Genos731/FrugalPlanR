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
    <title>Budget</title>
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
                        <a class="nav-link" href="Overview">Overview</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Expenses">Expenses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Income">Income</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link active" href="Budget">Budget</a>
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
                                            <input type="number" name="amount" min="0.00" step="0.01" class="form-control" id="Amount" placeholder="0.00" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Categories" class="col-sm-3 col-form-label">Select categories</label>
                                    <div class="col-sm-9">
                                        <select multiple class="form-control" name="categories" id="Categories" required>
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
									        <input type="text" class="input-sm form-control" name="start" id="budget-start" onchange="setDates()"/>
									        <span class="input-group-addon">to</span>
									        <input type="text" class="input-sm form-control" name="end" id="budget-end" onchange="setDates()"/>
									    </div>
                                        <input id="start-date" type="hidden" name="start-date">
                                        <input id="end-date" type="hidden" name="end-date">
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
                    <ul>
                    	<li>Budgets allow you to set an amount that you want to allocate to spending in a set of categories.</li>
                    	<li>You can click on a budget row to see a list of transactions that count towards that budget.</li>
                    	<li>To add a budget, click the <em>Add budget</em> button in the sidebar.</li>
                    	<li>Budgets can contain one or more categories. To select multiple categories, hold Ctrl while selecting a category.</li>
                   	</ul>
                </div>

                <!-- SUMMARY -->
                <div class="table-responsive">
                    <table class="table table-striped">
                       <thead>
                            <tr>
								<th>Start Date</th>
								<th>End Date</th>
                                <th>Value</th>
                                <th>Total Expenses</th>
                                <th>Budget Leftover</th>
                                <th>Description</th>
                                <th>Categories</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
							<c:if test="${budgets.size() == 0}">
                            	<tr>
                            		<td colspan="8">
                            		No Budgets
                            		</td>
                            	</tr>
                            	</c:if>
	                       	<c:forEach var="budget" items="${budgets}" >
	                         	<tr>
		                       		<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer"><c:out value="${budget.getStartDateForPrint()}" /> </td>
		                       		<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer"> <c:out value="${budget.getEndDateForPrint()}" /> </td>
		                          	<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer"> $<c:out value="${budget.getGoalValue()}" /> </td>
		                          	<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer"> $<c:out value="${budget.getBudgetExpensesTotal(transactions)}" /> </td>
		                       		<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer">
		                       			<c:if test = "${budget.getGoalValue() - budget.getBudgetExpensesTotal(transactions) < 0}"> 
		                       				-$<c:out value="${budget.getGoalValue()*-1 - budget.getBudgetExpensesTotal(transactions)*-1}" />
		                       			</c:if>
		                       			<c:if test = "${budget.getGoalValue() - budget.getBudgetExpensesTotal(transactions) >= 0}"> 
		                       			 	$<c:out value="${budget.getGoalValue() - budget.getBudgetExpensesTotal(transactions)}" />
		                       			</c:if>
	                       			</td>
		                          	<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer"> <c:out value="${budget.description}" /> </td>
		                          	<td onClick="toggle_it(${budget.getID()})" style="cursor:pointer"> 
			                            <c:forEach var="category" items="${budget.getCategoryList()}">
			                            	<c:out value="${category}" />
			                            </c:forEach>
		                          	</td>
		                          	<td class="text-right" id="button-${budget.getID()}"></td>
	                         	</tr>
								<tr>
									<td></td>
									<td colspan="6" id="${budget.getID()}" style="display: none">
										<table class="table table-sm">
										    <thead>
										        <tr>
										            <th colspan="3">List of transactions</th>
										        </tr>
										        <tr>
													<th>Date</th>
													<th>Amount</th>
													<th>Categories</th>
										        </tr>
										    </thead>
										    <tbody>
												<%@include file="Presets/MiniTable.jsp" %>
										    </tbody>
										</table>
									</td>
									<td></td>
								</tr>
							</c:forEach>
					 	</tbody>
                    </table>
                </div>
            <!-- EDIT BUDGET MODAL -->
			<div class="modal fade" id="editBudget" tabindex="-1" role="dialog" aria-labelledby="editBudgetLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <form id="editBudget" action="EditBudget" method="post">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="editBudgetLabel">Edit budget</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                            </div>
                            <div class="alert alert-danger" id="edit-budget-error" role="alert">
                            <input type="hidden" name="edit-id" id="edit-id">
                            <input type="hidden" name="is-delete" id="is-delete" value=false>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Description" class="col-sm-3 col-form-label">Description</label>
                                    <div class="col-sm-9">
                                        <input type="text" name="edit-description" class="form-control" id="EditDescription" placeholder="Description of your budget" maxlength="255">
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Amount" class="col-sm-3 col-form-label">Amount</label>
                                    <div class="col-sm-9">
                                        <div class="input-group">
                                            <span class="input-group-addon">$</span>
                                            <input type="number" name="edit-amount" min="0.00" step="0.01" class="form-control" id="EditAmount" placeholder="0.00">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Categories" class="col-sm-3 col-form-label">Select categories</label>
                                    <div class="col-sm-9">
                                        <select multiple class="form-control" name="edit-categories" id="EditCategories">
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
                                        <div class="input-daterange input-group" id="edit-budget-datepicker">
									        <input type="text" class="input-sm form-control" name="edit-start" id="edit-budget-start" onchange="setEditDates()"/>
									        <span class="input-group-addon">to</span>
									        <input type="text" class="input-sm form-control" name="edit-end" id="edit-budget-end" onchange="setEditDates()"/>
									    </div>
                                        <input id="edit-start-date" type="hidden" name="edit-start-date">
                                        <input id="edit-end-date" type="hidden" name="edit-end-date">
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-danger" onclick="deleteBudget()" value="Delete budget">
                                <input type="submit" class="btn btn-primary" value="Edit budget">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            </main>
        </div>
    </div>
    <%@include file="Presets/Scripts.jsp" %>
   	<script>
	   	function toggle_it(itemID){ 
	        // Toggle visibility between none and ''
	        $('#' + itemID).toggle();
	    } 
   	
        $('#budget-datepicker').datepicker({
            format: "yyyy/mm/dd",
            weekStart: 1,
            maxViewMode: 2,
            todayBtn: "linked"
        });
        
        $('#edit-budget-datepicker').datepicker({
            format: "yyyy/mm/dd",
            weekStart: 1,
            maxViewMode: 2,
            todayBtn: "linked"
        });
        
        function setDates() {
            // set dates
            var start = $('#budget-datepicker').datepicker().data('datepicker').pickers[0].viewDate.getTime();
            $('#start-date').val(start);
            
            var end = $('#budget-datepicker').datepicker().data('datepicker').pickers[1].viewDate.getTime();
            $('#end-date').val(end);
        }

        $('#add-budget-error').hide();
        $('#AddBudget').submit(function (e) {
            // form validation
            var error = "";
            $('#add-budget-error').hide();
            if (!$('#Amount').val()) error += "Please enter an amount.\n";
            if (!$('#Categories').val()) error += "Please select at least one category.\n";
            if (!$('#budget-start').val()) error += "Please select a date range.\n";
            if (error.length > 0) {
                $('#add-budget-error').text(error);
                $('#add-budget-error').show();
                return false;
            }
        });
                
        function editBudget(id, description, amount, categories, start, end) {
        	// set id
        	$('#edit-id').val(id);
        	// set description
        	$('#EditDescription').val(description);
        	// set amount
        	$('#EditAmount').val(amount);
        	// set categories
        	$('#EditCategories').val(categories);
        	// set date
        	$('#edit-budget-datepicker').datepicker().data('datepicker').pickers[0].setDate(start);
        	$('#edit-budget-datepicker').datepicker().data('datepicker').pickers[1].setDate(end);
        	setEditDates();
        }        
        
        function setEditDates() {
            // set dates
            var start = $('#edit-budget-datepicker').datepicker().data('datepicker').pickers[0].viewDate.getTime();
            $('#edit-start-date').val(start);
            
            var end = $('#edit-budget-datepicker').datepicker().data('datepicker').pickers[1].viewDate.getTime();
            $('#edit-end-date').val(end);
        }

        $('#edit-budget-error').hide();
        $('#EditBudget').submit(function (e) {
            // form validation
            var error = "";
            $('#edit-budget-error').hide();
            if (!$('#EditAmount').val()) error += "Please enter an amount.\n";
            if (!$('#EditCategories').val()) error += "Please select at least one category.\n";
            if (!$('#edit-budget-start').val()) error += "Please select a date range.\n";
            if (error.length > 0) {
                $('#add-budget-error').text(error);
                $('#add-budget-error').show();
                return false;
            }
        });
        
        function deleteBudget() {
        	$('#is-delete').val(true);
        }
        
		// add Edit buttons to each budget
		
		var categories = new Array();
        <c:forEach var="budget" items="${budgets}" >
			categories[${budget.getID()}] = new Array();
			<c:forEach items="${budget.getCategoryList()}" var="category">
				categories[${budget.getID()}].push("${category}");
			</c:forEach>
			var button = document.createElement("button");
			button.className = "btn btn-secondary edit";
			button.textContent = "Edit";
			button.setAttribute("data-toggle", "modal");
			button.setAttribute("data-target", "#editBudget");
			button.onclick = function() {editBudget(<c:out value="${budget.getID()}" />, "<c:out value="${budget.getDescription()}" />", <c:out value="${budget.getGoalValue()}" />, categories[${budget.getID()}], "<c:out value="${budget.getStartDateForPrint()}" />", "<c:out value="${budget.getEndDateForPrint()}" />")};
			document.getElementById("button-${budget.getID()}").appendChild(button);
		</c:forEach>

    	function hideExplanation() {
    		localStorage.setItem('<c:out value="${userName}" /> budget', true);
    	}
    	
    	if (!localStorage.getItem('<c:out value="${userName}" /> budget')) $('#explanation').show();
    </script>
</body>

</html>
