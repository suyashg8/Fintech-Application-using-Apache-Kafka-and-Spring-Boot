package com.project.LendingEngineAppKafka.repository;


import com.project.LendingEngineAppKafka.model.Loan;
import com.project.LendingEngineAppKafka.model.Status;
import com.project.LendingEngineAppKafka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan,Long> {

	
	List<Loan> findAllByBorrowerAndStatus(User borrower, Status status);
	List<Loan> findAllByLenderAndStatus(User lender,Status status);
	Optional<Loan> findOneByIdAndBorrower(long id, User borrower);
	
}
