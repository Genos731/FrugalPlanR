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
                                            <input type="number" name="amount" min="0.00" step="0.01" class="form-control" id="Amount" placeholder="0.00" required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="container">
                                <div class="form-group row">
                                    <label for="Category" class="col-sm-3 col-form-label">Category</label>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="category" id="Category" onchange="changeCategory()">
                                            <c:forEach items="${categories}" var="category">
                                            	<option value="<c:out value="${category}"/>"><c:out value="${category}"/></option>
											</c:forEach>
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
                                        <input id="transaction-date" type="hidden" name="date" required>
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