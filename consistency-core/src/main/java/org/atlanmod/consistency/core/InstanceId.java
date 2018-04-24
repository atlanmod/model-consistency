/*
 * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */
package org.atlanmod.consistency.core;


import org.eclipse.emf.ecore.EStructuralFeature;

public class InstanceId extends CompositeId {

    protected InstanceId(Id high, Id low) {
        super(high, low);
    }

    public FeatureId withFeature(EStructuralFeature feature) {
        assert feature.getFeatureID() <= Short.MAX_VALUE;

        return new FeatureId(this, IdBuilder.fromShort((short) feature.getFeatureID()));
    }
}
