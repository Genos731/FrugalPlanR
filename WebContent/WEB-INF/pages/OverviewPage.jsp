<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

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

            <!-- ADD TRANSACTION MODAL -->
            <div class="modal fade" id="addTransaction" tabindex="-1" role="dialog" aria-labelledby="addTransactionLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addTransactionLabel">Modal title</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                        </div>
                        <div class="modal-body">
                            ...
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- MAIN -->
            <main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
                <!-- EXPLANATION -->
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button> Look at me, I'm an explanation of what this page does and what you can
                    do on it.
                </div>

                <!-- DATE PICKER -->
                <div class="card date-picker text-center">
                    <div>
                        <a class="text-secondary" href="#">&lt;</a> <a class="text-secondary" href="#">This month</a>
                        <a
                            class="text-secondary" href="#">&gt;</a>
                    </div>
                </div>

                <!-- SUMMARY -->
                <div class="card summary">
                    <div class="card-body">
                        <div class="row text-center">
                            <span class="col text-primary">$600 Balance</span>
                            <span class="col text-success">+ $1000 Income</span>
                            <span class="col text-danger">- $400 Expenses</span>
                            <span class="col text-warning">Budget goal or something</span>
                        </div>
                    </div>
                </div>

                <!-- GRAPH OF TRANSACTION -->

                <!-- TABLE OF TRANSACTION -->
                <section>
                    <h2>Transactions</h2>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Header</th>
                                    <th>Header</th>
                                    <th>Header</th>
                                    <th>Header</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1,001</td>
                                    <td>Lorem</td>
                                    <td>ipsum</td>
                                    <td>dolor</td>
                                    <td>sit</td>
                                </tr>
                                <tr>
                                    <td>1,002</td>
                                    <td>amet</td>
                                    <td>consectetur</td>
                                    <td>adipiscing</td>
                                    <td>elit</td>
                                </tr>
                                <tr>
                                    <td>1,003</td>
                                    <td>Integer</td>
                                    <td>nec</td>
                                    <td>odio</td>
                                    <td>Praesent</td>
                                </tr>
                                <tr>
                                    <td>1,003</td>
                                    <td>libero</td>
                                    <td>Sed</td>
                                    <td>cursus</td>
                                    <td>ante</td>
                                </tr>
                                <tr>
                                    <td>1,004</td>
                                    <td>dapibus</td>
                                    <td>diam</td>
                                    <td>Sed</td>
                                    <td>nisi</td>
                                </tr>
                                <tr>
                                    <td>1,005</td>
                                    <td>Nulla</td>
                                    <td>quis</td>
                                    <td>sem</td>
                                    <td>at</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
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
</body>

</html>