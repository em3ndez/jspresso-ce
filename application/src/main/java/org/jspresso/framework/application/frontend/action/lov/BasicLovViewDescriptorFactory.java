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
package org.jspresso.framework.application.frontend.action.lov;

import java.util.Map;

import org.jspresso.framework.application.action.AbstractActionContextAware;
import org.jspresso.framework.model.component.IComponent;
import org.jspresso.framework.model.component.IQueryComponent;
import org.jspresso.framework.model.descriptor.IComponentDescriptor;
import org.jspresso.framework.model.descriptor.IComponentDescriptorProvider;
import org.jspresso.framework.model.descriptor.IQueryComponentDescriptorFactory;
import org.jspresso.framework.view.action.ActionMap;
import org.jspresso.framework.view.action.IDisplayableAction;
import org.jspresso.framework.view.descriptor.ESelectionMode;
import org.jspresso.framework.view.descriptor.IQueryViewDescriptorFactory;
import org.jspresso.framework.view.descriptor.IViewDescriptor;
import org.jspresso.framework.view.descriptor.basic.BasicBorderViewDescriptor;
import org.jspresso.framework.view.descriptor.basic.BasicCollectionViewDescriptor;
import org.jspresso.framework.view.descriptor.basic.BasicTableViewDescriptor;
import org.jspresso.framework.view.descriptor.basic.BasicViewDescriptor;

/**
 * A default implementation for lov view factories.
 * 
 * @version $LastChangedRevision: 4321 $
 * @author Vincent Vandenschrick
 */
public class BasicLovViewDescriptorFactory extends AbstractActionContextAware
    implements ILovViewDescriptorFactory {

  private IQueryViewDescriptorFactory      queryViewDescriptorFactory;
  private IQueryComponentDescriptorFactory queryComponentDescriptorFactory;
  private ILovResultViewDescriptorFactory  resultViewDescriptorFactory;
  private ActionMap                        resultViewActionMap;
  private IDisplayableAction               sortingAction;
  private IViewDescriptor                  paginationViewDescriptor;

  /**
   * {@inheritDoc}
   */
  @Override
  public IViewDescriptor createLovViewDescriptor(
      IComponentDescriptorProvider<IComponent> entityRefDescriptor,
      ESelectionMode selectionMode, IDisplayableAction okAction,
      Map<String, Object> lovContext) {
    BasicBorderViewDescriptor lovViewDescriptor = new BasicBorderViewDescriptor();
    IComponentDescriptor<IQueryComponent> filterModelDescriptor = getQueryComponentDescriptorFactory()
        .createQueryComponentDescriptor(entityRefDescriptor);
    IViewDescriptor filterViewDescriptor = queryViewDescriptorFactory
        .createQueryViewDescriptor(entityRefDescriptor, filterModelDescriptor);
    lovViewDescriptor.setNorthViewDescriptor(filterViewDescriptor);
    lovViewDescriptor.setModelDescriptor(filterViewDescriptor
        .getModelDescriptor());
    BasicViewDescriptor resultViewDescriptor = createResultViewDescriptor(
        entityRefDescriptor, lovContext);
    BasicCollectionViewDescriptor resultCollectionViewDescriptor = (BasicCollectionViewDescriptor) BasicCollectionViewDescriptor
        .extractMainCollectionView(resultViewDescriptor);
    resultCollectionViewDescriptor.setSelectionMode(selectionMode);
    if (resultCollectionViewDescriptor instanceof BasicTableViewDescriptor) {
      ((BasicTableViewDescriptor) resultCollectionViewDescriptor)
          .setSortingAction(sortingAction);
    }
    resultCollectionViewDescriptor.setRowAction(okAction);
    if (entityRefDescriptor.getComponentDescriptor().getPageSize() != null
        && entityRefDescriptor.getComponentDescriptor().getPageSize()
            .intValue() >= 0) {
      if (paginationViewDescriptor != null) {
        resultCollectionViewDescriptor
            .setPaginationViewDescriptor(paginationViewDescriptor);
      }
    }
    if (resultViewActionMap != null) {
      resultViewDescriptor.setActionMap(resultViewActionMap);
    }
    lovViewDescriptor.setCenterViewDescriptor(resultViewDescriptor);
    return lovViewDescriptor;

  }

  /**
   * Sets the queryViewDescriptorFactory.
   * 
   * @param queryViewDescriptorFactory
   *          the queryViewDescriptorFactory to set.
   */
  public void setQueryViewDescriptorFactory(
      IQueryViewDescriptorFactory queryViewDescriptorFactory) {
    this.queryViewDescriptorFactory = queryViewDescriptorFactory;
  }

  /**
   * Sets the resultViewActionMap.
   * 
   * @param resultViewActionMap
   *          the resultViewActionMap to set.
   */
  public void setResultViewActionMap(ActionMap resultViewActionMap) {
    this.resultViewActionMap = resultViewActionMap;
  }

  /**
   * Sets the sortingAction.
   * 
   * @param sortingAction
   *          the sortingAction to set.
   */
  public void setSortingAction(IDisplayableAction sortingAction) {
    this.sortingAction = sortingAction;
  }

  /**
   * Creates a result collection view.
   * 
   * @param entityRefDescriptor
   *          the entity reference descriptor.
   * @param lovContext
   *          the action context the LOV was triggered on.
   * @return a result collection view.
   */
  protected BasicViewDescriptor createResultViewDescriptor(
      IComponentDescriptorProvider<IComponent> entityRefDescriptor,
      Map<String, Object> lovContext) {
    return getResultViewDescriptorFactory().createResultViewDescriptor(
        entityRefDescriptor, lovContext);
  }

  /**
   * Sets the paginationViewDescriptor.
   * 
   * @param paginationViewDescriptor
   *          the paginationViewDescriptor to set.
   */
  public void setPaginationViewDescriptor(
      BasicViewDescriptor paginationViewDescriptor) {
    this.paginationViewDescriptor = paginationViewDescriptor;
  }

  /**
   * Gets the resultViewDescriptorFactory.
   * 
   * @return the resultViewDescriptorFactory.
   */
  protected ILovResultViewDescriptorFactory getResultViewDescriptorFactory() {
    return resultViewDescriptorFactory;
  }

  /**
   * Sets the resultViewDescriptorFactory.
   * 
   * @param resultViewDescriptorFactory
   *          the resultViewDescriptorFactory to set.
   */
  public void setResultViewDescriptorFactory(
      ILovResultViewDescriptorFactory resultViewDescriptorFactory) {
    this.resultViewDescriptorFactory = resultViewDescriptorFactory;
  }

  /**
   * Gets the queryViewDescriptorFactory.
   * 
   * @return the queryViewDescriptorFactory.
   */
  protected IQueryViewDescriptorFactory getQueryViewDescriptorFactory() {
    return queryViewDescriptorFactory;
  }

  /**
   * Gets the queryComponentDescriptorFactory.
   * 
   * @return the queryComponentDescriptorFactory.
   */
  protected IQueryComponentDescriptorFactory getQueryComponentDescriptorFactory() {
    return queryComponentDescriptorFactory;
  }

  /**
   * Sets the queryComponentDescriptorFactory.
   * 
   * @param queryComponentDescriptorFactory
   *          the queryComponentDescriptorFactory to set.
   */
  public void setQueryComponentDescriptorFactory(
      IQueryComponentDescriptorFactory queryComponentDescriptorFactory) {
    this.queryComponentDescriptorFactory = queryComponentDescriptorFactory;
  }
}
