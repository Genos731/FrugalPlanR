package servlet;

import java.io.IOException;
import java.sql.SQLException;
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
import backend.accessor.TransactionAccessor;
import backend.accessor.TransactionAccessorImpl;
import backend.container.Account;
import backend.container.Repeating;
import backend.container.Transaction;
import backend.exception.InvalidAccountException;

/**
 * Servlet implementation class EditTransactionServlet
 */
@WebServlet("/EditTransaction")
public class EditTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTransactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get parameters
		int id = new Integer(request.getParameter("edit-id"));
		Double amount = new Double(request.getParameter("edit-amount"));
		String category = request.getParameter("edit-category");
		if (category.equals("new")) category = request.getParameter("edit-new-category");
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(Long.parseLong(request.getParameter("edit-date")));
		String description = request.getParameter("edit-description");
		String location = request.getParameter("edit-location");
		Repeating repeating = Repeating.toRepeating(request.getParameter("edit-repeating"));
		
		// Get the account
		AccountAccessor accountAccessor = new AccountAccessorImpl();
		String username = (String) request.getSession().getAttribute("userName");
		Account account = null;
		try {
			account = accountAccessor.getAccount(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			accountAccessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create transaction accessor
		TransactionAccessor accessor = new TransactionAccessorImpl();
		
		// Get transactions associated with account
		List<Transaction> transactionList = null;
		try {
			transactionList = accessor.getTransaction(account);
			
			if (transactionList == null) {
				System.out.println("returned null");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Find transaction by id
		Transaction t = null;
		for (Transaction transaction : transactionList) {
			if (transaction.getId() == id) t = transaction;
		}

		// Delete transaction
		if (request.getParameter("is-delete").equals("true")) {
			try {
				accessor.delete(t);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Edit transaction				
		try {
			accessor.updateTransaction(t, amount, date, description, location, category);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Close transaction accessor
		try {
			accessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doGet(request, response);
	}

}
