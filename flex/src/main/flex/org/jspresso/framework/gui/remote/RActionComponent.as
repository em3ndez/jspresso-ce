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


package org.jspresso.framework.gui.remote {


[RemoteClass(alias="org.jspresso.framework.gui.remote.RActionComponent")]
public class RActionComponent extends RComponent {

  private var _action:RAction;
  private var _actionList:RActionList;

  public function RActionComponent() {
    //default constructor.
  }

  public function set action(value:RAction):void {
    _action = value;
  }

  public function get action():RAction {
    return _action;
  }

  public function get actionList():RActionList {
    return _actionList;
  }

  public function set actionList(value:RActionList):void {
    _actionList = value;
  }
}
}
