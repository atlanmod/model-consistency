package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.Id;
import org.eclipse.emf.common.util.URI;

public abstract class PubSub {
    public static int TIMEOUT_MS = 1000;
    static Integer nextId = 0;
    public static Topic groupTopic = new Topic(URI.createURI("uniquegrouptopic"));

    Id clientId;
    Broker broker;

    PubSub(Broker broker) {
        this.broker = broker;
    }
}
