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
package org.jspresso.framework.util.format;

/**
 * A 1-way formatter for passwords.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class PasswordFormatter implements IFormatter<String, String> {

  /**
   * Echoes star char.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public String format(String value) {
    if (value == null) {
      return "";
    }
    return "***";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String parse(String source) {
    return source;
  }

}
