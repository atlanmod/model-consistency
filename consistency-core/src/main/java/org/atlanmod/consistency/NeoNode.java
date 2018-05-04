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

//import org.atlanmod.appa.Node;

import org.atlanmod.consistency.core.NodeId;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Created on 09/03/2017.
 * @author AtlanMod team.
 */
public class NeoNode //extends Node
{
    private static short lastNodeId = 0;

    private NodeId nid = new NodeId(lastNodeId++);
    private SharedResourceSet resourceSet = new SharedResourceSet();

    public SharedResourceSet getSharedResourceSet() {
        return resourceSet;
    }

    public void attachResource(URI uri) {
        resourceSet.getSharedResources().add(new SharedResource(uri, nid.nextRID(),null,null));
    }

    public void summary() {
        int i = 0;

        System.out.println("---------------------------- NODE " + nid + " SUMMARY ---------------------------");
        for (SharedResource each : resourceSet.getSharedResources()) {
            System.out.println("Resource " + (++i) + " : " + each.getURI());
            each.summary();
        }
        System.out.println("------------------------------ END OF NODE ------------------------------\n");
    }

    public void attachResource(SharedResource resource1) {
        resourceSet.getSharedResources().add(resource1);
    }
}
