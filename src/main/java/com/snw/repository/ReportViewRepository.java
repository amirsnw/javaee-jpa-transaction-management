package com.snw.repository;


import com.snw.entity.ReportEntity;
import com.snw.persistence.base.repository.RepositoryQuery;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportViewRepository extends RepositoryQuery<ReportEntity> {

    @Override
    protected Class<ReportEntity> entityType() {
        return ReportEntity.class;
    }

}
