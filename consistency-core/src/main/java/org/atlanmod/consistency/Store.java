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

package org.atlanmod.consistency;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

public class Store implements InternalEObject.EStore {

    private final InternalEObject.EStore  decorated;

    public Store(InternalEObject.EStore eStore) {
        decorated = eStore;
    }

    @Override
    public Object get(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, int i) {
        return decorated.get(internalEObject, eStructuralFeature, i);
    }

    @Override
    public Object set(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, int i, Object o) {
        return decorated.set(internalEObject, eStructuralFeature, i, o);
    }

    @Override
    public boolean isSet(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        return decorated.isSet(internalEObject, eStructuralFeature);
    }

    @Override
    public void unset(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        decorated.unset(internalEObject, eStructuralFeature);
    }

    @Override
    public boolean isEmpty(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        return decorated.isEmpty(internalEObject, eStructuralFeature);
    }

    @Override
    public int size(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        return decorated.size(internalEObject, eStructuralFeature);
    }

    @Override
    public boolean contains(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, Object o) {
        return decorated.contains(internalEObject, eStructuralFeature, o);
    }

    @Override
    public int indexOf(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, Object o) {
        return decorated.indexOf(internalEObject, eStructuralFeature, o);
    }

    @Override
    public int lastIndexOf(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, Object o) {
        return decorated.lastIndexOf(internalEObject, eStructuralFeature, o);
    }

    @Override
    public void add(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, int i, Object o) {
        decorated.add(internalEObject, eStructuralFeature, i, o);
    }

    @Override
    public Object remove(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, int i) {
        return decorated.remove(internalEObject, eStructuralFeature, i);
    }

    @Override
    public Object move(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, int i, int i1) {
        return decorated.move(internalEObject, eStructuralFeature, i, i1);
    }

    @Override
    public void clear(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        decorated.clear(internalEObject, eStructuralFeature);
    }

    @Override
    public Object[] toArray(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        return decorated.toArray(internalEObject, eStructuralFeature);
    }

    @Override
    public <T> T[] toArray(InternalEObject internalEObject, EStructuralFeature eStructuralFeature, T[] ts) {
        return decorated.toArray(internalEObject, eStructuralFeature, ts);
    }

    @Override
    public int hashCode(InternalEObject internalEObject, EStructuralFeature eStructuralFeature) {
        return decorated.hashCode(internalEObject, eStructuralFeature);
    }

    @Override
    public InternalEObject getContainer(InternalEObject internalEObject) {
        return decorated.getContainer(internalEObject);
    }

    @Override
    public EStructuralFeature getContainingFeature(InternalEObject internalEObject) {
        return decorated.getContainingFeature(internalEObject);
    }

    @Override
    public EObject create(EClass eClass) {
        return decorated.create(eClass);
    }
}
