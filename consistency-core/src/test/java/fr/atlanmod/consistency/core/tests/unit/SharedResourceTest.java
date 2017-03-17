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

package fr.atlanmod.consistency.core.tests.unit;

import fr.inria.atlanmod.consistency.SharedResource;
import graph.Edge;
import graph.Graph;
import graph.GraphFactory;
import graph.Vertex;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.emf.common.util.URI;

import java.io.IOException;
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
    private  URI uri;
    private GraphFactory factory = GraphFactory.eINSTANCE;
    private Graph graph = factory.createGraph();


    @Before
    public void setUp() throws IOException {
        uri = URI.createURI("file://tmp/");
        resource = new SharedResource(uri);
        //this.write(resource);

    }

    @Test
    public void testSharedResource() {
        assert true;
    }

    //@Test
    public void testNotification() {
        Vertex v = factory.createVertex();
        resource.getContents().add(v);
        v.setLabel("A");

        Edge e = factory.createEdge();
        resource.getContents().add(e);

        e.setFrom(v);
        e.setFrom(null);

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

        List<Vertex> vertices = Arrays.asList(new Vertex[] {v1,v2});
        g.getVertices().addAll(vertices);
        g.getVertices().add(v3);
        g.getVertices().move(0,2);

        g.getVertices().remove(v2);
    }

    @Test
    public void testBasicTypes() {
        Graph g = factory.createGraph();
        Vertex v1 = factory.createVertex();
        resource.getContents().add(g);
        g.getVertices().add(v1);

        v1.setWeight(5);
    }

    private void write(Resource resource) throws IOException {


		/*
		 * String id = EcoreUtil.getID(graph); EcoreUtil.setID(graph, "id");
		 * System.out.println(id);
		 */


        Vertex v = factory.createVertex();
        v.setLabel("a label");

        for (int i = 0; i < 100; i++) {
            Vertex v1 = factory.createVertex();
            v1.setLabel("Vertice " + i + "a");
            Vertex v2 = factory.createVertex();
            v2.setLabel("Vertice " + i + "b");
            Edge e = factory.createEdge();
            e.setFrom(v1);
            e.setTo(v2);
            graph.getEdges().add(e);
            graph.getVertices().add(v1);
            graph.getVertices().add(v2);
        }
        resource.getContents().add(graph);
    }



}
