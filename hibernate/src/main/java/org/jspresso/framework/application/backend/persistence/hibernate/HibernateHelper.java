/*
 * Copyright (c) 2005-2012 Vincent Vandenschrick. All rights reserved.
 *
 *  This file is part of the Jspresso framework.
 *
 *  Jspresso is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Jspresso is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Jspresso.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jspresso.framework.application.backend.persistence.hibernate;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.jspresso.framework.model.component.IComponent;
import org.jspresso.framework.model.entity.IEntity;
import org.jspresso.framework.util.bean.MissingPropertyException;
import org.jspresso.framework.util.bean.PropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class for Hibernate.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public final class HibernateHelper {

  private static final Logger LOG = LoggerFactory
                                      .getLogger(HibernateHelper.class);

  /**
   * Constructs a new <code>HibernateUtils</code> instance.
   */
  private HibernateHelper() {
    // Helper constructor
  }

  /**
   * Retrieves a component contract without initializing an entity.
   * 
   * @param component
   *          the componennt to rerieve the contract for.
   * @return the component contract.
   */
  public static <E extends IComponent> Class<? extends E> getComponentContract(
      E component) {
    if (!Hibernate.isInitialized(component)) {
      if (component instanceof HibernateProxy) {
        try {
          return (Class<? extends E>) Class
              .forName(((HibernateProxy) component)
                  .getHibernateLazyInitializer().getEntityName());
        } catch (ClassNotFoundException ex) {
          LOG.warn(
              "Can not retrieve entity class {} without initializing entity.",
              ((HibernateProxy) component).getHibernateLazyInitializer()
                  .getEntityName());
        }
      }
    }
    return (Class<? extends E>) component.getComponentContract();
  }

  /**
   * Test Object equality potentially unwrapping Hibernate proxies.
   * 
   * @param e1
   *          the 1st entity to test.
   * @param e2
   *          the 2nd entity to test.
   * @return true if they both refer to the same object reference.
   */
  public static boolean objectEquals(IEntity e1, IEntity e2) {
    IEntity actualE1 = e1;
    IEntity actualE2 = e2;

    if (actualE1 instanceof HibernateProxy) {
      actualE1 = (IEntity) ((HibernateProxy) actualE1)
          .getHibernateLazyInitializer().getImplementation();
    }
    if (actualE2 instanceof HibernateProxy) {
      actualE2 = (IEntity) ((HibernateProxy) actualE2)
          .getHibernateLazyInitializer().getImplementation();
    }
    return actualE1 == actualE2;
  }

  /**
   * Whenever the entity has dirty persistent collection, make them clean to
   * workaround a "bug" with hibernate since hibernate cannot re-attach a
   * "dirty" detached collection.
   * 
   * @param componentOrEntity
   *          the entity to clean the collections dirty state of.
   * @param targetSession
   *          the session that is targetted to after the dirty states have been
   *          reset or null if none.
   */
  public static void clearPersistentCollectionDirtyState(
      IComponent componentOrEntity, Session targetSession) {
    if (componentOrEntity != null) {
      // Whenever the entity has dirty persistent collection, make them
      // clean to workaround a "bug" with hibernate since hibernate cannot
      // re-attach a "dirty" detached collection.
      for (Map.Entry<String, Object> registeredPropertyEntry : componentOrEntity
          .straightGetProperties().entrySet()) {
        if (registeredPropertyEntry.getValue() instanceof PersistentCollection) {
          PersistentCollection persistentCollection = (PersistentCollection) registeredPropertyEntry
              .getValue();
          if (Hibernate.isInitialized(registeredPropertyEntry.getValue())) {
            (persistentCollection).clearDirty();
          }
          if (persistentCollection instanceof AbstractPersistentCollection
              && targetSession != ((AbstractPersistentCollection) persistentCollection)
                  .getSession()) {
            // The following is to avoid to avoid Hibernate exceptions due to
            // reassociating a collection that is already associated with the
            // session.
            persistentCollection
                .unsetSession(((AbstractPersistentCollection) persistentCollection)
                    .getSession());
          }
        }
      }
    }
  }

  /**
   * Computes the Hibernate role name of a persistent collection.
   * 
   * @param entityContract
   *          the entity contract owing the collection.
   * @param property
   *          the property name to compute the role name for.
   * @return the Hibernate role name of the persistent collection.
   */
  public static String getHibernateRoleName(Class<?> entityContract,
      String property) {
    // have to find the highest entity class declaring the collection role.
    PropertyDescriptor roleDescriptor;
    try {
      roleDescriptor = PropertyHelper.getPropertyDescriptor(entityContract,
          property);
    } catch (MissingPropertyException ex) {
      return null;
    }
    Class<?> propertyDeclaringClass = roleDescriptor.getReadMethod()
        .getDeclaringClass();
    Class<?> roleClass;
    if (IEntity.class.isAssignableFrom(propertyDeclaringClass)) {
      roleClass = propertyDeclaringClass;
    } else {
      roleClass = getHighestEntityClassInRole(entityContract,
          propertyDeclaringClass);
    }
    return roleClass.getName() + "." + property;
  }

  private static Class<?> getHighestEntityClassInRole(Class<?> entityContract,
      Class<?> propertyDeclaringClass) {
    for (Class<?> superInterface : entityContract.getInterfaces()) {
      if (IEntity.class.isAssignableFrom(superInterface)
          && propertyDeclaringClass.isAssignableFrom(superInterface)) {
        return getHighestEntityClassInRole(superInterface,
            propertyDeclaringClass);
      }
    }
    return entityContract;
  }

}
