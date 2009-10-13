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
package org.jspresso.framework.application.printing.frontend.action;

import java.util.Map;

import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.application.printing.model.IReport;
import org.jspresso.framework.application.printing.model.descriptor.IReportDescriptor;
import org.jspresso.framework.binding.ICollectionConnector;
import org.jspresso.framework.binding.ICollectionConnectorProvider;
import org.jspresso.framework.binding.IValueConnector;
import org.jspresso.framework.model.entity.IEntity;

/**
 * Frontend action to generate a report. The report is injected statically into
 * the action.
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
 * @version $LastChangedRevision: 1332 $
 * @author Vincent Vandenschrick
 * @param <E>
 *          the actual gui component type used.
 * @param <F>
 *          the actual icon type used.
 * @param <G>
 *          the actual action type used.
 */
public class StaticReportAction<E, F, G> extends AbstractReportAction<E, F, G> {

  private IReportDescriptor reportDescriptor;

  /**
   * Gets the report to execute out of the model connector.
   * <p>
   * {@inheritDoc}
   */
  @Override
  protected IReport getReportToExecute(
      @SuppressWarnings("unused") IActionHandler actionHandler,
      Map<String, Object> context) {
    IReport report = getReportFactory().createReportInstance(reportDescriptor,
        getTranslationProvider(context), getLocale(context));
    return report;
  }

  /**
   * Sets the reportDescriptor.
   * 
   * @param reportDescriptor
   *          the reportDescriptor to set.
   */
  public void setReportDescriptor(IReportDescriptor reportDescriptor) {
    this.reportDescriptor = reportDescriptor;
  }

  /**
   * TODO Comment needed.
   * <p>
   * {@inheritDoc}
   */
  @Override
  protected Map<String, Object> getInitialReportContext(
      IActionHandler actionHandler, Map<String, Object> context) {
    Map<String, Object> initialReportContext = super.getInitialReportContext(
        actionHandler, context);
    IValueConnector viewConnector = getViewConnector(context);
    Object model = null;
    if (viewConnector instanceof ICollectionConnectorProvider) {
      int[] selectedIndices = ((ICollectionConnectorProvider) viewConnector)
          .getCollectionConnector().getSelectedIndices();
      ICollectionConnector collectionConnector = (ICollectionConnector) ((ICollectionConnectorProvider) viewConnector)
          .getCollectionConnector().getModelConnector();
      if (selectedIndices == null || selectedIndices.length == 0
          || collectionConnector == null) {
        return null;
      }
      model = collectionConnector.getChildConnector(selectedIndices[0])
          .getConnectorValue();
    } else {
      model = viewConnector.getModelConnector().getConnectorValue();
    }
    if (model instanceof IEntity) {
      initialReportContext.put(IReportDescriptor.ENTITY_ID, ((IEntity) model)
          .getId());
    }
    return initialReportContext;
  }
}
