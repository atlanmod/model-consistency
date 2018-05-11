package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.Id;
import org.eclipse.emf.common.util.URI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class PubSub {
    static int TIMEOUT_MS = 10000;
    static Integer nextId = 0;
    static Topic groupTopic = new Topic(URI.createURI("uniquegrouptopic"));

    Id clientId;
    List<Serializable> sentMsgHistory = new ArrayList<>();
    List<Serializable> receivedMsgHistory = new ArrayList<>();
    Broker broker;


    PubSub(Broker broker) {
        this.broker = broker;
    }
    public List<Serializable> getReceived() {
        return receivedMsgHistory;
    }

    public List<Serializable> getSent() {
        return sentMsgHistory;
    }
}
