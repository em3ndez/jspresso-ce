/*
 * Copyright (c) 2005-2016 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.gui.remote.mobile;

import org.jspresso.framework.gui.remote.RMap;

/**
 * A remote mobile map component.
 *
 * @author Vincent Vandenschrick
 */
public class RMobileMap extends RMap {

  private static final long serialVersionUID = 2672012641048981941L;
  private String position;
  private boolean inline;

  /**
   * Constructs a new {@code RMobileMap} instance.
   *
   * @param guid
   *     the guid.
   */
  public RMobileMap(String guid) {
    super(guid);
  }

  /**
   * Constructs a new {@code RMobileMap} instance. Only used for
   * serialization support.
   */
  public RMobileMap() {
    // For serialization support
  }

  /**
   * Get position.
   *
   * @return the boolean
   */
  public String getPosition() {
    return position;
  }

  /**
   * Sets position.
   *
   * @param position
   *     the position
   */
  public void setPosition(String position) {
    this.position = position;
  }

  /**
   * Is inline boolean.
   *
   * @return the boolean
   */
  public boolean isInline() {
    return inline;
  }

  /**
   * Sets inline.
   *
   * @param inline
   *     the inline
   */
  public void setInline(boolean inline) {
    this.inline = inline;
  }
}
