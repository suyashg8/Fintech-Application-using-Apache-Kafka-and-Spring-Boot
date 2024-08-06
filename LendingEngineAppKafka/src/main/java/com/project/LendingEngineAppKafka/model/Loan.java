package com.project.LendingEngineAppKafka.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Loan {

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private User borrower;
	@ManyToOne
	private User lender;
	@OneToOne(cascade= CascadeType.ALL)
	private Money loanAmount;
	private double interestRate;
	private LocalDate dateLent;
	private LocalDate dateDue;
	@OneToOne(cascade= CascadeType.ALL)
	private Money amountRepayed;
	private Status status;
	
	
	public Loan() {
		
	}
	
	public Loan(User lender,LoanApplication loanApplication)
	{
		this.borrower=loanApplication.getBorrower();
		this.lender=lender;
		this.loanAmount=loanApplication.getAmount();
		this.interestRate=loanApplication.getInterestRate();
		this.dateLent= LocalDate.now();
		this.dateDue= LocalDate.now().plusDays(loanApplication.getRepaymentTermInDays());
		this.amountRepayed= new Money(0,Currency.USD);;
		this.status=Status.ONGOING;
	}

	public long getId() {
		return id;
	}

	public User getBorrower() {
		return borrower;
	}

	public User getLender() {
		return lender;
	}

	public Money getAmountRepayed() {
		return amountRepayed;
	}

	public Money getAmountOwed() {
		return loanAmount.times(1+interestRate/100d).minus(amountRepayed);
	}
	
	public void repay(final Money money) {
		borrower.withDraw(money);
		lender.topUp(money);
		amountRepayed=amountRepayed.add(money);
		
		if(getAmountOwed().equals(new Money(0,Currency.USD)))
		{
			status=Status.COMPLETED;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountRepayed, borrower, dateDue, dateLent, id, interestRate, lender, loanAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loan other = (Loan) obj;
		return Objects.equals(amountRepayed, other.amountRepayed) && Objects.equals(borrower, other.borrower)
				&& Objects.equals(dateDue, other.dateDue) && Objects.equals(dateLent, other.dateLent) && id == other.id
				&& Double.doubleToLongBits(interestRate) == Double.doubleToLongBits(other.interestRate)
				&& Objects.equals(lender, other.lender) && Objects.equals(loanAmount, other.loanAmount);
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", borrower=" + borrower + ", lender=" + lender + ", loanAmount=" + loanAmount
				+ ", interestRate=" + interestRate + ", dateLent=" + dateLent + ", dateDue=" + dateDue
				+ ", amountRepayed=" + amountRepayed + "]";
	}	
	
	
	
}
