package intermediate;

import container.Account;

public interface AccountCommunicator {
	// FIXUP
	public boolean create(Account a);
	public boolean login(Account a);
	public Account get(String name);
	public boolean edit(Account old, Account neww);
	public void logout(Account a);
}
