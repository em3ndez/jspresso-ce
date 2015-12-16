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
package org.jspresso.framework.util.event;

import java.util.EventObject;

/**
 * An event notifying an item selection change. It contains the object at the
 * source of the event and the newly selected item (or null if none).
 * 
 * @author Vincent Vandenschrick
 */
public class ItemSelectionEvent extends EventObject {

  private static final long serialVersionUID = -5022556820638206657L;

  private Object            selectedItem;

  /**
   * Constructs a new {@code ItemSelectionEvent}.
   *
   * @param source
   *          the object that initiated the event.
   * @param selectedItem
   *          the new selected item.
   */
  public ItemSelectionEvent(Object source, Object selectedItem) {
    super(source);
    this.selectedItem = selectedItem;
  }

  /**
   * Gets the selectedItem.
   * 
   * @return the selectedItem.
   */
  public Object getSelectedItem() {
    return selectedItem;
  }

  /**
   * Sets the selectedItem.
   * 
   * @param selectedItem
   *          the selectedItem to set.
   */
  public void setSelectedItem(Object selectedItem) {
    this.selectedItem = selectedItem;
  }
}
