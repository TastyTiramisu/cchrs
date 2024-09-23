package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.AssetHistory;

@Repository
public interface AssetHistoryRepo extends CrudRepository<AssetHistory, Integer> {

}
