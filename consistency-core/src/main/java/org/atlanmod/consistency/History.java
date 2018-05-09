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

import com.google.common.collect.Maps;
import org.atlanmod.consistency.core.FeatureId;
import org.atlanmod.consistency.update.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public class History {
    private BlockingQueue<Operation> incoming = new LinkedBlockingQueue<>();
    private Map<FeatureId, List<Operation>> changes = Maps.newHashMap();
    private final SharedResource resource;
    private List<Operation> basicHistory = new ArrayList<>();

    public History(SharedResource resource) {
        this.resource = resource;
    }

    public void integrate(Operation operation) {
        resource.execute(operation);

    }

    public void add(Operation operation) {
        resource.broadcast(operation);
        System.out.println("New operation: " + operation);
        incoming.offer(operation);
        basicHistory.add(operation);
    }

    public void basicAdd(Operation operation) {
        if (changes.containsKey(operation.instanceId())){
            changes.get(operation.instanceId()).add(operation);
        }
    }

    public BlockingQueue<Operation> queue() {
        return incoming;
    }

    public List<Operation> basicHistory() {
        return basicHistory;
    }
}
