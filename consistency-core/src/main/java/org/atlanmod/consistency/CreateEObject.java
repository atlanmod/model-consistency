/*
 *
 *  * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 *
 *
 */

package org.atlanmod.consistency;

import org.atlanmod.consistency.core.Id;
import org.atlanmod.consistency.message.AbstractUpdateMessage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;


public class CreateEObject extends AbstractUpdateMessage {

    private Id id;
    private EObject eObject;

    public CreateEObject(Id id, EClass eClass) {

        super(null);
        //eClass.getClassifierID();
        this.id = id;

         if (eClass != null) this.eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
    }

    public EObject getObject() {
        return eObject;
    }
}
