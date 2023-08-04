package com.snw.service;

import com.snw.dto.FilterDto;
import com.snw.dto.SortDto;
import com.snw.entity.ReportEntity;
import com.snw.persistence.annotation.ServiceQuery;
import com.snw.repository.ReportViewRepository;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Set;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ServiceQuery
public class ReportQueryService {

    @Inject
    private ReportViewRepository repository;


    public List<ReportEntity> search(int pageNumber, int pageSize, Set<FilterDto> filters, Set<SortDto> sorters) {
        try {
            return repository.findAll(pageNumber, pageSize, filters, sorters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
