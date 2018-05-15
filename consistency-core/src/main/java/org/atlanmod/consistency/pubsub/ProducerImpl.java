package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.IntegerId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProducerImpl extends PubSub implements Producer {

    private List<Serializable> sentMsgHistory = new ArrayList<>();

    public ProducerImpl(Broker broker) {
        super(broker);
        clientId = new IntegerId(nextId++);
    }

    public boolean publish(Topic topic,Serializable message) {
        return broker.receive(topic, message) && sentMsgHistory.add(message);
    }

    public List<Serializable> getSent() {
        return sentMsgHistory;
    }

    @Override
    public void send(Serializable serializable) {
        publish(groupTopic, serializable);
    }
}
