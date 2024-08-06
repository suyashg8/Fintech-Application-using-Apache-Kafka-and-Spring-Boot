package com.project.ClientRequestAppKafka.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="User_Data")
public class UserData {

    @Id
    @Column(name = "user_name")
    @NotNull(message = "name cannot be null")
    private String user_name;

    @Pattern(regexp = "[a-zA-Z]*")
    @Column(name = "user_password")
    @NotNull(message = "password cannot be null")
    private String user_password;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]*")
    private String firstName;

    @Pattern(regexp = "[a-zA-Z]*")
    @NotNull
    private String lastName;

    @Range(min = 0,max = 55,message = "age should be in range")
    private int age;

    @Pattern(regexp = "[a-zA-Z]*")
    private String occupation;
}
