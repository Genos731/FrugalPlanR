package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
import backend.container.Repeating;
import backend.exception.InvalidAccountException;

/**
 * Servlet implementation class AddBudgetServlet
 */
@WebServlet("/AddBudget")
public class AddBudgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBudgetServlet() {
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
		Double amount = new Double(request.getParameter("amount"));
		String[] categoryArray = request.getParameterValues("categories");
		List<String> categories = Arrays.asList(categoryArray);
		Calendar dateStart = new GregorianCalendar();
		dateStart.setTimeInMillis(Long.parseLong(request.getParameter("start")));
		Calendar dateEnd = new GregorianCalendar();
		dateStart.setTimeInMillis(Long.parseLong(request.getParameter("end")));
		
		// Get an account
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

		// Create budget accessor
		BudgetAccessor accessor = new BudgetAccessorImpl();

		// Create transaction
		try {
			accessor.create(account, amount, dateStart, dateEnd, categories);
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
