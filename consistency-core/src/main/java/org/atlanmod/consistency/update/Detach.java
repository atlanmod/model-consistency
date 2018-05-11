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
import org.atlanmod.consistency.core.Id;
import org.atlanmod.consistency.message.InstanceMessage;
import org.atlanmod.consistency.message.MessageType;
import org.atlanmod.consistency.message.UpdateMessage;
import org.eclipse.emf.ecore.EObject;

/**
 * Created on 09/03/2017.
 *
 * @author AtlanMod team.
 */
public class Detach implements Operation {
    private final Id instanceId;

    public Detach(Id instanceId) {
        this.instanceId = instanceId;
    }

    public Detach(UpdateMessage message) {
        instanceId = message.instanceId();
    }

    @Override
    public Id instanceId() {
        return instanceId;
    }

    @Override
    public UpdateMessage asMessage() {
        return new InstanceMessage(MessageType.Detach, this.instanceId);
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {
        resource.getContents().remove(eObject);
    }
}
