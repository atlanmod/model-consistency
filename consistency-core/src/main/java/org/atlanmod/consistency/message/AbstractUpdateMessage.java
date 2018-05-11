/*
 *
 *  * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 *
 *
 */

package org.atlanmod.consistency.message;

import org.atlanmod.consistency.core.Id;

/**
 * Created on 17/03/2017.
 *
 * @author AtlanMod team.
 */
public class AbstractUpdateMessage implements UpdateMessage {
    private final MessageType type;

    public AbstractUpdateMessage(MessageType type) {
        this.type = type;
    }

    @Override
    public MessageType type() {
        return type;
    }

    @Override
    public Id instanceId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Id featureId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object value() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object oldValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Id reference() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Id oldReference() {
        throw new UnsupportedOperationException();
    }
}
