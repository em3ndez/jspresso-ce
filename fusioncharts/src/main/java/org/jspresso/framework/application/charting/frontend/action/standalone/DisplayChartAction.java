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
package org.jspresso.framework.application.charting.frontend.action.standalone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.application.charting.frontend.action.AbstractChartAction;
import org.jspresso.framework.util.gui.Dimension;
import org.jspresso.framework.util.lang.StringUtils;
import org.jspresso.framework.view.IView;
import org.jspresso.framework.view.action.IDisplayableAction;
import org.springframework.jdbc.core.ConnectionCallback;

/**
 * This is the concrete implementation of the Fusionchart display action used in
 * standalone UI channels (Swing).
 * 
 * @author Vincent Vandenschrick
 * @param <E>
 *          the actual gui component type used.
 * @param <F>
 *          the actual icon type used.
 * @param <G>
 *          the actual action type used.
 * @internal
 */
public class DisplayChartAction<E, F, G> extends AbstractChartAction<E, F, G> {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean execute(IActionHandler actionHandler,
      final Map<String, Object> context) {
    String chartUrl = getChartDescriptor().getUrl();
    String chartData = getJdbcTemplate().execute(
        new ConnectionCallback<String>() {

          @Override
          public String doInConnection(Connection con) throws SQLException {
            return getChartDescriptor().getData(getChartModel(context), con,
                getTranslationProvider(context), getLocale(context));
          }
        });

    Map<String, String> flashContext = new LinkedHashMap<>();
    flashContext.put("dataXml", StringUtils.prependUtf8Bom(chartData));
    Dimension d = getChartDescriptor().getDimension();
    flashContext.put("chartWidth", Integer.toString(d.getWidth() - 20));
    flashContext.put("chartHeight", Integer.toString(d.getHeight() - 100));
    List<G> chartActions = new ArrayList<>();
    for (IDisplayableAction action : getActions()) {
      IView<E> view = getView(context);
      chartActions.add(getActionFactory(context).createAction(action,
          actionHandler, view, getLocale(context)));
    }

    getController(context).displayFlashObject(
        chartUrl,
        flashContext,
        chartActions,
        getTranslationProvider(context).getTranslation(
            getChartDescriptor().getTitle(), getLocale(context)),
        getSourceComponent(context), context, d, false);
    return super.execute(actionHandler, context);
  }

  /**
   * Gets the domain entry point for the chart.
   * 
   * @param context
   *          the action context.
   * @return the domain entry point for the chart.
   */
  protected Object getChartModel(Map<String, Object> context) {
    return getSelectedModel(context);
  }
}
