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
import graph.Graph;
import graph.GraphFactory;
import graph.Vertex;
import org.eclipse.emf.common.util.URI;

/**
 * Created on 09/03/2017.
 * @author AtlanMod team.
 */
public class NeoNode //extends Node
{

    public static void main(String[] args) {

        SharedResource resource1 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), null, null);
        SharedResource resource2 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource2"), null, null);


        GraphFactory factory = GraphFactory.eINSTANCE;
        Graph graph1 = factory.createGraph();
        Graph graph2 = factory.createGraph();


        Vertex vertexA = factory.createVertex();
        Vertex vertexB = factory.createVertex();
        Vertex vertexC = factory.createVertex();
        Edge edgeAC = factory.createEdge();

        resource1.attachedHelper(graph1);


        vertexA.setLabel("X");
        graph1.getVertices().add(vertexA);
        vertexA.setLabel("A");

        graph1.getVertices().add(vertexB);
        vertexB.setLabel("B");

        resource2.attachedHelper(graph2);

        graph2.getVertices().add(vertexB);

        graph1.getVertices().add(vertexC);
        graph1.getEdges().add(edgeAC);

        edgeAC.setFrom(vertexA);
        edgeAC.setTo(vertexC);

        vertexC.setLabel("C");

        Vertex vertexZ = factory.createVertex();
        vertexZ.setLabel("Z");

        graph2.getVertices().add(vertexZ);

        resource1.summary();
        resource2.summary();




        /*
        NeoNode node = new NeoNode();
        node.start();
        */
    }

}
