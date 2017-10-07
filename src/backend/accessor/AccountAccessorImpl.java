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
	Connection dbConnection;

	public AccountAccessorImpl() {
		dbConnection = FrugalDBConnection.getConnection();
	}

	@Override
	public void create(String username, String password, String email) throws SQLException, IllegalArgumentException, InvalidEmailException, DuplicateUsernameException, DuplicateEmailException {
		// Test if variables are syntactically correct
		@SuppressWarnings("unused")
		Account tempA = new Account(0, username, password, email);

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
		int id = a.getId();

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
		if (username.length() > Account.getMaxString())
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
		if (newPassword.length() > Account.getMaxString())
			throw new IllegalArgumentException(newPassword + " length is too long (" + newPassword.length() + "), should be less than " + Account.getMaxString());

		// Prepare SQL line
		String sqlQuery = "UPDATE account "
				+ "SET password = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, newPassword);
		statement.setInt(2, a.getId());

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
		if (!Account.isValidEmail(newEmail))
			throw new InvalidEmailException(newEmail + " is invalid");

		// Prepare SQL line
		String sqlQuery = "UPDATE account "
				+ "SET email = ? "
				+ "WHERE id = ?";
		PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
		statement.setString(1, newEmail);
		statement.setInt(2, a.getId());

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
		if (a == null)
			return false;
		
		Account otherAccount;
		try {
			otherAccount = getAccount(a.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return a.equals(otherAccount);
	}

}
