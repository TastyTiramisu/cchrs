package com.corp.cchrs.service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.corp.cchrs.model.Asset;
import com.corp.cchrs.model.repository.AssetRepo;

@Service
public class AssetService {

	@Autowired
	AssetRepo assetRepo;
	
	@Autowired
	HardwareService hardwareService;

	public void saveAsset(Asset asset) {
		assetRepo.save(asset);
	}

	public void deleteAsset(Integer id) {
		final Asset asset = assetRepo.findById(id).orElseThrow();
		asset.setDeleted(true);
		asset.getAssetHistory().setDisposalDate(LocalDateTime.now());
		saveAsset(asset);
	}

	public Asset getAsset(Integer id) {
		return assetRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Asset with id " + id + " doesn't exist!"));
	}
	
	public Page<Asset> getAssets(Integer type, Integer group, Pageable paging) {
		if(group == null || type == null) return assetRepo.findByDeletedFalse(paging);
		if(group == 0 && type == 0) return assetRepo.findByDeletedFalse(paging);
		if(group == 0) return assetRepo.findByHardwareIdAndDeletedFalse(type, paging);
		if(type == 0) return assetRepo.findByHardwareHardwareGroupIdAndDeletedFalse(group, paging);
		return assetRepo.findByHardwareIdAndHardwareHardwareGroupIdAndDeletedFalse(type, group, paging);
	}
	
	public Page<Asset> getDeletedAssets(Integer type, Integer group, Pageable paging) {
		if(group == null || type == null) return assetRepo.findByDeletedTrue(paging);
		if(group == 0 && type == 0) return assetRepo.findByDeletedTrue(paging);
		if(group == 0) return assetRepo.findByHardwareIdAndDeletedTrue(type, paging);
		if(type == 0) return assetRepo.findByHardwareHardwareGroupIdAndDeletedTrue(group, paging);
		return assetRepo.findByHardwareIdAndHardwareHardwareGroupIdAndDeletedTrue(type, group, paging);
	}
	
	public Page<Asset> getAssets(Integer personId, Integer type, Integer group, Pageable paging) {
		if(group == null || type == null) return assetRepo.findByPerson(personId, paging);
		if(group == 0 && type == 0) return assetRepo.findByPerson(personId, paging);
		if(group == 0) return assetRepo.findByType(personId, type, paging);
		if(type == 0) return assetRepo.findByGroup(personId, group, paging);
		return assetRepo.findByPersonTypeAndGroup(personId, type, group, paging);
	}
}
