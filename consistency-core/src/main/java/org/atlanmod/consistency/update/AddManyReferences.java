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
import org.atlanmod.consistency.message.MessageType;
import org.atlanmod.consistency.message.UpdateMessage;
import org.atlanmod.consistency.message.ValueMessage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.atlanmod.consistency.util.ConsistencyUtil.identifierFor;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class AddManyReferences extends BaseOperation {
    private final FeatureId fid;
    private final List<Id> oids;

    public AddManyReferences(FeatureId fid, List<Id> oids, NodeId originator) {
        super(originator);
        this.fid = fid;
        this.oids = oids;
    }

    @Override
    public String toString() {
        return "AddManyReferences{" +
                "fid=" + fid +
                ", value=" + oids +
                '}';
    }

    @Override
    public UpdateMessage asMessage() {
        return new ValueMessage(MessageType.AddManyReferences, fid, oids, null, getOriginator());
    }

    @Override
    public Id instanceId() {
        return fid.asInstanceId();
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {
        List<EObject> objects = new ArrayList<>();
        for (Id id : oids) {
            objects.add(resource.contents().get(id));
        }
        ((Collection) ((BasicEObjectImpl) eObject).eGet(fid.toInt(),true,true)).addAll(objects);
    }
}
