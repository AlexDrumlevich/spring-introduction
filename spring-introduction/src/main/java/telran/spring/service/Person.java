package telran.spring.service;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



public record Person(
		@NotNull(message = "id must be not null") long id, 
		@NotNull @Pattern(regexp = "[A-Z][a-z]{2,}", message = "Wrong name structure") String name,
		@NotBlank String city, 
		@Email @NotNull String email,
		@Pattern(regexp = "(\\+972-?|0)5\\d-?\\d{7}", message="not Israel mobile phone") @NotEmpty String phone
		) implements Serializable {}

