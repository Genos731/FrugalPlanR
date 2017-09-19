package backend;

import container.Account;
import intermediate.AccountCommunicator;

public class AccountCommFake implements AccountCommunicator {

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
		// TODO Auto-generated method stub
		return null;
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
