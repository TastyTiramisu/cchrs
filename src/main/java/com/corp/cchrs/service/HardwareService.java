package com.corp.cchrs.service;

import java.util.ArrayList;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.Hardware;
import com.corp.cchrs.model.HardwareGroup;
import com.corp.cchrs.model.repository.HardwareRepo;

@Service
public class HardwareService {

	@Autowired
	HardwareRepo repo;

	public ArrayList<String> getTypesByGroup(Integer groupNumber) {
		if (groupNumber == 0)
			return getAllTypes();

		final Iterable<Hardware> allHardware = repo.findAll();
		ArrayList<String> res = new ArrayList<>();

		for (Hardware h : allHardware) {
			if (h.getHardwareGroup().getId() == groupNumber)
				res.add(h.getType());
		}
		return res;
	}

	public ArrayList<String> getAllTypes() {
		ArrayList<String> res = new ArrayList<>();

		for (Hardware h : repo.findAll())
			res.add(h.getType());
		return res;
	}

	public ArrayList<Hardware> getAllHardwares() {
		ArrayList<Hardware> res = new ArrayList<>();

		for (Hardware h : repo.findAll())
			res.add(h);
		return res;
	}

	/**
	 * @param type has to be equal to constant name of Enum from EHardwareType. If
	 *             not, method throws NoSuchElementException.
	 * @return Hardware object. Assign it to Asset. Now Asset has type and thus can
	 *         be filtered out as such.
	 */
	public Hardware findTypeOfAsset(String type) {
		return StreamSupport.stream(repo.findAll().spliterator(), false).filter(hw -> type.equals(hw.getType()))
				.findAny().orElseThrow();
	}

	public void saveHardware(Hardware hardware) {
		repo.save(hardware);
	}

	public HardwareGroup getGroupByType(String type) {
		return getHardwareByType(type).getHardwareGroup();
	}

	// the type must be in format of enum, use Utils if needed.
	public Hardware getHardwareByType(String type) {
		return getAllHardwares().stream().filter(n -> n.getType().equals(type)).findAny().orElseThrow();
	}
}
