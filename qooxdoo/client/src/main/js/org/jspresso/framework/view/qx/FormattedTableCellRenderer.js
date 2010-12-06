/**
 * Copyright (c) 2005-2010 Vincent Vandenschrick. All rights reserved.
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

qx.Class.define("org.jspresso.framework.view.qx.FormattedTableCellRenderer",
{
  extend : qx.ui.table.cellrenderer.Default,
  include : [org.jspresso.framework.view.qx.MCellAdditionalStyle],

  /**
   * 
   * @param {org.jspresso.framework.view.qx.DefaultQxViewFactory} viewFactory
   * @param {org.jspresso.framework.gui.remote.RComponent} rComponent
   * @param {org.jspresso.framework.action.IActionHandler} actionHandler
   */
  construct : function(format) {
    this.base(arguments);
    this.__format = format;
  },

  members :
  {
    __format : null,
    __action : null,
    
    _formatValue : function(cellInfo) {
      if(this.__format && cellInfo.value) {
        return this.__format.format(cellInfo.value);
      }
      return this.base(arguments, cellInfo);
    },
    
    _getContentHtml : function(cellInfo) {
      if(this.__action) {
        //return "<a href='javascript:qx.event.Registration.fireEvent(qx.core.Init.getApplication().getRoot(),\"tableLinkClicked\",\"qx.event.type.Data\",\""+this.__action.getGuid()+"\");'>"+this._formatValue(cellInfo)+"</a>";
        //return "<a href='javascript:>"+this._formatValue(cellInfo)+"</a>";
        return "<u>"+this.base(arguments, cellInfo)+"</u>";
      } else {
	    	if(org.jspresso.framework.util.html.HtmlUtil.isHtml(cellInfo.value)) {
	    		return cellInfo.value;
	    	}
	      return this.base(arguments, cellInfo);
      }
    },
    
    setAction : function(action) {
      this.__action = action;
    },

    getAction : function() {
      return this.__action;
    },
    
    _getCellStyle : function(cellInfo) {
      var superStyle = this.base(arguments, cellInfo);
      return superStyle + this._getAdditionalCellStyle(cellInfo);
    }
  }
});
