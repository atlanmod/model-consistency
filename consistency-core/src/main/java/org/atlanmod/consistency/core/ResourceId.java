/*
 * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package org.atlanmod.consistency.core;

public class ResourceId extends ShortId {

    private int lastId = 0;

    protected ResourceId(short s) {
        super(s);
    }

    public InstanceId nextId() {
        return new InstanceId(this, new IntegerId(lastId++));
    }
}
