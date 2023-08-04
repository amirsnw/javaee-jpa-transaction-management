package com.snw.persistence.aspect;

import com.snw.exception.ServiceException;
import com.snw.persistence.annotation.ServiceQuery;
import com.snw.persistence.aspect.model.DBSession;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@ServiceQuery
@Slf4j
public class AspectSessionManagement {

    @Inject
    DBSession session;

    @AroundInvoke
    public Object manageSession(InvocationContext context) throws Exception {
        if (session.isSession()) return context.proceed();
        try {
            session.open();
            log.info("Session Opened");
        } catch (Exception e) {
            log.error("Missing Session: " + e.getMessage());
            throw new ServiceException();
        }

        Object result;
        try {
            result = context.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
            log.info("Session Closed");
        }
        return result;
    }
}
