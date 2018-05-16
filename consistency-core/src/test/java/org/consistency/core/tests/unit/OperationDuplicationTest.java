package org.consistency.core.tests.unit;

import graph.Graph;
import graph.GraphFactory;
import graph.Vertex;
import org.atlanmod.consistency.NeoNode;
import org.atlanmod.consistency.SharedResource;
import org.atlanmod.consistency.pubsub.Broker;
import org.atlanmod.consistency.update.*;
import org.eclipse.emf.common.util.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.assertj.core.api.Assertions.assertThat;

class OperationDuplicationTest {

    private NeoNode node1, node2;
    private Broker broker;
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

        node1.attachResource(uri1);
        node2.attachResource(uri2);

        resource = node1.getSharedResourceSet().getSharedResource(uri1);
        resource2 = node2.getSharedResourceSet().getSharedResource(uri2);
    }

    @Test
    void AttachTest() {
        resource.getContents().add(graph);

        broadcast();

        assertThat(resource2.contents().size()).isEqualTo(1);
        assertThat(resource2.getHistory().basicHistory().size()).isEqualTo(1);
        assertThat(resource.getHistory().basicHistory()).extracting("class").containsOnlyOnce(Attach.class);
    }

    @Test
    void DetachTest() {
        resource.getContents().add(graph);
        resource.getContents().remove(graph);

        broadcast();


        assertThat(resource2.contents().size()).isEqualTo(0);
        assertThat(resource2.getHistory().basicHistory().size()).isEqualTo(2);
        assertThat(resource.getHistory().basicHistory()).extracting("class").containsOnlyOnce(Detach.class);

    }

    @Test
    void SetReferenceTest() {

        Vertex vA = factory.createVertex();
        resource.getContents().add(graph);
        graph.getVertices().add(vA);

        broadcast();

        Graph graph2 = (Graph) resource2.contentAt(0);
        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(resource2.contents().size()).isEqualTo(2);
        assertThat(vB.getOwner()).isEqualTo(graph2);
        assertThat(resource.getHistory().basicHistory()).extracting("class").containsOnlyOnce(SetReference.class);

    }

    @Test
    void AddReferenceTest() {

        Vertex vA = factory.createVertex();
        resource.getContents().add(graph);
        graph.getVertices().add(vA);

        broadcast();

        Graph graph2 = (Graph) resource2.contentAt(0);
        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(resource2.contents().size()).isEqualTo(2);
        assertThat(graph2.getVertices()).contains(vB);
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(AddReference.class);
    }

    @Test
    void SetValueTest() {
        Vertex vA = factory.createVertex();
        resource.getContents().add(graph);
        graph.getVertices().add(vA);

        vA.setLabel("A");

        broadcast();

        Vertex vB = (Vertex) resource2.contentAt(1);

        assertThat(vB.getLabel()).isEqualTo("A");
        assertThat(resource2.getHistory().basicHistory()).extracting("class").containsOnlyOnce(SetValue.class);

    }

    /*@Test
    void AddValueTest() {

    }

    /*@Test
    void AddManyReferencesTest() {
        assertThat(false).isTrue();
    }

    @Test
    void AddManyValuesTest() {
        assertThat(false).isTrue();
    }

    @Test
    void RemoveManyReferencesTest() {
        assertThat(false).isTrue();
    }

    @Test
    void RemoveManyValuesTest() {
        assertThat(false).isTrue();
    }

    @Test
    void RemoveReferenceTest() {
        assertThat(false).isTrue();
    }

    @Test
    void RemoveValueTest() {
        assertThat(false).isTrue();
    }

    @Test
    void UnsetTest() {
        assertThat(false).isTrue();
    }*/

    private void broadcast() {
        node1.sendAll();
        broker.publishAll();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        node2.receiveAll();
    }
}
