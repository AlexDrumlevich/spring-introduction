package telran.spring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.writer.UpdaterMapper;
import telran.spring.controller.GreetingsController;
import telran.spring.service.GreetingsService;
import telran.spring.service.Person;

@WebMvcTest
public class GreetingsControllerTest {
    @Autowired //this annotation allows dependency injection inside following field 
	GreetingsController controller;
    @MockBean
    GreetingsService greetingsService;
    @Autowired
    MockMvc mockMvc; //imitator of Web Server
    @Autowired
    ObjectMapper objectMapper;
     
    
    //Application Context Test
    @Test 
     void applicationContextTest() {
    	 assertNotNull(controller);
    	 assertNotNull(greetingsService);
    	 assertNotNull(mockMvc);
    	 assertNotNull(objectMapper);
    	 
     }
    
    //**************************************************
    //ADD PERSON TESTS:
    
    
    Person personNormal = new Person(123, "Vasya", "Rehovot", "vasya@gmail.com",
    		"054-1234567");
    
    Person personWrongPhone = new Person(124, "Vasya", "Rehovot", "vasya@gmail.com",
    		"54-1234567");
    Person personNullPhone = new Person(124, "Vasya", "Rehovot", "vasya@gmail.com",
    		null);

  
    Person personNullCity = new Person(124, "Vasya", null, "vasya@gmail.com",
    		"054-1234567");
    Person personEmptyCity = new Person(124, "Vasya", "", "vasya@gmail.com",
    		"054-1234567");
    Person personEmptySpacesCity = new Person(124, "Vasya", " ", "vasya@gmail.com",
    		"054-1234567");    

    Person personNullName = new Person(123, null, "Rehovot", "vasya@gmail.com",
    		"054-1234567");
    Person personWrongFormatName = new Person(123, "Va", "Rehovot", "vasya@gmail.com",
    		"054-1234567");
    
    Person personNullEmail = new Person(123, "Vasya", "Rehovot", null,
    		"054-1234567");
    Person personWrongFormatEmail = new Person(123, "Vasya", "Rehovot", "vasyagmail.com",
    		"054-1234567");
    

    
    //NORMAL PERSON TEST
     @Test
     void normalFlowAddPerson() throws Exception{
    	 mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNormal)))
    	 .andDo(print()).andExpect(status().isOk());
     }
     
     //WRONG PHONE TESTS
     @Test
     void addPersonWrongPhone() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongPhone)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    	 assertEquals("not Israel mobile phone", response.strip());
     }
     @Test
     void addPersonNullPhone() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNullPhone)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must not be empty", response.strip());
     }
     
     //WRONG CITY TESTS
     @Test
     void addPersonNullCity() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNullCity)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must not be blank", response.strip());
     }
     @Test
     void addPersonEmptyCity() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personEmptyCity)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must not be blank", response.strip());
     }
     @Test
     void addPersonEmptySpacesCity() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personEmptySpacesCity)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must not be blank", response.strip());
     }
     
     //WRONG NAME TESTS
     @Test
     void addPersonNullName() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNullName)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must not be null", response.strip());
     }

     @Test
     void addPersonWrongFormatName() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongFormatName)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("Wrong name format, name should start from uppercased letter and has at least three letters", response.strip());
     }
     
     //WRONG EMAIL TESTS
     @Test
     void addPersonNullEmail() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNullEmail)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must not be null", response.strip());
     }

     @Test
     void addPersonWrongFormatEmail() throws Exception{
    	 String response = mockMvc.perform(post("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personWrongFormatEmail)))
    	 .andDo(print()).andExpect(status().isBadRequest())
    	 .andReturn().getResponse().getContentAsString();
    
 
    	 assertEquals("must be a well-formed email address", response.strip());
     }
     
     //**************************************************
     //GET PERSON TESTS
    
     @Test
     void getPersonById() throws Exception {
    	 long testId = 2;
    	 mockMvc.perform(get(String.format("http://localhost:8080/greetings/id/%d", testId))).andDo(print()).andExpect(status().isOk());
     }
     
     //DELETE PERSON TESTS
     
     @Test
     void deletePersonById() throws Exception {
    	 long testId = 2;
    	 mockMvc.perform(delete(String.format("http://localhost:8080/greetings/%d", testId))).andDo(print()).andExpect(status().isOk());
     }
     
  //UPDATE PERSON TESTS
     
     @Test
     void updetePerson() throws Exception {
    	 
    	 mockMvc.perform(
    		put("http://localhost:8080/greetings")
    			 .contentType(MediaType.APPLICATION_JSON)
    			 .content(objectMapper.writeValueAsString(personNormal))
    		)
    		.andDo(print())
    		.andExpect(status().isOk());
    	
     }
}
