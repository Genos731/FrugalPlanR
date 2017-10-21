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
import backend.accessor.BudgetAccessorImpl;
import backend.accessor.TransactionAccessorImpl;
import backend.container.Account;
import backend.container.Budget;
import backend.container.Transaction;

/**
 * Servlet implementation class BudgetServlet
 */
@WebServlet("/Budget")
public class BudgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BudgetServlet() {
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
				BudgetAccessorImpl budgetAccessor = new BudgetAccessorImpl();
				AccountAccessor accessor = new AccountAccessorImpl();
				
				Account userAccount = accessor.getAccount(username);	
				
				List<Budget> budgets = budgetAccessor.getBudgets(userAccount);
				
				List<Transaction> transactions = accountAccessor.getTransaction(userAccount);	
				HashMap<String, Integer> expenses = getTotals(false, transactions);
				List<String> expensesCategories = new ArrayList<String>(expenses.size());
				Set set = expenses.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry a = (Map.Entry) iterator.next();
					expensesCategories.add((String) a.getKey());
				}				
				
				request.setAttribute("budgets", budgets);
				request.setAttribute("categories", expensesCategories);

				accountAccessor.close();		
				budgetAccessor.close();
				accessor.close();
			} catch (SQLException e) {
	    		request.setAttribute("message", e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
	    		request.setAttribute("message", e.getMessage());
				e.printStackTrace();
			}
		}
		if (request.getSession().getAttribute("userName") == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
		} else {
			request.getRequestDispatcher("WEB-INF/pages/BudgetPage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private HashMap<String, Integer> getTotals(boolean isIncome, List<Transaction> transactions) {
		HashMap<String, Integer> totals = new HashMap<String, Integer>();
		for (Transaction transaction : transactions) {
			if (transaction.isIncome() == isIncome) {
				String category = transaction.getCategory();
				int value = Double.valueOf(transaction.getValue()).intValue();
				if (totals.containsKey(category)) {
					totals.put(category, totals.get(category) + value);
				} else {
					totals.put(category, value);
				}
			}
		}
		return totals;
	}
}
