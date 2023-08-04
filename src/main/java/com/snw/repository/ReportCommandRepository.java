package com.snw.repository;

import com.snw.entity.ReportEntity;
import com.snw.persistence.base.repository.RepositoryCommand;

public class ReportCommandRepository extends RepositoryCommand<ReportEntity> {

    @Override
    protected Class<ReportEntity> entityType() {
        return ReportEntity.class;
    }

}
