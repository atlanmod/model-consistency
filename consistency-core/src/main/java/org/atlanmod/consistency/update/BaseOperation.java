package org.atlanmod.consistency.update;

import org.atlanmod.consistency.SharedResource;
import org.atlanmod.consistency.core.NodeId;
import org.atlanmod.consistency.message.UpdateMessage;
import org.eclipse.emf.ecore.EObject;

/**
 * TODO: Implement these methods.
 */
public abstract class BaseOperation implements Operation {

    private final NodeId originator;

    public BaseOperation(NodeId originator) {
        this.originator = originator;
    }

    public UpdateMessage asMessage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(SharedResource resource, EObject eObject) {

    }

    @Override
    public NodeId getOriginator() {
        return originator;
    }
}
