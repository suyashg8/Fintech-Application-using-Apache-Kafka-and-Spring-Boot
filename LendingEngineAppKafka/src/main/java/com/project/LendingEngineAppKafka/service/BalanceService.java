package com.project.LendingEngineAppKafka.service;


import com.project.LendingEngineAppKafka.exception.UserNotFoundException;
import com.project.LendingEngineAppKafka.model.Money;
import com.project.LendingEngineAppKafka.model.User;
import com.project.LendingEngineAppKafka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BalanceService {

	private final UserRepository userRepository;

	@Autowired
	public BalanceService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Transactional 
	public void topUpBalance(final Money money, String authToken) {
		User user = findUser(authToken);
		user.topUp(money);
	}
	
	@Transactional 
	public void withdrawFromBalance(final Money money,String authToken) {
		User user = findUser(authToken);
		user.withDraw(money);
	}
	
	private User findUser(String authToken) {
		return userRepository.findById(authToken).orElseThrow(() -> new UserNotFoundException(authToken));
	}
	
}
