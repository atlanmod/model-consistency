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
package fr.inria.atlanmod.consistency.adapter;

import fr.inria.atlanmod.consistency.core.Id;
import fr.inria.atlanmod.consistency.core.InstanceId;
import fr.inria.atlanmod.consistency.update.ChangeManager;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;

public class EObjectAdapter implements Adapter {

    private final InstanceId id;
    private final ChangeManager manager;

    public EObjectAdapter(ChangeManager cm, InstanceId id) {
        this.manager = cm;
        this.id = id;
    }

    @Override
    public void notifyChanged(Notification notification) {
        manager.notifyChanged(id, notification);
    }

    @Override
    public Notifier getTarget() {
        return null;
    }

    @Override
    public void setTarget(Notifier notifier) {

    }

    @Override
    public boolean isAdapterForType(Object o) {
        return EObject.class.isAssignableFrom(o.getClass());
    }

    public Id id() {
        return id;
    }
}
