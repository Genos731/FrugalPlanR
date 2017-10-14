package backend.container;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaction {
	private static final int DESC_LENGTH = 255;
	private static final int LOC_LENGTH = 45;
	private static final int CAT_LENGTH = 45;
	
	private int id;
	private boolean isIncome;
	private double value;
	private Calendar calendar;
	private String description;
	private String location;
	private Repeating repeating;
	private String category;
	private int accountID;
	
	public Transaction(int id, boolean isIncome, double value, Calendar calendar, String description, String location, Repeating repeating, String category, int accountID) throws IllegalArgumentException {
		if (description.length() > DESC_LENGTH)
			throw new IllegalArgumentException("Description length too long. Must be less than " + DESC_LENGTH + " characters");
		if (location.length() > LOC_LENGTH)
			throw new IllegalArgumentException("Location length too long. Must be less than " + LOC_LENGTH + " characters");
		if (category.length() > LOC_LENGTH)
			throw new IllegalArgumentException("Category length too long. Must be less than " + CAT_LENGTH + " characters");
		
		this.id = id;
		this.isIncome = isIncome;
		this.value = value;
		this.calendar = calendar;
		this.description = description;
		this.location = location;
		this.repeating = repeating;
		this.category = category;
		this.accountID = accountID;
	}

	public static int getDescLength() {
		return DESC_LENGTH;
	}

	public static int getLocLength() {
		return LOC_LENGTH;
	}

	public static int getCatLength() {
		return CAT_LENGTH;
	}

	public int getId() {
		return id;
	}
	
	public double getActualValue(){
		double value = this.value;
		if (!isIncome()){
			value = value*-1;
		}
		return value;
	}

	public boolean isIncome() {
		return isIncome;
	}

	public double getValue() {
		return value;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}

	public Repeating getRepeating() {
		return repeating;
	}

	public String getCategory() {
		return category;
	}

	public int getAccountID() {
		return accountID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		result = prime * result + ((calendar == null) ? 0 : calendar.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + (isIncome ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((repeating == null) ? 0 : repeating.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (accountID != other.accountID)
			return false;
		
		if (calendar == null) {
			if (other.calendar != null)
				return false;
		} else if (!calendar.equals(other.calendar))
			return false;
		
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isIncome != other.isIncome)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (repeating != other.repeating)
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}
	
	public String toString() {
		String s = "";
		s += id + " ";
		s += isIncome + " ";
		s += value + " ";
		s += calendar.toString() + " ";
		s += description + " ";
		s += location + " ";
		s += repeating.name() + " ";
		s += category + " ";
		s += accountID;
		
		return s;
	}
	
	public String getDate() {
		Calendar cal = getCalendar();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(cal.getTime());
 	}
}
