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

qx.Class.define("org.jspresso.framework.view.qx.ColorTableCellRenderer", {
  extend: qx.ui.table.cellrenderer.Default,
  include: [org.jspresso.framework.view.qx.MCellAdditionalStyle],


  construct: function () {
    this.base(arguments);
  },


  members: {

    _getContentHtml: function (cellInfo) {
      var cellViewState = null;
      if (cellInfo.rowData instanceof org.jspresso.framework.state.remote.RemoteCompositeValueState) {
        cellViewState = cellInfo.rowData.getChildren().getItem(cellInfo.col + 1);
      }
      if (cellViewState && !cellViewState.getReadable()) {
        return "";
      }
      return this.base(arguments, cellInfo);
    },

    _getCellStyle: function (cellInfo) {
      var superStyle = this.base(arguments, cellInfo);
      if (cellInfo.value) {
        var bgStyle = "color: #" + cellInfo.value.substring(4) + ";";
        superStyle = (superStyle || "") + bgStyle;
      }
      return superStyle + this._getAdditionalCellStyle(cellInfo);
    }
  }
});
