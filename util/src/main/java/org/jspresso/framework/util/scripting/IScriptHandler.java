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
package org.jspresso.framework.util.scripting;

import java.util.Map;

/**
 * The interface implemented by script executors.
 * 
 * @author Vincent Vandenschrick
 */
public interface IScriptHandler {

  /**
   * Evaluates a scripted expression.
   * 
   * @param script
   *          the script to evaluate.
   * @param context
   *          the action context.
   * @return the evaluation result.
   */
  Object evaluate(IScript script, Map<String, Object> context);

  /**
   * Executes a script.
   * 
   * @param script
   *          the script to execute.
   * @param context
   *          the action context.
   */
  void execute(IScript script, Map<String, Object> context);
}
