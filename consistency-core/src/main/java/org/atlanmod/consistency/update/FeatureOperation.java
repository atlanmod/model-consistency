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

package org.atlanmod.consistency.update;

import org.atlanmod.consistency.core.FeatureId;
import org.atlanmod.consistency.core.Id;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public abstract class FeatureOperation extends BaseOperation {

    private final FeatureId featureId;

    public FeatureOperation(FeatureId featureId) {
        this.featureId = featureId;
    }

    @Override
    public Id instanceId() {
        return featureId.asInstanceId();
    }

    public FeatureId featureId() {
        return featureId;
    }
}
