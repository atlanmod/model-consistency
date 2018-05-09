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

package org.atlanmod.consistency.update;

import org.atlanmod.consistency.SharedResource;
import org.atlanmod.consistency.core.FeatureId;
import org.atlanmod.consistency.core.Id;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class AddReference extends BaseOperation {
    private final FeatureId fid;
    private final Id oid;

    public AddReference(FeatureId fid, Id oid) {
        this.fid = fid;
        this.oid = oid;
    }

    @Override
    public String toString() {
        return "AddReference{" +
                "fid=" + fid +
                ", oid=" + oid +
                '}';
    }

    @Override
    public Id instanceId() {
        return oid;
    }

   /* @Override
    public void execute(SharedResource resource, EObject eObject) {

        BasicEObjectImpl obj = ((BasicEObjectImpl) (resource.contents().get(fid.asInstanceId())));
        obj.eSet(fid.toInt(),eObject);
    }*/
}
