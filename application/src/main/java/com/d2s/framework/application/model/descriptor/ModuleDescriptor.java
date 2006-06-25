/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.application.model.descriptor;

import java.util.ArrayList;
import java.util.List;

import com.d2s.framework.application.model.Module;
import com.d2s.framework.model.descriptor.IComponentDescriptor;
import com.d2s.framework.model.descriptor.IPropertyDescriptor;
import com.d2s.framework.model.descriptor.basic.BasicCollectionDescriptor;
import com.d2s.framework.model.descriptor.basic.BasicCollectionPropertyDescriptor;
import com.d2s.framework.model.descriptor.basic.BasicComponentDescriptor;
import com.d2s.framework.model.descriptor.basic.BasicObjectPropertyDescriptor;
import com.d2s.framework.model.descriptor.basic.BasicReferencePropertyDescriptor;
import com.d2s.framework.model.descriptor.basic.BasicStringPropertyDescriptor;

/**
 * The model descriptor of module objects.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 * 
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public final class ModuleDescriptor extends BasicComponentDescriptor {

  /**
   * <code>MODULE_DESCRIPTOR</code> is a unique reference to the model
   * descriptor of modules.
   */
  public static final IComponentDescriptor MODULE_DESCRIPTOR = new ModuleDescriptor();

  /**
   * Constructs a new <code>ModuleDescriptor</code> instance.
   */
  private ModuleDescriptor() {

    super(Module.class.getName());

    BasicReferencePropertyDescriptor parentDescriptor = new BasicReferencePropertyDescriptor();
    parentDescriptor.setName("parent");
    parentDescriptor.setReferencedDescriptor(this);

    BasicCollectionDescriptor moduleListDescriptor = new BasicCollectionDescriptor();
    moduleListDescriptor.setCollectionInterface(List.class);
    moduleListDescriptor.setElementDescriptor(this);

    BasicCollectionPropertyDescriptor subModulesDescriptor = new BasicCollectionPropertyDescriptor();
    subModulesDescriptor.setReferencedDescriptor(moduleListDescriptor);
    subModulesDescriptor.setName("subModules");

    BasicObjectPropertyDescriptor projectedObjectDescriptor = new BasicObjectPropertyDescriptor();
    projectedObjectDescriptor.setName("projectedObject");

    BasicCollectionPropertyDescriptor projectedObjectsDescriptor = new BasicCollectionPropertyDescriptor();

    BasicStringPropertyDescriptor nameDescriptor = new BasicStringPropertyDescriptor();
    nameDescriptor.setName("name");

    BasicStringPropertyDescriptor descriptionDescriptor = new BasicStringPropertyDescriptor();
    descriptionDescriptor.setName("description");

    parentDescriptor.setReverseRelationEnd(subModulesDescriptor);
    subModulesDescriptor.setReverseRelationEnd(parentDescriptor);

    List<IPropertyDescriptor> propertyDescriptors = new ArrayList<IPropertyDescriptor>();
    propertyDescriptors.add(projectedObjectDescriptor);
    propertyDescriptors.add(projectedObjectsDescriptor);
    propertyDescriptors.add(nameDescriptor);
    propertyDescriptors.add(descriptionDescriptor);
    propertyDescriptors.add(parentDescriptor);
    propertyDescriptors.add(subModulesDescriptor);
    setPropertyDescriptors(propertyDescriptors);
  }
}
