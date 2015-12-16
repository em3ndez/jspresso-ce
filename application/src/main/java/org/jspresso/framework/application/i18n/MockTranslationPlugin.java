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
package org.jspresso.framework.application.i18n;

import java.util.Locale;

import org.jspresso.framework.application.backend.session.IApplicationSession;

/**
 * A NO-OP translation plugin.
 * 
 * @author Vincent Vandenschrick
 */
public class MockTranslationPlugin implements ITranslationPlugin {

  /**
   * Always return null.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public String getTranslation(String key, Locale locale,
      IApplicationSession session) {
    return null;
  }

  /**
   * Always return null.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public String getTranslation(String key, Object[] args, Locale locale,
      IApplicationSession session) {
    return null;
  }

}
