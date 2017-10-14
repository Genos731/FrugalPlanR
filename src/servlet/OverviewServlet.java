package servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.accessor.TransactionAccessorImpl;
import backend.container.Account;
import backend.container.Transaction;

/**
 * Servlet implementation class OverviewServlet
 */
@WebServlet("/Overview")
public class OverviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OverviewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("userName");
		if (username != null){
			try {
				TransactionAccessorImpl accountAccessor = new TransactionAccessorImpl();
				AccountAccessor accessor = new AccountAccessorImpl();
				Account userAccount = accessor.getAccount(username);
				List<Transaction> transactions = accountAccessor.getTransaction(userAccount);	
				
				double totalExpenses = totalExpenses(transactions);
				double totalIncome = totalIncome(transactions);
				double balance = totalIncome - totalExpenses;
				request.setAttribute("transactions", transactions);
				request.setAttribute("totalExpenses", totalExpenses);
				request.setAttribute("totalIncome", totalIncome);
				request.setAttribute("balance", balance);

				List<String> categories = accountAccessor.getCategories(userAccount);
				request.setAttribute("categories", categories);
				
				HashMap<String, Integer> totals = getTotals(transactions);
				List<String> graphCategories = new ArrayList<String>(totals.size());
				List<String> graphTotals = new ArrayList<String>(totals.size());
				Set set = totals.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry a = (Map.Entry) iterator.next();
					graphCategories.add((String) a.getKey());
					graphTotals.add((String) String.valueOf(a.getValue()));
				}
				request.setAttribute("graphCategories", graphCategories);
				request.setAttribute("graphTotals", graphTotals);
				
				accountAccessor.close();
				accessor.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.getRequestDispatcher("WEB-INF/pages/OverviewPage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private double totalIncome(List<Transaction> transactions){
		double expenses = 0;
		for (int x = 0; x < transactions.size(); x++){
			if (transactions.get(x).isIncome()){
				expenses += transactions.get(x).getValue();
			}
		}
		return expenses;
	}
	
	private double totalExpenses(List<Transaction> transactions){
		double expenses = 0;
		for (int x = 0; x < transactions.size(); x++){
			if (!transactions.get(x).isIncome()){
				expenses += transactions.get(x).getValue();
			}
		}
		return expenses;
	}
	
	private HashMap<String, Integer> getTotals(List<Transaction> transactions) {
		HashMap<String, Integer> totals = new HashMap<String, Integer>();
		for (Transaction transaction : transactions) {
			String category = transaction.getCategory();
			int value = Double.valueOf(transaction.getValue()).intValue();
			if (totals.containsKey(category)) {
				totals.put(category, totals.get(category) + value);
			} else {
				totals.put(category, value);
			}
		}		
		return totals;
	}

}
