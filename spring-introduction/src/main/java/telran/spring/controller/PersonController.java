package telran.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.spring.service.GreetingsService;
import telran.spring.service.Person;

@RestController
@RequestMapping("person")
@RequiredArgsConstructor
public class PersonController {

	final private GreetingsService greetingsService;
	
	@GetMapping("id:{id}")
	Person getPerson(@PathVariable long id) {
		return greetingsService.getPerson(id);
	}
	@GetMapping("city:{city}")
	List<Person> getPerson(@PathVariable String city) {
		return greetingsService.getPersonsByCity(city);
	}

	@PostMapping
	Person addPerson(@RequestBody Person person) {
		return greetingsService.addPerson(person);
	}
	
	@DeleteMapping("{id}")
	Person deletePerson(@PathVariable long id) {
		return greetingsService.deletePerson(id);
	}

	@PutMapping
	Person updatePerson(@RequestBody Person person) {
		return greetingsService.updatePerson(person);
	}
}
