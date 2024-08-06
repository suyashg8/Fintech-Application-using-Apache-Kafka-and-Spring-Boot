package com.project.LendingEngineAppKafka.model;


import jakarta.persistence.*;
import lombok.Setter;

import java.util.Objects;



@Entity
@Setter
public final class User {
    @Id
	@Column(name = "userName")
	private String username;
	private String firstName;
	private String lastName;
	private int age;
	private String occupation;
	@OneToOne(cascade= CascadeType.ALL)
	private Balance balance;
	
	public User() {
		
	}
	

	public User(String user_name, String firstName, String lastName, int age, String occupation, Balance balance) {
		super();
		this.username = user_name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.occupation = occupation;
		this.balance = balance;
	}




	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getOccupation() {
		return occupation;
	}

	
	public void topUp(final Money money) {
		balance.topUp(money);
	}
	
	public void withDraw(final Money money) {
		balance.withdraw(money);
	}
	
	public Balance getBalance() {
		return balance;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(age, firstName, lastName, occupation, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return age == other.age && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(occupation, other.occupation)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", occupation=" + occupation + "]";
	}
	
	
	
}
