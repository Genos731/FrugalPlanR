package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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
 * Servlet implementation class IncomeServlet
 */
@WebServlet("/Income")
public class IncomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IncomeServlet() {
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
				
				
				Calendar currentDate = new GregorianCalendar();
				Calendar stringDate = (Calendar) request.getSession().getAttribute("date");
				
				
				
				
				String todayButton = (String) request.getParameter("today");
				if (stringDate == null || todayButton != null){
					currentDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
					
					TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
					currentDate.setTimeZone(timeZone);
					
					currentDate.set(Calendar.HOUR, 0);
					currentDate.set(Calendar.MINUTE, 1);
					currentDate.set(Calendar.SECOND, 0);
					currentDate.set(Calendar.MILLISECOND, 0); 
					
					
					
					request.getSession().setAttribute("date", currentDate);
				}else{
					currentDate = stringDate;
				}
				Calendar nextDate = new GregorianCalendar();
				nextDate.setTimeInMillis(currentDate.getTimeInMillis());
				
				int frequencyNum = 0;
				String frequency = (String) request.getParameter("frequency");
				String frequency2 = (String) request.getSession().getAttribute("frequency");
				
				if (frequency != null){
					if (frequency.equals("all time")){
						request.setAttribute("frequency", "all time");
					}
					else if (frequency.equals("daily")){
						frequencyNum = 1;
						request.setAttribute("frequency", "daily");
					}
					else if (frequency.equals("weekly")){
						frequencyNum = 2;
						request.setAttribute("frequency", "weekly");
						nextDate.add(Calendar.DATE, 6);
					}
					else if (frequency.equals("monthly")){
						frequencyNum = 3;
						request.setAttribute("frequency", "monthly");
						nextDate.add(Calendar.MONTH, 1);
					}	
				}
				else{
					if (frequency2 == null){
						request.setAttribute("frequency", "all time");
					}
					else{
						if (frequency2.equals("daily")){
							frequencyNum = 1;
							request.setAttribute("frequency", "daily");
							nextDate.add(Calendar.DATE, 0);
						}
						else if (frequency2.equals("weekly")){
							frequencyNum = 2;
							request.setAttribute("frequency", "weekly");
							nextDate.add(Calendar.DATE, 6);
						}
						else if (frequency2.equals("monthly")){
							frequencyNum = 3;
							request.setAttribute("frequency", "monthly");
							nextDate.add(Calendar.MONTH, 1);
						}	
					}
				}
				
				String changeTime = (String) request.getParameter("timeDirection");
				if (changeTime != null){
					if (changeTime.equals("<")){
						if (frequencyNum == 1){
							currentDate.add(Calendar.DATE, -1);
							nextDate.add(Calendar.DATE, -1);
						}
						else if (frequencyNum == 2){
							currentDate.add(Calendar.DATE, -7);
							nextDate.add(Calendar.DATE, -7);
						}
						else if (frequencyNum == 3){
							currentDate.add(Calendar.MONTH, -1);
							nextDate.add(Calendar.MONTH, -1);
						}
					}
					else{
						if (frequencyNum == 1){
							currentDate.add(Calendar.DATE, 1);
							nextDate.add(Calendar.DATE, 1);
						}
						else if (frequencyNum == 2){
							currentDate.add(Calendar.DATE, 7);
							nextDate.add(Calendar.DATE, 7);
						}
						else if (frequencyNum == 3){
							currentDate.add(Calendar.MONTH, 1);
							nextDate.add(Calendar.MONTH, 1);
						}
					}
					request.getSession().setAttribute("date", currentDate);
					
				}
				
				
				request.setAttribute("day", currentDate.get(Calendar.DATE));
				request.setAttribute("month", currentDate.get(Calendar.MONTH) + 1);
				request.setAttribute("year", currentDate.get(Calendar.YEAR));
				
				request.setAttribute("day2", nextDate.get(Calendar.DATE));
				request.setAttribute("month2", nextDate.get(Calendar.MONTH) + 1);
				request.setAttribute("year2", nextDate.get(Calendar.YEAR));
				
				
				List<Transaction> transactions = accountAccessor.getTransactionWithOptions(userAccount, currentDate, frequencyNum, 1);
				request.setAttribute("transactions", transactions);
				
				List<String> categories = accountAccessor.getCategories(userAccount);
				request.setAttribute("categories", categories);
						
				accountAccessor.close();
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
			request.getRequestDispatcher("WEB-INF/pages/IncomePage.jsp").forward(request, response);
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
