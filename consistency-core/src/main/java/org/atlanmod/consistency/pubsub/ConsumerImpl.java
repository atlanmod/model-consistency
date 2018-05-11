package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.IntegerId;

public class Subscriber extends PubSub {

    public Subscriber(Broker broker) {
        super(broker);
        clientId = new IntegerId(nextId++);
    }

    public boolean subscribe(Topic topic) {
        return broker.newSubscriber(this, topic);
    }

    public boolean unsubscribe(Topic topic) {
        return broker.unsubscribe(this, topic);
    }

    public boolean receive(Object message) {
        return msgHistory.add(message);
    }
}
