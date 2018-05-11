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


public class IdBuilder {

    private static short lastRID = 0;

    public static Id fromShort(short s) {
        return new ShortId(s);
    }

    public static Id fromInt(int i) {
        return new IntegerId(i);
    }

    public static ResourceId generateRID(){
        return new ResourceId(lastRID++);
    }

/*    public Id generate() {
        return new Id(high, low++);
    }*/

//    public TypeId from(EStructuralFeature feature) {
//        feature.getFeatureID();
//    }
//
//    public TypeId from(EReference reference) {
//        reference.getFeatureID();
//    }
//
//    public TypeId from(EClass eClass) {
//        eClass.getClassifierID();
//    }
}
