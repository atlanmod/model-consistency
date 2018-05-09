package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.Id;

import java.util.ArrayList;
import java.util.List;

public abstract class PubSub {
    static Integer nextId = 0;
    List<Object> msgHistory = new ArrayList<>();
    Broker broker;
    Id clientId;

    PubSub(Broker broker) {
        this.broker = broker;
    }
    public List<Object> getMsgHistory() {
        return msgHistory;
    }
}
