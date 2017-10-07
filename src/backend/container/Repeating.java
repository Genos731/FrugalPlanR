package backend.container;

public enum Repeating {
	DAILY, 
	WEEKLY, 
	FORTNIGHTLY, 
	MONTHLY;
	
	public static Repeating toRepeating(String s) {
		switch (s.toLowerCase()) {
		case ("daily"):
			return DAILY;
		case ("weekly"):
			return WEEKLY;
		case ("fortnightly"):
			return FORTNIGHTLY;
		case ("monthly"):
			return MONTHLY;
		default:
			return null;
		}
	}
}
