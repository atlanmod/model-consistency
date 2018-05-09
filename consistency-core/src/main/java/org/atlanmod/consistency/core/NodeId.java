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

public class NodeId extends ShortId {

    private short lastId = 0;

    public NodeId(short s) {
        super(s);
    }

    public ResourceId nextRID() {
        return new ResourceId(lastId++);
    }
}
