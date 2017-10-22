package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.accessor.BudgetAccessor;
import backend.accessor.BudgetAccessorImpl;
import backend.accessor.TransactionAccessor;
import backend.accessor.TransactionAccessorImpl;
import backend.container.Account;
import backend.container.Budget;
import backend.container.Repeating;
import backend.container.Transaction;
import backend.exception.InvalidAccountException;

/**
 * Servlet implementation class EditBudgetServlet
 */
@WebServlet("/EditBudget")
public class EditBudgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBudgetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/Budget");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountAccessor accountAccessor = new AccountAccessorImpl();
		String username = (String) request.getSession().getAttribute("userName");
		Account account = null;
		try {
			account = accountAccessor.getAccount(username);
		} catch (SQLException e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		try {
			accountAccessor.close();
		} catch (Exception e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}

		// Create budget accessor
		BudgetAccessor accessor = new BudgetAccessorImpl();
		
		// Get budgets associated with account
		List<Budget> budgetList = null;
		try {
			budgetList = accessor.getBudgets(account);
			
			if (budgetList == null) {
				System.out.println("returned null");
				return;
			}
		} catch (SQLException e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		
		// Find budget by id
		int id = new Integer(request.getParameter("edit-id"));
		Budget b = null;
		for (Budget budget : budgetList) {
			if (budget.getID() == id) b = budget;
		}

		// Get parameters
		String description = request.getParameter("edit-description");
		Double amount = new Double(request.getParameter("edit-amount"));
		String[] categoryArray = request.getParameterValues("edit-categories");
		List<String> categories = Arrays.asList(categoryArray);
		Calendar dateStart = new GregorianCalendar();
		dateStart.setTimeInMillis(Long.parseLong(request.getParameter("edit-start-date")));
		Calendar dateEnd = new GregorianCalendar();
		dateEnd.setTimeInMillis(Long.parseLong(request.getParameter("edit-end-date")));
		
		// Delete transaction				
		try {
			accessor.delete(b);
		} catch (InvalidAccountException e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		
		// Edit
		if (!request.getParameter("is-delete").equals("true")) {
			try {
				accessor.create(account, description, amount, dateStart, dateEnd, categories);
			} catch (IllegalArgumentException | InvalidAccountException | SQLException e) {
	    		request.setAttribute("message", e.getMessage());
				e.printStackTrace();
			}
		}
		
		// Close transaction accessor
		try {
			accessor.close();
		} catch (Exception e) {
    		request.setAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		
		if (request.getAttribute("message") != null) request.getRequestDispatcher("WEB-INF/pages/BudgetPage.jsp").forward(request, response);
		else doGet(request, response);
	}
		

}
