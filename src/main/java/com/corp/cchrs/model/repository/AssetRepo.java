package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.Asset;

@Repository
public interface AssetRepo extends CrudRepository<Asset, Integer> {

}
