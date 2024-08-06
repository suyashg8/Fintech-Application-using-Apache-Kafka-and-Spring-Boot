package com.project.LendingEngineAppKafka.service;


import com.project.LendingEngineAppKafka.exception.LoanApplicationNotFoundException;
import com.project.LendingEngineAppKafka.exception.LoanNotFoundException;
import com.project.LendingEngineAppKafka.exception.UserNotFoundException;
import com.project.LendingEngineAppKafka.model.*;
import com.project.LendingEngineAppKafka.repository.LoanApplicationRepository;
import com.project.LendingEngineAppKafka.repository.LoanRepository;
import com.project.LendingEngineAppKafka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LoanService {
	
	private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
	
    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository,
			LoanRepository loanRepository) {
		super();
		this.loanApplicationRepository = loanApplicationRepository;
		this.userRepository = userRepository;
		this.loanRepository = loanRepository;
	}
	
    @Transactional
    public void repayLoan(final Money amountToRepay, final long loanId, final User borrower) {
    	Loan loan= loanRepository.findOneByIdAndBorrower(loanId, borrower).orElseThrow(LoanNotFoundException::new);
    	
    	Money actualPaidAmount= amountToRepay.getAmount() > loan.getAmountOwed().getAmount() ? 
    			                 loan.getAmountOwed() : amountToRepay;
    	
    	loan.repay(actualPaidAmount);
    	
    }
    
    @Transactional
    public void acceptLoan(final long loanApplicationId, final String lenderUsername)
    {
    	User lender= findUser(lenderUsername);
    	LoanApplication loanApplication= findLoanApplication(loanApplicationId);
        loanRepository.save(loanApplication.acceptLoanApplication(lender));
    }
    
    public List<Loan> findAllBorrowedLoans(User borrower,final Status status)
    {
    	return loanRepository.findAllByBorrowerAndStatus(borrower,status);
    }
    
    public List<Loan> findAllLentLoans(User lender,final Status status){
    	return loanRepository.findAllByLenderAndStatus(lender,status);
    }
    
    public List<Loan> getLoans()
	{
		return loanRepository.findAll();
	}
	
    private User findUser(String lenderUsername) {
    	return userRepository.findById(lenderUsername).orElseThrow(() -> new UserNotFoundException(lenderUsername));
    }
    
    private LoanApplication findLoanApplication(long loanApplicationId) {
    	return loanApplicationRepository.findById(loanApplicationId).orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
    }
}
