/**
 * Copyright (c) 2005-2012 Vincent Vandenschrick. All rights reserved.
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

qx.Class.define("org.jspresso.framework.gui.remote.RComponent",
{
  extend : org.jspresso.framework.util.remote.RemotePeer,
  
  implement : [org.jspresso.framework.state.remote.IRemoteStateOwner],
  
  construct : function() {
    this.base(arguments);
  },

  properties :
  {
    actionLists :
    {
      check : "Array",
      nullable : true
    },
    secondaryActionLists :
    {
      check : "Array",
      nullable : true
    },
    background :
    {
      check : "String",
      nullable : true
    },
    borderType :
    {
      check : "String",
      nullable : true
    },
    font :
    {
      check : "org.jspresso.framework.util.gui.Font",
      nullable : true
    },
    foreground :
    {
      check : "String",
      nullable : true
    },
    icon :
    {
      check : "org.jspresso.framework.gui.remote.RIcon",
      nullable : true
    },
    label :
    {
      check : "String",
      nullable : true
    },
    state :
    {
      check : "org.jspresso.framework.state.remote.RemoteValueState",
      nullable : true
    },
    toolTip :
    {
      check : "String",
      nullable : true
    },
    preferredSize :
    {
      check : "org.jspresso.framework.util.gui.Dimension",
      nullable : true
    },
    styleName :
    {
      check : "String",
      nullable : true
    }
  },
  
  members:
  {
    /**@type qx.ui.core.Widget*/
    __peer : null,

    assignPeer : function(value) {
      this.__peer = value;
    },
    
    retrievePeer : function() {
      return this.__peer;
    }
  }
});
