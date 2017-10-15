package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * Servlet implementation class ExpensesServlet
 */
@WebServlet("/Expenses")
public class ExpensesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpensesServlet() {
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
				List<Transaction> incomes = new ArrayList<Transaction>();
				for (int x = 0; x < transactions.size(); x++){
					if (!transactions.get(x).isIncome()){
						incomes.add(transactions.get(x));
					}
				}

				request.setAttribute("transactions", incomes);
						
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
		if (request.getSession().getAttribute("userName") == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
		} else {
			request.getRequestDispatcher("WEB-INF/pages/ExpensePage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
