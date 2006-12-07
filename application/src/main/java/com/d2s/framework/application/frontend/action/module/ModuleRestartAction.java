/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.application.frontend.action.module;

import java.util.Map;

import com.d2s.framework.action.IActionHandler;
import com.d2s.framework.application.frontend.action.AbstractChainedAction;
import com.d2s.framework.application.view.descriptor.IModuleDescriptor;

/**
 * A simple action which restarts the current module executing the module
 * startup action.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 * @param <E>
 *          the actual gui component type used.
 * @param <F>
 *          the actual icon type used.
 * @param <G>
 *          the actual action type used.
 */
public class ModuleRestartAction<E, F, G> extends
    AbstractChainedAction<E, F, G> {

  /**
   * Gets the current module and restarts it. {@inheritDoc}
   */
  @Override
  public boolean execute(IActionHandler actionHandler,
      Map<String, Object> context) {
    IModuleDescriptor moduleDescriptor = getModuleDescriptor(context);
    if (moduleDescriptor.getStartupAction() != null) {
      return actionHandler
          .execute(moduleDescriptor.getStartupAction(), context);
    }
    return true;
  }
}
