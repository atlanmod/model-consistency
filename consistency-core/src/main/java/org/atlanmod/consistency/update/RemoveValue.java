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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;

import java.lang.annotation.Inherited;
import java.util.Objects;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class RemoveValue extends BaseOperation {
    private final FeatureId fid;
    private final Object value;

    public RemoveValue(FeatureId fid, Object value, NodeId originator) {
        super(originator);
        this.fid = fid;
        this.value = value;
    }

    @Override
    public String toString() {
        return getOriginator() + " RemoveValue{" +
                "fid=" + fid +
                ", value=" + value +
                '}';
    }

    @Override
    public UpdateMessage asMessage() {
        return new ValueMessage(MessageType.RemoveValue, fid, value, null, getOriginator());
    }

    @Override
    public Id instanceId() {
        return fid.asInstanceId();
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {
        ((EList<Objects>) ((BasicEObjectImpl)eObject).eGet(fid.toInt(),true,true)).remove(value);
    }


}
