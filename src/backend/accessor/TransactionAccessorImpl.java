package backend.accessor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.transaction.InvalidTransactionException;

import backend.container.Account;
import backend.container.Repeating;
import backend.container.Transaction;
import backend.database.FrugalDBConnection;
import backend.exception.InvalidAccountException;

public class TransactionAccessorImpl implements TransactionAccessor {
	Connection dbConnection;

	public TransactionAccessorImpl() {
		dbConnection = FrugalDBConnection.getConnection();
	}

	@Override
	public void create(Account a, boolean isIncome, double value,
			Calendar calendar, String description, String location,
			Repeating repeating, String category) throws SQLException,
	InvalidAccountException, IllegalArgumentException {
		// Check that account is valid
		AccountAccessorImpl accountImpl = new AccountAccessorImpl();
		if (!accountImpl.isValidAccount(a)) {
			accountImpl.close();
			throw new InvalidAccountException("Account is invalid");
		}
		accountImpl.close();

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(0, isIncome, value, calendar,
				description, location, repeating, category, 0);

		// Convert calendar to sqlDate
		Date date = new Date(calendar.getTimeInMillis());

		// Get category id
		int categoryID = getCategoryID(category, a.getId());

		// If categoryID is 0, then it doesn't exist
		// Create new category
		if (categoryID == 0) {
			createCategory(category, a.getId());
			categoryID = getCategoryID(category, a.getId());
		}

		// Get Repeating id
		int repeatID = getRepeatingID(repeating);

		// Create SQL query
		PreparedStatement statement = null;
		if (repeatID != 0) {
			String sqlQuery = "INSERT INTO transaction (isIncome, value, date, description, "
					+ "location, repeating_id, category_id, account_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			statement = dbConnection.prepareStatement(sqlQuery);
			statement.setBoolean(1, isIncome);
			statement.setDouble(2, value);
			statement.setDate(3, date);
			statement.setString(4, description);
			statement.setString(5, location);
			statement.setInt(6, repeatID);
			statement.setInt(7, categoryID);
			statement.setInt(8, a.getId());
		} else {
			String sqlQuery = "INSERT INTO transaction (isIncome, value, date, description, "
					+ "location, category_id, account_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			statement = dbConnection.prepareStatement(sqlQuery);
			statement.setBoolean(1, isIncome);
			statement.setDouble(2, value);
			statement.setDate(3, date);
			statement.setString(4, description);
			statement.setString(5, location);
			statement.setInt(6, categoryID);
			statement.setInt(7, a.getId());
		}

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void delete(Transaction t) throws SQLException,
	InvalidTransactionException {
		// Do nothing if transaction does not exists.
		if (!isValidTransaction(t))
			throw new InvalidTransactionException("Invalid transaction");

		// Prepare SQL
		String sqlQuery = "DELETE FROM transaction "
				+ "WHERE id = ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();

		// Delete empty categories
		// Get category id
		int categoryID = getCategoryID(t.getCategory(), t.getAccountID());

		// Get all transactions with that category
		sqlQuery = "SELECT * "
				+ "FROM transaction "
				+ "WHERE category_id = ?";
		statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, categoryID);

		ResultSet result = statement.executeQuery();
		// If no more transactions have that category id
		if (!result.next()) {
			// If nothing in category_list references this category id
			String sqlQuery2 = "SELECT * "
					+ "FROM category_list "
					+ "WHERE category_id = ?";
			PreparedStatement statement2 = dbConnection.prepareStatement(sqlQuery2);
			statement2.setInt(1, categoryID);

			ResultSet result2 = statement.executeQuery();
			// Nothing reference this categoryid, delete it
			if (!result2.next() ) {
				sqlQuery = "DELETE FROM category "
						+ "WHERE id = ?";
				statement = dbConnection.prepareStatement(sqlQuery);
				statement.setInt(1, categoryID);
				statement.executeUpdate();
			}
			statement2.close();
			result2.close();
		}

		statement.close();
		result.close();
	}

	@Override
	public List<Transaction> getTransaction(Account a) throws SQLException {
		// Check null
		if (a == null)
			return null;

		// List to return
		List<Transaction> transactionList = new ArrayList<Transaction>();

		// Prepare sql query
		String sqlQuery = "SELECT transaction.id, transaction.isIncome, "
				+ "transaction.value, transaction.date, "
				+ "transaction.description, transaction.location, "
				+ "repeating.type, category.name, transaction.account_id "
				+ "FROM transaction " + "LEFT JOIN repeating "
				+ "ON repeating.id = transaction.repeating_id "
				+ "LEFT JOIN category "
				+ "ON category.id = transaction.category_id "
				+ "WHERE transaction.account_id = ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, a.getId());

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();

		// Place result into list
		while (result.next()) {
			// Retrieve variable results
			int transID = result.getInt("id");
			boolean isIncome = result.getBoolean("isIncome");
			double value = result.getDouble("value");
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(result.getDate("date").getTime());
			String description = result.getString("description");
			String location = result.getString("location");
			Repeating repeating = null;
			if (result.getString("type") != null) {
				repeating = Repeating.toRepeating(result.getString("type"));
			}
			String category = result.getString("name");
			int accountID = result.getInt("account_id");

			// Create transaction & add to the list
			Transaction newT = new Transaction(transID, isIncome, value,
					calendar, description, location, repeating, category,
					accountID);
			transactionList.add(newT);
		}
		statement.close();
		result.close();

		// If list is empty, return null
		// Otherwise return the list
		if (transactionList.size() == 0)
			return null;
		return transactionList;
	}
	
	@Override
	public List<Transaction> getTransactionWithOptions(Account a, Calendar date, int option, int neutrality) throws SQLException {
		List<Transaction> transactionList = getTransaction(a);
		
		if (neutrality == 1){
			for (int counter = 0; counter < transactionList.size(); counter++){
				if (transactionList.get(counter).getActualValue() < 0){
					transactionList.remove(counter);
					counter--;
				}
			}
		}
		else if (neutrality == 2){
			for (int counter = 0; counter < transactionList.size(); counter++){
				if (transactionList.get(counter).getActualValue() > 0){
					transactionList.remove(counter);
					counter--;
				}
			}
		}
		Calendar secondDate = new GregorianCalendar();
		TimeZone timeZone = TimeZone.getTimeZone("Australia/Sydney");
		secondDate.setTimeZone(timeZone);
		secondDate.setTimeInMillis(date.getTimeInMillis());
		if (option == 1){
			secondDate.add(Calendar.DATE, 1);
		}
		else if (option == 2){
			secondDate.add(Calendar.DATE, 7);
		}
		else if (option == 3){
			secondDate.add(Calendar.MONTH, 1);
		}
		if (option != 0){
			long dateInMillis = date.getTimeInMillis();
			long secondDateInMillis = secondDate.getTimeInMillis();
			for (int counter = 0; counter < transactionList.size(); counter++){	
				long current = transactionList.get(counter).getCalendar().getTimeInMillis();
				Calendar cal = transactionList.get(counter).getCalendar();
				boolean getRid = true;
				if (date.get(Calendar.YEAR) == cal.get(Calendar.YEAR) || secondDate.get(Calendar.YEAR) == cal.get(Calendar.YEAR)){
					int calendarMonth = cal.get(Calendar.MONTH);
					int calendarDay = cal.get(Calendar.DATE);
					int firstDay = date.get(Calendar.DATE);
					int firstMonth = date.get(Calendar.MONTH);
					int secondDay = secondDate.get(Calendar.DATE);
					int secondMonth = secondDate.get(Calendar.MONTH);
					
					if (firstMonth == calendarMonth && secondMonth == calendarMonth){
						if (firstDay <= calendarDay && secondDay > calendarDay){
							getRid = false;
						}
					}
					else if (firstMonth == calendarMonth && secondMonth > calendarMonth){
						if (firstDay <= calendarDay){
							getRid = false;
						}
					}
					else if (secondMonth == calendarMonth && firstMonth < calendarMonth){
						if (secondDay > calendarDay){
							getRid = false;
						}
					}
					
				}
				if (getRid){
					transactionList.remove(counter);
					counter--;
				}
				/*
				if (current < dateInMillis || current > secondDateInMillis){
					transactionList.remove(counter);
					counter--;
				}*/
			}
		}
		
		return transactionList;
	}

	@Override
	public List<String> getCategories(Account a) throws SQLException {
		// Check null
		if (a == null)
			return null;

		// List to return
		List<String> categoryList = new ArrayList<String>();

		// Prepare sql query
		String sqlQuery = "SELECT name " + "FROM category "
				+ "WHERE account_id = ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, a.getId());

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();

		// Place result into list
		while (result.next()) {
			categoryList.add(result.getString("name"));
		}
		statement.close();
		result.close();

		// If list is empty, return null
		// Otherwise return the list
		if (categoryList.size() == 0)
			return null;
		return categoryList;
	}

	public void updateTransaction(Transaction t, double newValue,
			Calendar newCalendar, String newDescription, String newLocation,
			String newCategory) throws SQLException, IllegalArgumentException {

		// If transaction does not exists, do nothing
		if (!isValidTransaction(t))
			return;

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(t.getId(), t.isIncome(),
				newValue, newCalendar, newDescription, newLocation,
				t.getRepeating(), newCategory, t.getAccountID());

		// Convert calendar to sqlDate
		Date newDate = new Date(newCalendar.getTimeInMillis());

		// Get category id
		int categoryID = getCategoryID(newCategory, t.getAccountID());

		// If categoryID is 0, then it doesn't exist
		// Create new category
		if (categoryID == 0) {
			createCategory(newCategory, t.getAccountID());
			categoryID = getCategoryID(newCategory, t.getAccountID());
		}

		// Prepare SQL line
		String sqlQuery = "UPDATE transaction "
				+ "SET value = ?, "
				+ "date = ?, "
				+ "description = ?, "
				+ "location = ?, "
				+ "category_id = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDouble(1, newValue);
		statement.setDate(2, newDate);
		statement.setString(3, newDescription);
		statement.setString(4, newLocation);
		statement.setInt(5, categoryID);
		statement.setInt(6, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateValue(Transaction t, double newValue)
			throws SQLException, IllegalArgumentException {
		// If transaction does not exists, do nothing
		if (!isValidTransaction(t))
			return;

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(t.getId(), t.isIncome(),
				newValue, t.getCalendar(), t.getDescription(), t.getLocation(),
				t.getRepeating(), t.getCategory(), t.getAccountID());

		// Prepare SQL line
		String sqlQuery = "UPDATE transaction " + "SET value = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDouble(1, newValue);
		statement.setInt(2, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateCalendar(Transaction t, Calendar newCalendar)
			throws SQLException, IllegalArgumentException {
		// If transaction does not exists, do nothing
		if (!isValidTransaction(t))
			return;

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(t.getId(), t.isIncome(),
				t.getValue(), newCalendar, t.getDescription(), t.getLocation(),
				t.getRepeating(), t.getCategory(), t.getAccountID());

		// Convert calendar to sqlDate
		Date newDate = new Date(newCalendar.getTimeInMillis());

		// Prepare SQL line
		String sqlQuery = "UPDATE transaction " + "SET date = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setDate(1, newDate);
		statement.setInt(2, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateDescription(Transaction t, String newDescription)
			throws SQLException, IllegalArgumentException {
		// If transaction does not exists, do nothing
		if (!isValidTransaction(t))
			return;

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(t.getId(), t.isIncome(),
				t.getValue(), t.getCalendar(), newDescription, t.getLocation(),
				t.getRepeating(), t.getCategory(), t.getAccountID());

		// Prepare SQL line
		String sqlQuery = "UPDATE transaction " + "SET description = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, newDescription);
		statement.setInt(2, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateLocation(Transaction t, String newLocation)
			throws SQLException, IllegalArgumentException {
		// If transaction does not exists, do nothing
		if (!isValidTransaction(t))
			return;

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(t.getId(), t.isIncome(),
				t.getValue(), t.getCalendar(), t.getDescription(), newLocation,
				t.getRepeating(), t.getCategory(), t.getAccountID());

		// Prepare SQL line
		String sqlQuery = "UPDATE transaction " + "SET location = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, newLocation);
		statement.setInt(2, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateCategory(Transaction t, String newCategory)
			throws SQLException, IllegalArgumentException {
		// If transaction does not exists, do nothing
		if (!isValidTransaction(t))
			return;

		// Create transaction, to test it follows proper syntax
		@SuppressWarnings("unused")
		Transaction tempTrans = new Transaction(t.getId(), t.isIncome(),
				t.getValue(), t.getCalendar(), t.getDescription(),
				t.getLocation(), t.getRepeating(), newCategory,
				t.getAccountID());

		// Get category id
		int categoryID = getCategoryID(newCategory, t.getAccountID());

		// If categoryID is 0, then it doesn't exist
		// Create new category
		if (categoryID == 0) {
			createCategory(newCategory, t.getAccountID());
			categoryID = getCategoryID(newCategory, t.getAccountID());
		}

		// Prepare SQL line
		String sqlQuery = "UPDATE transaction " + "SET category_id = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, categoryID);
		statement.setInt(2, t.getId());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void close() throws SQLException {
		dbConnection.close();
	}

	/**
	 * Checks if the transaction exists in the DB
	 * 
	 * @param t
	 *            Transaction to check
	 * @return True if transaction found, false otherwise
	 * @throws SQLException
	 *             If a database error occurs
	 */
	private boolean isValidTransaction(Transaction t) throws SQLException {
		// Check null
		if (t == null)
			return false;

		// Prepare sql query
		String sqlQuery = "SELECT transaction.id, transaction.isIncome, "
				+ "transaction.value, transaction.date, "
				+ "transaction.description, transaction.location, "
				+ "repeating.type, category.name, transaction.account_id "
				+ "FROM transaction " + "LEFT JOIN repeating "
				+ "ON repeating.id = transaction.repeating_id "
				+ "LEFT JOIN category "
				+ "ON category.id = transaction.category_id "
				+ "WHERE transaction.id = ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, t.getId());

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();

		// If results not found, return false
		if (!result.next()) {
			statement.close();
			result.close();
			return false;
		}

		// Otherwise create transaction with result
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(result.getDate("date").getTime());
		Repeating repeating = Repeating.toRepeating(result.getString("type"));

		Transaction tempTrans = null;
		try {
			tempTrans = new Transaction(result.getInt("id"),
					result.getBoolean("isIncome"), result.getDouble("value"),
					cal, result.getString("description"),
					result.getString("location"), repeating,
					result.getString("name"), result.getInt("account_id"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} finally {
			statement.close();
			result.close();
		}

		// Compare extracted transaction to given one
		// And return result
		return (t.equals(tempTrans));
	}

	private int getCategoryID(String category, int accountID)
			throws SQLException {
		// Prepare sql query
		String sqlQuery = "SELECT id " + "FROM category "
				+ "WHERE name LIKE ? " + "AND account_id = ?";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, category);
		statement.setInt(2, accountID);

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();

		// If results found, retrieve and return id
		// Otherwise return 0;
		if (result.next()) {
			int id = result.getInt("id");

			statement.close();
			result.close();
			return id;
		}

		statement.close();
		result.close();
		return 0;
	}

	private int getRepeatingID(Repeating repeating) throws SQLException {
		// Check null
		if (repeating == null)
			return 0;

		// Prepare sql query
		String sqlQuery = "SELECT id " + "FROM repeating "
				+ "WHERE type LIKE ? ";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, repeating.name());

		// Execute and retrieve result
		ResultSet result = statement.executeQuery();

		// If results found, retrieve and return id
		// Otherwise return 0;
		if (result.next()) {
			int id = result.getInt("id");

			statement.close();
			result.close();
			return id;
		}

		statement.close();
		result.close();
		return 0;
	}

	private void createCategory(String category, int accountID)
			throws SQLException {
		// Prepare sql query
		String sqlQuery = "INSERT INTO category (name, account_id) "
				+ "VALUES (?, ?)";

		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, category);
		statement.setInt(2, accountID);

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}
}
