package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.Person;

@Repository
public interface PersonRepo extends CrudRepository<Person, Integer> {

}
