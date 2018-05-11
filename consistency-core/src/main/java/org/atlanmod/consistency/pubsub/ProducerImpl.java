package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.IntegerId;

import java.io.Serializable;

public class ProducerImpl extends PubSub implements Producer {

    public ProducerImpl(Broker broker) {
        super(broker);
        clientId = new IntegerId(nextId++);
    }

    public boolean publish(Topic topic,Serializable message) {
        return broker.receive(topic, message) && sentMsgHistory.add(message);
    }

    @Override
    public void send(Serializable serializable) {
        publish(groupTopic, serializable);
    }
}
