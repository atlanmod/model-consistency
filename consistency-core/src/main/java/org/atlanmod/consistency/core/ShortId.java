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

import java.util.Objects;

public class ShortId extends DefaultId implements Id {
    protected final short id;

    public ShortId(short s) {
        id = s;
    }

    @Override
    public String toString() {
        return "[" +
                 id +
                ']';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ShortId)) {
            return false;
        }
        final ShortId other = (ShortId) obj;

        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int toInt() {
        return id;
    }
}
