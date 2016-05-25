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

qx.Class.define("org.jspresso.framework.gui.remote.RForm", {
  extend: org.jspresso.framework.gui.remote.RComponent,

  construct: function () {
    this.base(arguments);
  },

  properties: {
    columnCount: {
      check: "Integer"
    },
    elementWidths: {
      check: "Array"
    },
    elements: {
      check: "Array"
    },
    elementLabels: {
      check: "Array"
    },
    labelHorizontalPositions: {
      check: "Array"
    },
    labelsPosition: {
      check: "String"
    },
    verticallyScrollable: {
      check: "Boolean"
    },
    widthResizeable: {
      check: "Boolean"
    }
  },

  members: {
    transferToState: function (stateMapping) {
      this.base(arguments, stateMapping);
      if (this.getElements()) {
        for (var i = 0; i < this.getElements().length; i++) {
          this.getElements()[i].transferToState(stateMapping);
        }
      }
    }
  }
});
