// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.administrator.common.persistence.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.persister.entity.EntityPersister;
import org.talend.administrator.common.data.BasicItem;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.DBException;
import org.talend.administrator.common.exception.ItemNotFoundException;
import org.talend.administrator.common.exception.ProgrammerException;
import org.talend.administrator.common.exception.SystemException;
import org.talend.administrator.common.exception.UnicityException;
import org.talend.administrator.common.logging.LoggerFactory;
import org.talend.administrator.common.persistence.conditions.BasicCriterionCondition;
import org.talend.administrator.common.persistence.conditions.ComparaisonOperator;
import org.talend.administrator.common.persistence.conditions.ICriterionCondition;
import org.talend.administrator.common.persistence.conditions.ValuedCriterionCondition;
import org.talend.administrator.common.util.reflect.BeanAccessorsInvoker;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class HibernatePersistenceAdapter {

    protected static Logger log = LoggerFactory.getLogger(HibernatePersistenceAdapter.class);

    private SessionFactory sessionFactory;

    private static BeanAccessorsInvoker beanAccessorsInvoker = new BeanAccessorsInvoker();

    public HibernatePersistenceAdapter(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ITransactionHolder createTransaction() throws DBException {
        try {
            Session session = sessionFactory.openSession();

            return new HibernateTransactionHolder(session, session.beginTransaction());
        } catch (HibernateException ex) {
            throw new DBException("Cannot create session or transaction.", ex);
        }
    }

    public void save(Object persistent) throws SystemException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        save(persistent, transactionHolder);
        transactionHolder.commitAndClose();
    }

    public void save(Object persistent, ITransactionHolder transaction) throws DBException {
        try {
            if (log.isLoggable(Level.FINEST)) {
                log.finest("### updating : " + persistent.toString());
            }
            ((HibernateTransactionHolder) transaction).getSession().save(persistent);
        } catch (HibernateException ex) {
            transaction.rollbackAndClose();
            throw new DBException("Cannot update object.", ex);
        }
    }

    public void saveOrUpdate(Object persistent) throws SystemException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        saveOrUpdate(persistent, transactionHolder);
        transactionHolder.commitAndClose();
    }

    public void saveOrUpdate(Object persistent, ITransactionHolder transaction) throws DBException {
        try {
            if (log.isLoggable(Level.FINEST)) {
                log.finest("### updating : " + persistent.toString());
            }
            ((HibernateTransactionHolder) transaction).getSession().saveOrUpdate(persistent);
        } catch (HibernateException ex) {
            transaction.rollbackAndClose();
            throw new DBException("Cannot update object.", ex);
        }
    }

    public void merge(Object persistent) throws SystemException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        merge(persistent, transactionHolder);
        transactionHolder.commitAndClose();
    }

    public void merge(Object persistent, ITransactionHolder transaction) throws DBException {
        try {
            if (log.isLoggable(Level.FINEST)) {
                log.finest("### updating : " + persistent.toString());
            }
            ((HibernateTransactionHolder) transaction).getSession().merge(persistent);
        } catch (HibernateException ex) {
            transaction.rollbackAndClose();
            throw new DBException("Cannot update object.", ex);
        }
    }

    public void persist(Object persistent) throws SystemException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        persist(persistent, transactionHolder);
        transactionHolder.commitAndClose();
    }

    public void persist(Object persistent, ITransactionHolder transaction) throws DBException {
        try {
            if (log.isLoggable(Level.FINEST)) {
                log.finest("### persisting : " + persistent.toString());
            }
            ((HibernateTransactionHolder) transaction).getSession().persist(persistent);
        } catch (HibernateException ex) {
            transaction.rollbackAndClose();
            throw new DBException("Cannot persist object.", ex);
        }
    }

    public int update(ITransactionHolder transaction, String hql) {
        Query query = ((HibernateTransactionHolder) transaction).getSession().createQuery(hql);
        return query.executeUpdate();
    }

    public void update(Object persistent) throws SystemException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        update(persistent, transactionHolder);
        transactionHolder.commitAndClose();
    }

    public void update(Object persistent, ITransactionHolder transaction) throws DBException {
        try {
            if (log.isLoggable(Level.FINEST)) {
                log.finest("### persisting : " + persistent.toString());
            }
            ((HibernateTransactionHolder) transaction).getSession().update(persistent);
        } catch (HibernateException ex) {
            transaction.rollbackAndClose();
            throw new DBException("Cannot update object.", ex);
        }
    }

    public <T> List<T> retrieve(T persistent, Criterion[] matchCriterions) throws DBException {
        return retrieve(persistent, matchCriterions, null);
    }

    public <T> List<T> retrieve(T persistent, String... matchCriterions) throws DBException {
        return retrieve(persistent, createCriterions(persistent, matchCriterions));
    }

    public <T> List<T> retrieve(T persistent, Criterion[] matchCriterions, String[] dependentObjectsToLoad)
            throws DBException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        List<T> retrieved = retrieve(transactionHolder, persistent, matchCriterions, dependentObjectsToLoad);
        transactionHolder.commitAndClose();
        return retrieved;
    }

    public <T> List<T> retrieve(ITransactionHolder transaction, T persistent, Criterion[] matchCriterions)
            throws DBException {
        return retrieve(transaction, persistent, matchCriterions, null);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> retrieve(ITransactionHolder transaction, T persistent, Criterion[] matchCriterions,
            String[] dependentObjectsToLoad) throws DBException {
        return retrieve(transaction, persistent, matchCriterions, null, dependentObjectsToLoad, null, 0);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> retrieve(ITransactionHolder transaction, T persistent, Criterion[] matchCriterions,
            Map<String, ICriterionCondition[]> subCollectionConditions, String[] dependentObjectsToLoad,
            BasicItem<String, Boolean>[] orderBy, int maxRows) throws DBException {
        if (maxRows < 0) {
            throw new IllegalArgumentException("maxRows can't be negative. (" + maxRows + ")");
        }
        try {
            Criteria criteria = createCriteria(transaction, persistent, matchCriterions);
            if (orderBy != null) {
                for (int i = 0; i < orderBy.length; i++) {
                    criteria.addOrder(orderBy[i].getValue().booleanValue() ? Order.asc(orderBy[i].getKey()) : Order
                            .desc(orderBy[i].getKey()));
                }
            }
            if (maxRows > 0) {
                criteria.setMaxResults(maxRows);
            }
            if (subCollectionConditions != null) {
                createSubCollectionCriteria(criteria, persistent, subCollectionConditions);
            }
            List<T> toReturn = criteria.list();
            if (toReturn == null) {
                return null;
            }
            if (dependentObjectsToLoad != null) {
                callGetters(dependentObjectsToLoad, toReturn.toArray());
            }
            return toReturn;
        } catch (HibernateException ex) {
            ((HibernateTransactionHolder) transaction).commitAndClose();
            throw new DBException("Cannot retrieve bean: " + persistent, ex);
        }
    }

    private static void callGetters(String[] dependentObjectsToLoad, Object[] toCallOn) {
        try {
            for (int i = 0; i < toCallOn.length; i++) {
                for (int j = 0; j < dependentObjectsToLoad.length; j++) {
                    Hibernate.initialize(PropertyUtils.getProperty(toCallOn[i], dependentObjectsToLoad[j]));
                }
            }
        } catch (HibernateException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw new ProgrammerException(e);
        } catch (InvocationTargetException e) {
            throw new ProgrammerException(e);
        } catch (NoSuchMethodException e) {
            throw new ProgrammerException(e);
        }
    }

    public List delete(ITransactionHolder transaction, Object persistent, Criterion[] matchCriterions)
            throws DBException {
        try {
            Criteria criteria = createCriteria(transaction, persistent, matchCriterions);
            List toDelete = criteria.list();
            for (int i = 0; i < toDelete.size(); i++) {
                delete(toDelete.get(i), transaction);
            }
            return toDelete;
        } catch (HibernateException ex) {
            throw new DBException("Cannot retrieve bean: " + persistent, ex);
        }
    }

    public void delete(Object bean, ITransactionHolder transactionHolder) throws DBException {
        try {
            ((HibernateTransactionHolder) transactionHolder).getSession().delete(bean);
        } catch (HibernateException ex) {
            throw new DBException("Cannot delete bean: " + bean, ex);
        }
    }

    public void delete(Collection<? extends Object> beans, ITransactionHolder transactionHolder) throws DBException {
        for (Iterator iter = beans.iterator(); iter.hasNext();) {
            delete(iter.next(), transactionHolder);
        }
    }

    public void delete(Collection<Object> beans) throws DBException {
        for (Iterator iter = beans.iterator(); iter.hasNext();) {
            delete(iter.next());
        }
    }

    public void delete(Object persistent) throws DBException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        delete(persistent, transactionHolder);
        transactionHolder.commitAndClose();
    }

    public static Criteria createCriteria(ITransactionHolder transaction, Object persistent, Criterion[] matchCriterions) {
        Criteria criteria = (((HibernateTransactionHolder) transaction).getSession()).createCriteria(persistent
                .getClass().getInterfaces()[0].getSimpleName());
        if (matchCriterions != null) {
            for (int i = 0; i < matchCriterions.length; i++) {
                criteria.add(matchCriterions[i]);
            }
        }
        return criteria;
    }

    public static Criteria createSubCollectionCriteria(Criteria parentCriteria, Object persistent,
            Map<String, ICriterionCondition[]> subCollectionConditions) {
        for (Map.Entry<String, ICriterionCondition[]> subset : subCollectionConditions.entrySet()) {
            Object subProp = beanAccessorsInvoker.getBeanPropertyValue(persistent, subset.getKey());
            if (subProp instanceof Collection) {
                try {
                    Object subPropValue = ((Collection) subProp).iterator().next();
                    Criterion[] subCrits = createCriterions(subPropValue, subset.getValue());
                    Criteria subCriteria = parentCriteria.createCriteria(subset.getKey());
                    for (Criterion subCrit : subCrits) {
                        subCriteria.add(subCrit);
                    }
                } finally {
                    //
                }
            }
        }
        return parentCriteria;
    }

    public static void deleteHQL(ITransactionHolder transaction, String queryHQL) {
        ((HibernateTransactionHolder) transaction).getSession().delete(queryHQL);
    }

    public static Criterion[] createCriterions(Object bean, ICriterionCondition[] conditions) {
        ArrayList<Criterion> list = new ArrayList<Criterion>();
        if (conditions != null) {
            for (int i = 0; i < conditions.length; i++) {
                String property = conditions[i].getProperty();
                Object value;
                if (conditions[i] instanceof ValuedCriterionCondition) {
                    value = ((ValuedCriterionCondition) conditions[i]).getToCompareWithValue();
                } else {
                    value = beanAccessorsInvoker.getBeanPropertyValue(bean, property);
                }
                list.add(constructExpression(conditions[i].getCondition(), property, value));
            }
        }
        return list.toArray(new Criterion[list.size()]);
    }

    @SuppressWarnings("unchecked")
    public static Criterion[] createCriterions(Object bean, String... matchProperties) {
        return createCriterions(bean, createMatchCriterions(matchProperties).toArray(new ICriterionCondition[0]));
    }

    public static Criterion constructExpression(ComparaisonOperator comparaisonOperator, String property, Object value) {
        switch (comparaisonOperator) {
        case EQUALS:
            return Restrictions.eq(property, value);
        case DIFFERENT:
            return Restrictions.ne(property, value);
        case SUP:
            return Restrictions.gt(property, value);
        case INF:
            return Restrictions.lt(property, value);
        case SUP_EQ:
            return Restrictions.ge(property, value);
        case INF_EQ:
            return Restrictions.le(property, value);
        case LIKE:
            return Restrictions.like(property, value);
        case IN:
            return Restrictions.in(property, (Collection) value);

        default:
            throw new UnsupportedOperationException("" + comparaisonOperator);
        }
    }

    public static Hashtable<String, Object> getColumnsValues(Object bean, String[] columns, boolean exitIfAnyIsNull) {
        Hashtable<String, Object> toReturn = new Hashtable<String, Object>();
        for (int i = 0; i < columns.length; i++) {

            Object value = beanAccessorsInvoker.getBeanPropertyValue(bean, columns[i]);
            if (exitIfAnyIsNull && value == null) {
                return null;
            }
            toReturn.put(columns[i], value);
        }
        return toReturn;
    }

    public Object retrieveByPrimaryKey(Object persistentBean, String notFoundExceptionKey) throws DBException,
            BusinessException {
        return retrieveByPrimaryKey(persistentBean, notFoundExceptionKey, null);
    }

    public Object retrieveByPrimaryKey(HibernateTransactionHolder transactionHolder, Object persistentBean,
            String notFoundExceptionKey) throws BusinessException, SystemException {
        return retrieveByPrimaryKey(transactionHolder, persistentBean, notFoundExceptionKey, null);
    }

    public Object retrieveByPrimaryKey(Object persistentBean, String notFoundExceptionKey,
            String[] dependentObjectsToLoad) throws DBException, BusinessException {
        HibernateTransactionHolder transactionHolder = (HibernateTransactionHolder) createTransaction();
        Object toReturn = retrieveByPrimaryKey(transactionHolder, persistentBean, notFoundExceptionKey,
                dependentObjectsToLoad);
        transactionHolder.commitAndClose();
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    public Object retrieveByPrimaryKey(ITransactionHolder transactionHolder, Object persistentBean,
            String notFoundExceptionKey, String[] dependentObjectsToLoad) throws DBException, BusinessException {
        Criterion[] criteria = createCriterions(persistentBean, createMatchCriterions(
                getKeyColumnsAccessors(persistentBean)).toArray(new ICriterionCondition[0]));
        Object[] beans = retrieve(transactionHolder, persistentBean, criteria, dependentObjectsToLoad).toArray();

        if (beans == null) {
            return null;
        }

        int size = beans.length;
        if (size > 1) {
            throw new UnicityException("Bean is not unique: " + beans + "\n\t--->count: " + size);
        } else if (size == 0) {
            throw new BusinessException(notFoundExceptionKey);
        }
        return beans[0];
    }

    public String[] getKeyColumnsAccessors(Object persistentDTO) {
        EntityPersister metadata = (EntityPersister) sessionFactory.getClassMetadata(persistentDTO.getClass());
        System.out.println("metadata.getClass() " + metadata.getClass());
        return null;
    }

    public static List<ICriterionCondition> createMatchCriterions(String... matchProperties) {
        if (matchProperties == null) {
            return null;
        }
        List<ICriterionCondition> toReturn = new ArrayList<ICriterionCondition>(matchProperties.length);
        for (int i = 0; i < matchProperties.length; i++) {
            toReturn.add(new BasicCriterionCondition(matchProperties[i], ComparaisonOperator.EQUALS));
        }
        return toReturn;
    }

    public Object[] retrieve(ITransactionHolder transaction, String hql, BasicItem<? extends Object, Object>[] params)
            throws DBException {
        return retrieve((HibernateTransactionHolder) transaction, hql, params, new String[0]);
    }

    public Object[] retrieve(ITransactionHolder transaction, String hql, BasicItem<? extends Object, Object>[] params,
            String[] dependentObjectsToLoad) throws DBException {
        try {
            Query query = ((HibernateTransactionHolder) transaction).getSession().createQuery(hql);
            if (params != null) {
                for (BasicItem<? extends Object, Object> param : params) {
                    Object key = (Object) param.getKey();
                    if (key instanceof Integer) {
                        query.setParameter((Integer) param.getKey(), param.getValue());
                    } else {
                        query.setParameter(String.valueOf(param.getKey()), param.getValue());
                    }
                }
            }
            Object[] toReturn = query.list().toArray();
            if (toReturn == null) {
                return null;
            }
            if (dependentObjectsToLoad != null) {
                callGetters(dependentObjectsToLoad, toReturn);
            }
            return toReturn;
        } catch (HibernateException e) {
            throw new DBException(hql, e);
        }
    }

    public List retrieveColumns(String hql) throws DBException {
        HibernateTransactionHolder transaction = (HibernateTransactionHolder) createTransaction();
        List toReturn = retrieveColumns(transaction, hql);
        transaction.commitAndClose();
        return toReturn;
    }

    public static List retrieveColumns(ITransactionHolder transaction, String hql) throws DBException {
        try {
            Query query = ((HibernateTransactionHolder) transaction).getSession().createQuery(hql);
            return query.list();
        } catch (HibernateException e) {
            ((HibernateTransactionHolder) transaction).commitAndClose();
            throw new DBException(hql, e);
        }
    }

    public Object[] retrieve(String hql, BasicItem<? extends Object, Object>[] params) throws DBException {
        return retrieve(hql, params, new String[0]);
    }

    public Object[] retrieve(String hql, BasicItem<? extends Object, Object>[] params, String[] dependentObjectsToLoad)
            throws DBException {
        HibernateTransactionHolder transaction = (HibernateTransactionHolder) createTransaction();
        Object[] toReturn = retrieve(transaction, hql, params, dependentObjectsToLoad);
        transaction.commitAndClose();
        return toReturn;
    }

    public <T> T retrieveUnique(ITransactionHolder transaction, T persistent, String[] matchProperties,
            String... dependentObjectsToLoad) throws DBException, BusinessException {

        Criterion[] criterions = HibernatePersistenceAdapter.createCriterions(persistent, matchProperties);

        List<T> result = retrieve(transaction, persistent, criterions, dependentObjectsToLoad);
        int size = result.size();
        if (size == 0) {
            throw new ItemNotFoundException(persistent, matchProperties);
        }
        if (size > 1) {
            throw new UnicityException(persistent, matchProperties);
        }
        return result.get(0);
    }

    public <T> T retrieveUnique(T persistent, String[] matchProperties, String... dependentObjectsToLoad)
            throws DBException, BusinessException {
        HibernateTransactionHolder transaction = (HibernateTransactionHolder) createTransaction();
        T result = retrieveUnique(transaction, persistent, matchProperties, dependentObjectsToLoad);
        transaction.commitAndClose();
        return result;
    }
}
