package servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
				
				//checks frequency to give correct set of transactions
				if (frequency != null){
					if (frequency.equals("all time")){
						request.setAttribute("frequency", "all time");
					}
					else if (frequency.equals("daily")){
						frequencyNum = 1;
						request.setAttribute("frequency", "daily");
						nextDate.add(Calendar.DATE, 1);
					}
					else if (frequency.equals("weekly")){
						frequencyNum = 2;
						request.setAttribute("frequency", "weekly");
						nextDate.add(Calendar.DATE, 7);
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
							nextDate.add(Calendar.DATE, 7);
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
				
				
				List<Transaction> transactions = accountAccessor.getTransactionWithOptions(userAccount, currentDate, frequencyNum, 0);	
				
				double totalExpenses = totalExpenses(transactions);
				double totalIncome = totalIncome(transactions);
				double balance = totalIncome - totalExpenses;
				request.setAttribute("transactions", transactions);
				request.setAttribute("totalExpenses", totalExpenses);
				request.setAttribute("totalIncome", totalIncome);
				request.setAttribute("balance", balance);

				List<String> categories = accountAccessor.getCategories(userAccount);
				request.setAttribute("categories", categories);
				
				HashMap<String, Integer> income = getTotals(true, transactions);
				
				List<String> incomeCategories = new ArrayList<String>(income.size());
				List<String> incomeTotals = new ArrayList<String>(income.size());
				Set set = income.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry a = (Map.Entry) iterator.next();
					incomeCategories.add((String) a.getKey());
					incomeTotals.add((String) String.valueOf(a.getValue()));
				}
				request.setAttribute("incomeCategories", incomeCategories);
				request.setAttribute("incomeTotals", incomeTotals);
				
				HashMap<String, Integer> expenses = getTotals(false, transactions);
				List<String> expensesCategories = new ArrayList<String>(expenses.size());
				List<String> expensesTotals = new ArrayList<String>(expenses.size());
				set = expenses.entrySet();
				iterator = set.iterator();
				while(iterator.hasNext()) {
					Map.Entry a = (Map.Entry) iterator.next();
					expensesCategories.add((String) a.getKey());
					expensesTotals.add((String) String.valueOf(a.getValue()));
				}
				request.setAttribute("expensesCategories", expensesCategories);
				request.setAttribute("expensesTotals", expensesTotals);
				
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
			request.getRequestDispatcher("WEB-INF/pages/OverviewPage.jsp").forward(request, response);
		}
	}
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
