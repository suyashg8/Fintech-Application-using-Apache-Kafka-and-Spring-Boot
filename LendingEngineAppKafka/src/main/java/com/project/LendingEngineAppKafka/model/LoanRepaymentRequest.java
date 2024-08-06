package com.project.LendingEngineAppKafka.model;



import java.util.Objects;

public final class LoanRepaymentRequest {

	private final double amount;
	private final long loanId;
	
	
	public LoanRepaymentRequest(double amount, long loanId) {
		super();
		this.amount = amount;
		this.loanId = loanId;
	}


	public Money getAmount() {
		return new Money(amount,Currency.USD);
	}


	public long getLoanId() {
		return loanId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(amount, loanId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoanRepaymentRequest other = (LoanRepaymentRequest) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && loanId == other.loanId;
	}


	@Override
	public String toString() {
		return "LoanRepaymentRequest [amount=" + amount + ", loanId=" + loanId + "]";
	}
	
	
}
