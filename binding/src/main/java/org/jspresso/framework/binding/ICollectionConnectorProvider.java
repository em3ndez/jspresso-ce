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
package org.jspresso.framework.binding;

import org.jspresso.framework.util.event.ISelectable;

/**
 * Marks objects being able to provide a collection connector.
 * 
 * @author Vincent Vandenschrick
 */
public interface ICollectionConnectorProvider extends
    ICollectionConnectorListProvider, ISelectable {

  /**
   * Clones this connector.
   * 
   * @return the connector's clone.
   */
  @Override
  ICollectionConnectorProvider clone();

  /**
   * Clones this connector.
   * 
   * @param newConnectorId
   *          the identifier of the clone connector
   * @return the connector's clone.
   */
  @Override
  ICollectionConnectorProvider clone(String newConnectorId);

  /**
   * Gets the collection connector.
   * 
   * @return the collection connector.
   */
  ICollectionConnector getCollectionConnector();
}
