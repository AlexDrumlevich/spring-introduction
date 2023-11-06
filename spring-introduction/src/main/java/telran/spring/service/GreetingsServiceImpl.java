package telran.spring.service;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
@Service
public class GreetingsServiceImpl implements GreetingsService {
    
	//GREETING
	   Map<Long, String> greetingsMap = new HashMap<>(Map.of(123l, "David", 124l, "Sara",125l, "Rivka"));
	
	@Override
	public String getGreetings(long id) {	
		String name =  greetingsMap.getOrDefault(id, "Unknown Guest");
		return "Hello, " + name;
	
	}
	
	
	//ID NAME
	
	@Override
	public String addName(IdName idName) {
		String name = greetingsMap.putIfAbsent(idName.id(), idName.name());
		if(name != null) {
			throw new IllegalStateException(idName.id() + " already exists");
		}
		return idName.name();
	}
	
	@Override
	public String deleteName(long id) {
		String name = greetingsMap.remove(id);
		if(name == null) {
			throw new IllegalStateException(id + " not found");
		}
		return name;
	}
	
	@Override
	public String updateName(IdName idName) {
		if(!greetingsMap.containsKey(idName.id())) {
			throw new IllegalStateException(idName.id() + " not found");
		}
		greetingsMap.put(idName.id(), idName.name());
		return idName.name();
	}

	
	
	//PERSON
	
	
	 private Map<Long, Person> personsByIdMap = new HashMap<>();
	 private Map<String, Set<Person>> personsByCityMap = new HashMap<>();
	
	 
	@Override
	public Person getPerson(long id) {
		// TODO
		return personsByIdMap.get(id);
	}

	@Override
	public List<Person> getPersonsByCity(String city) {
		// TODO
		Set<Person> persons = personsByCityMap.get(city); 
		return persons == null ? null : new ArrayList<Person>(personsByCityMap.get(city));
	}

	@Override
	public Person addPerson(Person person) {
		// TODO
		Person putResult = personsByIdMap.putIfAbsent(person.id(), person);
		if (putResult == null) {
			personsByCityMap.computeIfAbsent(person.city(), k -> new HashSet<Person>()).add(person);
		}
		return putResult == null ? personsByIdMap.get(person.id()) : null;
	}

	@Override
	public Person deletePerson(long id) {
		// TODO
		Person removedPerson = personsByIdMap.remove(id);
		if(removedPerson != null) {
			personsByCityMap.get(removedPerson.city()).remove(removedPerson);
			if(personsByCityMap.get(removedPerson.city()).isEmpty()) {
				personsByCityMap.remove(removedPerson.city());
			}
		}
		return removedPerson;
	}

	@Override
	public Person updatePerson(Person person) {
		// TODO
		Person updatedPerson = null;
		if(personsByIdMap.containsKey(person.id())) {
			Person previousPerson = personsByIdMap.put(person.id(), person);
			updatedPerson = personsByIdMap.get(person.id());
			personsByCityMap.get(previousPerson.city()).remove(previousPerson);
			if(personsByCityMap.get(previousPerson.city()).isEmpty() && !previousPerson.city().equals(updatedPerson.city())) {
				personsByCityMap.remove(previousPerson.city());
			}
			personsByCityMap.computeIfAbsent(updatedPerson.city(), k -> new HashSet<Person>()).add(updatedPerson);
			
		}
		return updatedPerson;
	}
	
	

}


