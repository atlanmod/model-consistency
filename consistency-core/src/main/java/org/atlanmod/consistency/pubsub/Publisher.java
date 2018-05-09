package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.IntegerId;

public class Publisher extends PubSub {

    public Publisher(Broker broker) {
        super(broker);
        clientId = new IntegerId(nextId++);
    }

    public boolean publish(Topic topic,Object message) {
        msgHistory.add(message);
        return broker.receive(topic, message);
    }
}
