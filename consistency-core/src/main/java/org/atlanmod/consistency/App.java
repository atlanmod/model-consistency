package org.atlanmod.consistency;

import graph.Graph;
import graph.GraphFactory;
import org.atlanmod.consistency.pubsub.Broker;
import org.eclipse.emf.common.util.URI;


public class App {
    public static void main(String[] args) {

        /*
         * Version 3 test main
         * (Inclusion of pubsub)
         */

        GraphFactory factory = GraphFactory.eINSTANCE;
        Graph graph1 = factory.createGraph();
        Graph graph2 = factory.createGraph();

        Broker broker = new Broker();

        NeoNode node1 = new NeoNode(broker);
        NeoNode node2 = new NeoNode(broker);

        URI uri1 = URI.createURI("org.atlanmod.consistency.NeoNode:resource1");
        URI uri2 = URI.createURI("org.atlanmod.consistency.NeoNode:resource2");

        node1.attachResource(uri1); // Attach
        node2.attachResource(uri2);

        SharedResource resource1 = node1.getSharedResourceSet().getSharedResource(uri1);
        SharedResource resource2 = node2.getSharedResourceSet().getSharedResource(uri2);

        resource1.getContents().add(graph1);
        //resource2.getContents().add(graph2);

        graph1.getVertices().add(factory.createVertex());
        //graph2.getVertices().add(factory.createVertex());

        graph1.getVertices().get(0).setLabel("B");


        try {
            for (int i = 0; i < 0; ++i) { // To bypass the Attach messages (put i<4 for the SetValue)
                resource1.getHistory().queue().take();
            }
            while (!resource1.getHistory().queue().isEmpty()) {
                //if (resource1.getHistory().queue().element() instanceof Attach || resource1.getHistory().queue().element() instanceof Detach || resource1.getHistory().queue().element() instanceof SetValue)
                node1.send(resource1.getHistory().queue().take().asMessage());
            }
            broker.publishAll(); // Equivalent to broker.topicPublish(PubSub.groupTopic) here
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        node2.receiveAll();
        node1.summary();
        node2.summary();


        /*
         * Version 2 test main
         * (Nodes, operations spreading tries)
         */

        /*
        GraphFactory factory = GraphFactory.eINSTANCE;
        Graph graph1 = factory.createGraph();
        Graph graph2 = factory.createGraph();

        NeoNode node1 = new NeoNode();
        NeoNode node2 = new NeoNode();

        URI uri1 = URI.createURI("org.atlanmod.consistency.NeoNode:resource1");
        URI uri2 = URI.createURI("org.atlanmod.consistency.NeoNode:resource2");

        //SharedResource resource1 = new SharedResource(uri1, null, null);
        //SharedResource resource2 = new SharedResource(uri2, null, null);

        node1.attachResource(uri1); // Attach
        node2.attachResource(uri2);

        SharedResource resource1 = node1.getSharedResourceSet().getSharedResource(uri1);
        SharedResource resource2 = node2.getSharedResourceSet().getSharedResource(uri2);

        Vertex vertexA = factory.createVertex();
        Vertex vertexB = factory.createVertex();
        Vertex vertexC = factory.createVertex();
        Vertex vertexD = factory.createVertex();

        resource1.getContents().add(graph1); // Attach
        resource2.getContents().add(graph2);

        graph1.getVertices().add(vertexA); // SetReference to graph / AddReference from graph to vertex
        graph1.getVertices().add(vertexC);
        graph2.getVertices().add(vertexB);
        graph2.getVertices().add(vertexD);

        vertexA.setLabel("A"); // SetValue
        vertexA.setWeight(12);
        vertexA.setLabel("C");

        Vertex x = graph1.getVertices().move(0, 1);


       /* // In order to clear the Attachments to the resource (graph1 and vertexA) -- need to find a better way
        try {
            for (int i = 0; i < 4; ++i) {
                resource1.getHistory().queue().take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (resource1.getHistory().queue().size() > 0) {
            Operation last = resource1.getHistory().queue().take();
            System.out.println("Print : " + last.toString());
            // Throws UnsupportedOperationException because BaseOperation needs to be implemented
            resource2.execute(last);
        }//

        node1.summary();
        node2.summary();

*/

        //*/
        /*
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
