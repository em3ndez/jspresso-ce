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
package org.jspresso.framework.application.frontend.action.flow;

import java.util.Map;

import org.jspresso.framework.action.IActionHandler;

/**
 * This action pops-up a binary question. The message, instead of being
 * extracted out of the context, is parametrized statically into the action
 * through its internationalization key.
 *
 * @param <E>
 *     the actual gui component type used.
 * @param <F>
 *     the actual icon type used.
 * @param <G>
 *     the actual action type used.
 * @author Vincent Vandenschrick
 */
public class StaticYesNoAction<E, F, G> extends YesNoAction<E, F, G> {

  private String messageCode;

  /**
   * {@inheritDoc}
   *
   * @param actionHandler
   *     the action handler
   * @param context
   *     the context
   * @return the boolean
   */
  @Override
  public boolean execute(IActionHandler actionHandler,
      Map<String, Object> context) {
    setActionParameter(translate(getMessageCode(context), context), context);
    return super.execute(actionHandler, context);
  }

  /**
   * Configures the i18n key used to translate the message that is to be
   * displayed to the user. When the action executes, the statically configured
   * message is first translated, then placed into the action context to be
   * popped-up.
   *
   * @param messageCode
   *     the messageCode to set.
   */
  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  /**
   * Gets message code.
   *
   * @param context
   *     the context
   * @return the message code
   */
  protected String getMessageCode(Map<String, Object> context) {
    return messageCode;
  }
}
