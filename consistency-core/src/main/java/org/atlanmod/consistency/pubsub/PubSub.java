package org.atlanmod.consistency;

import org.eclipse.emf.common.util.URI;

import javax.ws.rs.core.UriBuilder;
import java.sql.Struct;
import java.util.List;

public abstract class PubSub {

    private Broker broker = new Broker();

    public PubSub(){}
    public PubSub(Broker broker) {
        this.broker = broker;
    }

    public class Publisher extends PubSub {

    }

    public class Subscriber extends PubSub {

    }

    public class Broker {
        private List<Publisher> publishers;
        private List<Subscriber> subscribers;
    }

    public class Topic {
        private URI uri;

        public Topic(URI uri) {
            this.uri = uri;
        }
    }
}
