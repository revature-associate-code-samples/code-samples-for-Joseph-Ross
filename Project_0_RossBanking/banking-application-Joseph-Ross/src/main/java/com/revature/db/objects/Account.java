package com.revature.db.objects;

public class Account {

	private int accountId;
	private int userId;
	private String accountType;
	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", userId=" + userId + ", accountType=" + accountType
				+ ", accountBalance=" + accountBalance + "]";
	}
	private double accountBalance;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int user_id) {
		this.userId = user_id;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Account(int accountId, String accountType, double accountBalance, int userId) {
		super();
		this.accountId = accountId;
		this.userId = userId;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}
	public Account() { super(); }
}
