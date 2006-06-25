/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.binding.model;

import com.d2s.framework.binding.IValueConnector;
import com.d2s.framework.model.descriptor.ICollectionDescriptor;
import com.d2s.framework.model.descriptor.ICollectionPropertyDescriptor;
import com.d2s.framework.model.descriptor.IComponentDescriptor;
import com.d2s.framework.model.descriptor.IComponentDescriptorRegistry;
import com.d2s.framework.model.descriptor.IModelDescriptor;
import com.d2s.framework.model.descriptor.IPropertyDescriptor;
import com.d2s.framework.model.descriptor.IReferencePropertyDescriptor;
import com.d2s.framework.model.descriptor.IScalarPropertyDescriptor;
import com.d2s.framework.util.accessor.IAccessorFactory;

/**
 * Default implementation for model connectors factory.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class DefaultModelConnectorFactory implements IModelConnectorFactory {

  private IAccessorFactory          accessorFactory;
  private IComponentDescriptorRegistry descriptorRegistry;

  /**
   * TODO Comment needed.
   * <p>
   * {@inheritDoc}
   */
  public IValueConnector createModelConnector(IModelDescriptor modelDescriptor) {
    if (modelDescriptor instanceof IComponentDescriptor) {
      return new ModelConnector((IComponentDescriptor) modelDescriptor, this);
    } else if (modelDescriptor instanceof ICollectionDescriptor) {
      return new ModelCollectionConnector(
          (ICollectionDescriptor) modelDescriptor, this);
    } else if (modelDescriptor instanceof IPropertyDescriptor) {
      if (modelDescriptor instanceof IReferencePropertyDescriptor) {
        return new ModelRefPropertyConnector(
            (IReferencePropertyDescriptor) modelDescriptor, this);
      } else if (modelDescriptor instanceof ICollectionPropertyDescriptor) {
        return new ModelCollectionPropertyConnector(
            (ICollectionPropertyDescriptor) modelDescriptor, this);
      } else if (modelDescriptor instanceof IScalarPropertyDescriptor) {
        return new ModelScalarPropertyConnector(
            (IScalarPropertyDescriptor) modelDescriptor, accessorFactory);
      }
    }
    return null;
  }

  /**
   * Sets the factory for the accessors used to access the model properties.
   * 
   * @param accessorFactory
   *          The <code>IAccessorFactory</code> to use.
   */
  public void setAccessorFactory(IAccessorFactory accessorFactory) {
    this.accessorFactory = accessorFactory;
  }

  /**
   * {@inheritDoc}
   */
  public IAccessorFactory getAccessorFactory() {
    return accessorFactory;
  }

  
  /**
   * Gets the descriptorRegistry.
   * 
   * @return the descriptorRegistry.
   */
  public IComponentDescriptorRegistry getDescriptorRegistry() {
    return descriptorRegistry;
  }

  
  /**
   * Sets the descriptorRegistry.
   * 
   * @param descriptorRegistry the descriptorRegistry to set.
   */
  public void setDescriptorRegistry(
      IComponentDescriptorRegistry descriptorRegistry) {
    this.descriptorRegistry = descriptorRegistry;
  }
}
