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
package org.jspresso.framework.application.backend.async;

import org.jspresso.framework.action.ActionContextConstants;
import org.jspresso.framework.action.IAction;
import org.jspresso.framework.action.IActionHandler;
import org.jspresso.framework.application.backend.AbstractBackendController;
import org.jspresso.framework.application.backend.BackendControllerHolder;
import org.jspresso.framework.application.backend.action.Asynchronous;
import org.jspresso.framework.application.backend.action.BackendAction;
import org.jspresso.framework.application.backend.action.Transactional;
import org.jspresso.framework.application.backend.session.EMergeMode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A specialized thread dedicated to executing asynchronous actions. It is able
 * to record the action progress.
 *
 * @author Vincent Vandenschrick
 */
public class AsyncActionExecutor extends Thread {

  private IAction                   action;
  private Map<String, Object>       context;
  private Map<String, Object>       progressionData;
  private AbstractBackendController slaveBackendController;

  private Date   startedTimestamp;
  private Date   endedTimestamp;
  private double progress;
  private String i18nName;
  private String status;

  private static final String EXCEPTION_KEY            = "EXCEPTION_KEY";
  private static final String COMPLETED_CONTROLLER_KEY = "COMPLETED_CONTROLLER_KEY";

  /**
   * Constructs a new {@code AsyncActionExecutor} instance.
   *
   * @param action                 the action to execute asynchronously.
   * @param context                the action context.
   * @param group                  the thread group.
   * @param slaveBackendController the slave backend controller used to execute the action.
   */
  public AsyncActionExecutor(IAction action, Map<String, Object> context,
      ThreadGroup group, AbstractBackendController slaveBackendController) {
    super(group, /* "Jspresso Asynchronous Action Runner " + */
    action.getClass().getSimpleName() + "["
        + slaveBackendController.getApplicationSession().getId() + "]["
        + slaveBackendController.getApplicationSession().getUsername() + "]");
    this.action = action;
    this.context = context;
    this.slaveBackendController = slaveBackendController;
    this.progressionData = new HashMap<>();
  }

  /**
   * Runs the action.
   * <p>
   * {@inheritDoc}
   */
  @Override
  public void run() {
    startedTimestamp = new Date();
    // The following will not store the slave backend controller in the HTTP
    // session since we are in a new thread.
    BackendControllerHolder.setThreadBackendController(slaveBackendController);

    // Clone the context to ensure the outer one is not modified.
    final Map<String, Object> slaveContext = new HashMap<>();
    slaveContext.putAll(context);
    // Ensure the action will be executing in the context of the slave
    // backend controller.
    slaveContext.putAll(slaveBackendController.getInitialActionContext());
    // Make sure front controller cannot be referenced.
    slaveContext.remove(ActionContextConstants.FRONT_CONTROLLER);

    try {
      if (action.getClass().getAnnotation(Asynchronous.class).autoMergeBackEntities()) {
        slaveBackendController.recordUowMergedEntities();
      }
      if (action.getClass().isAnnotationPresent(Transactional.class)) {
        slaveBackendController.executeTransactionally(action, slaveContext);
      } else {
        action.execute(slaveBackendController, slaveContext);
      }
      if (action.getClass().getAnnotation(Asynchronous.class).autoMergeBackEntities()) {
        Map<String, Object> mergeContext = new HashMap<>();
        mergeContext.put(COMPLETED_CONTROLLER_KEY, slaveBackendController);
        slaveBackendController.executeLater(new BackendAction() {
          @Override
          public boolean execute(IActionHandler actionHandler, Map<String, Object> innerContext) {
            AbstractBackendController completedController =
                (AbstractBackendController) innerContext.get(COMPLETED_CONTROLLER_KEY);
            AbstractBackendController mainController = (AbstractBackendController) getBackendController(innerContext);
            mainController.merge(completedController.getRecordedUowMergedEntitiesAndClear(),
                EMergeMode.MERGE_LAZY);
            return super.execute(actionHandler, innerContext);
          }
        }, mergeContext);
      }
    } catch (RuntimeException ex) {
      if (action.getClass().getAnnotation(Asynchronous.class).pushRuntimeExceptions()) {
        Map<String, Object> exceptionContext = new HashMap<>();
        exceptionContext.put(EXCEPTION_KEY, ex);
        slaveBackendController.executeLater(new BackendAction() {
          @Override
          public boolean execute(IActionHandler actionHandler, Map<String, Object> innerContext) {
            throw (RuntimeException) innerContext.get(EXCEPTION_KEY);
          }
        }, exceptionContext);
        status = "exception";
      }
    } finally {
      endedTimestamp = new Date();
      slaveBackendController.cleanupRequestResources();
      slaveBackendController.stop();
      BackendControllerHolder.setThreadBackendController(null);
      action = null;
      context = null;
      slaveBackendController = null;
    }
  }

  /**
   * Gets the progress.
   *
   * @return the progress.
   */
  public double getProgress() {
    return progress;
  }

  /**
   * Sets the progress.
   *
   * @param progress the progress to set.
   */
  public void setProgress(double progress) {
    this.progress = progress;
  }

  /**
   * Gets the startedTimestamp.
   *
   * @return the startedTimestamp.
   */
  public Date getStartedTimestamp() {
    return startedTimestamp;
  }

  /**
   * Gets ended timestamp.
   *
   * @return the ended timestamp
   */
  public Date getEndedTimestamp() {
    return endedTimestamp;
  }

  /**
   * Sets i18n name.
   *
   * @param i18nName the 18n name
   */
  public void setI18nName(String i18nName) {
    this.i18nName = i18nName;
  }

  /**
   * Gets i18n name.
   *
   * @return the i18n name if not null, otherwise return's thread's name.
   */
  public String getI18nName() {

    if (i18nName!=null)
      return i18nName;

    return getName();
  }

  /**
   * Sets i18n status.
   *
   * @param status the i18n status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets i18n status.
   *
   * @return the i18n status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Gets progression data.
   *
   * @return the progression data
   */
  public Map<String, Object> getProgressionData() {
    return progressionData;
  }

  /**
   * Gets estimated remaining time asn string.
   *
   * @return the estimated remaining time
   */
  public Long getEstimatedRemainingTime() {

    Date startedTimestamp = getStartedTimestamp();
    if (startedTimestamp == null)
      return null;

    Date endedTimestamp = getEndedTimestamp();
    if (endedTimestamp != null)
      return null;

    double progress = getProgress();
    if (progress<0.05d)
      return null;

    long elapsed = new Date().getTime() - startedTimestamp.getTime();
    double eta = elapsed / (1.0d - progress);
    if (eta < 1)
      return null;

    return new Double(eta).longValue();
  }

  /**
   * Gets estimated remaining time asn string.
   *
   * @return the estimated remaining time
   */
  public Long getTotalDuration() {

    Date startedTimestamp = getStartedTimestamp();
    if (startedTimestamp == null)
      return null;

    Date endedTimestamp = getEndedTimestamp();
    if (endedTimestamp == null)
      return null;

    long elapsed = endedTimestamp.getTime() - startedTimestamp.getTime();
    if (elapsed < 1)
      return null;

    return elapsed;
  }

}
