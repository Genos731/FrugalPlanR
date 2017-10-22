	<script>
        $('#transaction-datepicker').datepicker({
            format: "dd/mm/yyyy",
            weekStart: 1,
            maxViewMode: 2,
            todayBtn: "linked",
            todayHighlight: true
        });

        $('#edit-transaction-datepicker').datepicker({
            format: "yyyy-mm-dd",
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
        $('#edit-new-category').hide();

        function changeCategory() {
            if(document.getElementById('Category').value == "new") {
                $('#new-category').show();
            } else {
                $('#new-category').hide();
            }
        }

        function editChangeCategory() {
            if(document.getElementById('edit-category').value == "new") {
                $('#edit-new-category').show();
            } else {
                $('#edit-new-category').hide();
            }
        }
        
        changeCategory();

        $('#add-transaction-error').hide();
        $('#edit-transaction-error').hide();

        $('#AddTransaction').submit(function (e) {
            // set date
            var date = $("#transaction-datepicker").find(".active").data("date");
            $('#transaction-date').val(date);

            // form validation
            var error = "";
            $('#add-transaction-error').hide();
            if (!$('#Amount').val()) error += "Please enter an amount.\n";
            if ($('#Category').val() === "new" && !$('#new-category').val()) error += "Please enter a new category.\n";
            if (error.length > 0) {
                $('#add-transaction-error').text(error);
                $('#add-transaction-error').show();
                return false;
            }
        });
        
        function editTransaction(id, type, amount, category, date, description, location, repeating) {
        	// set id
        	$('#edit-id').val(id);
        	// set type
        	if (type === true) $('#edit-type').text("income");
        	else $('#edit-type').text("expense");
        	// set amount
        	$('#edit-amount').val(amount);
        	// set category
        	$('#edit-category').val(category);
        	editChangeCategory();
        	// set date
        	$('#edit-transaction-datepicker').datepicker("update", date);
        	// set description
        	$('#edit-description').val(description);
        	// set location
        	$('#edit-location').val(location);
        	// set repeating
        	if (repeating) $('#edit-repeating').val(repeating.toLowerCase());
        	else $('#edit-repeating').val("never");
        }
        
        $('#EditTransaction').submit(function (e) {
            // set date
            var date = $("#edit-transaction-datepicker").find(".active").data("date");
            $('#edit-transaction-date').val(date);

            // form validation
            var error = "";
            $('#edit-transaction-error').hide();
            if (!$('#edit-amount').val()) error += "Please enter an amount.\n";
            if ($('#edit-category').val() === "new" && !$('#edit-new-category').val()) error += "Please enter a new category.\n";
            if (error.length > 0) {
                $('#edit-transaction-error').text(error);
                $('#edit-transaction-error').show();
                return false;
            }
        });
        
        function deleteTransaction() {
        	$('#is-delete').val(true);
        }
    </script>