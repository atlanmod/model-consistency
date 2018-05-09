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

import graph.*;
import graph.impl.VertexImpl;
import org.atlanmod.consistency.SharedResource;
//import org.eclipse.emf.ecore.resource.Resource;

import org.atlanmod.consistency.core.IdBuilder;
import org.atlanmod.consistency.update.Attach;
import org.atlanmod.consistency.update.Operation;
import org.atlanmod.consistency.update.RemoveManyValues;
import org.atlanmod.consistency.util.ConsistencyUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


/*
import org.junit.Before;
import org.junit.Test;
*/


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.URI;


//import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class SharedResourceTest {

    private SharedResource resource;
    private URI uri;
    private GraphFactory factory = GraphFactory.eINSTANCE;
    private Graph graph = factory.createGraph();


    @BeforeEach
    public void setUp() //throws IOException
    {
        uri = URI.createURI("file://tmp/");
        resource = new SharedResource(uri, null, null);
        //this.write(resource);

    }

    @Test
    public void testSharedResource() {
        SharedResource other = new SharedResource(URI.createURI("sharedresourcetest:other"), IdBuilder.generateRID(),null, null);

        assertTrue(resource.getURI().equals(uri));

        assertFalse(resource.equals(other));
    }

    @Test
    public void testAttachDetach() {
        SharedResource other = new SharedResource(URI.createURI("sharedresourcetest:other"), IdBuilder.generateRID(),null, null);

        resource.getContents().add(graph);

        assertTrue(resource.contains(graph));

        other.getContents().add(graph);

        assertFalse(resource.contains(graph));
        assertTrue(other.contains(graph));

        other.getContents().remove(graph);
        resource.getContents().add(graph);

        assertFalse(other.contains(graph));
        assertTrue(resource.contains(graph));

    }

    @Test
    public void testContainment() {
        Vertex v1 = factory.createVertex();
        v1.setLabel("A");
        Vertex v2 = factory.createVertex();
        v2.setLabel("B");

        resource.getContents().add(graph);
        graph.getVertices().add(v1);

        assertTrue(resource.contains(v1));
        assertFalse(resource.contains(v2));
    }

    @Test
    public void testNotification() {
        Vertex v = factory.createVertex();
        resource.getContents().add(v);
        v.setLabel("A");

        Edge e = factory.createEdge();
        resource.getContents().add(e);

        e.setFrom(v);

        try {
            e.setFrom(null);
            fail();
        }
        catch (AssertionError assertionError) {
            assertTrue(assertionError.getMessage().equals("Set with a null value"));
        }

        Graph g = factory.createGraph();
        resource.getContents().add(g);
        g.getEdges().add(e);
        g.getVertices().add(v);
    }

    @Test
    public void testAddMany() {
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

        assertTrue(g.getVertices().size() == 2);
        assertTrue(g.getVertices().get(1).equals(v1));
        assertTrue(g.getVertices().get(0).equals(v3));
    }

    @Test
    public void testRemoveManyReferences() {
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

        assertFalse(graph.eContents().contains(v1) && graph.eContents().contains(v3));
        assertFalse(resource.contains(v1));

        assertTrue(resource.contains(v2) && graph.getVertices().contains(v2));
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

    @Test
    public void testBasicTypes() {
        Graph g = factory.createGraph();
        Vertex v1 = factory.createVertex();
        resource.getContents().add(g);
        g.getVertices().add(v1);

        v1.setWeight(5);

    }

    @Test
    public void testClear() {

        resource.getContents().add(graph);
        Vertex vertexA = factory.createVertex();
        Vertex vertexB = factory.createVertex();
        Vertex vertexC = factory.createVertex();

        graph.getVertices().add(vertexA);
        graph.getVertices().add(vertexB);
        graph.getVertices().add(vertexC);

        assertFalse(graph.getVertices().isEmpty());

        graph.getVertices().clear();

        assertTrue(graph.getVertices().isEmpty());

    }

    /*private void write(Resource resource) throws IOException {


		*//*
		 * String instanceId = EcoreUtil.getID(graph); EcoreUtil.setID(graph, "instanceId");
		 * System.out.println(instanceId);
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
