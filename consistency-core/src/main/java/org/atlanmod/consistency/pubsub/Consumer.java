package org.atlanmod.consistency.pubsub;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public interface Consumer {

    Serializable receive(int timeout);

    BlockingQueue<Serializable> getReceived();

    Serializable archive();
}
