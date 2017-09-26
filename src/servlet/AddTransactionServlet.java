package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

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
		String type = request.getParameter("type");
		Double amount = new Double(request.getParameter("amount"));
		Date date = new Date(Long.parseLong(request.getParameter("date")));
		String description = request.getParameter("description");
		String location = request.getParameter("location");
		
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
			accessor.create(account, amount, description, date);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doGet(request, response);
	}

}
