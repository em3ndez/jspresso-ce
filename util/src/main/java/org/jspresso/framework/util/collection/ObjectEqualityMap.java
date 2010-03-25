/*
 * Copyright (c) 2005-2010 Vincent Vandenschrick. All rights reserved.
 */
package org.jspresso.framework.util.collection;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jspresso.framework.util.bean.IPropertyChangeCapable;
import org.jspresso.framework.util.bean.SinglePropertyChangeSupport;

/**
 * a map which equality is based on object identity.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 * @param <K>
 *          the key class.
 * @param <V>
 *          the value class.
 */
public class ObjectEqualityMap<K, V> extends HashMap<K, V> implements
    IPropertyChangeCapable {

  private static final long               serialVersionUID = 8981204989863563244L;

  private transient PropertyChangeSupport propertyChangeSupport;

  /**
   * Constructs a new <code>ObjectEqualityMap</code> instance.
   */
  public ObjectEqualityMap() {
    super();
    propertyChangeSupport = new SinglePropertyChangeSupport(this);
  }

  /**
   * Constructs a new <code>ObjectEqualityMap</code> instance.
   * 
   * @param initialCapacity
   *          initialCapacity.
   */
  public ObjectEqualityMap(int initialCapacity) {
    super(initialCapacity);
    propertyChangeSupport = new SinglePropertyChangeSupport(this);
  }

  /**
   * Constructs a new <code>ObjectEqualityMap</code> instance.
   * 
   * @param initialCapacity
   *          initialCapacity.
   * @param loadFactor
   *          loadFactor.
   */
  public ObjectEqualityMap(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
    propertyChangeSupport = new SinglePropertyChangeSupport(this);
  }

  /**
   * Constructs a new <code>ObjectEqualityMap</code> instance.
   * 
   * @param m
   *          map.
   */
  public ObjectEqualityMap(Map<? extends K, ? extends V> m) {
    super(m);
    propertyChangeSupport = new SinglePropertyChangeSupport(this);
  }

  /**
   * {@inheritDoc}
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  /**
   * {@inheritDoc}
   */
  public void addPropertyChangeListener(String propertyName,
      PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
  }

  /**
   * Solely based on object's equality.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    return this == o;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Associates the specified value with the specified key in this map. If the
   * map previously contained a mapping for the key, the old value is replaced.
   * 
   * @param key
   *          key with which the specified value is to be associated
   * @param value
   *          value to be associated with the specified key
   * @return the previous value associated with <tt>key</tt>, or <tt>null</tt>
   *         if there was no mapping for <tt>key</tt>. (A <tt>null</tt> return
   *         can also indicate that the map previously associated <tt>null</tt>
   *         with <tt>key</tt>.)
   */
  @Override
  public V put(K key, V value) {
    V putVal = super.put(key, value);
    Object oldValue = putVal;
    if (oldValue instanceof Collection<?>) {
      oldValue = new ArrayList<Object>((Collection<?>) oldValue) {

        private static final long serialVersionUID = 7466229820747338355L;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
          return this == o;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
          return super.hashCode();
        }
      };
    }
    propertyChangeSupport.firePropertyChange(key.toString(), oldValue, value);
    return putVal;
  }

  /**
   * {@inheritDoc}
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  /**
   * {@inheritDoc}
   */
  public void removePropertyChangeListener(String propertyName,
      PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
  }

  /**
   * @param propertyName
   *          the property name.
   * @param oldValue
   *          the old value.
   * @param newValue
   *          the new value.
   * @see java.beans.PropertyChangeSupport#firePropertyChange(java.lang.String,
   *      java.lang.Object, java.lang.Object)
   */
  protected void firePropertyChange(String propertyName, Object oldValue,
      Object newValue) {
    propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }

  private void readObject(ObjectInputStream in) throws IOException,
      ClassNotFoundException {
    in.defaultReadObject();
    propertyChangeSupport = new SinglePropertyChangeSupport(this);
  }
}
