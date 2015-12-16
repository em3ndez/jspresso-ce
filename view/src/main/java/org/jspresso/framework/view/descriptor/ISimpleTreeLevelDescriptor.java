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
package org.jspresso.framework.view.descriptor;

/**
 * This interface is implemented by descriptors of simple tree levels. Simple
 * tree levels are tree levels where children are organised into a single group.
 * 
 * @author Vincent Vandenschrick
 */
public interface ISimpleTreeLevelDescriptor extends ITreeLevelDescriptor {

  /**
   * Gets the subtree descriptor.
   * 
   * @return the subtree descriptor.
   */
  ITreeLevelDescriptor getChildDescriptor();
}
