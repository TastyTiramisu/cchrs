package com.corp.cchrs.model.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.corp.cchrs.model.BorrowHistory;

@Repository
public interface BorrowHistoryRepo extends PagingAndSortingRepository<BorrowHistory, Integer> {

}