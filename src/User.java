
public class User {
	
	/**
	 * String storing the username of the user
	 */
	private String username;
	
	/**
	 * String storing the password of the user
	 */
	private String password;
	
	/**
	 * Constructor initializing the user.
	 * @param user - String username to save
	 * @param pas - String password to save
	 */
	public User(String user, String pas) {
		this.username = user;
		this.password = pas;
	}
	
	/**
	 * @return The user's password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * 
	 * @retur The user's username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Print function to output the original username and password.
	 */
	public void output() {
		System.out.println("Username          :  " + getUsername());
		System.out.println("Original Password :  " + getPassword());
		System.out.println();
	}
}
