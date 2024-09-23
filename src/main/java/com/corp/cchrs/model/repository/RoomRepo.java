package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.Room;

@Repository
public interface RoomRepo extends CrudRepository<Room, Integer> {

}
