
<tr>
<th>
List of Transactions
</th>
</tr>
<c:forEach var= "transaction" items="${transactions}" >
	<c:forEach var="category" items="${budget.getCategoryList()}">
		<c:if test = "${transaction.getCategory().equals(category)}">
			<tr>
				<td> <c:out value="${transaction.getDate()}" /> </td>
				<td> $<c:out value="${transaction.getValue()}" /> </td>
				<td> <c:out value="${transaction.getCategory()}" /> </td>
			</tr>
		</c:if>
		
	</c:forEach>
	
</c:forEach>	


