/*
 * Copyright (c) 2005-2013 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.application.backend.component;

import java.lang.reflect.Proxy;

import org.jspresso.framework.application.backend.BackendControllerHolder;
import org.jspresso.framework.application.backend.IBackendController;
import org.jspresso.framework.application.backend.session.IApplicationSessionAware;
import org.jspresso.framework.model.component.IComponent;
import org.jspresso.framework.model.component.IComponentCollectionFactory;
import org.jspresso.framework.model.component.IComponentExtension;
import org.jspresso.framework.model.component.IComponentExtensionFactory;
import org.jspresso.framework.model.component.IComponentFactory;
import org.jspresso.framework.model.component.basic.BasicComponentInvocationHandler;
import org.jspresso.framework.model.descriptor.ICollectionPropertyDescriptor;
import org.jspresso.framework.model.descriptor.IComponentDescriptor;
import org.jspresso.framework.model.descriptor.IPropertyDescriptor;
import org.jspresso.framework.model.descriptor.IReferencePropertyDescriptor;
import org.jspresso.framework.model.descriptor.IStringPropertyDescriptor;
import org.jspresso.framework.model.entity.EntityHelper;
import org.jspresso.framework.model.entity.IEntityLifecycleHandlerAware;
import org.jspresso.framework.security.ISubjectAware;
import org.jspresso.framework.util.accessor.IAccessorFactory;

/**
 * This component invocation handler handles initialization of lazy loaded
 * properties like collections an entity references, delegating the
 * initialization job to the backend controller.
 * 
 * @author Vincent Vandenschrick
 */
public class ControllerAwareComponentInvocationHandler extends
    BasicComponentInvocationHandler {

  private static final long serialVersionUID = -3613223267370638150L;

  /**
   * Constructs a new {@code ControllerAwareEntityInvocationHandler}
   * instance.
   *
   * @param componentDescriptor
   *          The descriptor of the proxy entity.
   * @param inlineComponentFactory
   *          the factory used to create inline components.
   * @param collectionFactory
   *          The factory used to create empty entity collections from
   *          collection getters.
   * @param accessorFactory
   *          The factory used to access proxy properties.
   * @param extensionFactory
   *          The factory used to create entity extensions based on their
   *          classes.
   */
  public ControllerAwareComponentInvocationHandler(
      IComponentDescriptor<IComponent> componentDescriptor,
      IComponentFactory inlineComponentFactory,
      IComponentCollectionFactory collectionFactory,
      IAccessorFactory accessorFactory,
      IComponentExtensionFactory extensionFactory) {
    super(componentDescriptor, inlineComponentFactory, collectionFactory,
        accessorFactory, extensionFactory);
  }

  /**
   * Sets the JAAS subject to subject aware extensions.
   * <p>
   * {@inheritDoc}
   */
  @Override
  protected void configureExtension(IComponentExtension<IComponent> extension) {
    super.configureExtension(extension);
    if (getBackendController() != null) {
      if (extension instanceof ISubjectAware) {
        ((ISubjectAware) extension).setSubject(getBackendController()
            .getApplicationSession().getSubject());
      }
      if (extension instanceof IApplicationSessionAware) {
        ((IApplicationSessionAware) extension)
            .setApplicationSession(getBackendController()
                .getApplicationSession());
      }
    }
    if (extension instanceof IEntityLifecycleHandlerAware) {
      ((IEntityLifecycleHandlerAware) extension)
          .setEntityLifecycleHandler(getBackendController());
    }
  }

  /**
   * Gets the backendController.
   * 
   * @return the backendController.
   */
  protected IBackendController getBackendController() {
    return BackendControllerHolder.getCurrentBackendController();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getCollectionProperty(Object proxy,
      ICollectionPropertyDescriptor<? extends IComponent> propertyDescriptor) {
    getBackendController().initializePropertyIfNeeded((IComponent) proxy,
        propertyDescriptor.getName());
    return super.getCollectionProperty(proxy, propertyDescriptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getReferenceProperty(Object proxy,
      IReferencePropertyDescriptor<IComponent> propertyDescriptor) {
    String propertyName = propertyDescriptor.getName();
    getBackendController().initializePropertyIfNeeded((IComponent) proxy,
        propertyName);
    return super.getReferenceProperty(proxy, propertyDescriptor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isInitialized(Object objectOrProxy) {
    return getBackendController().isInitialized(objectOrProxy);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void storeReferenceProperty(Object proxy,
      IReferencePropertyDescriptor<?> propertyDescriptor,
      Object oldPropertyValue, Object newPropertyValue) {
    if (newPropertyValue != null
        && EntityHelper.isInlineComponentReference(propertyDescriptor)
        && !propertyDescriptor.isComputed()) {
      if (Proxy.isProxyClass(newPropertyValue.getClass())
          && Proxy.getInvocationHandler(newPropertyValue) instanceof BasicComponentInvocationHandler
          && !(Proxy.getInvocationHandler(newPropertyValue) instanceof ControllerAwareComponentInvocationHandler)) {
        IComponent sessionAwareComponent = getInlineComponentFactory()
            .createComponentInstance(
                ((IComponent) newPropertyValue).getComponentContract());
        sessionAwareComponent
            .straightSetProperties(((IComponent) newPropertyValue)
                .straightGetProperties());
        super.storeReferenceProperty(proxy, propertyDescriptor,
            oldPropertyValue, sessionAwareComponent);
      } else {
        super.storeReferenceProperty(proxy, propertyDescriptor,
            oldPropertyValue, newPropertyValue);
      }
    } else {
      super.storeReferenceProperty(proxy, propertyDescriptor, oldPropertyValue,
          newPropertyValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isCollectionSortOnReadEnabled() {
    // To prevent erratic sort of collections during flush
    return !getBackendController().isUnitOfWorkActive();
  }

  /**
   * Performs necessary checks in order to ensure isolation on unit of work.
   * <p>
   * {@inheritDoc}
   */
  @Override
  protected Object sanitizeModifierParam(Object target,
      IPropertyDescriptor propertyDescriptor, Object param) {
    return getBackendController().sanitizeModifierParam(target,
        propertyDescriptor, param);
  }

  /**
   * Delegates to backend controller.
   * <p>
   * {@inheritDoc}
   */
  @Override
  protected boolean isDirtyTrackingEnabled() {
    return getBackendController().isDirtyTrackingEnabled();
  }

  /**
   * Delegates to backend controller.
   * <p>
   * {@inheritDoc}
   */
  @Override
  protected void setDirtyTrackingEnabled(boolean enabled) {
    getBackendController().setDirtyTrackingEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String invokeNlsGetter(Object proxy, IStringPropertyDescriptor propertyDescriptor) {
    return getNlsPropertyValue(proxy, propertyDescriptor, getBackendController().getLocale());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void invokeNlsSetter(Object proxy, IStringPropertyDescriptor propertyDescriptor, String translatedValue) {
    setNlsPropertyValue(proxy, propertyDescriptor, translatedValue, getBackendController().getEntityFactory(),
        getBackendController().getLocale());
  }
}
