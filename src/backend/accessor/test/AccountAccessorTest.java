package backend.accessor.test;

import java.sql.SQLException;

import backend.accessor.AccountAccessor;
import backend.accessor.AccountAccessorImpl;
import backend.container.Account;
import backend.exception.DuplicateEmailException;
import backend.exception.DuplicateUsernameException;
import backend.exception.InvalidAccountException;
import backend.exception.InvalidEmailException;

public class AccountAccessorTest {

	public static void main(String[] args) {
		AccountAccessor accessor = new AccountAccessorImpl();

		// Test create
		try {
			accessor.create("ZeZe", "BooHoo2", "Boogie@hotmail.com");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvalidEmailException e) {
			e.printStackTrace();
		} catch (DuplicateUsernameException e) {
			e.printStackTrace();
		} catch (DuplicateEmailException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Test get
		Account zephyr = null;
		try {
			zephyr = accessor.getAccount("Zephyr");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Test isValidAccount
		if (accessor.isValidAccount(zephyr))
			System.out.println("Valid Account");
		else
			System.out.println("Invalid Account");

		// Test updatePassword
		try {
			accessor.updatePassword(zephyr, "ZingZong");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Test updateEmail
		try {
			accessor.updateEmail(zephyr, "ZoomZoom@email.com");
		} catch (InvalidEmailException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		// Test delete
		try {
			accessor.delete(zephyr);
		} catch (InvalidAccountException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Close accessor
		try {
			accessor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
