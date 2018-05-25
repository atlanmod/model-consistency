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
import org.atlanmod.consistency.core.NodeId;
import org.atlanmod.consistency.message.InstanceMessage;
import org.atlanmod.consistency.message.MessageType;
import org.atlanmod.consistency.message.UpdateMessage;
import org.atlanmod.consistency.message.ValueMessage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;

import java.util.*;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */

/*
This class extends RMR because of how notifications work.
RMR oids are actually indexes of the EObject reference in a feature, while RR oid is a real oid.
The problem is that you can't remove a reference to a given oid in a feature once the object has been detached of the resource.
The idea here is to create a singleton to use the index solution and be able to remove the reference in a feature.
 */

public class RemoveReference extends RemoveManyReferences {
    //private final FeatureId fid;
    //private final Id oid;

    public RemoveReference(FeatureId fid, Id oid, NodeId originator) {
        super(fid, Collections.singletonList(oid), originator);
        //this.fid = fid;
        //this.oid = oid;
    }

    @Override
    public String toString() {
        return getOriginator() + " RemoveReference{" +
                "fid=" + super.fid +
                ", oid=" + super.oids +
                '}';
    }

    @Override
    public UpdateMessage asMessage() {
        return new ValueMessage(MessageType.RemoveReference, fid, oids.get(0), null, getOriginator());
    }

   /* @Override
    public Id instanceId() {
        return oid;
    }*/

    /*@Override
    public void execute(SharedResource resource, EObject eObject) {
        //EStructuralFeature feature = (EStructuralFeature) ((BasicEObjectImpl)eObject).eGet(fid.toInt(), true, true);
        EStructuralFeature feature = eObject.eClass().getEStructuralFeature(fid.toInt());

        EObject obj = resource.contents().get(oid);
        ((Collection) feature).remove(obj);
    }*/
}
