/*
 * Copyright (c) 2005-2012 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.view.action;

import java.util.Collection;

import org.jspresso.framework.action.IAction;
import org.jspresso.framework.util.descriptor.IIconDescriptor;
import org.jspresso.framework.util.descriptor.IStylable;
import org.jspresso.framework.util.gate.IGate;


/**
 * This interface must be implemented by any displayable action of the
 * application.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public interface IDisplayableAction extends IAction, IIconDescriptor, IStylable {

  /**
   * Provides the action accelerator to the runtime framework using its string
   * representation.
   * 
   * @return the accelerator string representation.
   */
  String getAcceleratorAsString();

  /**
   * Gets the collection of gates determining the actionability state of this
   * action.
   * 
   * @return the collection of gates determining the actionability state of this
   *         action.
   */
  Collection<IGate> getActionabilityGates();

  /**
   * Provides the action mnemonic to the runtime framework using its string
   * representation.
   * 
   * @return the mnemonic string representation.
   */
  String getMnemonicAsString();
}
