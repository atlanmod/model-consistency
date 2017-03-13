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

import fr.inria.atlanmod.consistency.core.FeatureId;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class SetValue extends Operation {
    private final FeatureId fid;
    private final Object value;

    public SetValue(FeatureId fid, Object value) {
        this.fid = fid;
        this.value = value;
    }

    @Override
    public String toString() {
        return "SetValue{" +
                "fid=" + fid +
                ", value=" + value +
                '}';
    }
}
