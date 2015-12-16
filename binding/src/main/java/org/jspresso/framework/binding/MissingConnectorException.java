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
package org.jspresso.framework.binding;

/**
 * This exception is thrown whenever a binding requires a connector that does
 * not exist.
 * 
 * @see org.jspresso.framework.binding.DefaultMvcBinder#bind(IValueConnector,
 *      IValueConnector)
 * @author Vincent Vandenschrick
 */
public class MissingConnectorException extends RuntimeException {

  private static final long serialVersionUID = -2642954716866063359L;

  /**
   * Constructs a new MissingConnectorException with null as its detail message.
   */
  public MissingConnectorException() {
    super();
  }

  /**
   * Constructs a new MissingConnectorException with the specified detail
   * message.
   * 
   * @param message
   *          the detail message.
   */
  public MissingConnectorException(String message) {
    super(message);
  }

}
