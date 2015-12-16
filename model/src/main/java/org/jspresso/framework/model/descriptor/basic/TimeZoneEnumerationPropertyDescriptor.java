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
package org.jspresso.framework.model.descriptor.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * This is a special enumeration descriptor that holds all available timezones.
 *
 * @author Vincent Vandenschrick
 */
public class TimeZoneEnumerationPropertyDescriptor extends
    AbstractEnumerationPropertyDescriptor {

  private List<String> enumerationValues;

  /**
   * Instantiates a new Time zone enumeration property descriptor.
   */
  public TimeZoneEnumerationPropertyDescriptor() {
    enumerationValues = new ArrayList<>();
    for (String timezoneId : TimeZone.getAvailableIDs()) {
      if (timezoneId.matches(
          "(Africa/|America/|Antarctica/|Arctic/|Asia/" + "|Atlantic/|Australia/|Europe/|Indian/|Pacific/).*")) {
        if (!timezoneId.startsWith("Asia/Riyadh8")) {
          enumerationValues.add(timezoneId);
        }
      }
    }
    Collections.sort(enumerationValues);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TimeZoneEnumerationPropertyDescriptor clone() {
    TimeZoneEnumerationPropertyDescriptor clonedDescriptor = (TimeZoneEnumerationPropertyDescriptor) super.clone();

    return clonedDescriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getEnumerationValues() {
    return enumerationValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getIconImageURL(String value) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTranslated() {
    return false;
  }

  /**
   * Returns true.
   * @return {@code true}
   */
  @Override
  public boolean isLov() {
    return true;
  }
}
