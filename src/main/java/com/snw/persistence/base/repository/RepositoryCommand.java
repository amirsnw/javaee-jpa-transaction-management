package com.snw.persistence.base.repository;

import com.snw.persistence.aspect.model.DBSession;
import com.snw.persistence.base.BaseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

public abstract class RepositoryCommand<T extends BaseEntity> {

    @Inject
    protected DBSession dbSession;

    protected abstract Class<T> entityType() throws Exception;

    public T save(T entity) throws Exception {
        EntityManager session = dbSession.current();
        session.persist(entity);
        session.flush();
        return entity;
    }

    public T update(T entity) throws Exception {
        EntityManager session = dbSession.current();
        session.merge(entity);
        session.flush();
        return entity;
    }

    public void delete(T entity) throws Exception {
        EntityManager session = dbSession.current();
        session.remove(entity);
        session.flush();
    }

    public void flush() throws Exception {
        dbSession.current().flush();
    }

    public void commit() throws Exception {
        dbSession.commitTransaction();
    }

    public void clear() throws Exception {
        dbSession.current().clear();
    }

    public StoredProcedureQuery createStoredProcedureQuery(String procedureName) throws Exception {
        return dbSession.current().createStoredProcedureQuery(procedureName);
    }

    public Query createNativeQuery(String strQuery) throws Exception {
        return dbSession.current().createNativeQuery(strQuery);
    }
}
