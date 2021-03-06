/*
 * Copyright (c) 2005-2018 Vincent Vandenschrick. All rights reserved.
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
package org.jspresso.framework.view;

import java.util.Locale;

import org.jspresso.framework.action.IAction;
import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.util.i18n.ITranslationProvider;
import org.jspresso.framework.view.action.IDisplayableAction;
import org.jspresso.framework.view.descriptor.IViewDescriptor;

/**
 * Defines the contract of an UI debugger plugin.
 *
 * @author Vincent Vandenschrick
 */
public interface IUIDebugPlugin {

  /**
   * Computes a technical arbitrary string use to give technical information about a given view.
   *
   * @param originalDescription
   *     the originalDescription
   * @param viewDescriptor
   *     the view descriptor
   * @param actionHandler
   *     the action handler
   * @param locale
   *     the locale
   * @return a technical arbitrary string use to give technical information about a given view.
   */
  String computeTechnicalDescription(String originalDescription, IViewDescriptor viewDescriptor,
                                     IActionHandler actionHandler, Locale locale);

  /**
   * Computes a technical arbitrary string use to give technical information about a given view.
   *
   * @param originalDescription
   *     the originalDescription
   * @param action
   *     the displayable action
   * @param actionHandler
   *     the action handler
   * @param locale
   *     the locale
   * @return a technical arbitrary string use to give technical information about a given view.
   */
  String computeTechnicalDescription(String originalDescription, IAction action,
                                     IActionHandler actionHandler, Locale locale);
}
