package com.corp.cchrs.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.BorrowHistory;

@Repository
public interface BorrowHistoryRepo extends PagingAndSortingRepository<BorrowHistory, Integer> {
	
	//from https://stackoverflow.com/questions/7745609/sql-select-only-rows-with-max-value-on-a-column/7745635#7745635
	final String findAllLatestBhObjectsByPerson = "SELECT a FROM BorrowHistory a LEFT JOIN BorrowHistory b ON a.asset.id = b.asset.id AND a.id < b.id WHERE b.id IS NULL AND a.person.id = ?1 AND a.asset.deleted = 0";
	
	@Query(findAllLatestBhObjectsByPerson)
	Page<BorrowHistory> findByPerson(Integer personId, Pageable paging);

	@Query(findAllLatestBhObjectsByPerson + "AND a.asset.hardware.id = ?2")
	Page<BorrowHistory> findByType(Integer personId, Integer type, Pageable paging);

	@Query(findAllLatestBhObjectsByPerson + "AND a.asset.hardware.hardwareGroup.id = ?2")
	Page<BorrowHistory> findByGroup(Integer personId, Integer group, Pageable paging);

	@Query(findAllLatestBhObjectsByPerson + "AND a.asset.hardware.id = ?2 AND a.asset.hardware.hardwareGroup.id = ?3")
	Page<BorrowHistory> findByPersonTypeAndGroup(Integer personId, Integer type, Integer group, Pageable paging);

}