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

import org.atlanmod.consistency.core.FeatureId;
import org.atlanmod.consistency.core.Id;

/**
 * Created on 17/03/2017.
 *
 * @author AtlanMod team.
 */
public class ValueMessage extends AbstractUpdateMessage {
    private final Object value;
    private final Object previous;
    private final FeatureId featureId;

    public ValueMessage(MessageType type, FeatureId featureId, Object value, Object previous) {
        super(type);
        this.featureId = featureId;
        this.value = value;
        this.previous = previous;
    }

    @Override
    public Id featureId() {
        return featureId;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public Object oldValue() {
        return previous;
    }
}
