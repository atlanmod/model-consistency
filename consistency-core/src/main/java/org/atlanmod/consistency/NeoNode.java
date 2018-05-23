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

import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.consistency.core.NodeId;
import org.atlanmod.consistency.message.MessageType;
import org.atlanmod.consistency.message.UpdateMessage;
import org.atlanmod.consistency.pubsub.*;
import org.atlanmod.consistency.update.Detach;
import org.atlanmod.consistency.update.Operation;
import org.eclipse.emf.common.util.URI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

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
        resourceSet.getSharedResources().add(new SharedResource(uri, nid.nextRID(), nid));
    }

    /**
     * Short summary of what happened in the node during the session, for each resource contained in the node
     */

    public void summary() {
        int i = 0;

        //Log.info("---------------------------- NODE " + nid + " SUMMARY ---------------------------");
        System.out.println("---------------------------- NODE " + nid + " SUMMARY ---------------------------");
        for (SharedResource each : resourceSet.getSharedResources()) {
            //Log.info("Resource " + (++i) + " : " + each.getURI());
            System.out.println("Resource " + (++i) + " : " + each.getURI());
            each.summary();
        }
        //Log.info("------------------------------ END OF NODE ------------------------------\n");
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
                    //Log.trace(e);
                }
            }
        }
    }

    /**
     * Starts the process of receiving and dealing with a message
     * @param message the message to deal with
     */
    private void receive(UpdateMessage message) {
        //sub.receive(PubSub.TIMEOUT_MS);
        for (SharedResource resource : resourceSet.getSharedResources()) {
            resource.receive(message);
        }
    }

    /**
     * Receives all the messages sent to the node via the sub
     */
    public void receiveAll() {
        List<UpdateMessage> detachments = new ArrayList<>();
        UpdateMessage message;


        while (!sub.getReceived().isEmpty()) {
            message = (UpdateMessage) sub.getReceived().element();
            if (!nid.equals(message.getOriginator()) && !(message.getOriginator() == null)) {
                sub.archive();
                if (message.type().equals(MessageType.Detach)) {
                    detachments.add(message);
                } else {
                    receive(message);
                }
            } else {
                sub.getReceived().remove();
            }
        }
        for (UpdateMessage eachDetach : detachments) {
            receive(eachDetach);
        }
    }
}
