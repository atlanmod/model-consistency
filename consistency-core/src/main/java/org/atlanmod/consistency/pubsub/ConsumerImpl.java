package org.atlanmod.consistency.pubsub;

import org.atlanmod.consistency.core.IntegerId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConsumerImpl extends PubSub implements Consumer {

    private BlockingQueue<Serializable> messagesBQ = new LinkedBlockingQueue<>();
    private Thread t;
    private List<Serializable> receivedMsgHistory = new ArrayList<>();

    public ConsumerImpl(Broker broker) {
        super(broker);
        clientId = new IntegerId(nextId++);
        subscribe(groupTopic);
        t = new Thread(new FetchMessage());
        t.start();
    }

    public boolean subscribe(Topic topic) {
        return broker.newSubscriber(this, topic);
    }

    public boolean unsubscribe(Topic topic) {
        return broker.unsubscribe(this, topic);
    }

    public boolean receive(Serializable message) {
        return messagesBQ.offer(message);
    }

    @Override
    public Serializable receive(int timeout) {
        Serializable message = null;
        try {
            message = messagesBQ.poll(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }


    @Override
    public List<Serializable> getReceived() {
        return receivedMsgHistory;
    }

    public class FetchMessage implements Runnable {

        @Override
        public void run() {
            Serializable message;
            int cpt = 0;
            while(cpt < 7) { // Could be while(true) for infinite fetching, until program or thread manual shutdown
                message = receive(TIMEOUT_MS);
                if (message != null)
                    receivedMsgHistory.add(message);
                //System.out.println("Thread " + Thread.currentThread().getName() + " of client " + clientId + " received " + receivedMsgHistory.size() + " messages" + ((message != null) ? (" : " + message) : ". (TIMEOUT)"));
                ++cpt;
            }
        }
    }
}
