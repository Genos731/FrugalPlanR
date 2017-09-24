package intermediate;

import java.sql.SQLException;

import container.Account;
import exception.DuplicateEmailException;
import exception.DuplicateUsernameException;
import exception.InvalidEmailException;

public interface AccountCommunicator {
	
	/**
	 * Given valid account details, create a new account and place it into the DB
	 * @param a Account to be added
	 * @throws SQLException If a database error occurs
	 * @throws IllegalArgumentException If argument length is too long
	 * @throws InvalidEmailException If email is syntactically invalid
	 * @throws DuplicateUsernameException If username already exists in database
	 * @throws DuplicateEmailException If email already exists in database
	 */
	public void create(String username, String password, String email) throws SQLException, IllegalArgumentException, InvalidEmailException, DuplicateUsernameException, DuplicateEmailException;
	
	/**
	 * Delete an account from the DB, does nothing if account doesn't exist
	 * @param a Account to be deleted
	 * @throws SQLException If a database error occurs
	 */
	public void delete(Account a) throws SQLException;
	
	/**
	 * Receive an account from the DB, given a username
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public Account getAccount(String username) throws SQLException;
	
	/**
	 * Update the password for the account
	 * @param a Account to update
	 * @param newPassword New password for account
	 * @throws SQLException If a database error occurs
	 * @throws If newPassword length is too long
	 */
	public void updatePassword(Account a, String newPassword) throws SQLException;
	
	/**
	 * Update the email for the account
	 * @param a Account to update
	 * @param newEmail New email for account
	 * @throws SQLException If a database error occurs
	 * @throws InvalidEmailException If email is syntactically invalid
	 */
	public void updateEmail(Account a, String newEmail) throws SQLException, InvalidEmailException;
	
	// To discuss with frontend
	// - Should we be able to update username?
	// - What does logout do?
	// - What does login do? (talking about updating iterations of transactions...)
	public boolean login(Account a);
	public void logout(Account a);
}
