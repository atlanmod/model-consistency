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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.util.Map;


public class SharedResourceSet implements ResourceSet {

    private EList<SharedResource> resources = new BasicEList<>();

    //@Override
    public EList<SharedResource> getSharedResources() {
        return resources;
    }

    @Override
    public EList<Resource> getResources() {
        return null;
    }

    @Override
    public TreeIterator<Notifier> getAllContents() {
        return null;
    }

    @Override
    public EList<AdapterFactory> getAdapterFactories() {
        return null;
    }

    @Override
    public Map<Object, Object> getLoadOptions() {
        return null;
    }

    @Override
    public EObject getEObject(URI uri, boolean b) {
        for (Resource each : resources) {
            if (each.getURI().equals(uri))
                return (EObject) each;
        }
        return null;
    }

    @Override
    public Resource getResource(URI uri, boolean b) {
        for (Resource each : resources) {
            if (each.getURI().equals(uri))
                return each;
        }
        return null;
    }

    public SharedResource getSharedResource(URI uri) {
        return (SharedResource) getResource(uri, true);
    }

    @Override
    public Resource createResource(URI uri) {
        return null;
    }

    @Override
    public Resource createResource(URI uri, String s) {
        return null;
    }


    @Override
    public Resource.Factory.Registry getResourceFactoryRegistry() {
        return null;
    }

    @Override
    public void setResourceFactoryRegistry(Resource.Factory.Registry registry) {

    }

    @Override
    public URIConverter getURIConverter() {
        return null;
    }

    @Override
    public void setURIConverter(URIConverter uriConverter) {

    }

    @Override
    public EPackage.Registry getPackageRegistry() {
        return null;
    }

    @Override
    public void setPackageRegistry(EPackage.Registry registry) {

    }

    @Override
    public EList<Adapter> eAdapters() {
        return null;
    }

    @Override
    public boolean eDeliver() {
        return false;
    }

    @Override
    public void eSetDeliver(boolean b) {

    }

    @Override
    public void eNotify(Notification notification) {

    }
}
