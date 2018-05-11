package org.atlanmod.consistency.pubsub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Broker {
    private List<Topic> topics = new ArrayList<>();

    public boolean receive(Topic topic, Serializable message) {
        int index = getTopicIndexOrAdd(topic);

        return topics.get(index).add(message);
    }

    public boolean newSubscriber(ConsumerImpl sub, Topic topic) {
        int index = getTopicIndexOrAdd(topic);

        return topics.get(index).newSub(sub);
    }

    public void topicPublish(Topic topic) {
        assert topics.contains(topic) : "This topic does not exist in this broker";

        int index = topics.indexOf(topic);
        topics.get(index).publish();
    }

    public void publishAll() {
        for (Topic topic : topics) {
            if (topic.hasUnconsumedMessages())
                topicPublish(topic);
        }
    }

    public boolean unsubscribe(ConsumerImpl subscriber, Topic topic) {
        assert topics.contains(topic) : "This topic does not exist in this broker";

        int index = topics.indexOf(topic);
        return topics.get(index).removeSub(subscriber);
    }

    private int getTopicIndexOrAdd(Topic topic) {
        if (!topics.contains(topic)) {
            topics.add(topic);
        }
        return topics.indexOf(topic);
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public boolean containsTopic(Topic topic) {
        return topics.contains(topic);
    }
}
