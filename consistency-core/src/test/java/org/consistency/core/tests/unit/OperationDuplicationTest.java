package org.consistency.core.tests.unit;

import graph.*;
import graph.impl.MultiValuesExampleImpl;
import graph.impl.VertexImpl;
import fr.inria.atlanmod.commons.log.Log;
import org.atlanmod.consistency.NeoNode;
import org.atlanmod.consistency.SharedResource;
import org.atlanmod.consistency.pubsub.Broker;
import org.atlanmod.consistency.update.*;
import org.eclipse.emf.common.util.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class OperationDuplicationTest {

    private static final int MILLIS_WAIT = 10;
    private NeoNode node1, node2;
    private Broker broker;
    private MultiValuesExample multival;
    private GraphFactory factory;
    private Graph graph;
    private SharedResource resource, resource2;
    private final URI uri1 = URI.createURI("org.atlanmod.consistency.core.OperationDuplicationTest:resource1");
    private final URI uri2 = URI.createURI("org.atlanmod.consistency.core.OperationDuplicationTest:resource2");


    @BeforeEach
    void setup() {
        broker = new Broker();
        node1 = new NeoNode(broker);
        node2 = new NeoNode(broker);

        factory = GraphFactory.eINSTANCE;
        graph = factory.createGraph();

        multival = new MultiValuesExampleImpl();

        node1.attachResource(uri1);
        node2.attachResource(uri2);

        resource = node1.getSharedResourceSet().getSharedResource(uri1);
        resource2 = node2.getSharedResourceSet().getSharedResource(uri2);
    }

    @Test
    void AttachTest() {

        System.out.println("\n----------------- AttachTest -----------------");

        resource.getContents().add(graph);

        broadcast();

        assertThat(resource2.contents().size()).isEqualTo(1);
        assertThat(resource2.getHistory().basicHistory().size()).isEqualTo(1);
        assertThat(resource.getHistory().basicHistory()).extracting("class").containsOnlyOnce(Attach.class);
    }

    @Test
    void DetachTest() {

        System.out.println("\n----------------- DetachTest -----------------");

        resource.getContents().add(graph);
        resource.getContents().remove(graph);

        broadcast();

        assertThat(resource2.contents().size()).isEqualTo(0);
        assertThat(resource2.getHistory().basicHistory().size()).isEqualTo(2);
        assertThat(resource.getHistory().basicHistory()).extracting("class").containsOnlyOnce(Detach.class);

    }

    @Test
    void SetAddReferenceTest() {

        System.out.println("\n----------------- SetAddReferenceTest -----------------");

        Vertex vA = factory.createVertex();

        resource.getContents().add(graph);
        graph.getVertices().add(vA);

        broadcast();

        Graph graph2 = (Graph) resource2.contentAt(0);
        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(resource2.contents().size()).isEqualTo(2);

        assertThat(vB.getOwner()).isEqualTo(graph2);
        assertThat(graph2.getVertices()).contains(vB);

        assertThat(resource.getHistory().basicHistory()).extracting("class").containsOnlyOnce(SetReference.class);
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(AddReference.class);

    }

    @Test
    void SetValueTest() {

        System.out.println("\n----------------- SetValueTest -----------------");

        Vertex vA = factory.createVertex();

        resource.getContents().add(graph);
        graph.getVertices().add(vA);

        vA.setLabel("A");

        broadcast();

        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(vB.getLabel()).isEqualTo("A");
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(SetValue.class);

    }



    @Test
    void AddManyReferencesTest() {

        System.out.println("\n----------------- AddManyReferencesTest -----------------");

        Vertex vA = factory.createVertex();
        Vertex vB = factory.createVertex();

        resource.getContents().add(graph);
        graph.getVertices().addAll(Arrays.asList(vA, vB));

        broadcast();

        assertThat(resource2.contentAt(0).eContents().size()).isEqualTo(2);
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(AddManyReferences.class);
    }


    @Test
    void RemoveManyReferencesTest() {

        System.out.println("\n----------------- RemoveManyReferencesTest -----------------");

        Vertex vA = factory.createVertex();
        Vertex vB = factory.createVertex();

        resource.getContents().add(graph);
        graph.getVertices().addAll(Arrays.asList(vA, vB));
        graph.getVertices().add(factory.createVertex());
        graph.getVertices().removeAll(Arrays.asList(vA, vB));

        broadcast();

        assertThat(resource2.contentAt(0).eContents().size()).isEqualTo(1);
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(RemoveManyReferences.class);
    }

    @Test
    void RemoveReferenceTest() {

        System.out.println("\n----------------- RemoveReferenceTest -----------------");

        Vertex vA = factory.createVertex();

        resource.getContents().add(graph);
        graph.getVertices().add(vA);
        graph.getVertices().add(factory.createVertex());
        graph.getVertices().remove(vA);

        broadcast();

        Graph graph2 = (Graph) resource2.contentAt(0);
        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(resource2.contents().size()).isEqualTo(2);
        assertThat(graph2.getVertices()).contains(vB);
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(RemoveReference.class);
    }

    @Test
    void AddValueTest() {

        System.out.println("\n----------------- AddValueTest -----------------");

        Integer i1 = 5;

        resource.getContents().add(multival);
        multival.getNumbers().add(i1);

        node1.summary();

        broadcast();

        node2.summary();
    }

    /*@Test
    void AddManyValuesTest() {
        assertThat(false).isTrue();
    }



    @Test
    void RemoveManyValuesTest() {
        assertThat(false).isTrue();
    }



    @Test
    void RemoveValueTest() {
        assertThat(false).isTrue();
    }*/

    @Test
    void UnsetTest() {

        System.out.println("\n----------------- UnsetTest -----------------");

        VertexImpl vA = new VertexImpl();

        resource.getContents().add(graph);
        graph.getVertices().add(vA);
        vA.setLabel("A");

        broadcast();

        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(vB.getLabel()).isNotNull();

        vA.unsetLabel();

        broadcast();

        assertThat(vB.getLabel()).isNullOrEmpty();
    }

    private void broadcast() {

        node1.sendAll();
        broker.publishAll();

        try {
            Thread.sleep(MILLIS_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.warn(e);
        }

        System.out.println();

        node2.receiveAll();
        node1.receiveAll();

    }
}
