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
package org.jspresso.framework.util.gate;

import org.jspresso.framework.util.lang.IModelAware;

/**
 * A model based gate.
 * 
 * @author Vincent Vandenschrick
 */
public interface IModelGate extends IGate, IModelAware {

  /**
   * Gets whether the gate should be provided with a collection of components
   * instead of a single one if possible.
   * 
   * @return true if the gate should be provided with a collection of components
   *         instead of a single one if possible.
   */
  boolean isCollectionBased();
}
