package telran.spring.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;


@Service
@Slf4j
public class GreetingsServiceImpl implements GreetingsService {
	Map<Long, Person> greetingsMap = new HashMap<>();

	//PROPERTIES
	@Value("${app.greeting.message:Hello}")
	String greetingMessage;
	@Value("${app.unknown.name:unknown guest}")
	String unknownName;
	@Value("${app.file.name:persons.data}")
	String fileName;


	@Override
	public String getGreetings(long id) {
		log.debug("method getGreetings of class GreetingsServiceImpl, received id {}", id);
		Person person =  greetingsMap.get(id);
		log.debug("method getGreetings of class GreetingsServiceImpl, got from greetingsMap Person {}", person);

		String name = "";
		if (person == null) {
			name = unknownName;
			log.warn("person with id {} not found", id);
		} else {
			name = person.name();
			log.debug("person name is {}", name);
		}

		String result = String.format("%s, %s", greetingMessage, name);
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


	@Override
	public void save(String fileName) {
		// TODO saving persons data into ObjectOutputStream
		try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			objectOutputStream.writeObject(greetingsMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("persons data have been saved");

	}

	@Override
	public void restore(String fileName) {
		// TODO restoring from file using ObjectInputStream
		// TODO saving persons data into ObjectOutputStream
		try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
		 greetingsMap =	(Map<Long, Person>) objectInputStream.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("restored from file");

	}
	@PostConstruct
	void restoreFromFile() {
		restore(fileName);

	}
	@PreDestroy
	void saveToFile() {
		save(fileName);
	}


}


