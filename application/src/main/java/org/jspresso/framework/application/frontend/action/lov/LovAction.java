/*
 * Copyright (c) 2005-2010 Vincent Vandenschrick. All rights reserved.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.application.backend.action.CreateQueryComponentAction;
import org.jspresso.framework.application.backend.session.EMergeMode;
import org.jspresso.framework.application.frontend.action.FrontendAction;
import org.jspresso.framework.application.frontend.action.ModalDialogAction;
import org.jspresso.framework.binding.IRenderableCompositeValueConnector;
import org.jspresso.framework.binding.IValueConnector;
import org.jspresso.framework.model.component.IQueryComponent;
import org.jspresso.framework.model.descriptor.IComponentDescriptor;
import org.jspresso.framework.model.descriptor.IModelDescriptor;
import org.jspresso.framework.model.descriptor.IReferencePropertyDescriptor;
import org.jspresso.framework.model.descriptor.basic.BasicReferencePropertyDescriptor;
import org.jspresso.framework.model.entity.IEntity;
import org.jspresso.framework.util.i18n.ITranslationProvider;
import org.jspresso.framework.view.IView;
import org.jspresso.framework.view.action.IDisplayableAction;
import org.jspresso.framework.view.descriptor.ILovViewDescriptorFactory;

/**
 * A standard List of value action for reference property views. This action
 * should be used in view factories.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 * @param <E>
 *          the actual gui component type used.
 * @param <F>
 *          the actual icon type used.
 * @param <G>
 *          the actual action type used.
 */
public class LovAction<E, F, G> extends FrontendAction<E, F, G> {

  private boolean                               autoquery;
  private IDisplayableAction                    cancelAction;
  private CreateQueryComponentAction            createQueryComponentAction;
  private IComponentDescriptor<IEntity>         entityDescriptor;
  private IReferencePropertyDescriptor<IEntity> entityRefQueryDescriptor;
  private IDisplayableAction                    findAction;
  private Map<String, Object>                   initializationMapping;
  private ILovViewDescriptorFactory             lovViewDescriptorFactory;
  private IDisplayableAction                    okAction;

  /**
   * Constructs a new <code>LovAction</code> instance.
   */
  public LovAction() {
    setAutoquery(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean execute(IActionHandler actionHandler,
      Map<String, Object> context) {

    IReferencePropertyDescriptor<IEntity> erqDescriptor = getEntityRefQueryDescriptor(context);
    context.put(CreateQueryComponentAction.COMPONENT_REF_DESCRIPTOR,
        erqDescriptor);

    IValueConnector viewConnector = getViewConnector(context);
    if (viewConnector instanceof IRenderableCompositeValueConnector
        && ((IRenderableCompositeValueConnector) viewConnector)
            .getRenderingConnector() != null) {
      setActionParameter(((IRenderableCompositeValueConnector) viewConnector)
          .getRenderingConnector().getId(), context);
    }
    actionHandler.execute(createQueryComponentAction, context);

    String queryPropertyValue = getActionCommand(context);
    if (autoquery
        && queryPropertyValue != null
        && /* queryPropertyValue.length() > 0 && */!queryPropertyValue
            .equals("*")) {
      actionHandler.execute(findAction, context);
      IQueryComponent queryComponent = (IQueryComponent) context
          .get(IQueryComponent.QUERY_COMPONENT);
      if (queryComponent.getQueriedComponents() != null
          && queryComponent.getQueriedComponents().size() == 1) {
        IEntity selectedEntity = getController(context).getBackendController()
            .merge((IEntity) queryComponent.getQueriedComponents().get(0),
                EMergeMode.MERGE_KEEP);
        viewConnector.setConnectorValue(selectedEntity);
        return true;
      }
    }

    List<IDisplayableAction> actions = new ArrayList<IDisplayableAction>();
    getViewConnector(context).setConnectorValue(
        getViewConnector(context).getConnectorValue());

    actions.add(findAction);
    actions.add(okAction);
    actions.add(cancelAction);
    context.put(ModalDialogAction.DIALOG_ACTIONS, actions);
    IView<E> lovView = getViewFactory(context).createView(
        lovViewDescriptorFactory.createLovViewDescriptor(erqDescriptor,
            okAction), actionHandler, getLocale(context));
    context.put(ModalDialogAction.DIALOG_TITLE, getI18nName(
        getTranslationProvider(context), getLocale(context)));
    context.put(ModalDialogAction.DIALOG_VIEW, lovView);
    IValueConnector queryEntityConnector = (IValueConnector) context
        .get(CreateQueryComponentAction.QUERY_MODEL_CONNECTOR);
    getMvcBinder(context).bind(lovView.getConnector(), queryEntityConnector);

    return super.execute(actionHandler, context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getI18nDescription(ITranslationProvider translationProvider,
      Locale locale) {
    if (getDescription() == null) {
      if (entityDescriptor != null) {
        return translationProvider.getTranslation("lov.element.description",
            new Object[] {entityDescriptor.getI18nName(translationProvider,
                locale)}, locale);
      }
      return translationProvider.getTranslation("lov.description", locale);
    }
    return super.getI18nDescription(translationProvider, locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getI18nName(ITranslationProvider translationProvider,
      Locale locale) {
    if (getName() == null) {
      if (entityDescriptor != null) {
        return translationProvider.getTranslation("lov.element.name",
            new Object[] {entityDescriptor.getI18nName(translationProvider,
                locale)}, locale);
      }
      return translationProvider.getTranslation("lov.name", locale);
    }
    return super.getI18nName(translationProvider, locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getIconImageURL() {
    String iconImageURL = super.getIconImageURL();
    if (iconImageURL == null) {
      if (entityDescriptor != null) {
        iconImageURL = entityDescriptor.getIconImageURL();
      }
      if (iconImageURL == null) {
        iconImageURL = "classpath:org/jspresso/framework/application/images/find-48x48.png";
      }
    }
    return iconImageURL;
  }

  /**
   * Sets the autoquery.
   * 
   * @param autoquery
   *          the autoquery to set.
   */
  public void setAutoquery(boolean autoquery) {
    this.autoquery = autoquery;
  }

  /**
   * Sets the cancelAction.
   * 
   * @param cancelAction
   *          the cancelAction to set.
   */
  public void setCancelAction(IDisplayableAction cancelAction) {
    this.cancelAction = cancelAction;
  }

  /**
   * Sets the createQueryComponentAction.
   * 
   * @param createQueryComponentAction
   *          the createQueryComponentAction to set.
   */
  public void setCreateQueryComponentAction(
      CreateQueryComponentAction createQueryComponentAction) {
    this.createQueryComponentAction = createQueryComponentAction;
  }

  /**
   * Sets the entityDescriptor.
   * 
   * @param entityDescriptor
   *          the entityDescriptor to set.
   */
  public void setEntityDescriptor(IComponentDescriptor<IEntity> entityDescriptor) {
    this.entityDescriptor = entityDescriptor;
  }

  /**
   * Sets the entityRefQueryDescriptor.
   * 
   * @param entityRefQueryDescriptor
   *          the entityRefQueryDescriptor to set.
   */
  public void setEntityRefQueryDescriptor(
      IReferencePropertyDescriptor<IEntity> entityRefQueryDescriptor) {
    this.entityRefQueryDescriptor = entityRefQueryDescriptor;
  }

  /**
   * Sets the findAction.
   * 
   * @param findAction
   *          the findAction to set.
   */
  public void setFindAction(IDisplayableAction findAction) {
    this.findAction = findAction;
  }

  /**
   * Sets the initializationMapping.
   * 
   * @param initializationMapping
   *          the initializationMapping to set.
   */
  public void setInitializationMapping(Map<String, Object> initializationMapping) {
    this.initializationMapping = initializationMapping;
  }

  /**
   * Sets the lovViewDescriptorFactory.
   * 
   * @param lovViewDescriptorFactory
   *          the lovViewDescriptorFactory to set.
   */
  public void setLovViewDescriptorFactory(
      ILovViewDescriptorFactory lovViewDescriptorFactory) {
    this.lovViewDescriptorFactory = lovViewDescriptorFactory;
  }

  /**
   * Sets the okAction.
   * 
   * @param okAction
   *          the okAction to set.
   */
  public void setOkAction(IDisplayableAction okAction) {
    this.okAction = okAction;
  }

  /**
   * Gets the entityRefQueryDescriptor.
   * 
   * @param context
   *          the action context.
   * @return the entityRefQueryDescriptor.
   */
  @SuppressWarnings("unchecked")
  protected IReferencePropertyDescriptor<IEntity> getEntityRefQueryDescriptor(
      Map<String, Object> context) {
    if (entityDescriptor != null) {
      if (entityRefQueryDescriptor == null) {
        entityRefQueryDescriptor = new BasicReferencePropertyDescriptor<IEntity>();
        ((BasicReferencePropertyDescriptor<IEntity>) entityRefQueryDescriptor)
            .setReferencedDescriptor(entityDescriptor);
        ((BasicReferencePropertyDescriptor<IEntity>) entityRefQueryDescriptor)
            .setInitializationMapping(initializationMapping);
      }
      return entityRefQueryDescriptor;
    }
    IModelDescriptor modelDescriptor = getModelDescriptor(context);
    if (modelDescriptor instanceof IReferencePropertyDescriptor) {
      return (IReferencePropertyDescriptor<IEntity>) modelDescriptor;
    }
    return null;
  }
}
