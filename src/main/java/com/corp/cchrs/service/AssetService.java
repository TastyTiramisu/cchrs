package com.corp.cchrs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	
	public Page<Asset> getAllAssets(Pageable pageable) {
		return assetRepo.findAll(pageable);
	}
	
	private List<Asset> getListOfAssets() {
		List<Asset> allAssets = new ArrayList<>();
		assetRepo.findAll().forEach(allAssets::add);
		return allAssets;
	}
	
	protected List<Asset> getExistingAssets() {
		return getListOfAssets().stream().filter(a -> a.isDeleted().equals(false)).toList();
	}
	
	public List<Asset> getExistingAssets(List<Asset> assets) {
		return assets.stream().filter(a -> a.isDeleted().equals(false)).toList();
	}
	
	public List<Asset> getDeletedAssets() {
		return getListOfAssets().stream().filter(a -> a.isDeleted().equals(true)).toList();
	}
	
	public List<Asset> getAssets(Integer group, Integer type, boolean areAssetsDeleted) throws Exception{
		List<Asset> assets = areAssetsDeleted ? getDeletedAssets() : getExistingAssets();
		
		if (group == null || type == null) return assets;
		if (group == 0 && type == 0) return assets;
		if (group == 0)
			return assets.stream().filter(g -> g.getHardware().getId().equals(type)).toList();

		if (type == 0)
			return assets.stream().filter(g -> g.getHardware().getHardwareGroup().getId().equals(group)).toList();

		final String typeName = hardwareService.getTypesByGroup(group).get(type - 1);
		return assets.stream().filter(g -> g.getHardware().getType().equals(typeName))
				.filter(g -> g.getHardware().getHardwareGroup().getId().equals(group)).toList();
	}
		
	public List<Asset> getAssets(List<Asset> assetsByPerson, Integer group, Integer type) throws Exception{
		if (group == null || type == null) return assetsByPerson;
		if (group == 0 && type == 0) return assetsByPerson;
		if (group == 0) 
			return assetsByPerson.stream()
				.filter(g -> g.getHardware().getId().equals(type)).toList();
		
		if (type == 0) 
			return assetsByPerson.stream()
					.filter(g -> g.getHardware().getHardwareGroup().getId().equals(group)).toList();
		
		final String typeName = hardwareService.getTypesByGroup(group).get(type - 1);
		return assetsByPerson.stream()
				.filter(g -> g.getHardware().getType().equals(typeName))
				.filter(g -> g.getHardware().getHardwareGroup().getId().equals(group))
				.toList();
	}

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
	
	public Page<Asset> findByGroupAndType(Integer type, Integer group, Pageable paging) {
		if(group == null || type == null) return assetRepo.findAll(paging);
		if(group == 0 && type == 0) return assetRepo.findAll(paging);
		if(group == 0) return assetRepo.findByHardwareId(type, paging);
		if(type == 0) return assetRepo.findByHardwareHardwareGroupId(group, paging);
		return assetRepo.findByHardwareIdAndHardwareHardwareGroupId(type, group, paging);
	}
}
