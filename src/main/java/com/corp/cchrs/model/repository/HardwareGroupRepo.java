package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.HardwareGroup;

@Repository
public interface HardwareGroupRepo extends CrudRepository<HardwareGroup, Integer> {

}