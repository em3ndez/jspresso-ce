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
package org.jspresso.framework.model.descriptor;

import org.jspresso.framework.util.descriptor.IDescriptor;

/**
 * This is just a marker interface for model descriptors (usually bean
 * descriptors and sub descriptors).
 * 
 * @author Vincent Vandenschrick
 */
public interface IModelDescriptor extends IDescriptor {

  /**
   * Gets the type of the model.
   *
   * @return the type of the model.
   */
  Class<?> getModelType();

  /**
   * Gets the fully qualified type name of the model.
   *
   * @return the the fully qualified type name of the model.
   */
  String getModelTypeName();
}
