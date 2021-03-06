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
package org.jspresso.framework.gui.remote;

/**
 * A remote map component.
 *
 * @author Vincent Vandenschrick
 */
public class RMap extends RComponent {

  private Integer defaultZoom;
  private RAction markerAction;
  private RAction routeAction;
  private RAction zoneAction;
  private static final long serialVersionUID = 4763812955008036950L;

  /**
   * Constructs a new {@code RMap} instance.
   *
   * @param guid
   *     the guid.
   */
  public RMap(String guid) {
    super(guid);
  }

  /**
   * Constructs a new {@code RMap} instance. Only used for serialization
   * support.
   */
  public RMap() {
    // For serialization support
  }

  /**
   * Gets default zoom.
   *
   * @return the default zoom
   */
  public Integer getDefaultZoom() {
    return defaultZoom;
  }

  /**
   * Sets default zoom.
   *
   * @param defaultZoom
   *     the default zoom
   */
  public void setDefaultZoom(Integer defaultZoom) {
    this.defaultZoom = defaultZoom;
  }

  /**
   * Gets marker action.
   *
   * @return the marker action
   */
  public RAction getMarkerAction() {
    return markerAction;
  }

  /**
   * Sets marker action.
   *
   * @param markerAction
   *     the marker action
   */
  public void setMarkerAction(RAction markerAction) {
    this.markerAction = markerAction;
  }

  /**
   * Gets route action.
   *
   * @return the route action
   */
  public RAction getRouteAction() {
    return routeAction;
  }

  /**
   * Sets route action.
   *
   * @param routeAction
   *     the route action
   */
  public void setRouteAction(RAction routeAction) {
    this.routeAction = routeAction;
  }

  /**
   * Gets zone action.
   *
   * @return the zone action
   */
  public RAction getZoneAction() {
    return zoneAction;
  }

  /**
   * Sets zone action.
   *
   * @param zoneAction
   *     the zone action
   */
  public void setZoneAction(RAction zoneAction) {
    this.zoneAction = zoneAction;
  }
}
