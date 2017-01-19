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


package org.jspresso.framework.application.frontend.command.remote {

import org.jspresso.framework.gui.remote.RAction;

[RemoteClass(alias="org.jspresso.framework.application.frontend.command.remote.RemoteFileUploadCommand")]
public class RemoteFileUploadCommand extends RemoteFileCommand {

  private var _successCallbackAction:RAction;
  private var _fileMaxSize:int;

  public function RemoteFileUploadCommand() {
    //default constructor.
  }

  public function set successCallbackAction(value:RAction):void {
    _successCallbackAction = value;
  }

  public function get successCallbackAction():RAction {
    return _successCallbackAction;
  }

  public function get fileMaxSize():int {
    return _fileMaxSize;
  }

  public function set fileMaxSize(value:int):void {
    _fileMaxSize = value;
  }
}
}
