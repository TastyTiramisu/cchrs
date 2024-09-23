package com.corp.cchrs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.Person;
import com.corp.cchrs.model.repository.PersonRepo;

@Service
public class PersonService {

	@Autowired 
	PersonRepo repo;

	public List<Person> getPeople() {
		List<Person> people = new ArrayList<>();
		repo.findAll().forEach(n -> people.add(n));
		return people;
	}

	public Person getPerson(Integer personId) {
		return repo.findById(personId).orElseThrow();
	}

	public Person getPersonByName(String name) {
		return getPeople().stream().filter(p -> p.getName().equals(name)).findFirst().orElseThrow();
	}
	
	public Person getPersonByEmail(String email) {
		return getPeople().stream().filter(p -> p.getEmail().equals(email)).findFirst().orElseThrow();
	}
}
