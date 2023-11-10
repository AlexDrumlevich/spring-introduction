package telran.spring.service;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



public record Person(
		@Digits(fraction = 0, integer = 10, message = "wrong id format") @NotNull(message = "id must be not null") long id, 
		@Pattern(regexp = "[A-Z][a-z]{2,}", message = "Wrong name format, name should start from uppercased letter and has at least three letters") String name,
		@NotBlank(message = "city must not be null and must contain at least one non-whitespace character") String city,
		@NotNull(message = "email wrong format") @Email(message = "email wrong format") String email,
		@NotNull(message = "phone has wrong format, phone must start from 0 and have 10 figures") @Pattern(regexp = "0\\d{9}", message = "phone has wrong format, phone must start from 0 and have 10 figures") String phone) {

}
