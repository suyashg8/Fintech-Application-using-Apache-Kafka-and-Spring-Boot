package com.project.ClientRequestAppKafka.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

	 private String username;
	 private String password;
	 private String firstName;
	 private String lastName;
	 private int age;
	 private String occupation;
	 private Balance balance;
}