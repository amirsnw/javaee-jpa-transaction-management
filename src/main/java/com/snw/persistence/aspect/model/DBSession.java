package com.snw.persistence.aspect.model;

import com.snw.exception.ServiceException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.util.Date;
import java.util.List;

@RequestScoped
public class DBSession {

    private static final ThreadLocal<EntitySession> sessionLocal = new ThreadLocal<>();
    @Inject
    EntityManagerFactory entityManagerFactory;

    public boolean isSession() {
        return sessionLocal.get() != null && sessionLocal.get().isOpen();
    }

    public boolean isTransactional() {
        return sessionLocal.get() != null && sessionLocal.get().isTransactional();
    }

    public EntityManager current() {
        if (!isSession()) throw new ServiceException();
        return sessionLocal.get().getSession();
    }

    public void open() {
        if (isSession()) return;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntitySession entitySession = new EntitySession();
        entitySession.setCreationDate(new Date());
        entitySession.setSession(entityManager);
        entitySession.setOpen(true);
        entitySession.setTransactional(false);
        sessionLocal.set(entitySession);
    }

    public void beginTransaction() throws Exception {
        EntitySession entitySession = sessionLocal.get();
        if (entitySession.isTransactional()) return;
        EntityManager session = entitySession.getSession();

        // manually manages transactions using the UserTransaction interface using JNDI lookup
        Context ctx = new InitialContext();
        UserTransaction utx = (UserTransaction) ctx.lookup("java:comp/UserTransaction");
        entitySession.setUtx(utx);
        utx.begin();
        session.joinTransaction();

        // uses the built-in transaction management capabilities of the session
        /*session.getTransaction().begin();
        session.setFlushMode(FlushModeType.COMMIT);*/

        entitySession.setTransactional(true);
    }

    public void commitTransaction() throws Exception {
        EntitySession entitySession = sessionLocal.get();
        if (entitySession.isTransactional()) {
            entitySession.getUtx().commit();
        }
    }

    public void rollbackTransaction() throws Exception {
        EntitySession entitySession = sessionLocal.get();
        if (entitySession.isTransactional()) {
            entitySession.getUtx().rollback();
        }
    }

    public void close() {
        EntitySession entitySession = sessionLocal.get();
        EntityManager session = entitySession.getSession();
        if (session.isOpen()) session.close();
        entitySession.setEndDate(new Date());
        sessionLocal.set(null);
    }

    public void putJpaQuery(TypedQuery<?> query) {
        EntitySession entitySession = sessionLocal.get();
        entitySession.getQuery().add(query);
    }

    public List<TypedQuery<?>> getJpaQuery() {
        EntitySession entitySession = sessionLocal.get();
        return entitySession.getQuery();
    }
}
