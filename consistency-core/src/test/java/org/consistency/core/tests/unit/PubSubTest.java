package org.consistency.core.tests.unit;

import org.atlanmod.consistency.pubsub.Broker;
import org.atlanmod.consistency.pubsub.ProducerImpl;
import org.atlanmod.consistency.pubsub.ConsumerImpl;
import org.atlanmod.consistency.pubsub.Topic;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class PubSubTest {

    private Broker broker;
    private ProducerImpl producer;
    private Topic topic1, topic2;
    private ConsumerImpl sub1, sub2;

    @BeforeEach
    void setup() {
        broker = new Broker();
        producer = new ProducerImpl(broker);

        sub1 = new ConsumerImpl(broker);
        sub2 = new ConsumerImpl(broker);
        topic1 = new Topic(URI.createURI("topic1"));
        topic2 = new Topic(URI.createURI("topic2"));

        sub1.subscribe(topic1);
        sub2.subscribe(topic1);
        sub2.subscribe(topic2);
    }

    @Test
    void testInit() {
        assertThat(broker.getTopics().size()).isEqualTo(3); // Unique groupTopic, topic1, topic2
        assertThat(broker.containsTopic(topic1));
        assertThat(topic2.getUri()).isEqualTo(URI.createURI("topic2"));
    }
    @Test
    void testPublish() {
        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(topic2.hasUnconsumedMessages()).isFalse();

        producer.publish(topic1, "Hello");

        assertThat(topic1.hasUnconsumedMessages()).isTrue();
        assertThat(topic2.hasUnconsumedMessages()).isFalse();

        assertThat(producer.getSent().size()).isGreaterThan(0);
    }

    //@RepeatedTest(300) // Debug purpose on Thread execution (assertion being executed before the end of receiving)
    @Test
    void testTopicPublish() {
        producer.publish(topic1, "Hello");

        assertThat(topic1.hasUnconsumedMessages()).isTrue();
        assertThat(sub1.getReceived().size()).isZero();
        assertThat(sub2.getReceived().size()).isZero();

        broker.topicPublish(topic1);

        // 1 or 2 ms for IntelliJ & Maven, 5ms for Travis
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(sub1.getReceived().size()).isEqualTo(1);
        assertThat(sub2.getReceived().size()).isEqualTo(1);

        producer.publish(topic1, "World");

        broker.topicPublish(topic1);

        // 1 or 2 ms for IntelliJ & Maven, 5ms for Travis
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(sub1.getReceived().size()).isEqualTo(2);
        assertThat(sub2.getReceived().size()).isEqualTo(2);
    }

    @Test
    void testTopicPublishAll() {
        producer.publish(topic1, "Hello topic 1");
        producer.publish(topic2, "Hello topic 2");

        assertThat(topic1.hasUnconsumedMessages()).isTrue();
        assertThat(topic2.hasUnconsumedMessages()).isTrue();

        broker.publishAll();

        assertThat(topic1.hasUnconsumedMessages()).isFalse();
        assertThat(topic2.hasUnconsumedMessages()).isFalse();

        assertThat(sub1.getReceived().size()).isEqualTo(1);
        assertThat(sub2.getReceived().size()).isEqualTo(2);
    }

    @Test
    void testUnsub() {
        sub2.unsubscribe(topic1);

        assertThat(topic1.subCount()).isEqualTo(1);
        assertThat(topic2.subCount()).isEqualTo(1);
    }

    @Test
    void testUniqueTopic() {
        producer.send("Hello");

        broker.publishAll();

        // 1 or 2 ms for IntelliJ & Maven, 5ms for Travis
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(sub1.getReceived().size()).isEqualTo(1);
        assertThat(sub2.getReceived().size()).isEqualTo(1);
    }
}
