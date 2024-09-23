package com.corp.cchrs.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.BorrowHistory;

@Repository
public interface BorrowHistoryRepo extends CrudRepository<BorrowHistory, Integer> {

}
