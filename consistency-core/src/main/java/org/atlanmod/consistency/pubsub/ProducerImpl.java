package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.IntegerId;

public class Producer extends PubSub {

    public Producer(Broker broker) {
        super(broker);
        clientId = new IntegerId(nextId++);
    }

    public boolean publish(Topic topic,Object message) {
        msgHistory.add(message);
        return broker.receive(topic, message);
    }
}
