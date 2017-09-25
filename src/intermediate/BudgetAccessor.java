package intermediate;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import container.Account;
import container.Budget;
import exception.InvalidAccountException;

public interface BudgetAccessor {
	// FIXUP
	// need to add name to database :))
	public void create(Account a, double goalValue, Date startDate, Date endDate) throws SQLException, IllegalArgumentException, InvalidAccountException;
	public void delete(Budget b) throws SQLException;
	public void updateGoalValue(Budget b, double goalValue) throws SQLException;
	public void updateStartDate(Budget b, Date startDate) throws SQLException, IllegalArgumentException;
	public void updateEndDate(Budget b, Date endDate) throws SQLException, IllegalArgumentException;
	public List<Budget> getBudgets(Account a) throws SQLException;
	public void close() throws SQLException;
}
