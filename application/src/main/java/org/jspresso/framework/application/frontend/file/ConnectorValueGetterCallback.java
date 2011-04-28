/*
 * Copyright (c) 2005-2011 Vincent Vandenschrick. All rights reserved.
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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.application.action.AbstractActionContextAware;
import org.jspresso.framework.model.descriptor.IFileFilterable;
import org.jspresso.framework.model.descriptor.IModelDescriptor;

/**
 * Default handler implementation to deal with getting binary properties storing
 * them in files.
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class ConnectorValueGetterCallback extends AbstractActionContextAware
    implements IFileSaveCallback {

  /**
   * {@inheritDoc}
   */
  public void cancel(@SuppressWarnings("unused") IActionHandler actionHandler,
      @SuppressWarnings("unused") Map<String, Object> context) {
    // NO-OP
  }

  /**
   * {@inheritDoc}
   */
  public void fileChosen(@SuppressWarnings("unused") String name,
      OutputStream out,
      @SuppressWarnings("unused") IActionHandler actionHandler,
      Map<String, Object> context) throws IOException {
    OutputStream os = new BufferedOutputStream(out);
    Object connectorValue = getSelectedModel(context);
    byte[] content;
    if (connectorValue instanceof String) {
      content = ((String) connectorValue).getBytes();
    } else {
      content = (byte[]) connectorValue;
    }
    if (connectorValue != null) {
      os.write(content);
      os.flush();
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getFileName(Map<String, Object> context) {
    IModelDescriptor modelDescriptor = getModelDescriptor(context);
    if (modelDescriptor instanceof IFileFilterable) {
      return ((IFileFilterable) modelDescriptor).getFileName();
    }
    return null;
  }
}
