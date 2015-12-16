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
package org.jspresso.framework.application.frontend.action.workspace;

import java.util.Map;

import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.application.frontend.action.FrontendAction;

/**
 * This action displays a workspace using its (untranslated) name.
 * 
 * @author Vincent Vandenschrick
 * @param <E>
 *          the actual gui component type used.
 * @param <F>
 *          the actual icon type used.
 * @param <G>
 *          the actual action type used.
 */
public class WorkspaceSelectionAction<E, F, G> extends FrontendAction<E, F, G> {

  private String workspaceName;

  /**
   * Displays the workspace.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public boolean execute(IActionHandler actionHandler,
      Map<String, Object> context) {
    getController(context).displayWorkspace(workspaceName);
    return super.execute(actionHandler, context);
  }

  /**
   * Configures the name of the workspace to display.
   * 
   * @param workspaceName
   *          the workspaceName to set.
   */
  public void setWorkspaceName(String workspaceName) {
    this.workspaceName = workspaceName;
  }

}
