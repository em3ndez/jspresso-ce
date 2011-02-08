/*
 * Copyright (c) 2005-2011 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.view.descriptor;

import org.jspresso.framework.model.descriptor.IComponentDescriptor;
import org.jspresso.framework.model.entity.IEntity;

/**
 * Factory for query component view descriptor.
 * 
 * @version $LastChangedRevision: 959 $
 * @author Vincent Vandenschrick
 */
public interface IQueryViewDescriptorFactory {

  /**
   * Creates a new query component view descriptor.
   * 
   * @param componentDescriptor
   *          the component to create query descriptor for.
   * @return the created view descriptor.
   */
  IViewDescriptor createQueryViewDescriptor(
      IComponentDescriptor<IEntity> componentDescriptor);
}
