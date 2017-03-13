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

package fr.inria.atlanmod.consistency.core;

/**
 * Created by sunye on 09/03/2017.
 */
public class DefaultId implements Id {
    @Override
    public CompositeId composeWith(Id low) {
        return new CompositeId(this, low);
    }

}
