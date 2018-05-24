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
import org.atlanmod.consistency.core.NodeId;
import org.atlanmod.consistency.message.MessageType;
import org.atlanmod.consistency.message.UpdateMessage;
import org.atlanmod.consistency.message.ValueMessage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;

import javax.xml.soap.Node;
import java.util.Collection;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class AddValue extends FeatureOperation {
    private final Object value;

    public AddValue(FeatureId fid, Object value, NodeId originator) {
        super(fid, originator);
        this.value = value;
    }

    @Override
    public String toString() {
        return getOriginator() + " AddValue{" +
                "fid=" + featureId() +
                ", value=" + value +
                '}';
    }

    @Override
    public UpdateMessage asMessage() {
        return new ValueMessage(MessageType.AddValue, featureId(), value, null, getOriginator());
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {
        BasicEObjectImpl obj = ((BasicEObjectImpl) (resource.contents().get(featureId().asInstanceId())));
        ((Collection) obj.eGet(featureId().toInt(),true,true)).add(eObject);
    }
}
