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

//import org.atlanmod.appa.Node;

import org.atlanmod.consistency.core.NodeId;
import org.atlanmod.consistency.message.UpdateMessage;
import org.atlanmod.consistency.pubsub.*;
import org.eclipse.emf.common.util.URI;

import java.io.Serializable;

/**
 * Created on 09/03/2017.
 * @author AtlanMod team.
 */
public class NeoNode //extends Node
{
    private static short lastNodeId = 0;

    private NodeId nid = new NodeId(lastNodeId++);

    private Producer pub;
    private Consumer sub;

    private SharedResourceSet resourceSet = new SharedResourceSet();

    public NeoNode(Broker broker) {
        pub = new ProducerImpl(broker);
        sub = new ConsumerImpl(broker);
    }

    public SharedResourceSet getSharedResourceSet() {
        return resourceSet;
    }

    public void attachResource(URI uri) {
        resourceSet.getSharedResources().add(new SharedResource(uri, nid.nextRID()));
    }

    /**
     * Short summary of what happened in the node during the session, for each resource contained in the node
     */

    public void summary() {
        int i = 0;

        System.out.println("---------------------------- NODE " + nid + " SUMMARY ---------------------------");
        for (SharedResource each : resourceSet.getSharedResources()) {
            System.out.println("Resource " + (++i) + " : " + each.getURI());
            each.summary();
        }
        System.out.println("------------------------------ END OF NODE ------------------------------\n");
    }

    public void send(UpdateMessage message) {
        pub.send(message);
    }

    public void sendAll() {
        for (SharedResource resource : resourceSet.getSharedResources()) {
            while (resource.getHistory().queue().size() > 0) {
                try {
                    send(resource.getHistory().queue().take().asMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Starts the process of receiving and dealing with a message
     * @param message the message to deal with
     */
    private void receive(Serializable message) {
        sub.receive(PubSub.TIMEOUT_MS);
        for (SharedResource resource : resourceSet.getSharedResources()) {
            resource.receive((UpdateMessage) message);
        }
    }

    /**
     * Receives all the messages sent to the node via the sub
     */
    public void receiveAll() {
        while (!sub.getReceived().isEmpty()) {
            try {
                receive(sub.getReceived().take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
