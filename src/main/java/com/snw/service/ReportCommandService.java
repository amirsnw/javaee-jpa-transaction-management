package com.snw.service;

import com.snw.entity.ReportEntity;
import com.snw.persistence.annotation.ServiceCommand;
import com.snw.repository.ReportCommandRepository;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
@ServiceCommand
public class ReportCommandService {
    @Inject
    private ReportCommandRepository repository;

    public ReportEntity create(ReportEntity entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
