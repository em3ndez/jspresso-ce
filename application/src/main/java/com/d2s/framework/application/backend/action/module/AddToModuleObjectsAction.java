/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.application.backend.action.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.d2s.framework.application.backend.action.AbstractCollectionAction;
import com.d2s.framework.application.model.BeanModule;
import com.d2s.framework.binding.ConnectorHelper;
import com.d2s.framework.binding.ICompositeValueConnector;
import com.d2s.framework.model.descriptor.ICollectionDescriptor;
import com.d2s.framework.model.descriptor.IComponentDescriptor;
import com.d2s.framework.model.entity.IEntity;
import com.d2s.framework.util.bean.IPropertyChangeCapable;
import com.d2s.framework.view.action.ActionContextConstants;
import com.d2s.framework.view.action.IActionHandler;

/**
 * This action adds a new object in the projected collection.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class AddToModuleObjectsAction extends AbstractCollectionAction {

  /**
   * Adds a new object in the projected collection.
   * <p>
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public void execute(@SuppressWarnings("unused")
  IActionHandler actionHandler, Map<String, Object> context) {
    ICompositeValueConnector moduleConnector = getModuleConnector(context);
    BeanModule module = (BeanModule) moduleConnector.getConnectorValue();
    IComponentDescriptor projectedComponentDescriptor = ((ICollectionDescriptor) getModelDescriptor(context))
        .getElementDescriptor();

    Collection<IPropertyChangeCapable> projectedCollection;
    if (module.getModuleObjects() == null) {
      projectedCollection = new ArrayList<IPropertyChangeCapable>();
    } else {
      projectedCollection = new ArrayList<IPropertyChangeCapable>(module
          .getModuleObjects());
    }
    IEntity newEntity = getEntityFactory(context).createEntityInstance(
        (Class<? extends IEntity>) projectedComponentDescriptor
            .getComponentContract());
    projectedCollection.add(newEntity);
    module.setModuleObjects(projectedCollection);

    getModelConnector(context).setConnectorValue(projectedCollection);

    context.put(ActionContextConstants.SELECTED_INDICES, ConnectorHelper
        .getIndicesOf(getModelConnector(context), Collections
            .singleton(newEntity)));
  }
}
