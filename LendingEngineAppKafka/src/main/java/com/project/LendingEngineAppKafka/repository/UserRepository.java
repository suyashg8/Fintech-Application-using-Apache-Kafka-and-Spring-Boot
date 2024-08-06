package com.project.LendingEngineAppKafka.repository;


import com.project.LendingEngineAppKafka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
