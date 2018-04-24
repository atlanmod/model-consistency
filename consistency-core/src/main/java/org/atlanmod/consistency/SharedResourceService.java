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

import fr.inria.atlanmod.appa.core.Service;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.pubsub.Consumer;
import fr.inria.atlanmod.appa.pubsub.Producer;
import fr.inria.atlanmod.appa.pubsub.PublishSubscribe;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import javax.annotation.Nonnull;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public class SharedResourceService implements Service {

    private final PublishSubscribe pubSub;

    public SharedResourceService(@Nonnull PublishSubscribe pubSub) {
        this.pubSub = pubSub;
    }


    @Nonnull
    @Override
    public ServiceDescription description() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }


    public SharedResource open(@Nonnull URI uri) {
        return null; //SharedResourceFactory.getInstance().createResource(uri);
    }

    public SharedResource share(Resource resource) {
        URI uri = resource.getURI();
        String topic = uri.toString();
        Producer producer = pubSub.createTopic(topic);
        Consumer consumer = pubSub.consumeTopic(topic);

        SharedResource result = new SharedResource(uri, producer, consumer);
        resource.getContents().addAll(resource.getContents());
        return result;

    }

}
