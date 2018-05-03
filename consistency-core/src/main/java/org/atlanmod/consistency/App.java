package org.atlanmod.consistency;

import graph.Edge;
import graph.Graph;
import graph.GraphFactory;
import graph.Vertex;
import org.apache.activemq.artemis.*;
import org.atlanmod.consistency.update.Operation;
import org.eclipse.emf.common.util.URI;
import org.fusesource.mqtt.client.*;

import java.util.concurrent.TimeUnit;


public class App {
    public static void main(String[] args) {


        GraphFactory factory = GraphFactory.eINSTANCE;
        Graph graph1 = factory.createGraph();
        Graph graph2 = factory.createGraph();

        NeoNode node1 = new NeoNode();
        NeoNode node2 = new NeoNode();

        URI uri1 = URI.createURI("org.atlanmod.consistency.NeoNode:resource1");

        //SharedResource resource1 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), null, null);
        //SharedResource resource2 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), null, null);

        node1.attachResource(uri1);
        node2.attachResource(uri1);

        node1.getSharedResourceSet().getSharedResource(uri1).attachedHelper(graph1);

        //Operation last = node1.getSharedResourceSet().getSharedResource(uri1).getHistory().queue().element();
        //node2.getSharedResourceSet().getSharedResource(uri1).execute(last);

        node1.summary();
        node2.summary();

        /*



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

        */

    }
}
