package org.atlanmod.consistency.pubsub;

import org.eclipse.emf.common.util.URI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Topic {
    private URI uri;
    private BlockingQueue<Serializable> messages = new LinkedBlockingQueue<>();
    private List<ConsumerImpl> subscribers = new ArrayList<>();


    public Topic(URI uri) {
        this.uri = uri;
    }

    public boolean add(Serializable message) {
        return messages.offer(message);
    }

    public boolean newSub(ConsumerImpl sub) {
        return subscribers.add(sub);
    }

    public void publish() {
        for (Serializable msg : messages) {
            for (ConsumerImpl sub : subscribers) {
                sub.receive(msg);
            }
            messages.remove();
        }
    }

    public boolean removeSub(ConsumerImpl subscriber) {
        return subscribers.remove(subscriber);
    }

    public int subCount() {
        return subscribers.size();
    }

    public boolean hasUnconsumedMessages() {
        return messages.size() > 0;
    }

    public BlockingQueue<Serializable> getMessages() {
        return messages;
    }
}
