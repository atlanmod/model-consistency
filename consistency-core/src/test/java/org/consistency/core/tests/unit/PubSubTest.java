package org.consistency.core.tests.unit;

import org.atlanmod.consistency.pubsub.Broker;
import org.atlanmod.consistency.pubsub.Publisher;
import org.atlanmod.consistency.pubsub.Subscriber;
import org.atlanmod.consistency.pubsub.Topic;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PubSubTest {

    private Broker broker;
    private Publisher publisher;
    private Topic topic1, topic2;
    private Subscriber sub1, sub2;

    @BeforeEach
    void setup() {
        broker = new Broker();
        publisher = new Publisher(broker);

        sub1 = new Subscriber(broker);
        sub2 = new Subscriber(broker);
        topic1 = new Topic(URI.createURI("topic1"));
        topic2 = new Topic(URI.createURI("topic2"));

        sub1.subscribe(topic1);
        sub2.subscribe(topic1);
        sub2.subscribe(topic2);
    }

    @Test
    void testInit() {
        assertThat(broker.getTopics().size() == 2);
        assertThat(broker.containsTopic(topic1));
    }
    @Test
    void testPublish() {
        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(topic2.hasUnconsumedMessages()).isFalse();

        publisher.publish(topic1, "Hello");

        assertThat(topic1.hasUnconsumedMessages()).isTrue();
        assertThat(topic2.hasUnconsumedMessages()).isFalse();

        assertThat(publisher.getMsgHistory().size() > 0);
    }

    @Test
    void testTopicPublish() {
        publisher.publish(topic1, "Hello");

        assertThat(topic1.hasUnconsumedMessages()).isTrue();
        assertThat(sub1.getMsgHistory().size() == 0);
        assertThat(sub2.getMsgHistory().size() == 0);

        broker.topicPublish(topic1);

        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(sub1.getMsgHistory().size() > 0);
        assertThat(sub2.getMsgHistory().size() > 0);
    }

    @Test
    void testTopicPublishAll() {
        publisher.publish(topic1, "Hello topic 1");
        publisher.publish(topic2, "Hello topic 2");

        assertThat(topic1.hasUnconsumedMessages()).isTrue();
        assertThat(topic2.hasUnconsumedMessages()).isTrue();

        broker.publishAll();

        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(topic2.hasUnconsumedMessages()).isFalse();
    }

    @Test
    void testUnsub() {
        sub2.unsubscribe(topic1);

        assertThat(topic1.subCount() == 1 && topic2.subCount() == 0);
    }
}
