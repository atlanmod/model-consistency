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
import org.atlanmod.consistency.message.UpdateMessage;
import org.eclipse.emf.ecore.EObject;

/**
 * Created on 09/03/2017.
 *
 * @author AtlanMod team.
 */
public interface Operation {

    /**
     *
     * @return an Id.
     */
    Id instanceId();

    /**
     * Transforms this operation into a Serializable message.
     *
     * @return an UpdateMessage
     */
    UpdateMessage asMessage();

    /**
     *
     * @param resource a SharedResource
     * @param eObject the concerned EObject
     */
    void execute(SharedResource resource, EObject eObject);
}
