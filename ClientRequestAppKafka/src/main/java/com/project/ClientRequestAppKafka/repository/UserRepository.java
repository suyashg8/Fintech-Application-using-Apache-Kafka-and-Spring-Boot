package com.project.ClientRequestAppKafka.repository;

import com.project.ClientRequestAppKafka.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserData,String> {
}
