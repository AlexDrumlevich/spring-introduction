package telran.spring.service;

import java.util.List;

public interface GreetingsService {
 String getGreetings(long id);
 
 String addName(IdName idName);
 String deleteName(long id);
 String updateName(IdName idName);

 //TODO add For HW #57
 Person getPerson(long id);
 List<Person> getPersonsByCity(String city);
 
 //TODO update For HW# 57
Person addPerson(Person person);
Person deletePerson(long id);
Person updatePerson(Person person);
}
