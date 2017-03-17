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

import fr.inria.atlanmod.appa.activemq.PubSub;
import fr.inria.atlanmod.appa.core.Service;
import fr.inria.atlanmod.appa.datatypes.Id;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import javax.annotation.Nonnull;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public class SharedResourceService implements Service {

    private final PubSub pubSub;

    public SharedResourceService(@Nonnull PubSub pubSub) {
        this.pubSub = pubSub;
    }


    @Override
    public Id id() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public int port() {
        return 0;
    }

    @Override
    public void run() {

    }

    public SharedResource open(@Nonnull URI uri) {
        return null; //SharedResourceFactory.getInstance().createResource(uri);
    }

    public SharedResource share(Resource resource) {
        URI uri = resource.getURI();
        return null;

    }

}
