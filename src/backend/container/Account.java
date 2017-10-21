package backend.container;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import backend.exception.InvalidEmailException;

public class Account {
	private static final int MAX_STRING = 45;
	
	// If id = 0, not yet in database
	private int id;
	private String username;
	private String password;
	private String email;
	
	public Account(int id, String username, String password, String email) throws IllegalArgumentException, InvalidEmailException {
		if (username.length() > MAX_STRING)
				throw new IllegalArgumentException("Username length too long. Must be less than " + MAX_STRING + " characters");
		if (password.length() > MAX_STRING)
			throw new IllegalArgumentException("Password length too long. Must be less than " + MAX_STRING + " characters");
		if (email.length() > MAX_STRING)
			throw new IllegalArgumentException("Email length too long. Must be less than " + MAX_STRING + " characters");
		
		if (username.length() == 0) {
			throw new IllegalArgumentException("Username can't be empty.");
		}
		if (password.length() == 0) {
			throw new IllegalArgumentException("Password can't be empty.");
		}
		if (email.length() == 0) {
			throw new IllegalArgumentException("Email can't be empty.");
		}
		
		if (!isValidEmail(email))
			throw new InvalidEmailException(email + " is syntactically invalid");
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public static int getMaxString() {
		return MAX_STRING;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public static boolean isValidEmail(String email) {
		if (email.length() > MAX_STRING)
			return false;
		
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
}
