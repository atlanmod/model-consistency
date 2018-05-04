package org.atlanmod.consistency;

import graph.Edge;
import graph.Graph;
import graph.GraphFactory;
import graph.Vertex;
import org.apache.activemq.artemis.*;
import org.atlanmod.consistency.core.IdBuilder;
import org.atlanmod.consistency.update.Operation;
import org.atlanmod.consistency.util.ConsistencyUtil;
import org.eclipse.emf.common.util.URI;
import org.fusesource.mqtt.client.*;

import java.util.concurrent.TimeUnit;


public class App {
    public static void main(String[] args) {

        //*
        /**
         * Version 2 test main
         * (Tries with nodes, operations spreading tries)
         */

        GraphFactory factory = GraphFactory.eINSTANCE;
        Graph graph1 = factory.createGraph();
        Graph graph2 = factory.createGraph();

        NeoNode node1 = new NeoNode();
        NeoNode node2 = new NeoNode();

        URI uri1 = URI.createURI("org.atlanmod.consistency.NeoNode:resource1");
        URI uri2 = URI.createURI("org.atlanmod.consistency.NeoNode:resource2");

        SharedResource resource1 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), null, null);

        node1.attachResource(resource1); // Attach
        node2.attachResource(uri2);

        Vertex vertexA = factory.createVertex();

        resource1.getContents().add(graph1); // Attach
        node2.getSharedResourceSet().getSharedResource(uri2).attachedHelper(graph2);

        graph1.getVertices().add(vertexA); // SetReference to graph / AddReference from graph to this

        vertexA.setLabel("A"); // SetValue


        // In order to clear the Attachments to the resource (graph1 and vertexA) -- need to find a better way
        /*try {
            node1.getSharedResourceSet().getSharedResource(uri1).getHistory().queue().take();
            node1.getSharedResourceSet().getSharedResource(uri1).getHistory().queue().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Operation last = node1.getSharedResourceSet().getSharedResource(uri1).getHistory().queue().element();
        System.out.println("Print : " + last.toString());
        // Throws UnsupportedOperationException because BaseOperation needs to be implemented
        //node2.getSharedResourceSet().getSharedResource(uri2).execute(last);

        node1.summary();
        node2.summary();



        //*/
        /**
         * Version 1 test main
         * (No node, no spreading, only attachments and local modifications on resources)
         */

        /*
        SharedResource resource1 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), IdBuilder.generateRID(), null, null);
        SharedResource resource2 = new SharedResource(URI.createURI("org.atlanmod.consistency.NeoNode:resource1"), IdBuilder.generateRID(), null, null);


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

        */


    }
}
