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

package org.atlanmod.consistency.util;

import org.atlanmod.consistency.adapter.EObjectAdapter;
import org.atlanmod.consistency.core.Id;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import static java.util.Objects.isNull;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class ConsistencyUtil {

    public static EObjectAdapter adapterFor(EObject eObject) {
        return eObject.eAdapters().stream()
                .filter(EObjectAdapter.class::isInstance)
                .map(EObjectAdapter.class::cast)
                .findFirst().orElse(null);
    }

    public static Id identifierFor(EObject eObject) {
        EObjectAdapter adapter = adapterFor(eObject);
        return isNull(adapter) ? null : adapter.id();
    }

    public static boolean isEReference(EStructuralFeature feature) {
        return EReference.class.isAssignableFrom(feature.getClass());
    }

    public static boolean isEAttribute(EStructuralFeature feature) {
        return EAttribute.class.isAssignableFrom(feature.getClass());
    }

}
