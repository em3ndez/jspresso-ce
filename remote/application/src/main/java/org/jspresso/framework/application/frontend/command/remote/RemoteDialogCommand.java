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
package org.jspresso.framework.application.frontend.command.remote;

import org.jspresso.framework.gui.remote.RComponent;

/**
 * A command to trigger a modal remote dialog pop-up.
 * 
 * @author Vincent Vandenschrick
 */
public class RemoteDialogCommand extends RemoteAbstractDialogCommand {

  private static final long serialVersionUID = 7993231946633084545L;

  private RComponent        view;

  private boolean           modal;

  /**
   * Gets the view.
   * 
   * @return the view.
   */
  public RComponent getView() {
    return view;
  }

  /**
   * Sets the view.
   * 
   * @param view
   *          the view to set.
   */
  public void setView(RComponent view) {
    this.view = view;
  }

  /**
   * Gets the modal.
   * 
   * @return the modal.
   */
  public boolean isModal() {
    return modal;
  }

  /**
   * Sets the modal.
   * 
   * @param modal the modal to set.
   */
  public void setModal(boolean modal) {
    this.modal = modal;
  }

}
