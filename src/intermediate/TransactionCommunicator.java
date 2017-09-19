package intermediate;

import container.Transaction;

public interface TransactionCommunicator {
	// FIXUP
	public boolean put(Transaction t);
	public boolean edit(Transaction t);
	public boolean delete(Transaction t);
	public Transaction getTransaction();
}
