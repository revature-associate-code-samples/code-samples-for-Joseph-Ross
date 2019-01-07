package com.ex.pojos;

public class ReimbursementEntry {
	
	private int reimbId;
	private double amount;
	private String timeSubmitted ;
	private String timeResolved;
	private String desc;
	private String username;
	private String type;
	private String status;
	private int userId;
	private int resolverId;
	private int statusId;
	private int reimbTypeId;
	
	public ReimbursementEntry() {};
	
	public ReimbursementEntry(
			int reimbId,
			double amount,
			String timeSubmitted,
			String timeResolved,
			String desc,
			String username,
			String type,
			String status,
			int userId,
			int resolverId,
			int statusId,
			int reimbTypeId
	)
	{
		this.reimbId = reimbId;
		this.amount = amount;
		this.timeSubmitted = timeSubmitted;
		this.timeResolved = timeResolved;
		this.desc = desc;
		this.username = username;
		this.type = type;
		this.status = status;
		this.userId = userId;
		this.resolverId = resolverId;
		this.statusId = statusId;
		this.reimbTypeId = reimbTypeId;
	}
	

	@Override
	public String toString() {
		return "ReimbursementEntry [reimbId=" + reimbId + ", amount=" + amount + ", timeSubmitted=" + timeSubmitted
				+ ", timeResolved=" + timeResolved + ", desc=" + desc + ", username=" + username + ", type=" + type
				+ ", status=" + status + ", userId=" + userId + ", resolverId=" + resolverId + ", statusId=" + statusId
				+ ", reimbTypeId=" + reimbTypeId + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getReimbId() {
		return reimbId;
	}
	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTimeSubmitted() {
		return timeSubmitted;
	}
	public void setTimeSubmitted(String timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}
	public String getTimeResolved() {
		return timeResolved;
	}
	public void setTimeResolved(String timeResolved) {
		this.timeResolved = timeResolved;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getResolverId() {
		return resolverId;
	}
	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getReimbTypeId() {
		return reimbTypeId;
	}
	public void setReimbTypeId(int reimbTypeId) {
		this.reimbTypeId = reimbTypeId;
	}
}
