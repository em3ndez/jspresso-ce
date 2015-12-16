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
package org.jspresso.framework.application.frontend.file;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.jspresso.framework.action.IActionHandler;

/**
 * This interface is used react to file save.
 * 
 * @author Vincent Vandenschrick
 */
@SuppressWarnings("UnusedParameters")
public interface IFileSaveCallback extends IFileCallback {

  /**
   * Called whenever a file is chosen as save destination.
   * 
   * @param name
   *          the actual file name.
   * @param out
   *          the output stream to write to the file.
   * @param actionHandler
   *          the action handler.
   * @param context
   *          the action context.
   * @throws IOException
   *           whenever an IOException occurs.
   */
  void fileChosen(String name, OutputStream out, IActionHandler actionHandler,
      Map<String, Object> context) throws IOException;

  /**
   * Computes a file name based on the action context.
   * 
   * @param context
   *          the action context.
   * @return a file name to save the file.
   */
  String getFileName(Map<String, Object> context);
}
