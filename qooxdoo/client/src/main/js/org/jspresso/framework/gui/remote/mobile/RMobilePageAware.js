/*
 * Copyright (c) 2005-2014 Vincent Vandenschrick. All rights reserved.
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

qx.Class.define("org.jspresso.framework.gui.remote.mobile.RMobilePageAware", {
  extend: org.jspresso.framework.gui.remote.RContainer,

  construct: function () {
    this.base(arguments);
  },

  properties: {
    enterAction: {
      check: "org.jspresso.framework.gui.remote.RAction",
      nullable: true
    },
    backAction: {
      check: "org.jspresso.framework.gui.remote.RAction",
      nullable: true
    },
    mainAction: {
      check: "org.jspresso.framework.gui.remote.RAction",
      nullable: true
    },
    pageEndAction: {
      check: "org.jspresso.framework.gui.remote.RAction",
      nullable: true
    },
    swipeLeftAction: {
      check: "org.jspresso.framework.gui.remote.RAction",
      nullable: true
    },
    swipeRightAction: {
      check: "org.jspresso.framework.gui.remote.RAction",
      nullable: true
    },
    headerText: {
      check: "String",
      nullable: true
    }
  }

});
