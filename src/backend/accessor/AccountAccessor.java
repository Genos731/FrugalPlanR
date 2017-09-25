package backend.accessor;

import java.sql.SQLException;

import backend.container.Account;
import backend.exception.DuplicateEmailException;
import backend.exception.DuplicateUsernameException;
import backend.exception.InvalidAccountException;
import backend.exception.InvalidEmailException;

public interface AccountAccessor extends AutoCloseable {
	
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
	 * Delete an account from the DB
	 * @param a Account to be deleted
	 * @throws SQLException If a database error occurs
	 * @throws InvalidAccountException If the account does not exists
	 */
	public void delete(Account a) throws SQLException, InvalidAccountException;
	
	/**
	 * Receive an account from the DB, given a username
	 * @param name
	 * @return The account requested
	 * @throws SQLException f a database error occurs
	 */
	public Account getAccount(String username) throws SQLException;
	
	/**
	 * Update the password for the account
	 * Does not update account passed
	 * @param a Account to update
	 * @param newPassword New password for account
	 * @throws SQLException If a database error occurs
	 * @throws If newPassword length is too long
	 */
	public void updatePassword(Account a, String newPassword) throws SQLException;
	
	/**
	 * Update the email for the account
	 * Does not update account passed
	 * @param a Account to update
	 * @param newEmail New email for account
	 * @throws SQLException If a database error occurs
	 * @throws InvalidEmailException If email is syntactically invalid
	 */
	public void updateEmail(Account a, String newEmail) throws SQLException, InvalidEmailException;
	
	/**
	 * Checks if the given account matches all variables in the DB
	 * @param a Account to check
	 * @return True if all paramaters match, false otherwise
	 */
	public boolean isValidAccount(Account a);	
}
