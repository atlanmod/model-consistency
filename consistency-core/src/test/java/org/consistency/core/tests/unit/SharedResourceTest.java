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

package org.consistency.core.tests.unit;


import fr.inria.atlanmod.commons.log.Log;
import graph.Edge;
import graph.Graph;
import graph.GraphFactory;
import graph.Vertex;
import org.atlanmod.consistency.NeoNode;
import org.atlanmod.consistency.SharedResource;
import org.atlanmod.consistency.pubsub.Broker;
import org.eclipse.emf.common.util.URI;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
class SharedResourceTest {

    private NeoNode node;
    private SharedResource resource;
    private URI uri, otherUri;
    private GraphFactory factory = GraphFactory.eINSTANCE;
    private Graph graph = factory.createGraph();


    @BeforeEach
    void setUp() //throws IOException
    {
        node = new NeoNode(new Broker());
        uri = URI.createURI("file://tmp/");
        otherUri = URI.createURI("sharedresourcetest:other");
        node.attachResource(uri);
        resource = node.getSharedResourceSet().getSharedResource(uri);
        //resource = new SharedResource(uri);
        //this.write(resource);

    }

    @Test
    void testSharedResource() {
        Log.info("");
        Log.info("----------------- SharedResourceTest -----------------");

        node.attachResource(otherUri);
        SharedResource other =node.getSharedResourceSet().getSharedResource(otherUri);

        assertThat(resource.getURI()).isEqualTo(uri);
        assertThat(resource).isNotEqualTo(other);
    }

    @Test
    void testAttachDetach() {
        Log.info("");
        Log.info("----------------- AttachDetachTest -----------------");
        node.attachResource(otherUri);
        SharedResource other =node.getSharedResourceSet().getSharedResource(otherUri);

        resource.getContents().add(graph);

        assertThat(resource.contains(graph)).isTrue();

        other.getContents().add(graph);

        assertThat(resource.contains(graph)).isFalse();
        assertThat(other.contains(graph)).isTrue();

        other.getContents().remove(graph);
        resource.getContents().add(graph);

        assertThat(other.contains(graph)).isFalse();
        assertThat(resource.contains(graph)).isTrue();

    }

    @Test
    void testContainment() {
        Log.info("");
        Log.info("----------------- ContainmentTest -----------------");
        Vertex v1 = factory.createVertex();
        v1.setLabel("A");
        Vertex v2 = factory.createVertex();
        v2.setLabel("B");

        resource.getContents().add(graph);
        graph.getVertices().add(v1);

        assertThat(resource.contains(v1)).isTrue();
        assertThat(resource.contains(v2)).isFalse();
    }

    /*@Test
    void testNotification() {
        Log.info("");
        Log.info("----------------- NotificationTest -----------------");
        Vertex v = factory.createVertex();
        resource.getContents().add(v);
        v.setLabel("A");

        Edge e = factory.createEdge();
        resource.getContents().add(e);

        e.setFrom(v);

        try {
            e.setFrom(null);
            Assertions.fail("");
        }
        catch (AssertionError assertionError) {
            assertThat(assertionError.getMessage()).isEqualTo("Set with a null value");
        }

        Graph g = factory.createGraph();
        resource.getContents().add(g);
        g.getEdges().add(e);
        g.getVertices().add(v);
    }*/

    @Test
    void testAddMany() {
        Log.info("");
        Log.info("----------------- AddManyTest -----------------");

        Graph g = factory.createGraph();
        Vertex v1 = factory.createVertex();
        Vertex v2 = factory.createVertex();
        Vertex v3 = factory.createVertex();
        resource.getContents().add(g);

        List<Vertex> vertices = Arrays.asList(v1,v2);
        g.getVertices().addAll(vertices);
        g.getVertices().add(v3);
        g.getVertices().move(0,2);

        g.getVertices().remove(v2);

        assertThat(g.getVertices().size()).isEqualTo(2);
        assertThat(g.getVertices().get(1)).isEqualTo(v1);
        assertThat(g.getVertices().get(0)).isEqualTo(v3);
    }

    @Test
    void testRemoveManyReferences() {
        Log.info("");
        Log.info("----------------- RemoveManyReferencesTest -----------------");

        Vertex v1 = factory.createVertex();
        v1.setLabel("A");
        Vertex v2 = factory.createVertex();
        Vertex v3 = factory.createVertex();
        resource.getContents().add(graph);

        graph.getVertices().add(v1);
        graph.getVertices().add(v2);
        graph.getVertices().add(v3);

        List<Vertex> vertices = Arrays.asList(v1,v3);

        graph.getVertices().removeAll(vertices);

        assertThat(graph.eContents()).doesNotContain(v1);
        assertThat(graph.eContents()).doesNotContain(v3);

        assertThat(resource.contains(v1)).isFalse();

        assertThat(resource.contains(v2)).isTrue();
        assertThat(graph.getVertices()).contains(v2);
    }

    @Test
    void testBasicTypes() {
        Log.info("");
        Log.info("----------------- BasicTypesTest -----------------");

        Graph g = factory.createGraph();
        Vertex v1 = factory.createVertex();
        resource.getContents().add(g);
        g.getVertices().add(v1);

        v1.setWeight(5);

    }

    @Test
    void testClear() {
        Log.info("");
        Log.info("----------------- ClearTest -----------------");

        resource.getContents().add(graph);
        Vertex vertexA = factory.createVertex();
        Vertex vertexB = factory.createVertex();
        Vertex vertexC = factory.createVertex();

        graph.getVertices().add(vertexA);
        graph.getVertices().add(vertexB);
        graph.getVertices().add(vertexC);

        assertThat(graph.getVertices()).isNotEmpty();

        graph.getVertices().clear();

        assertThat(graph.getVertices().isEmpty()).isTrue();

    }


/*

    Not OK
    I don't see the point actually ------


    @Test
    public void testRemoveManyValues() {
        VertexImpl v1 = (VertexImpl) factory.createVertex();
        v1.setLabel("A");
        v1.setWeight(5);
        v1.setOwner(graph);

        assertTrue(v1.getLabel() != null);

        List<Integer> delete = new ArrayList<>();
        for (int i = 0; i < GraphPackage.VERTEX_FEATURE_COUNT; ++i) {
            //Object feature = v1.eGet(i,true,true);
            delete.add(i);
        }



        assertTrue(v1.getLabel() == null);
    }

    */


    /*private void write(Resource resource) throws IOException {


		*//*
		 * String instanceId = EcoreUtil.getID(graph); EcoreUtil.setID(graph, "instanceId");
		 * Log.info(instanceId);
		 *//*


        Vertex v = factory.createVertex();
        v.setLabel("a label");

        for (int i = 0; i < 100; i++) {
            Vertex v1 = factory.createVertex();
            v1.setLabel("Vertex " + i + "a");
            Vertex v2 = factory.createVertex();
            v2.setLabel("Vertex " + i + "b");
            Edge e = factory.createEdge();
            e.setFrom(v1);
            e.setTo(v2);
            graph.getEdges().add(e);
            graph.getVertices().add(v1);
            graph.getVertices().add(v2);
        }
        resource.getContents().add(graph);
    }*/



}
