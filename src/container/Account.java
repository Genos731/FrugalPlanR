package container;

public class Account {
	String name;
	String password;
	
	// Todo
	public Account(String n, String p){
		name = n;
		password = p;
	}
	
	public String getName(){
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}
}
