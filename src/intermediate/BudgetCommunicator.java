package intermediate;

import container.Budget;

public interface BudgetCommunicator {
	// FIXUP
	public boolean create(Budget b);
	public boolean delete(Budget b);
	public boolean edit(Budget old, Budget neww);
	public String get(String name);
}
