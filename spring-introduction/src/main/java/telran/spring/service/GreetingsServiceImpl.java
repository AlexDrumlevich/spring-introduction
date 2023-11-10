package telran.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;


@Service
@Slf4j
public class GreetingsServiceImpl implements GreetingsService {
    Map<Long, Person> greetingsMap = new HashMap<>();
	
    @Override
	public String getGreetings(long id) {
    	log.debug("method getGreetings of class GreetingsServiceImpl, received id {}", id);
		Person person =  greetingsMap.get(id);
		log.debug("method getGreetings of class GreetingsServiceImpl, got from greetingsMap Person {}", person);
		String name = person == null ? "Unknown guest" : person.name();
		String result = "Hello, " + name;
		log.debug("method getGreetings of class GreetingsServiceImpl, returned Strung {}", result);
		return result;
	}
	
	@Override
	public Person getPerson(long id) {
		log.debug("method getPerson of class GreetingsServiceImpl, received id {}", id);
		Person result = greetingsMap.get(id);
		log.debug("method getPerson of class GreetingsServiceImpl, returned Person {}", result);
		if(result == null) {
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		return result;
	}
	
	@Override
	public List<Person> getPersonsByCity(String city) {
		log.debug("method getPersonsByCity of class GreetingsServiceImpl, received String city {}", city);
		List<Person> persons = greetingsMap.values().stream()
				.filter(p -> p.city().equals(city))
				.toList();
		log.debug("method getPersonsByCity of class GreetingsServiceImpl, returned Persons {}", persons);
		return persons;
		
	}
	@Override
	public Person addPerson(Person person) {
		log.debug("method addPerson of class GreetingsServiceImpl, received Person {}", person);
		long id = person.id();
		if (greetingsMap.containsKey(id) ){
			throw new IllegalStateException(String.format("person with id %d already exists", id));
		}
		Person putResult = greetingsMap.put(id, person);
		log.debug("method addPerson of class GreetingsServiceImpl, result of put method greetingsMap Person {}", putResult);
		log.debug("method addPerson of class GreetingsServiceImpl, returned Person {}", person);
		return person;
	}
	
	@Override
	public Person deletePerson(long id) {
		log.debug("method addPerson of class GreetingsServiceImpl, received id {}", id);
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		Person result = greetingsMap.remove(id);
		log.debug("method deletePerson of class GreetingsServiceImpl, returned Person {}", result);
		return result;
	}
	
	@Override
	public Person updatePerson(Person person) {
		log.debug("method updatePerson of class GreetingsServiceImpl, received Person {}", person);
		long id = person.id();
		if (!greetingsMap.containsKey(id) ){
			throw new NotFoundException(String.format("person with id %d doesn't exist", id));
		}
		Person putResult = greetingsMap.put(id, person);
		log.debug("method updatePerson of class GreetingsServiceImpl, result of put method greetingsMap Person {}", putResult);
		return person;
	}

}


