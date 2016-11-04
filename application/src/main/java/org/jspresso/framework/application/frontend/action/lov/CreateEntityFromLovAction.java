/*
 * Copyright (c) 2005-2016 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.application.frontend.action.lov;

import java.util.Arrays;
import java.util.Map;

import org.jspresso.framework.application.frontend.action.ModalDialogAction;
import org.jspresso.framework.application.frontend.action.std.EditComponentAction;
import org.jspresso.framework.model.component.IComponent;
import org.jspresso.framework.model.component.IQueryComponent;
import org.jspresso.framework.model.descriptor.IComponentDescriptor;
import org.jspresso.framework.model.entity.IEntity;
import org.jspresso.framework.model.entity.IEntityFactory;
import org.jspresso.framework.view.descriptor.IViewDescriptor;
import org.jspresso.framework.view.descriptor.basic.BasicComponentViewDescriptor;
import org.jspresso.framework.view.descriptor.basic.BasicViewDescriptor;

/**
 * The type Create entity from lov action.
 *
 * @param <E>
 *     the type parameter
 * @param <F>
 *     the type parameter
 * @param <G>
 *     the type parameter
 */
public class CreateEntityFromLovAction<E, F, G> extends EditComponentAction<E, F, G> {

  /**
   * Gets component to edit.
   *
   * @param context
   *     the context
   * @return the component to edit
   */
  @Override
  protected Object getComponentToEdit(Map<String, Object> context) {
    IEntityFactory entityFactory = getBackendController(context).getEntityFactory();
    IQueryComponent lovQueryComponent = (IQueryComponent) context.get(IQueryComponent.QUERY_COMPONENT);
    Class<IEntity> entityToCreateContract = lovQueryComponent.getQueryContract();

    String dialogTitle = getI18nName(getTranslationProvider(context), getLocale(context));
    dialogTitle += " : " + lovQueryComponent.getQueryDescriptor().getI18nName(getBackendController(context), getBackendController(context).getLocale());
    context.put(ModalDialogAction.DIALOG_TITLE, dialogTitle);

    IEntity entityInstance = entityFactory.createEntityInstance(entityToCreateContract);
    setActionParameter(Arrays.asList(entityInstance), context);
    return entityInstance;
  }

  /**
   * Gets view descriptor.
   *
   * @param context
   *     the context
   * @return the view descriptor
   */
  @SuppressWarnings("unchecked")
  @Override
  protected IViewDescriptor getViewDescriptor(Map<String, Object> context) {
    IViewDescriptor viewDescriptor = super.getViewDescriptor(context);
    
    if (viewDescriptor == null) {
      
      ILovViewDescriptorForCreationFactory factory = (ILovViewDescriptorForCreationFactory) context.get(LovAction.CREATE_ENTITY_LOV_VIEW_DESCRIPTOR_FACTORY);
      if (factory!=null) {
        
        IQueryComponent lovQueryComponent = (IQueryComponent) context.get(IQueryComponent.QUERY_COMPONENT);
        IComponentDescriptor<IComponent> entityToCreateDescriptor = (IComponentDescriptor<IComponent>) lovQueryComponent.getQueryDescriptor();
        viewDescriptor = factory.createLovViewDescriptorForCreation(entityToCreateDescriptor, context);
      }
    }
      
    if (viewDescriptor == null) {
      
      IEntityFactory entityFactory = getBackendController(context).getEntityFactory();
      IQueryComponent lovQueryComponent = (IQueryComponent) context.get(IQueryComponent.QUERY_COMPONENT);
      Class<IEntity> entityToCreateContract = lovQueryComponent.getQueryContract();
      final IComponentDescriptor<?> entityToCreateDescriptor = entityFactory.getComponentDescriptor(entityToCreateContract);
  
      viewDescriptor = new BasicComponentViewDescriptor();
      ((BasicViewDescriptor) viewDescriptor).setModelDescriptor(entityToCreateDescriptor);
    } 

    return viewDescriptor;
  }
}
