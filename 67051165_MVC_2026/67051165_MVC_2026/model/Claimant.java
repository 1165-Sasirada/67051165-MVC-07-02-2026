package model;

public class Claimant {
	public String id;
	public String name;
	public String surname;
	public String email;
	public double income;
	public String type; // LOW, GENERAL, HIGH

	public Claimant(String id, String name, String surname, String email, double income, String type) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.income = income;
		this.type = type;
	}
}
