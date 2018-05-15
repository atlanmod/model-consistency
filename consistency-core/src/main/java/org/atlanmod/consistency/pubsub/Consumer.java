package org.atlanmod.consistency.pubsub;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public interface Consumer {

    Serializable receive(int timeout);

    List<Serializable> getReceived();
}
