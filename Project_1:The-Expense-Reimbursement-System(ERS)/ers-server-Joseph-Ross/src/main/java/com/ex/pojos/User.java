package com.ex.pojos;

public class User {
	
	private int userId;
	private String username;
	private int role;
	private String firstName;
	private String lastName;
	private String email;
	
	public User() {}
	
	public User(
			int userId,
			String username,
			int role,
			String firstName,
			String lastName,
			String email
	)
	{
		this.userId = userId;
		this.username = username;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", role=" + role + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
