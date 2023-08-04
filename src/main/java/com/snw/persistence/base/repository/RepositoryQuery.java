package com.snw.persistence.base.repository;

import com.snw.dto.FilterDto;
import com.snw.dto.SortDto;
import com.snw.exception.ServiceException;
import com.snw.persistence.aspect.model.DBSession;
import com.snw.persistence.base.BaseEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.*;

public abstract class RepositoryQuery<T extends BaseEntity> {

    @Inject
    protected DBSession dbSession;

    protected abstract Class<T> entityType() throws Exception;

    private Set<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Root<T> root, Set<FilterDto> filters) throws Exception {
        Set<Predicate> predicates = new HashSet<>();

        if (filters == null) {
            return predicates;
        }

        for (FilterDto filter : filters) {
            Predicate predicate;
            switch (filter.getOperator()) {
                case EQUALS:
                    predicate = this.equal(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case CONTAINS:
                    predicate = this.contains(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case START_WITH:
                    predicate = this.startWith(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case END_WITH:
                    predicate = this.endWith(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case IN:
                    predicate = this.in(criteriaBuilder, root, filter.getProperty(), filter.getValues());
                    break;
                case NOT_EQUALS:
                    predicate = this.notEqual(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case GREATER_THAN:
                    predicate = this.greaterThan(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case GREATER_THAN_OR_EQUALS:
                    predicate = this.greaterThanOrEqualTo(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case LESS_THAN:
                    predicate = this.lessThan(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case LESS_THAN_OR_EQUALS:
                    predicate = this.lessThanOrEqualTo(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case NOT_IN:
                    predicate = this.notIn(criteriaBuilder, root, filter.getProperty(), filter.getValues());
                    break;
                case IS_NULL:
                    predicate = this.isNull(criteriaBuilder, root, filter.getProperty());
                    break;
                case BETWEEN:
                    predicate = this.between(criteriaBuilder, root, filter.getProperty(), filter.getValue());
                    break;
                case OR:
                    predicates.add(or(criteriaBuilder, this.getPredicates(criteriaBuilder, root, filter.getOrValue())));
                    continue;
                default:
                    predicate = this.isNotNull(criteriaBuilder, root, filter.getProperty());
                    break;
            }
            predicates.add(predicate);
        }

        return predicates;
    }

    private Predicate and(CriteriaBuilder criteriaBuilder, Set<Predicate> predicates) throws Exception {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate or(CriteriaBuilder criteriaBuilder, Set<Predicate> predicates) throws Exception {
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Predicate isNull(CriteriaBuilder criteriaBuilder, Root<T> root, String field) {
        return criteriaBuilder.isNull(root.get(field));
    }

    private Predicate isNotNull(CriteriaBuilder criteriaBuilder, Root<T> root, String field) {
        return criteriaBuilder.isNotNull(root.get(field));
    }

    private Predicate equal(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
        return criteriaBuilder.equal(root.get(field), value);
    }

    private Predicate notEqual(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
        return criteriaBuilder.notEqual(root.get(field), value);
    }

    private Predicate contains(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
        return criteriaBuilder.like(root.get(field), "%" + value + "%");
    }

    private Predicate startWith(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
        return criteriaBuilder.like(root.get(field), value + "%");
    }

    private Predicate endWith(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) {
        return criteriaBuilder.like(root.get(field), "%" + value);
    }

    private Predicate in(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Set<Object> values) throws Exception {
        CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.get(field));
        for (Object val : values) inClause.value(val);
        return inClause;
    }

    private Predicate notIn(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Set<Object> values) throws Exception {
        CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(root.get(field));
        for (Object val : values) inClause.value(val);
        return inClause.not();
    }

    private Predicate greaterThan(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) throws Exception {
        if (root.get(field).getJavaType().isAssignableFrom(Integer.class))
            return criteriaBuilder.greaterThan(root.get(field), Integer.parseInt(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Long.class))
            return criteriaBuilder.greaterThan(root.get(field), Long.parseLong(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Timestamp.class))
            return criteriaBuilder.greaterThan(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Date.class))
            return criteriaBuilder.greaterThan(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        throw new ServiceException();
    }

    private Predicate greaterThanOrEqualTo(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) throws Exception {
        if (root.get(field).getJavaType().isAssignableFrom(Integer.class))
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field), Integer.parseInt(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Long.class))
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field), Long.parseLong(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Timestamp.class))
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Date.class))
            return criteriaBuilder.greaterThanOrEqualTo(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        throw new ServiceException();
    }

    private Predicate lessThan(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) throws Exception {
        if (root.get(field).getJavaType().isAssignableFrom(Integer.class))
            return criteriaBuilder.lessThan(root.get(field), Integer.parseInt(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Long.class))
            return criteriaBuilder.lessThan(root.get(field), Long.parseLong(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Timestamp.class))
            return criteriaBuilder.lessThan(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Date.class))
            return criteriaBuilder.lessThan(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        throw new ServiceException();
    }

    private Predicate lessThanOrEqualTo(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) throws Exception {
        if (root.get(field).getJavaType().isAssignableFrom(Integer.class))
            return criteriaBuilder.lessThanOrEqualTo(root.get(field), Integer.parseInt(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Long.class))
            return criteriaBuilder.lessThanOrEqualTo(root.get(field), Long.parseLong(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Timestamp.class))
            return criteriaBuilder.lessThanOrEqualTo(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        if (root.get(field).getJavaType().isAssignableFrom(Date.class))
            return criteriaBuilder.lessThanOrEqualTo(root.get(field), Timestamp.valueOf(String.valueOf(value)));
        throw new ServiceException();
    }

    private Predicate between(CriteriaBuilder criteriaBuilder, Root<T> root, String field, Object value) throws Exception {
        String val = String.valueOf(value);
        if (!val.contains(",")) throw new ServiceException();
        String val1 = val.split(",")[0];
        String val2 = val.split(",")[1];
        if ("".equals(val1.trim()) || "".equals(val2.trim())) throw new ServiceException();
        if (root.get(field).getJavaType().isAssignableFrom(Integer.class))
            return criteriaBuilder.between(root.get(field), Integer.parseInt(val1), Integer.parseInt(val2));
        if (root.get(field).getJavaType().isAssignableFrom(Long.class))
            return criteriaBuilder.between(root.get(field), Long.parseLong(val1), Long.parseLong(val2));
        if (root.get(field).getJavaType().isAssignableFrom(Timestamp.class))
            return criteriaBuilder.between(root.get(field), Timestamp.valueOf(val1), Timestamp.valueOf(val2));
        if (root.get(field).getJavaType().isAssignableFrom(Date.class))
            return criteriaBuilder.between(root.get(field), Timestamp.valueOf(String.valueOf(value)), Timestamp.valueOf(String.valueOf(value)));
        throw new ServiceException();
    }

    private List<Order> order(CriteriaBuilder criteriaBuilder, Root<T> root, Set<SortDto> sorters) throws Exception {
        List<Order> orders = new ArrayList<>();
        if (sorters == null) {
            return orders;
        }
        for (SortDto sorter : sorters) {
            if (sorter.getProperty() == null) throw new ServiceException();
            if (sorter.getDirection() == null || SortDto.Sort.getValue(sorter.getDirection().name()) == null)
                throw new ServiceException();
            if (sorter.getDirection() == SortDto.Sort.DESC) {
                orders.add(this.desc(criteriaBuilder, root, sorter.getProperty()));
                continue;
            }
            orders.add(this.asc(criteriaBuilder, root, sorter.getProperty()));
        }
        return orders;
    }

    private Order asc(CriteriaBuilder criteriaBuilder, Root<T> root, String field) throws Exception {
        return criteriaBuilder.asc(root.get(field));
    }

    private Order desc(CriteriaBuilder criteriaBuilder, Root<T> root, String field) throws Exception {
        return criteriaBuilder.desc(root.get(field));
    }

    public T find(Long id) throws Exception {
        EntityManager session = dbSession.current();
        return session.find(entityType(), id);
    }

    public List<T> findAll(int pageNumber, int pageSize, Set<FilterDto> filters, Set<SortDto> sorters)
            throws Exception {
        CriteriaBuilder criteriaBuilder = dbSession.current().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityType());
        Root<T> root = criteriaQuery.from(entityType());
        criteriaQuery.where(this.and(criteriaBuilder, this.getPredicates(criteriaBuilder, root, filters)));
        criteriaQuery.select(root).orderBy(this.order(criteriaBuilder, root, sorters));
        TypedQuery<T> query = dbSession.current().createQuery(criteriaQuery);
        query.setFirstResult(pageNumber).setMaxResults(pageSize);
        dbSession.putJpaQuery(query);
        return query.getResultList();
    }

    public long count(Set<FilterDto> filters) throws Exception {
        CriteriaBuilder criteriaBuilder = dbSession.current().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityType());
        criteriaQuery.select(criteriaBuilder.count(root)).where(this.and(criteriaBuilder, this.getPredicates(criteriaBuilder, root, filters)));
        TypedQuery<Long> query = dbSession.current().createQuery(criteriaQuery);
        dbSession.putJpaQuery(query);
        return query.getSingleResult();
    }
}
