package com.corp.cchrs.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.BorrowHistory;
import com.corp.cchrs.model.repository.BorrowHistoryRepo;

@Service
public class BorrowHistoryService {
	@Autowired
	BorrowHistoryRepo bhRepo;
	
	@Autowired
	AssetService assetService;
	
	public BorrowHistory getLastRecordInHistoryOfAsset(Integer assetId) {
		Comparator<BorrowHistory> comparator = Comparator.comparing(BorrowHistory::getId);
		BorrowHistory bh = StreamSupport.stream(bhRepo.findAll().spliterator(), false)
		.filter(id -> assetId.equals(id.getAsset().getId()))
		.max(comparator).get();
		return bhRepo.findById(bh.getId()).get();
	}

	public void saveBorrowHistory(BorrowHistory borrowHistory) {
		bhRepo.save(borrowHistory);
	}

	public List<BorrowHistory> getBorrowHistoryOfAsset(Integer assetId) {
		return StreamSupport.stream(bhRepo.findAll().spliterator(), false)
				.filter(bh -> assetId.equals(bh.getAsset().getId()))
				.toList();
	}

	public Page<BorrowHistory> getBorrowHistory(Integer personId, Integer type, Integer group, Pageable paging) {
		if(group == null || type == null) return bhRepo.findByPerson(personId, paging);
		if(group == 0 && type == 0) return bhRepo.findByPerson(personId, paging);
		if(group == 0) return bhRepo.findByType(personId, type, paging);
		if(type == 0) return bhRepo.findByGroup(personId, group, paging);
		return bhRepo.findByPersonTypeAndGroup(personId, type, group, paging);
	}
	
}
