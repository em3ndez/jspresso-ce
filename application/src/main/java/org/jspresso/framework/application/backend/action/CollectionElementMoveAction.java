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
package org.jspresso.framework.application.backend.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.binding.ICollectionConnector;
import org.jspresso.framework.model.component.IComponent;
import org.jspresso.framework.model.entity.IEntity;

/**
 * This action can be declared on views that are backed by collections with list
 * semantics (indexed collections). It allows to take a the selected elements
 * and move them in the collection using a configured offset. It allows for
 * re-ordering the list.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class CollectionElementMoveAction extends AbstractCollectionAction {

  private int offset;

  /**
   * Retrieves the master and its managed collection from the model connector
   * then it moves the selected element of the offset.
   * <p>
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public boolean execute(IActionHandler actionHandler,
      Map<String, Object> context) {

    if (!List.class.isAssignableFrom(getModelDescriptor(context)
        .getCollectionDescriptor().getCollectionInterface())) {
      return false;
    }

    ICollectionConnector collectionConnector = getModelConnector(context);
    if (collectionConnector == null) {
      return false;
    }

    int[] indicesToMove = getSelectedIndices(context);
    if (indicesToMove == null || indicesToMove.length == 0) {
      return false;
    }

    List<?> elementsToMove = getSelectedModels(context);
    if (elementsToMove == null || elementsToMove.size() == 0) {
      return false;
    }

    List originalList = (List) collectionConnector.getConnectorValue();
    List targetList = new ArrayList<Object>(originalList);

    int[] targetIndices = new int[indicesToMove.length];
    for (int i = indicesToMove.length - 1; i >= 0; i--) {
      targetIndices[i] = indicesToMove[i] + offset;
    }
    if (targetIndices[0] >= 0
        && targetIndices[targetIndices.length - 1] < targetList.size()) {
      for (int i = indicesToMove.length - 1; i >= 0; i--) {
        targetList.remove(indicesToMove[i]);
      }
      for (int i = 0; i < indicesToMove.length; i++) {
        targetList.add(targetIndices[i], elementsToMove.get(i));
      }
      ((IComponent) collectionConnector.getParentConnector()
          .getConnectorValue()).straightSetProperty(
          collectionConnector.getId(), null);
      originalList.clear();
      originalList.addAll(targetList);
      ((IEntity) collectionConnector.getParentConnector().getConnectorValue())
          .straightSetProperty(collectionConnector.getId(), originalList);
      setSelectedModels(elementsToMove, context);
    }
    return super.execute(actionHandler, context);
  }

  /**
   * Configures the offset to use when moving the selected elements inside the
   * list. A cofigured offset of <b>1</b> will increase (move down) by one the
   * selected elements indices whereas an offset of <b>-1</b> will decrease
   * (move up) the selected elements indices.
   * 
   * @param offset
   *          the offset to set.
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }
}
