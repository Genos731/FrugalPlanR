package backend.accessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.container.Account;
import backend.database.FrugalDBConnection;
import backend.exception.DuplicateEmailException;
import backend.exception.DuplicateUsernameException;
import backend.exception.InvalidAccountException;
import backend.exception.InvalidEmailException;

public class AccountAccessorImpl implements AccountAccessor {

	private static final int MAX_STRING = 45;

	Connection dbConnection;

	public AccountAccessorImpl() {
		dbConnection = FrugalDBConnection.getConnection();
	}

	@Override
	public void create(String username, String password, String email) throws SQLException, IllegalArgumentException, InvalidEmailException, DuplicateUsernameException, DuplicateEmailException {
		// Checks if variables are correct length
		if (!isValidLength(username)) {
			throw new IllegalArgumentException(username + " length is too long (" + username.length() + "), should be less than " + MAX_STRING);
		}
		else if (!isValidLength(password)) {
			throw new IllegalArgumentException(password + " length is too long (" + password.length() + "), should be less than " + MAX_STRING);
		}
		else if (!isValidLength(email)) {
			throw new IllegalArgumentException(email + " length is too long (" + email.length() + "), should be less than " + MAX_STRING);
		}

		// If email is not valid, throw error
		if (!isValidEmail(email))
			throw new InvalidEmailException(email + " is syntactically invalid");

		// Find if username or email already exists
		String sqlQuery = "SELECT username, email "
				+ "FROM account "
				+ "WHERE username LIKE ? OR email LIKE ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, username);
		statement.setString(2, email);
		ResultSet result = statement.executeQuery();

		// If result finds a match
		// Throw duplicate error
		if (result.next()) {
			String resultUsername = result.getString("username");

			if (resultUsername.toLowerCase().equals(username.toLowerCase())) {
				result.close();
				throw new DuplicateUsernameException(username + " already exists");
			}
			else {
				statement.close();
				result.close();
				throw new DuplicateEmailException(email + " already exists");
			}
		}
		statement.close();
		result.close();

		// No errors, insert into DB
		sqlQuery = "INSERT INTO account (username, password, email) "
				+ "VALUES (?, ?, ?)";
		statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, username);
		statement.setString(2, password);
		statement.setString(3, email);

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void delete(Account a) throws SQLException, InvalidAccountException {
		// If account does not exists, do nothing
		if (!isValidAccount(a))
			throw new InvalidAccountException();

		// Receive account variable
		int id = a.getID();

		// Prepare SQL
		String sqlQuery = "DELETE FROM account "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setInt(1, id);

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public Account getAccount(String username) throws SQLException {
		// If username is too long, invalid username return null
		if (!isValidLength(username))
			return null;
		
		// Prepare required account variables
		String password;
		String email;
		int id;

		// Prepare SQL line
		String sqlQuery = "SELECT * "
				+ "FROM account "
				+ "WHERE username LIKE ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, username);

		// Execute SQL query
		ResultSet result = statement.executeQuery();

		// If result found, set variables
		// Otherwise, return null
		if (result.next()) {
			id = result.getInt("id");
			username = result.getString("username");
			password = result.getString("password");
			email = result.getString("email");
			statement.close();
			result.close();
		}
		else {
			statement.close();
			result.close();
			return null;
		}

		// Create account & return it
		Account account = new Account(id, username, password, email);
		return account;
	}

	@Override
	public void updatePassword(Account a, String newPassword) throws SQLException, IllegalArgumentException {
		// If account does not exists, do nothing
		if (!isValidAccount(a))
			return;
		
		// If password is too long, throw error
		if (!isValidLength(newPassword))
			throw new IllegalArgumentException(newPassword + " length is too long (" + newPassword.length() + "), should be less than " + MAX_STRING);

		// Prepare SQL line
		String sqlQuery = "UPDATE account "
				+ "SET password = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, newPassword);
		statement.setInt(2, a.getID());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}

	@Override
	public void updateEmail(Account a, String newEmail) throws SQLException, InvalidEmailException {
		// If account does not exists, do nothing
		if (!isValidAccount(a))
			return;
		
		// Throw if email is syntactically invalid
		if (!isValidEmail(newEmail))
			throw new InvalidEmailException(newEmail + " is invalid");

		// Prepare SQL line
		String sqlQuery = "UPDATE account "
				+ "SET email = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, newEmail);
		statement.setInt(2, a.getID());

		// Execute, throws error if failed
		statement.executeUpdate();
		statement.close();
	}
	
	@Override
	public void close() throws SQLException {
		dbConnection.close();
	}

	@Override
	public boolean isValidAccount(Account a) {
		Account otherAccount;
		try {
			otherAccount = getAccount(a.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return a.equals(otherAccount);
	}

	/**
	 * Check if email is a syntactically valid
	 * @param email Email string
	 * @return true if email is valid, false otherwise
	 */
	private boolean isValidEmail(String email) {
		if (!isValidLength(email))
			return false;
		
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Return true if s is smaller than MAX_STRING
	 * @param s String to check
	 * @return true if s is smaller than MAX_STRING
	 */
	private boolean isValidLength(String s) {
		if (s.length() > MAX_STRING)
			return false;
		return true;
	}

}
