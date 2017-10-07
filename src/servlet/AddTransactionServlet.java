package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import backend.exception.InvalidAccountException;

/**
 * Servlet implementation class AddTransactionServlet
 */
@WebServlet("/AddTransaction")
public class AddTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTransactionServlet() {
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
		boolean type = Boolean.valueOf(request.getParameter("type"));
		Double amount = new Double(request.getParameter("amount"));
		String category = request.getParameter("category");
		if (category.equals("new")) category = request.getParameter("new");
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(Long.parseLong(request.getParameter("date")));
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		Repeating repeating = Repeating.toRepeating(request.getParameter("repeating"));
		
		// Get an account
		AccountAccessor accountAccessor = new AccountAccessorImpl();
		Account account = null;
		try {
			account = accountAccessor.getAccount("Betsy");
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

		// Create transaction
		try {
			accessor.create(account, type, amount, date, description, location, repeating, category);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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
