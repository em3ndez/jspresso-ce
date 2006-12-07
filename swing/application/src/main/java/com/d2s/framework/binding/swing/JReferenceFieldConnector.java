/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.binding.swing;

import java.util.Collection;
import java.util.Collections;

import com.d2s.framework.binding.ConnectorValueChangeEvent;
import com.d2s.framework.binding.ICompositeValueConnector;
import com.d2s.framework.binding.IConnectorValueChangeListener;
import com.d2s.framework.binding.IValueConnector;
import com.d2s.framework.gui.swing.components.JActionField;

/**
 * JReferenceFieldConnector connector.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class JReferenceFieldConnector extends JActionFieldConnector implements
    ICompositeValueConnector {

  private IValueConnector               toStringPropertyConnector;
  private IConnectorValueChangeListener toStringListener;

  /**
   * Constructs a new <code>JActionFieldConnector</code> instance.
   * 
   * @param id
   *          the id of the connector.
   * @param actionField
   *          the connected JActionField.
   */
  public JReferenceFieldConnector(String id, JActionField actionField) {
    super(id, actionField);
    toStringListener = new ToStringConnectorListener();
  }

  /**
   * {@inheritDoc}
   */
  public void addChildConnector(@SuppressWarnings("unused")
  IValueConnector childConnector) {
    throw new UnsupportedOperationException(
        "Child connectors cannot be added to action field connector");
  }

  /**
   * {@inheritDoc}
   */
  public boolean areChildrenReadable() {
    return isReadable();
  }

  /**
   * {@inheritDoc}
   */
  public boolean areChildrenWritable() {
    return isWritable();
  }

  /**
   * {@inheritDoc}
   */
  public IValueConnector getChildConnector(String connectorKey) {
    if (connectorKey.equals(toStringPropertyConnector.getId())) {
      return toStringPropertyConnector;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public int getChildConnectorCount() {
    if (toStringPropertyConnector != null) {
      return 1;
    }
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<String> getChildConnectorKeys() {
    if (toStringPropertyConnector != null) {
      return Collections.singleton(toStringPropertyConnector.getId());
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JReferenceFieldConnector clone() {
    return clone(getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JReferenceFieldConnector clone(String newConnectorId) {
    JReferenceFieldConnector clonedConnector = (JReferenceFieldConnector) super
        .clone(newConnectorId);
    if (toStringPropertyConnector != null) {
      clonedConnector.toStringPropertyConnector = toStringPropertyConnector
          .clone();
    }
    return clonedConnector;
  }

  /**
   * Sets the toStringPropertyConnector.
   * 
   * @param toStringPropertyConnector
   *          the toStringPropertyConnector to set.
   */
  public void setToStringPropertyConnector(
      IValueConnector toStringPropertyConnector) {
    if (this.toStringPropertyConnector != null) {
      this.toStringPropertyConnector
          .removeConnectorValueChangeListener(toStringListener);
    }
    this.toStringPropertyConnector = toStringPropertyConnector;
    if (this.toStringPropertyConnector != null) {
      this.toStringPropertyConnector
          .addConnectorValueChangeListener(toStringListener);
    }
  }

  private final class ToStringConnectorListener implements
      IConnectorValueChangeListener {

    /**
     * {@inheritDoc}
     */
    public void connectorValueChange(@SuppressWarnings("unused")
    ConnectorValueChangeEvent evt) {
      protectedSetConnecteeValue(getConnecteeValue());
    }

  }
}
