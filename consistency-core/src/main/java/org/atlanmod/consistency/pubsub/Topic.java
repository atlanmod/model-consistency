package org.atlanmod.consistency.pubsub;

import org.eclipse.emf.common.util.URI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Topic {
    private URI uri;
    private BlockingQueue<Serializable> unreadMessages = new LinkedBlockingQueue<>();
    private List<ConsumerImpl> subscribers = new ArrayList<>();
    private List<Serializable> history = new ArrayList<>();

    public Topic(URI uri) {
        this.uri = uri;
    }

    public boolean add(Serializable message) {
        return unreadMessages.offer(message) && history.add(message);
    }

    public boolean newSub(ConsumerImpl sub) {
        return subscribers.add(sub);
    }

    public void publish() {
        for (Serializable msg : unreadMessages) {
            for (ConsumerImpl sub : subscribers) {
                sub.receive(msg);
            }
            unreadMessages.remove();
        }
    }

    public boolean removeSub(ConsumerImpl subscriber) {
        return subscribers.remove(subscriber);
    }

    public int subCount() {
        return subscribers.size();
    }

    public boolean hasUnconsumedMessages() {
        return unreadMessages.size() > 0;
    }

    public URI getUri() {
        return uri;
    }
}
