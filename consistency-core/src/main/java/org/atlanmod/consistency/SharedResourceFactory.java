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

package org.atlanmod.consistency;

import org.atlanmod.consistency.core.IdBuilder;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class SharedResourceFactory implements Resource.Factory {
    /**
     * Constructs a new {@code PersistentResourceFactory}.
     */
    SharedResourceFactory() {
    }

    /**
     * Returns the instance of this class.
     *
     * @return the instance of this class
     */
    @Nonnull
    public static SharedResourceFactory getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * {@inheritDoc}
     * <p>
     *
     */
    @Nullable
    @Override
    public Resource createResource(@Nonnull URI uri) {
        checkNotNull(uri);
        return new SharedResource(uri, IdBuilder.generateRID(),null, null);
    }

    /**
     * The initialization-on-demand holder of the singleton of this class.
     */
    private static class Holder {

        /**
         * The instance of the outer class.
         */
        private static final SharedResourceFactory INSTANCE = new SharedResourceFactory();
    }
}
