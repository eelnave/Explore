package edu.byui.cit.collections;

public class Account {
	private String accountNumber;
	private String accountType;
	private String givenName;
	private String familyName;

	public Account(String accountNumber, String accountType,
					String givenName, String familyName) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.givenName = givenName;
		this.familyName = familyName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	@Override
	public String toString() {
		return accountNumber + " " + accountType + ": "
				+ givenName + " " + familyName;
	}
}
