package com.corp.cchrs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.Room;
import com.corp.cchrs.model.repository.RoomRepo;

@Service
public class RoomService {

	@Autowired
	RoomRepo repo;

	public List<Room> getRooms() {
		List<Room> rooms = new ArrayList<>();
		repo.findAll().forEach(n -> rooms.add(n));
		return rooms;
	}

	public Room getRoom(Integer roomId) {
		return repo.findById(roomId).orElseThrow();
	}
}
