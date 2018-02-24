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
import fr.inria.atlanmod.consistency.core.Id;
import fr.inria.atlanmod.consistency.message.InstanceMessage;
import fr.inria.atlanmod.consistency.message.MessageType;
import fr.inria.atlanmod.consistency.message.UpdateMessage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Created on 09/03/2017.
 *
 * @author AtlanMod team.
 */
public class Attach implements Operation {
    private Id instanceId;

    public Attach(Id instanceId) {
        this.instanceId = instanceId;
    }

    public Attach(UpdateMessage message) {
        this.instanceId = message.instanceId();
    }

    @Override
    public Id instanceId() {
        return null;
    }

    @Override
    public UpdateMessage asMessage() {
        return new InstanceMessage(MessageType.Attach, this.instanceId);
    }


    @Override
    public void execute(SharedResource resource, EObject eObject) {
        resource.getContents().add(eObject);
    }
}
