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

package fr.inria.atlanmod.consistency;

import com.google.common.collect.Maps;
import fr.inria.atlanmod.consistency.adapter.EObjectAdapter;
import fr.inria.atlanmod.consistency.core.Id;
import fr.inria.atlanmod.consistency.core.IdBuilder;
import fr.inria.atlanmod.consistency.core.InstanceId;
import fr.inria.atlanmod.consistency.core.ResourceId;
import fr.inria.atlanmod.consistency.update.ChangeManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import java.util.Map;
import java.util.Objects;

import static fr.inria.atlanmod.consistency.util.ConsistencyUtil.adapterFor;

/**
 * Created on 17/02/2017.
 */
public class SharedResource extends ResourceImpl {

    private Map<Id, EObject> contents = Maps.newHashMap();
    private IdBuilder builder = new IdBuilder();
    private ChangeManager manager = new ChangeManager();
    private ResourceId rid = builder.generateRID();


    public SharedResource(URI uri) {
        super(uri);
    }

    @Override
    protected void attachedHelper(EObject eObject) {
        InstanceId id = rid.nextId();
        eObject.eAdapters().add(new EObjectAdapter(manager,id));
        contents.put(id, eObject);

        System.out.println("Adding Id to: " + id);
    }

    @Override
    protected void detachedHelper(EObject eObject) {
        EObjectAdapter adapter = adapterFor(eObject);
        System.out.println("--detaching object "+adapter.id()+"--");
        if (Objects.nonNull(adapter)) {
            eObject.eAdapters().remove(adapter);
            contents.remove(adapter.id());
        }
        super.detachedHelper(eObject);
    }

    @Override
    protected boolean isAttachedDetachedHelperRequired() {
        return true;
    }



}
