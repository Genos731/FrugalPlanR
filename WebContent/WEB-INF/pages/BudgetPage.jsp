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

        .form-control#Repeating, .form-control#Category {
            height: 45px;
        }

        .new-category {
            margin-top: 15px;
        }

        #transaction-datepicker .datepicker {
            margin: 0 auto;
        }

        #transaction-datepicker tfoot,
        #transaction-date {
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
                        <a class="nav-link" href="Overview">Overview</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Expenses">Expenses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Income">Income</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="Budget active">Budget</a>
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
            <div class="modal fade" id="addTransaction" tabindex="-1" role="dialog" aria-labelledby="addTransactionLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <form id="AddTransaction" action="AddTransaction" method="post">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addTransactionLabel">Add transaction</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                            </div>
                            <div class="alert alert-danger" id="add-transaction-error" role="alert">
                            </div>
                            <div class="modal-body text-center">
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn transaction-type btn-secondary active">
                                    <input type="radio" name="type" id="expense" value="false" autocomplete="off" checked> Expense
                                </label>
                                    <label class="btn transaction-type btn-secondary">
                                    <input type="radio" name="type" id="income" value="true" autocomplete="off"> Income
                                </label>
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
                                    <label for="Category" class="col-sm-3 col-form-label">Category</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="category" id="Category" onchange="changeCategory()">
                                            <option value="default" selected="selected">Default</option>
                                            <option value="new">Create new category...</option>
                                        </select>
                                        <input class="form-control new-category" id="new-category" name="new" type="text" placeholder="New category name" maxlength="45">
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Date" class="col-sm-3 col-form-label">Date</label>
                                    <div class="col-sm-9">
                                        <div id="transaction-datepicker"></div>
                                        <input id="transaction-date" type="date" name="date">
                                    </div>
                                </div>
                            </div>
                            <div class="container text-center">
                                <a class="btn btn-secondary more-options" data-toggle="collapse" href="#more-options" aria-expanded="false" aria-controls="more-options">
                                Show more options
                            </a>
                            </div>
                            <div class="collapse" id="more-options">
                                <div class="container">
                                    <div class="form-group row">
                                        <label for="Description" class="col-sm-3 col-form-label">Description</label>
                                        <div class="col-sm-9">
                                            <input type="text" name="description" class="form-control" id="Description" placeholder="Optional" maxlength="255">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="Location" class="col-sm-3 col-form-label">Location</label>
                                        <div class="col-sm-9">
                                            <input type="text" name="location" class="form-control" id="Location" placeholder="Optional" maxlength="45">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="Repeating" class="col-sm-3 col-form-label">Repeating</label>
                                        <div class="col-sm-9">
                                            <select class="form-control" name="repeating" id="Repeating">
                                                <option value="never" selected="selected">Never</option>
                                                <option value="daily">Daily</option>
                                                <option value="weekly">Weekly</option>
                                                <option value="fortnightly">Fortnightly</option>
                                                <option value="monthly">Monthly</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-primary" value="Add transaction">
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- MAIN -->
            <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
                <!-- EXPLANATION -->
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
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
        $('#transaction-datepicker').datepicker({
            format: "dd/mm/yyyy",
            weekStart: 1,
            maxViewMode: 2,
            todayBtn: "linked",
            todayHighlight: true
        });

        $("#add-transaction").click(function () {
            setTimeout(function () {
                $("#transaction-datepicker .today").trigger('click');
            }, 10);
        });

        $('#new-category').hide();

        function changeCategory() {
            if(document.getElementById('Category').value == "new") {
                $('#new-category').show();
            } else {
                $('#new-category').hide();
            }
        }

        $('#add-transaction-error').hide();

        $('#AddTransaction').submit(function (e) {
            // set date
            var date = $("#transaction-datepicker").find(".active").data("date");
            $('#transaction-date').val(date);
            console.log($('#transaction-date').val());

            // form validation
            var error = "";
            $('#add-transaction-error').hide();
            if (!$('#Amount').val()) error += "Please enter an amount.";
            if (error.length > 0) {
                $('#add-transaction-error').text(error);
                $('#add-transaction-error').show();
                return false;
            }
        });
    </script>
</body>

</html>
