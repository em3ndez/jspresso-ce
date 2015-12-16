/**
 * Copyright (c) 2005-2013 Vincent Vandenschrick. All rights reserved.
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
 */
package org.jspresso.framework.view.flex {

import mx.controls.List;
import mx.controls.listClasses.IListItemRenderer;

/**
 *  List that uses checkboxes for multiple selection
 */
public class EnhancedList extends List {

  //Allow selection managed by checkboxes
  private var _cumulativeSelection:Boolean;

  public function EnhancedList() {
    super();
    _cumulativeSelection = false;
  }

  public function set cumulativeSelection(value:Boolean):void {
    _cumulativeSelection = value;
  }

  // fake all mouse interaction as if it had the ctrl key down
  override protected function selectItem(item:IListItemRenderer, shiftKey:Boolean, ctrlKey:Boolean,
                                         transition:Boolean = true):Boolean {
    if (_cumulativeSelection) {
      return super.selectItem(item, false, true, transition);
    }
    return super.selectItem(item, shiftKey, ctrlKey, transition);
  }

  /**
   *  Workarounds a NPE that occurs when a selection is made before the table is drawn.
   */
  override protected function UIDToItemRenderer(uid:String):IListItemRenderer {
    if (!(listContent && visibleData)) {
      return null;
    }
    return super.UIDToItemRenderer(uid);
  }
}
}
