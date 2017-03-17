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

package fr.inria.atlanmod.consistency.update;



import fr.inria.atlanmod.consistency.core.Id;

import java.io.Serializable;

/**
 * Created on 09/03/2017.
 *
 * @author AtlanMod team.
 */
public abstract class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract Id instanceId();

}
