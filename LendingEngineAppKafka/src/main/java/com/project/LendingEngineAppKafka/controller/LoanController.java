package com.project.LendingEngineAppKafka.controller;


import com.project.LendingEngineAppKafka.model.*;
import com.project.LendingEngineAppKafka.service.TokenValidationService;
import com.project.LendingEngineAppKafka.repository.LoanApplicationRepository;
import com.project.LendingEngineAppKafka.service.LoanApplicationAdapter;
import com.project.LendingEngineAppKafka.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class LoanController {
	
	private final LoanApplicationRepository loanApplicationRepository;
	
	private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;
    
	@Autowired
	public LoanController(LoanApplicationRepository loanApplicationRepository,LoanApplicationAdapter loanApplicationAdapter,LoanService loanService, TokenValidationService tokenValidationService) {
		super();
		this.loanApplicationRepository = loanApplicationRepository;
		
		this.loanApplicationAdapter = loanApplicationAdapter;
		this.loanService = loanService;
		this.tokenValidationService = tokenValidationService;
	}



	@PostMapping(value="/loan/request")
	public void requestLoan(@RequestBody final LoanRequest loanRequest, HttpServletRequest request )
	{
		
		User borrower= tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
		LoanApplication loanApplication=loanApplicationAdapter.transform(loanRequest,borrower);
		loanApplicationRepository.save(loanApplication);
	}
	
	
	
	
	@GetMapping(value="/loan/requests")
	public List<LoanApplication> findAllLoanApplications(HttpServletRequest request){
		tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
		return loanApplicationRepository.findAllByStatusEquals(Status.ONGOING);
		
	}
	
	
	@GetMapping(value="/loan/{status}/borrowed")
	public List<Loan> findBorrowedLoans(@RequestHeader String authorization, @PathVariable Status status)
	{
		User borrower= tokenValidationService.validateTokenAndGetUser(authorization);
		return loanService.findAllBorrowedLoans(borrower,status);
	}
	
	
	@GetMapping(value="/loan/{status}/lent")
	public List<Loan> findLentLoans(@RequestHeader String authorization,@PathVariable Status status)
	{
		User lender= tokenValidationService.validateTokenAndGetUser(authorization);
		return loanService.findAllLentLoans(lender,status);
	}
	
	
	@PostMapping(value="/loan/repay")
	public void repayLoan(@RequestBody LoanRepaymentRequest request, @RequestHeader String authorization){
		User borrower= tokenValidationService.validateTokenAndGetUser(authorization);
		loanService.repayLoan(request.getAmount(), request.getLoanId(), borrower);
	}
	
	@PostMapping(value="/loan/accept/{loanApplicationId}")
	public void acceptLoan(@PathVariable final String loanApplicationId,HttpServletRequest request){
		User lender=tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
		loanService.acceptLoan(Long.parseLong(loanApplicationId), lender.getUsername());
	}
	
	@GetMapping(value="/loans")
	public List<Loan> getLoans(HttpServletRequest request){
		tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
		return loanService.getLoans();
	} 
	
	              
}
