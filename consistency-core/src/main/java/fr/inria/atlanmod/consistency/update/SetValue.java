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

package fr.inria.atlanmod.consistency.update;

import fr.inria.atlanmod.consistency.SharedResource;
import fr.inria.atlanmod.consistency.core.FeatureId;
import fr.inria.atlanmod.consistency.message.MessageType;
import fr.inria.atlanmod.consistency.message.UpdateMessage;
import fr.inria.atlanmod.consistency.message.ValueMessage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class SetValue extends FeatureOperation {
    private final Object value;
    private final Object previous;

    public SetValue(FeatureId fid, Object value, Object previous) {
        super(fid);
        this.value = value;
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "SetValue{" +
                "fid=" + featureId() +
                ", value=" + value +
                ", previous="+previous+
                '}';
    }

    @Override
    public UpdateMessage asMessage() {
        return new ValueMessage(MessageType.Set, featureId(), value, previous);
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {
        EStructuralFeature feature = eObject.eClass().getEStructuralFeature(featureId().low().toInt());
        eObject.eSet(feature, eObject);
    }
}
