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
package org.jspresso.framework.gui.remote;

/**
 * A container. Its children are indexed by their view names.
 *
 * @author Vincent Vandenschrick
 */
public abstract class RContainer extends RComponent implements IRemoteScrollable {

  private static final long serialVersionUID = -7174072538766465667L;
  private boolean verticallyScrollable;
  private boolean horizontallyScrollable;

  /**
   * Constructs a new {@code RContainer} instance.
   *
   * @param guid
   *     the guid
   */
  public RContainer(String guid) {
    super(guid);
  }

  /**
   * Constructs a new {@code RContainer} instance. Only used for
   * serialization support.
   */
  public RContainer() {
    // For serialization support
  }

  /**
   * Is vertically scrollable boolean.
   *
   * @return the boolean
   */
  @Override
  public boolean isVerticallyScrollable() {
    return verticallyScrollable;
  }

  /**
   * Sets vertically scrollable.
   *
   * @param verticallyScrollable
   *     the vertically scrollable
   */
  @Override
  public void setVerticallyScrollable(boolean verticallyScrollable) {
    this.verticallyScrollable = verticallyScrollable;
  }

  /**
   * Is horizontally scrollable boolean.
   *
   * @return the boolean
   */
  @Override
  public boolean isHorizontallyScrollable() {
    return horizontallyScrollable;
  }

  /**
   * Sets horizontally scrollable.
   *
   * @param horizontallyScrollable
   *     the horizontally scrollable
   */
  @Override
  public void setHorizontallyScrollable(boolean horizontallyScrollable) {
    this.horizontallyScrollable = horizontallyScrollable;
  }
}
