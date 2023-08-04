package com.snw.persistence.aspect;

import com.snw.exception.ServiceException;
import com.snw.persistence.annotation.ServiceCommand;
import com.snw.persistence.aspect.model.DBSession;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@ServiceCommand
@Slf4j
public class AspectTransactionManagement {
    @Inject
    DBSession session;

    @AroundInvoke
    public Object managingTransactionalSession(InvocationContext ic) throws Exception {
        if (!session.isSession()) {
            try {
                session.open();
                log.info("Session Opened");
            } catch (Exception e) {
                log.error("Missing Session: " + e.getMessage());
                throw new ServiceException();
            }
        }
        if (session.isTransactional()) return ic.proceed();
        try {
            session.beginTransaction();
            log.info("Begin Transaction");
        } catch (Exception e) {
            log.error("Fail Begin Transaction, " + e.getMessage());
            throw new ServiceException();
        }
        Object result = ic.proceed();
        try {
            session.commitTransaction();
            log.info("Transaction Commit");
        } catch (Exception e) {
            log.error("Fail Transaction, " + e.getMessage());
            session.rollbackTransaction();
            throw new ServiceException();
        } finally {
            session.close();
            log.info("Session Closed");
        }
        return result;
    }
}
