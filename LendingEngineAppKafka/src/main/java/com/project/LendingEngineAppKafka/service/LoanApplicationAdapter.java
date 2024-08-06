package com.project.LendingEngineAppKafka.service;


import com.project.LendingEngineAppKafka.exception.UserNotFoundException;
import com.project.LendingEngineAppKafka.model.LoanApplication;
import com.project.LendingEngineAppKafka.model.LoanRequest;
import com.project.LendingEngineAppKafka.model.User;
import com.project.LendingEngineAppKafka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;


@Component
public class LoanApplicationAdapter {
	
	private final UserRepository userRepository;
	
	@Autowired
	public LoanApplicationAdapter(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public LoanApplication transform(LoanRequest req, User borrower)
	{
		Optional<User> userOptional = userRepository.findById(borrower.getUsername());
		
		if(userOptional.isPresent())
		{
			System.out.println("ji"+Duration.ofDays(req.getDaysToRepay())+"as"+req.toString());
			return new LoanApplication(req.getAmount(),userOptional.get(),req.getDaysToRepay(),req.getInterestRate());
		}
		else {
			throw new UserNotFoundException(borrower.getUsername());
			
		}
		
	}

}
