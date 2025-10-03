package com.corp.cchrs.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.Asset;

@Repository
public interface AssetRepo extends PagingAndSortingRepository<Asset, Integer> {
	
	public Page<Asset> findByDeletedFalse(Pageable paging);
	public Page<Asset> findByHardwareIdAndDeletedFalse(Integer type, Pageable paging);
	public Page<Asset> findByHardwareHardwareGroupIdAndDeletedFalse(Integer group, Pageable paging);
	public Page<Asset> findByHardwareIdAndHardwareHardwareGroupIdAndDeletedFalse(Integer type, Integer group, Pageable paging);
	
	public Page<Asset> findByDeletedTrue(Pageable paging);
	public Page<Asset> findByHardwareIdAndDeletedTrue(Integer type, Pageable paging);
	public Page<Asset> findByHardwareHardwareGroupIdAndDeletedTrue(Integer group, Pageable paging);
	public Page<Asset> findByHardwareIdAndHardwareHardwareGroupIdAndDeletedTrue(Integer type, Integer group,
			Pageable paging);
	
	@Query("SELECT bh.asset FROM BorrowHistory bh WHERE bh.person.id = ?1 AND bh.asset.deleted = 0")
	public Page<Asset> findByPerson(Integer personId, Pageable paging);
	
	@Query("SELECT bh.asset FROM BorrowHistory bh WHERE bh.person.id = ?1 AND bh.asset.hardware.id = ?2 AND bh.asset.deleted = 0")
	public Page<Asset> findByType(Integer personId, Integer type, Pageable paging);
	
	@Query("SELECT bh.asset FROM BorrowHistory bh WHERE bh.person.id = ?1 AND bh.asset.hardware.hardwareGroup.id = ?2 AND bh.asset.deleted = 0")
	public Page<Asset> findByGroup(Integer personId, Integer group, Pageable paging);
	
	@Query("SELECT bh.asset FROM BorrowHistory bh WHERE bh.person.id = ?1 AND bh.asset.hardware.id = ?2 AND bh.asset.hardware.hardwareGroup.id = ?3 AND bh.asset.deleted = 0")
	public Page<Asset> findByPersonTypeAndGroup(Integer personId, Integer type, Integer group, Pageable paging);

}