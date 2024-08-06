package com.project.LendingEngineAppKafka.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Entity
public final class LoanApplication {
   
	 @Id
	 @GeneratedValue
	 private long id;
	 private int amount;
	 @ManyToOne
	 private User borrower;
     private int repaymentTermInDays;
	 private double interestRate;
	 private Status status;
	 
	 public LoanApplication() {}
	
	public LoanApplication( int amount, User borrower, int repaymentTermInDays, double interestRate) {
		super();
		this.amount = amount;
		this.borrower = borrower;
		this.repaymentTermInDays = repaymentTermInDays;
		this.interestRate = interestRate;
		this.status=Status.ONGOING;
	}
	
	public Loan acceptLoanApplication (final User lender) {
		lender.withDraw(getAmount());
		borrower.topUp(getAmount());
		status=Status.COMPLETED;
		return new Loan(lender, this);
	}
	
	public Money getAmount() {
		return new Money(amount,Currency.USD);
	}
	public User getBorrower() {
		return borrower;
	}
	public int getRepaymentTermInDays() {
		return repaymentTermInDays;
	}
	public double getInterestRate() {
		return interestRate;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LoanApplication [ amount=" + amount + ", borrower=" + borrower + ", repaymentTerm="
				+ repaymentTermInDays + ", interestRate=" + interestRate + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, borrower, id, interestRate, repaymentTermInDays);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanApplication other = (LoanApplication) obj;
		return amount == other.amount && Objects.equals(borrower, other.borrower) && id == other.id
				&& Double.doubleToLongBits(interestRate) == Double.doubleToLongBits(other.interestRate)
				&& repaymentTermInDays == other.repaymentTermInDays;
	}
	
}
