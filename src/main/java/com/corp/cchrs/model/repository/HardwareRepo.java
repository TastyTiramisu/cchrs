package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.Hardware;

@Repository
public interface HardwareRepo extends CrudRepository<Hardware, Integer> {

}