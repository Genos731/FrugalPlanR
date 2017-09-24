package backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import container.Account;
import intermediate.AccountCommunicator;

public class AccountImpl implements AccountCommunicator {
	Connection dbConnection;
	
	public AccountImpl() {
		dbConnection = FrugalDBConnection.getConnection();
	}

	@Override
	public boolean create(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Account get(String name) {
		String password = null;
		String email = null;
		
		String sqlQuery = "SELECT name, password, email "
				+ "FROM account "
				+ "WHERE name LIKE ?";
		
		try {
			PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
			statement.setString(1, name);
			ResultSet result = statement.executeQuery();
			
			if (result.next()) {
				name = result.getString("name");
				password = result.getString("password");
				email = result.getString("email");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		Account account = new Account(name, password, email);
		return account;
	}

	@Override
	public boolean edit(Account old, Account neww) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void logout(Account a) {
		// TODO Auto-generated method stub
		
	}

}
