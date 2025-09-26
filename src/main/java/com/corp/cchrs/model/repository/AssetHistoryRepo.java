package com.corp.cchrs.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.AssetHistory;

@Repository
public interface AssetHistoryRepo extends PagingAndSortingRepository<AssetHistory, Integer> {

}
