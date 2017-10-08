package servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
				//Once Log in is working that'll replace this for who the account is.
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
	

	
	//NOT sure if these should be done here or somewhere else
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

}
