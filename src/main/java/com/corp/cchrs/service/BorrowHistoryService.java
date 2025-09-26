package com.corp.cchrs.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.Asset;
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

	protected List<BorrowHistory> getBorrowHistory() {
		List<BorrowHistory> bh = new ArrayList<>();
		for (BorrowHistory borrowHistory : repo.findAll()) {
			bh.add(borrowHistory);
		}
		return bh;
	}
	
	//gets latest entry of assets from borrowHistory
	//then filters them by person
	//metod ensures that returned assets are not deleted.
	public List<Asset> getAssetsByPerson(Integer personId) {
		final List<BorrowHistory> borrowHistory = getBorrowHistory();
		final Map<Asset, BorrowHistory> assetsWithTheirHistory = borrowHistory.stream().collect(Collectors.toMap(BorrowHistory::getAsset, e -> e, (o, n) -> o.getAsset().getId() > n.getAsset().getId() ? o : n));
		final Map<Asset, BorrowHistory> personsAssets = assetsWithTheirHistory.entrySet().stream().filter(e -> e.getValue().getPerson().getId().equals(personId)).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
		
		List<Asset> assets = new ArrayList<>();
		personsAssets.forEach((k, v) -> assets.add(k));
		assets.sort(Comparator.comparing(Asset::getId));
		return assetService.getExistingAssets(assets);
	}

	//get persons, rooms and dates
	public List<BorrowHistory> getBorrowHistoryOfAsset(Integer assetId) {
		return StreamSupport.stream(repo.findAll().spliterator(), false)
				.filter(bh -> assetId.equals(bh.getAsset().getId()))
				.toList();
	}
	
	public Page<Asset> getAssets(Integer personId, Integer type, Integer group, Pageable paging) {
		//TODO
		repo.findByPersonId(personId, paging);
		return assetService.findByGroupAndType(type, group, paging);
	}
	
	/*
	public Page<Asset> getAssets(Integer personId, Integer type, Integer group, Pageable paging) {
		if(group == null || type == null) return repo.findByPersonId(personId, paging);
		if(group == 0 && type == 0) return repo.findByPersonId(personId, paging);
		if(group == 0) return repo.findByPersonIdAndAssetHardwareId(personId, type, paging);
		if(type == 0) return repo.findByPersonIdAndAssetHardwareHardwareGroupId(personId, group, paging);
		return repo.findByPersonIdAndAssetHardwareIdAndAssetHardwareHardwareGroupId(personId, type, group, paging);
	}*/
}
