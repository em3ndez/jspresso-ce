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
 * This interface is implemented by strategies providing card descriptors based
 * on their names.
 * 
 * @author Vincent Vandenschrick
 */
public interface ICardProvider {

  /**
   * Gets the card view for the name passed as parameter.
   * 
   * @param cardName
   *          the card name to lookup the view for.
   * @return the card view descriptor.
   */
  IViewDescriptor getCardViewDescriptor(String cardName);

}
