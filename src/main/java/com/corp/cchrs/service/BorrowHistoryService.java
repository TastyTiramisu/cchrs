package com.corp.cchrs.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.BorrowHistory;
import com.corp.cchrs.model.repository.BorrowHistoryRepo;

@Service
public class BorrowHistoryService {
	@Autowired
	BorrowHistoryRepo repo;
	
	@Autowired
	AssetService assetService;
	
	public BorrowHistory getLastRecordInHistoryOfAsset(Integer assetId) {
		Comparator<BorrowHistory> comparator = Comparator.comparing(BorrowHistory::getId);
		BorrowHistory bh = StreamSupport.stream(repo.findAll().spliterator(), false)
		.filter(id -> assetId.equals(id.getAsset().getId()))
		.max(comparator).get();
		return repo.findById(bh.getId()).get();
	}

	public void saveBorrowHistory(BorrowHistory borrowHistory) {
		repo.save(borrowHistory);
	}

	public List<BorrowHistory> getBorrowHistoryOfAsset(Integer assetId) {
		return StreamSupport.stream(repo.findAll().spliterator(), false)
				.filter(bh -> assetId.equals(bh.getAsset().getId()))
				.toList();
	}
}
