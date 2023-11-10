package telran.spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.service.Person;
import telran.spring.service.GreetingsService;
import telran.spring.service.IdName;

@RestController
@RequestMapping("greetings")
@RequiredArgsConstructor
@Slf4j
public class GreetingsController {
	final GreetingsService greetingsService;
	
	@GetMapping("{id}")
	String getGreetings(@PathVariable long id) {
		log.debug("method getGreetings, received id {}", id);
		String result = greetingsService.getGreetings(id);
		log.trace("method getGreetings, returns String {}", result);
		return result;
	}
	
	@PostMapping
	Person addPerson(@RequestBody @Valid Person person) {
		log.debug("method: addPerson, received Person {}", person);
		Person result = greetingsService.addPerson(person);
		log.trace("method: addPerson, returns Person {}", result);
		return result;
	}
	
	@PutMapping
	Person updatePerson(@RequestBody @Valid Person person) {
		log.debug("method: updatePerson, received {}", person);
		Person result = greetingsService.updatePerson(person);
		log.trace("method: updatePerson, returns {}", person);
		return result;
	}
	
	@DeleteMapping("{id}")
	Person deleteName(@PathVariable long id) {
		log.debug("method: deleteName, received id {}", id);
		Person result = greetingsService.deletePerson(id);
		log.trace("method: deleteName, returns Person {}", result);
		return result;
	}
	
	@GetMapping("city/{city}")
	List<Person> getPersonsByCity(@PathVariable String city) {
		log.debug("method: getPersonsByCity, received String city {}", city);
		List<Person> result =  greetingsService.getPersonsByCity(city);
			
		if(result.isEmpty()) {
			log.warn("method: getPersonsByCity returns empty list for city: {}", city);
		} else {
			log.trace("method: getPersonsByCity returns Persons {}", result);
		}
		return result;
	}

	@GetMapping("id/{id}")
	Person getPerson(@PathVariable long id) {
		log.debug("method: getPerson, received id {}", id);
		Person result = greetingsService.getPerson(id);
		log.trace("method: getPersonsByCity returns Person {}", result);
		return result;
	}
	
}
