package com.snw.persistence.aspect.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
class EntitySession {
    // private String threadName;
    private List<TypedQuery<?>> query = new ArrayList<>();
    private EntityManager session;
    private UserTransaction utx;
    private Date creationDate;
    private Date endDate;
    private boolean isTransactional;
    private boolean isOpen;
}