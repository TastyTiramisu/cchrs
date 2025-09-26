package com.corp.cchrs.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.BorrowHistory;

@Repository
public interface BorrowHistoryRepo extends PagingAndSortingRepository<BorrowHistory, Integer> {

	//TODO
	@Query(nativeQuery=true, 
			countQuery = "SELECT count(asset.*) FROM borrow_history LEFT OUTER JOIN asset ON borrow_history.asset_id = asset.id WHERE borrow_history.person_id = ?1",
			value = "SELECT asset.* "
			+ "FROM borrow_history "
			+ "LEFT OUTER JOIN asset ON borrow_history.asset_id = asset.id "
			+ "WHERE borrow_history.person_id = ?1")
	Page<BorrowHistory> findByPersonId(Integer personId, Pageable paging);

}
//jeśli zwraca Page<BorrowHistory> i używamy @Query - asset_id not found
//jeśli zwraca Page<Asset> - w obydwu przypadkach - No converter found capable of converting from type [java.lang.Integer] to type [com.corp.cchrs.model.Asset]