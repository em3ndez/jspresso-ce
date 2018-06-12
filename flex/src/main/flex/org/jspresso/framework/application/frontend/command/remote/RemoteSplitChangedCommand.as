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


package org.jspresso.framework.application.frontend.command.remote {


[RemoteClass(alias="org.jspresso.framework.application.frontend.command.remote.RemoteSplitChangedCommand")]
public class RemoteSplitChangedCommand extends RemoteCommand {

  private var _splitPaneId:String;
  private var _separatorPosition:int;

  public function RemoteSplitChangedCommand() {
    //default constructor.
  }

  public function get splitPaneId():String {
    return _splitPaneId;
  }

  public function set splitPaneId(value:String):void {
    _splitPaneId = value;
  }

  public function get separatorPosition():int {
    return _separatorPosition;
  }

  public function set separatorPosition(value:int):void {
    _separatorPosition = value;
  }
}
}
