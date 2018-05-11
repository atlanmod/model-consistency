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

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class MoveValue extends BaseOperation {
    private final FeatureId fid;
    private final Object from;
    private final Object to;

    public MoveValue(FeatureId fid, Object from, Object to) {
        this.fid = fid;
        this.from = from;
        this.to = to;
    }


    @Override
    public String toString() {
        return "MoveValue{" +
                "fid=" + fid +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public Id instanceId() {
        return fid.asInstanceId();
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {
        return;
    }
}
