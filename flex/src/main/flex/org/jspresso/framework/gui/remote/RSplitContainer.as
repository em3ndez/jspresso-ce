/**
 * Copyright (c) 2005-2013 Vincent Vandenschrick. All rights reserved.
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
 */


package org.jspresso.framework.gui.remote {


[RemoteClass(alias="org.jspresso.framework.gui.remote.RSplitContainer")]
public class RSplitContainer extends RContainer {

  private var _leftTop:RComponent;
  private var _orientation:String;
  private var _rightBottom:RComponent;

  public function RSplitContainer() {
    //default constructor.
  }

  public function set leftTop(value:RComponent):void {
    _leftTop = value;
  }

  public function get leftTop():RComponent {
    return _leftTop;
  }

  public function set orientation(value:String):void {
    _orientation = value;
  }

  public function get orientation():String {
    return _orientation;
  }

  public function set rightBottom(value:RComponent):void {
    _rightBottom = value;
  }

  public function get rightBottom():RComponent {
    return _rightBottom;
  }
}
}
