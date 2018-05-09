package org.atlanmod.consistency.pubsub;

import java.io.Serializable;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public interface Consumer {

    Serializable receive(int timeout);
}
