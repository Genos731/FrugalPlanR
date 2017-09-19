package backend;

import container.Transaction;
import intermediate.TransactionCommunicator;

public class TransactionCommFake implements TransactionCommunicator {

	@Override
	public boolean put(Transaction t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(Transaction t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Transaction t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

}
