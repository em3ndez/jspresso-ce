/*
 * Copyright (c) 2005-2008 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.binding.remote.state;

import org.jspresso.framework.util.remote.RemotePeer;

/**
 * The state of a remote value.
 * <p>
 * Copyright (c) 2005-2008 Vincent Vandenschrick. All rights reserved.
 * <p>
 * This file is part of the Jspresso framework. Jspresso is free software: you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version. Jspresso is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with Jspresso. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class RemoteValueState extends RemotePeer {

  private Object  value;
  private boolean writable;
  private boolean readable;

  /**
   * Constructs a new <code>RemoteValueState</code> instance.
   * 
   * @param guid
   *          the state guid.
   */
  protected RemoteValueState(String guid) {
    super(guid);
  }

  /**
   * Gets the writable.
   * 
   * @return the writable.
   */
  public boolean isWritable() {
    return writable;
  }

  /**
   * Sets the writable.
   * 
   * @param writable
   *          the writable to set.
   */
  public void setWritable(boolean writable) {
    this.writable = writable;
  }

  /**
   * Gets the value.
   * 
   * @return the value.
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets the value.
   * 
   * @param value
   *          the value to set.
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Gets the readable.
   * 
   * @return the readable.
   */
  public boolean isReadable() {
    return readable;
  }

  /**
   * Sets the readable.
   * 
   * @param readable
   *          the readable to set.
   */
  public void setReadable(boolean readable) {
    this.readable = readable;
  }

}
