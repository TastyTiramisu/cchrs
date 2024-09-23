package com.corp.cchrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.AssetHistory;
import com.corp.cchrs.model.repository.AssetHistoryRepo;

@Service
public class AssetHistoryService {

	@Autowired
	AssetHistoryRepo repo;
	
	public void saveAssetHistory(AssetHistory assetHistory) {
		repo.save(assetHistory);
	}
}
