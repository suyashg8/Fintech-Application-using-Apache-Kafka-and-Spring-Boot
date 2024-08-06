package com.project.LendingEngineAppKafka.repository;


import com.project.LendingEngineAppKafka.model.LoanApplication;
import com.project.LendingEngineAppKafka.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Long> {

	List<LoanApplication> findAllByStatusEquals(Status status );
	
}
