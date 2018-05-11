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

import com.google.common.collect.Maps;
import graph.Graph;
import org.atlanmod.consistency.adapter.EObjectAdapter;
import org.atlanmod.consistency.core.Id;
import org.atlanmod.consistency.core.IdBuilder;
import org.atlanmod.consistency.core.InstanceId;
import org.atlanmod.consistency.core.ResourceId;
import org.atlanmod.consistency.message.UpdateMessage;
import org.atlanmod.consistency.pubsub.Consumer;
import org.atlanmod.consistency.pubsub.ProducerImpl;
import org.atlanmod.consistency.update.Attach;
import org.atlanmod.consistency.update.ChangeManager;
import org.atlanmod.consistency.update.Detach;
import org.atlanmod.consistency.update.Operation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import java.util.Map;
import java.util.Objects;

import static org.atlanmod.consistency.util.ConsistencyUtil.adapterFor;

//import org.atlanmod.appa.pubsub.Consumer;
//import org.atlanmod.appa.pubsub.ProducerImpl;

/**
 * Created on 17/02/2017.
 */
public class SharedResource extends ResourceImpl {

    private Map<Id, EObject> contents = Maps.newHashMap();
    //private IdBuilder builder = new IdBuilder();
    private History history = new History(this);
    private ChangeManager manager = new ChangeManager(history);
    private ResourceId rid; //= IdBuilder.generateRID();
    private ProducerImpl producer;
    private Consumer consumer;


    public SharedResource(URI uri, ProducerImpl producer, Consumer consumer) {
        this(uri, IdBuilder.generateRID(),producer,consumer);
    }

    public SharedResource(URI uri, ResourceId rid, ProducerImpl producer, Consumer consumer) {
        super(uri);
        this.rid = rid;
        this.producer = producer;
        this.consumer = consumer;
    }

    @Override
    public void attachedHelper(EObject eObject) {
        InstanceId oid;
        EObjectAdapter adapter = adapterFor(eObject);
        if (Objects.isNull(adapter)) {
            oid = rid.nextId();
            eObject.eAdapters().add(new EObjectAdapter(manager,oid));
            contents.put(oid, eObject);
            history.add(new Attach(oid));
        } else {
            oid = adapter.id();
            history.basicAdd(new Attach(oid));
        }

        System.out.println("Adding Id to: " + oid);
    }

    @Override
    public void detachedHelper(EObject eObject) {
        EObjectAdapter adapter = adapterFor(eObject);
        if (Objects.nonNull(adapter)) {
            Id oid = adapter.id();
            System.out.println("--detaching object "+oid+"--");
            eObject.eAdapters().remove(adapter);
            contents.remove(oid);
            history.add(new Detach(oid));
        }
        super.detachedHelper(eObject);
    }

    @Override
    protected boolean isAttachedDetachedHelperRequired() {
        return true;
    }


    public void execute(Operation operation) {
        Id oid = operation.instanceId();
        EObject eObject = contents.get(oid);
        eObject.eSetDeliver(false);
        operation.execute(this,eObject);
        eObject.eSetDeliver(true);
        history.add(operation);

    }

    public void cancel(Operation operation) {

    }

    public void broadcast(Operation operation) {
        //producer.send(operation.asMessage());
    }

    public void receive(UpdateMessage message) {
        Operation operation = null;
        switch (message.type()) {
            case Attach:
                operation = new Attach(message);
                break;
            case Detach:
                operation = new Detach(message);
        }

        if(operation != null) {this.history.integrate(operation);}
    }

    /**
     * Checks by semi-recursion whether an object appears in the resource (sub)content
     *
     * @param object the object to search
     * @return true if object is contained by the resource or one of its contents
     */

    public boolean contains(EObject object) {
        boolean containment = contents.containsValue(object);

        if (!containment) {
            for (EObject each : contents.values()) {
                if (each.eContents().contains(object)) {
                    containment = true;
                    break;
                }
            }
        }

        return containment;
    }

    /**
     * A basic output summary of what happened in this resource for the session
     */
    public void summary() {
        int counter;
        boolean plural;

        System.out.println("\n\n------ RESOURCE " + uri + " SUMMARY ------\nRID : " + rid);

        plural = contents.size() > 1;
        System.out.println("\nThere " + (plural ? "are " : "is ") + contents.size() + (plural ? " different EObjects" : " EObject") + " in the resource :\n");

        counter = 1;
        for (EObject each : contents.values()) {
            System.out.println("EObject " + counter++ + " : " + ((each instanceof Graph) ? (each + ((Graph)each).output()) : each));
        }

        plural = history.basicHistory().size() > 1;
        System.out.println("\nThere " + (plural ? "are " : "is ") + history.basicHistory().size() + " registered operation" + (plural ? "s" : "") + " in the resource :\n");

        counter = 1;
        for (Operation each : history.basicHistory()){
            System.out.println("Operation " + counter++ + " : " + each);
        }


        System.out.println("\n---------------------------- END OF RESOURCE ----------------------------");
    }

    public History getHistory() {
        return history;
    }

    public Map<Id, EObject> contents() {
        return contents;
    }

    public class ConsumerThread implements Runnable {
        @Override
        public void run() {
            /*while (true) {
                UpdateMessage message = (UpdateMessage) consumer.receive(3000);
            }*/

        }
    }

}
