package com.corp.cchrs.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.Asset;

@Repository
public interface AssetRepo extends PagingAndSortingRepository<Asset, Integer> {
	
	public Page<Asset> findByHardwareId(Integer type, Pageable paging);
	public Page<Asset> findByHardwareHardwareGroupId(Integer group, Pageable paging);
	public Page<Asset> findByHardwareIdAndHardwareHardwareGroupId(Integer type, Integer group, Pageable paging);

}
//https://www.bezkoder.com/thymeleaf-pagination/
//https://github.com/karelp90/control_asp/blob/master/src/main/resources/templates/centro/centros.html
//https://getbootstrap.com/docs/4.0/components/pagination/