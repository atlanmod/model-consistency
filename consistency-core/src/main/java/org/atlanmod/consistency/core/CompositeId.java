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


public class CompositeId extends DefaultId implements Id {
    private final Id high;
    private final Id low;

    protected CompositeId(Id high, Id low) {
        this.high = high;
        this.low = low;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompositeId)) {
            return false;
        }
        final CompositeId other = (CompositeId) obj;

        return Objects.equals(high,other.high) && Objects.equals(low,other.low);
    }

    @Override
    public int hashCode() {
        return Objects.hash(high, low);
    }

    @Override
    public String toString() {
        return "{" +high +
                "," + low +
                '}';
    }

    public Id high() {
        return high;
    }

    public Id low() {
        return low;
    }
}
