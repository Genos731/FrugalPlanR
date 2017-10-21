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
        
        #errorMessage .close, #successMessage .close {
        	margin-right: 8px;
        }

        .summary {
            font-size: 1.5em;
        }
        
        .alert-danger {
        	white-space: pre;
        }

        .add-transaction, .add-budget {
            margin: 50px auto 0;
            width: 75%;
        }

        .add-transaction .btn, .add-budget .btn {
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
        
        #EditTransaction .modal-header, #AddBudget .modal-header {
            margin-bottom: 15px;
        }

        .datepicker {
            margin: 0 auto;
        }

        #transaction-datepicker tfoot,
        #edit-transaction-datepicker tfoot,
        #budget-datepicker tfoot
         {
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
        
        .input-daterange .input-group-addon {
        	padding: .5rem .75rem;
        	margin-left: 0;
        	margin-right: 0;
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
        
        .card-date-picker {
		    width: auto;
		    height: auto;
		    display:inline-block;
        }
        
    </style>