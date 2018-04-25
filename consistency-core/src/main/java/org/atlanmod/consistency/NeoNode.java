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

import graph.Edge;
import graph.Vertex;
import graph.impl.EdgeImpl;
import graph.impl.VertexImpl;
import org.atlanmod.consistency.update.Operation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;

/**
 * Created on 09/03/2017.
 * @author AtlanMod team.
 */
public class NeoNode //extends Node
{

    public static void main(String[] args) {

        SharedResource resource1 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), null, null);
        SharedResource resource2 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource2"), null, null);


        Vertex vertexA = new VertexImpl("X");
        Vertex vertexB = new VertexImpl();
        Vertex vertexC = new VertexImpl();
        Edge edgeAB = new EdgeImpl();

        resource1.attachedHelper(vertexA);
        vertexA.setLabel("A");

        resource1.attachedHelper(vertexB);
        vertexB.setLabel("B");

        resource1.detachedHelper(vertexB);

        resource2.attachedHelper(vertexB);

        resource1.attachedHelper(vertexC);
        resource1.attachedHelper(edgeAB);
        edgeAB.setFrom(vertexA);
        edgeAB.setTo(vertexC);
        vertexC.setLabel("C");
        Vertex vertexZ = new VertexImpl();
        vertexZ.setLabel("Z");

        resource2.attachedHelper(vertexZ);

        resource1.summary();
        resource2.summary();




        /*
        NeoNode node = new NeoNode();
        node.start();
        */
    }

}
