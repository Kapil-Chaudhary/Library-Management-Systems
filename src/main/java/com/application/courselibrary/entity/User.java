package com.application.courselibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 5, max = 20)
    @NotBlank
    @NotNull
    private String name;

    @Email(message = "Email should be valid!!")
    @NotBlank(message = "Email must required!!")
    @Column(unique = true, nullable = false, name = "Email is already taken")
    private String email;

    // other properties if we want
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone number is not valid")
    private String phone;

    @Min(value = 5, message = "Age should be a positive number")
    private Integer age;


    // A User can initiate Transaction.
    // A User can have multiple Books issued through transactions.
    @OneToMany
    private List<Book> books = new ArrayList<>();

}
