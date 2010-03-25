/*
 * Copyright (c) 2005-2010 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.gui.ulc.components.shared;

/**
 * Constants shared by ULCColorPicker and UIColorPicker.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public final class ColorPickerConstants {

  /**
   * <code>RESETVALUE_KEY</code>.
   */
  public static final String RESETVALUE_KEY         = "resetValue";

  /**
   * <code>SET_RESETVALUE_REQUEST</code>.
   */
  public static final String SET_RESETVALUE_REQUEST = "setResetValue";

  // request constants
  /**
   * <code>SET_VALUE_REQUEST</code>.
   */
  public static final String SET_VALUE_REQUEST      = "setValue";

  // anything key constants
  /**
   * <code>VALUE_KEY</code>.
   */
  public static final String VALUE_KEY              = "value";

  private ColorPickerConstants() {
    // Empty constructor for utility class
  }
}
