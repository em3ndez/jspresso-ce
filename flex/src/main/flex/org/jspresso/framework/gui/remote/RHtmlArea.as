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


[RemoteClass(alias="org.jspresso.framework.gui.remote.RHtmlArea")]
public class RHtmlArea extends RTextComponent {

  private var _readOnly:Boolean;
  private var _verticallyScrollable:Boolean;
  private var _horizontallyScrollable:Boolean;
  private var _action:RAction;
  private var _editorConfiguration:String;

  public function RHtmlArea() {
    //default constructor.
  }

  public function set readOnly(value:Boolean):void {
    _readOnly = value;
  }

  public function get readOnly():Boolean {
    return _readOnly;
  }

  public function get verticallyScrollable():Boolean {
    return _verticallyScrollable;
  }

  public function set verticallyScrollable(value:Boolean):void {
    _verticallyScrollable = value;
  }

  public function get horizontallyScrollable():Boolean {
    return _horizontallyScrollable;
  }

  public function set horizontallyScrollable(value:Boolean):void {
    _horizontallyScrollable = value;
  }

  public function get action():RAction {
    return _action;
  }

  public function set action(value:RAction):void {
    _action = value;
  }

  public function get editorConfiguration():String {
    return _editorConfiguration;
  }

  public function set editorConfiguration(value:String):void {
    _editorConfiguration = value;
  }
}
}
