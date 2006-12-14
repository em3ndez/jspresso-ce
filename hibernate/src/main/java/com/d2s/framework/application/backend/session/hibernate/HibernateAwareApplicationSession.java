/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.application.backend.session.hibernate;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.collection.PersistentList;
import org.hibernate.collection.PersistentSet;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.d2s.framework.application.backend.session.basic.BasicApplicationSession;
import com.d2s.framework.model.descriptor.ICollectionPropertyDescriptor;
import com.d2s.framework.model.descriptor.IPropertyDescriptor;
import com.d2s.framework.model.entity.IEntity;
import com.d2s.framework.util.bean.PropertyHelper;

/**
 * Basic implementation of an application session aware of hibernate when
 * merging back entities from the unit of work.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 *
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class HibernateAwareApplicationSession extends BasicApplicationSession {

  private HibernateTemplate hibernateTemplate;
  private boolean           traversedPendingOperations = false;

  /**
   * Does the unit of work back merge in the scope of an hibernate session.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public void commitUnitOfWork() {
    try {
      hibernateTemplate.execute(new HibernateCallback() {

        /**
         * {@inheritDoc}
         */
        public Object doInHibernate(Session session) {
          for (IEntity entityToMergeBack : getEntitiesToMergeBack()) {
            if (entityToMergeBack.isPersistent()) {
              try {
                session.lock(entityToMergeBack, LockMode.NONE);
              } catch (Exception ex) {
                session.evict(session.get(entityToMergeBack.getContract(),
                    entityToMergeBack.getId()));
                session.lock(entityToMergeBack, LockMode.NONE);
              }
            }
          }
          performActualUnitOfWorkCommit();
          return null;
        }
      });
    } finally {
      traversedPendingOperations = false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void rollbackUnitOfWork() {
    try {
      super.rollbackUnitOfWork();
    } finally {
      traversedPendingOperations = false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void performPendingOperations() {
    if (!traversedPendingOperations) {
      traversedPendingOperations = true;
      hibernateTemplate.execute(new HibernateCallback() {

        /**
         * {@inheritDoc}
         */
        public Object doInHibernate(Session session) {
          boolean flushIsNecessary = false;
          List<IEntity> entitiesToUpdate = getEntitiesRegisteredForUpdate();
          if (entitiesToUpdate != null) {
            for (IEntity entityToUpdate : entitiesToUpdate) {
              IEntity sessionEntity;
              try {
                session.lock(entityToUpdate, LockMode.NONE);
                cloneInUnitOfWork(entityToUpdate);
                sessionEntity = entityToUpdate;
              } catch (Exception ex) {
                sessionEntity = (IEntity) session.get(entityToUpdate
                    .getContract(), entityToUpdate.getId());
              }
              session.saveOrUpdate(sessionEntity);
              flushIsNecessary = true;
            }
          }
          if (flushIsNecessary) {
            session.flush();
          }
          Set<IEntity> entitiesToDelete = getEntitiesRegisteredForDeletion();
          if (entitiesToDelete != null) {
            for (IEntity entityToDelete : entitiesToDelete) {
              IEntity sessionEntity;
              try {
                session.lock(entityToDelete, LockMode.NONE);
                sessionEntity = entityToDelete;
              } catch (Exception ex) {
                sessionEntity = (IEntity) session.get(entityToDelete
                    .getContract(), entityToDelete.getId());
              }
              session.delete(sessionEntity);
              flushIsNecessary = true;
            }
          }
          if (flushIsNecessary) {
            session.flush();
          }
          return null;
        }
      });
    }
  }

  private void performActualUnitOfWorkCommit() {
    super.commitUnitOfWork();
  }

  /**
   * This method wraps transient collections in the equivalent hibernate ones.
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Collection<IEntity> wrapDetachedEntityCollection(IEntity entity,
      Collection<IEntity> transientCollection,
      Collection<IEntity> snapshotCollection, String role) {
    if (!(transientCollection instanceof PersistentCollection)) {
      if (entity.isPersistent()) {
        if (transientCollection instanceof Set) {
          PersistentSet persistentSet = new PersistentSet(null,
              (Set<?>) transientCollection);
          persistentSet.setOwner(entity);
          HashMap<Object, Object> snapshot = new HashMap<Object, Object>();
          if (snapshotCollection == null) {
            persistentSet.clearDirty();
            snapshotCollection = transientCollection;
          }
          for (Object snapshotCollectionElement : snapshotCollection) {
            snapshot.put(snapshotCollectionElement, snapshotCollectionElement);
          }
          persistentSet.setSnapshot(entity.getId(), getHibernateRoleName(entity
              .getContract(), role), snapshot);
          return persistentSet;
        } else if (transientCollection instanceof List) {
          PersistentList persistentList = new PersistentList(null,
              (List<?>) transientCollection);
          persistentList.setOwner(entity);
          ArrayList<Object> snapshot = new ArrayList<Object>();
          if (snapshotCollection == null) {
            persistentList.clearDirty();
            snapshotCollection = transientCollection;
          }
          for (Object snapshotCollectionElement : snapshotCollection) {
            snapshot.add(snapshotCollectionElement);
          }
          persistentList.setSnapshot(entity.getId(), getHibernateRoleName(
              entity.getContract(), role), snapshot);
          return persistentList;
        }
      }
    } else {
      if (snapshotCollection == null) {
        ((PersistentCollection) transientCollection).clearDirty();
      } else {
        ((PersistentCollection) transientCollection).dirty();
      }
    }
    return super.wrapDetachedEntityCollection(entity, transientCollection,
        snapshotCollection, role);
  }

  /**
   * Sets the hibernateTemplate.
   *
   * @param hibernateTemplate
   *          the hibernateTemplate to set.
   */
  public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
    this.hibernateTemplate = hibernateTemplate;
  }

  private static String getHibernateRoleName(Class<?> entityContract,
      String role) {
    PropertyDescriptor roleDescriptor = PropertyHelper.getPropertyDescriptor(
        entityContract, role);
    Class<?> entityDeclaringClass = roleDescriptor.getReadMethod()
        .getDeclaringClass();
    if (!IEntity.class.isAssignableFrom(entityDeclaringClass)) {
      entityDeclaringClass = entityContract;
    }
    return entityDeclaringClass.getName() + "." + role;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInitialized(Object objectOrProxy) {
    return Hibernate.isInitialized(objectOrProxy);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void initializePropertyIfNeeded(final IEntity entity,
      IPropertyDescriptor propertyDescriptor) {
    final String propertyName = propertyDescriptor.getName();
    if (Hibernate.isInitialized(entity.straightGetProperty(propertyName))) {
      return;
    }
    boolean dirtRecorderWasEnabled = getDirtRecorder().isEnabled();
    try {
      getDirtRecorder().setEnabled(false);

      hibernateTemplate.setFlushMode(HibernateAccessor.FLUSH_NEVER);
      Object propertyValue = hibernateTemplate.execute(new HibernateCallback() {

        /**
         * {@inheritDoc}
         */
        public Object doInHibernate(Session session) {
          HibernateAwareApplicationSession
              .cleanPersistentCollectionDirtyState(entity);
          Object initializedProperty = entity.straightGetProperty(propertyName);
          if (entity.isPersistent()) {
            try {
              session.lock(entity, LockMode.NONE);
            } catch (Exception ex) {
              session.evict(session.get(entity.getContract(), entity.getId()));
              session.lock(entity, LockMode.NONE);
            }
          } else if (initializedProperty instanceof IEntity) {
            try {
              session.lock(initializedProperty, LockMode.NONE);
            } catch (Exception ex) {
              session.evict(session.get(((IEntity) initializedProperty)
                  .getContract(), ((IEntity) initializedProperty).getId()));
              session.lock(initializedProperty, LockMode.NONE);
            }
          }

          Hibernate.initialize(initializedProperty);
          return initializedProperty;
        }
      });
      if (propertyDescriptor instanceof ICollectionPropertyDescriptor) {
        sortCollectionProperty(entity, propertyName);
        if (propertyValue instanceof PersistentCollection) {
          ((PersistentCollection) propertyValue).clearDirty();
        }
        for (Iterator<IEntity> ite = ((Collection) propertyValue).iterator(); ite
            .hasNext();) {
          IEntity collectionElement = ite.next();
          if (isEntityRegisteredForDeletion(collectionElement)) {
            ite.remove();
          }
        }
      }
    } finally {
      getDirtRecorder().setEnabled(dirtRecorderWasEnabled);
    }
  }

  /**
   * Whenever the entity has dirty persistent collection, make them clean to
   * workaround a "bug" with hibernate since hibernate cannot re-attach a
   * "dirty" detached collection.
   *
   * @param entity
   *          the entity to clean the collections dirty state of.
   */
  public static void cleanPersistentCollectionDirtyState(IEntity entity) {
    if (entity != null) {
      // Whenever the entity has dirty persistent collection, make them
      // clean to workaround a "bug" with hibernate since hibernate cannot
      // re-attach a "dirty" detached collection.
      for (Map.Entry<String, Object> registeredPropertyEntry : entity
          .straightGetProperties().entrySet()) {
        if (registeredPropertyEntry.getValue() instanceof PersistentCollection
            && Hibernate.isInitialized(registeredPropertyEntry.getValue())) {
          ((PersistentCollection) registeredPropertyEntry.getValue())
              .clearDirty();
        }
      }
    }
  }

}
