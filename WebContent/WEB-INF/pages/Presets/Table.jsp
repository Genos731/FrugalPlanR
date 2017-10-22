					<div class="table-responsive">
                   		<script src="https://kryogenix.org/code/browser/sorttable/sorttable.js"></script>
                        <table class="table table-striped sortable">
                           <thead>
                                <tr>
                                    <th style="cursor:pointer" data-autoclick="true">Date 
                                    <script>
									    window.onload = function() {
									        $('[data-autoclick="true"]').click();
									    };
									</script></th>
                                    <th style="cursor:pointer">Value</th>
                                    <th style="cursor:pointer">Category</th>
                                    <th style="cursor:pointer">Description</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
	                            <c:forEach var= "transaction" items="${transactions}" >
	                            	<tr>
	                            		<td> <c:out value="${transaction.getDate()}" /> </td>
		                            	<td> 
		                            		<c:if test = "${transaction.getActualValue() >= 0 }">
		                            		$<c:out value="${transaction.getActualValue()}" /> 
		                            		</c:if>
		                            		<c:if test ="${transaction.getActualValue() < 0 }">
		                            		-$<c:out value="${transaction.getActualValue()*-1}" /> 
		                            		</c:if>	
		                            	</td>
		                            	<td> <c:out value="${transaction.category}" /> </td>
		                            	<td> <c:out value="${transaction.description}" /> </td>
		                            	<td class="text-right">
		                            		<button class="btn btn-secondary edit" data-toggle="modal" data-target="#editTransaction" id="edit-transaction"
		                            			onclick='editTransaction(<c:out value="${transaction.getId()}" />, <c:out value="${transaction.isIncome()}" />, <c:out value="${transaction.getValue()}" />,
		                            			"<c:out value="${transaction.getCategory()}" />", "<c:out value="${transaction.getDate()}" />", "<c:out value="${transaction.getDescription()}" />",
		                            			"<c:out value="${transaction.getLocation()}" />", "<c:out value="${transaction.getRepeating()}" />")'>Edit</button>
		                            	</td>
	                            	</tr>
							 	</c:forEach>
						 	</tbody>
                        </table>
                    </div>