					<div class="modal fade" id="editTransaction" tabindex="-1" role="dialog" aria-labelledby="editTransactionLabel" aria-hidden="true">
		                <div class="modal-dialog" role="document">
		                    <form id="EditTransaction" action="EditTransaction" method="post">
		                        <div class="modal-content">
		                            <div class="modal-header">
		                                <h5 class="modal-title" id="editTransactionLabel">Edit <span id="edit-type"></span></h5>
		                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                                        <span aria-hidden="true">&times;</span>
		                                    </button>
		                            </div>
		                            <div class="alert alert-danger" id="edit-transaction-error" role="alert"></div>
		                            <input type="hidden" name="edit-id" id="edit-id">
		                            <input type="hidden" name="is-delete" id="is-delete" value=false>
		                            <div class="container">
		                                <div class="form-group row">
		                                    <label for="edit-amount" class="col-sm-3 col-form-label">Amount</label>
		                                    <div class="col-sm-9">
		                                        <div class="input-group">
		                                            <span class="input-group-addon">$</span>
		                                            <input type="number" name="edit-amount" min="0.00" step="0.01" class="form-control" id="edit-amount" placeholder="0.00" required>
		                                        </div>
		                                    </div>
		                                </div>
		                            </div>
		                            <div class="container">
		                                <div class="form-group row">
		                                    <label for="edit-category" class="col-sm-3 col-form-label">Category</label>
		                                    <div class="col-sm-9">
		                                        <select class="form-control" name="edit-category" id="edit-category" onchange="editChangeCategory()">
		                                            <c:forEach items="${categories}" var="category">
		                                            	<option value="<c:out value="${category}"/>"><c:out value="${category}"/></option>
													</c:forEach>
		                                            <option value="new">Create new category...</option>
		                                        </select>
		                                        <input class="form-control new-category" id="edit-new-category" name="edit-new-category" type="text" placeholder="New category name" maxlength="45">
		                                    </div>
		                                </div>
		                            </div>
		                            <div class="container">
		                                <div class="form-group row">
		                                    <label for="Date" class="col-sm-3 col-form-label">Date</label>
		                                    <div class="col-sm-9">
		                                        <div id="edit-transaction-datepicker"></div>
		                                        <input id="edit-transaction-date" type="hidden" name="edit-date" required>
		                                    </div>
		                                </div>
		                            </div>
		                            <div class="container text-center">
		                                <a class="btn btn-secondary more-options" data-toggle="collapse" href="#edit-more-options" aria-expanded="false" aria-controls="edit-more-options">
		                                Show more options
		                            </a>
		                            </div>
		                            <div class="collapse" id="edit-more-options">
		                                <div class="container">
		                                    <div class="form-group row">
		                                        <label for="edit-description" class="col-sm-3 col-form-label">Description</label>
		                                        <div class="col-sm-9">
		                                            <input type="text" name="edit-description" class="form-control" id="edit-description" placeholder="Optional" maxlength="255">
		                                        </div>
		                                    </div>
		                                    <div class="form-group row">
		                                        <label for="edit-location" class="col-sm-3 col-form-label">Location</label>
		                                        <div class="col-sm-9">
		                                            <input type="text" name="edit-location" class="form-control" id="edit-location" placeholder="Optional" maxlength="45">
		                                        </div>
		                                    </div>
		                                    <div class="form-group row">
		                                        <label for="edit-repeating" class="col-sm-3 col-form-label">Repeating</label>
		                                        <div class="col-sm-9">
		                                            <select class="form-control" name="edit-repeating" id="edit-repeating" disabled readonly>
		                                                <option value="never">Never</option>
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
		                                <input type="submit" value="submit" style="visibility: hidden;">
		                                <input type="submit" class="btn btn-danger" onclick="deleteTransaction()" value="Delete transaction">
		                                <input type="submit" class="btn btn-primary" value="Edit transaction">
		                            </div>
		                        </div>
		                    </form>
		                </div>
		            </div>