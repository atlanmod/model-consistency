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

package org.atlanmod.consistency.update;

import com.google.common.primitives.Ints;
import org.atlanmod.consistency.History;
import org.atlanmod.consistency.core.FeatureId;
import org.atlanmod.consistency.core.Id;
import org.atlanmod.consistency.core.IdBuilder;
import org.atlanmod.consistency.core.InstanceId;
import org.atlanmod.consistency.util.ConsistencyUtil;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.atlanmod.consistency.util.ConsistencyUtil.*;

public class ChangeManager {

    private final History history;


    public ChangeManager(History history) {
        this.history = history;
    }



    public void notifyChanged(InstanceId oid, Notification notification) {
        assert nonNull(notification);
        assert nonNull(notification.getNotifier());
        assert EObject.class.isAssignableFrom(notification.getNotifier().getClass());

        if (notification.isTouch()) {return;}
        if(isNull(notification.getFeature())) {return;}
        int type = notification.getEventType();
        Operation op;

        switch (type) {
            case Notification.SET :
                op = set(oid, notification);
                history.add(op);
                break;
            case Notification.UNSET:
                op = unset(oid, notification);
                history.add(op);
                break;
            case Notification.ADD:
                op = add(oid, notification);
                history.add(op);
                break;
            case Notification.REMOVE:
                op = remove(oid, notification);
                history.add(op);
                break;
            case Notification.MOVE:
                op = move(oid, notification);
                history.add(op);
                break;
            case Notification.ADD_MANY:
                op = addMany(oid, notification);
                history.add(op);
                break;
            case Notification.REMOVE_MANY:
                op = removeMany(oid, notification);
                history.add(op);
                break;
            case Notification.REMOVING_ADAPTER:
                System.out.println("--removing adapter--");
                break;
            case Notification.NO_FEATURE_ID: break;
            case Notification.RESOLVE: break;

            default: break;
        }

    }

    private Operation set(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "Set of a null feature";
        assert nonNull(notification.getNewValue()) : "Set with a null value";

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);


        notification.wasSet();
        notification.isReset();

        if (isEAttribute(feature)) {
            return new SetValue(fid, notification.getNewValue(), notification.getOldValue());
        } else if (isEReference(feature)) {
            return new SetReference(fid, identifierFor((EObject) notification.getNewValue()));
        } else {
            return new Invalid();
        }
    }

    private Operation unset(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "Unset of a null feature";

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);
        return new Unset(fid);
    }

    private Operation add(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "Add of a null feature";
        assert nonNull(notification.getNewValue()) : "Add with a null value";

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);

        if (isEAttribute(feature)) {
            return new AddValue(fid, notification.getNewValue());
        } else if (isEReference(feature)) {
            return new AddReference(fid, identifierFor((EObject) notification.getNewValue()));
        } else {
            return new Invalid();
        }
    }

    private Operation remove(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "Remove of a null feature";
        assert nonNull(notification.getOldValue()) : "Remove with a null old value";

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);

        if (isEAttribute(feature)) {
            return new RemoveValue(fid, notification.getOldValue());
        } else if (isEReference(feature)) {
            return new RemoveReference(fid, identifierFor((EObject) notification.getOldValue()));
        } else {
            return new Invalid();
        }
    }

    private Operation move(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "Move of a null feature";
        System.out.println(notification);

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);
        return new MoveValue(fid, notification.getOldValue(), notification.getPosition());
    }

    private Operation addMany(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "AddMany of a null feature";
        assert nonNull(notification.getNewValue()) : "AddMany with a null value";

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);

        if (isEAttribute(feature)) {
            List<Object> values = (List<Object>) notification.getNewValue();
             return new AddManyValues(fid, values);
        } else if (isEReference(feature)) {
            List<EObject> values = (List<EObject>) notification.getNewValue();
            List<Id> ids = values.stream()
                    .map(ConsistencyUtil::identifierFor)
                    .collect(Collectors.toList());

            return new AddManyReferences(fid, ids);
        } else {
            return new Invalid();
        }
    }

    private Operation removeMany(InstanceId oid, Notification notification) {
        assert nonNull(notification.getFeature()) : "RemoveMany of a null feature";
        //assert nonNull(notification.getNewValue()) : "RemoveMany with a null value"; -- CLEAR

        EStructuralFeature feature = (EStructuralFeature) notification.getFeature();
        FeatureId fid = oid.withFeature(feature);

        if (notification.getNewValue() == null) { // clear() case
            if (isEAttribute(feature)) {
                List<EObject> values = ((EStructuralFeature) notification.getFeature()).eContents();

                return new RemoveManyValues(fid, values);

            } else if (isEReference(feature)) {

                EList<EObject> values = ((EStructuralFeature) notification.getFeature()).eContents();
                return new RemoveManyValues(fid,values);

            } else {
                return new Invalid();
            }
        } else {

            if (isEAttribute(feature)) {
                List<Object> values = (List<Object>) notification.getNewValue();
                return new RemoveManyValues(fid, values);
            } else if (isEReference(feature)) {

                List<Integer> values = Ints.asList((int[]) notification.getNewValue());

                List<Id> ids = new ArrayList<>();
                for (Integer i : values) {
                    ids.add(IdBuilder.fromInt(i));
                }

                return new RemoveManyReferences(fid, ids);
            } else {
                return new Invalid();
            }
        }
    }

}
