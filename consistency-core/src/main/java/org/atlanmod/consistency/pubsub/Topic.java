package org.atlanmod.consistency.pubsub;

import org.eclipse.emf.common.util.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Topic {
    private URI uri;
    private Queue<Object> messages = new LinkedBlockingQueue<>();
    private List<Subscriber> subscribers = new ArrayList<>();


    public Topic(URI uri) {
        this.uri = uri;
    }

    public boolean add(Object message) {
        return messages.add(message);
    }

    public boolean newSub(Subscriber sub) {
        return subscribers.add(sub);
    }

    public void publish() {
        for (Object msg : messages) {
            for (Subscriber sub : subscribers) {
                sub.receive(msg);
            }
            messages.remove();
        }
    }

    public boolean removeSub(Subscriber subscriber) {
        return subscribers.remove(subscriber);
    }

    public int subCount() {
        return subscribers.size();
    }

    public boolean hasUnconsumedMessages() {
        return messages.size() > 0;
    }
}
