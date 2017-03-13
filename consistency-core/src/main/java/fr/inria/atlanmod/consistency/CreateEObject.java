package fr.inria.atlanmod.consistency;

import fr.inria.atlanmod.consistency.core.Id;
import org.eclipse.emf.ecore.EClass;

/**
 * Created by sunye on 16/02/2017.
 */
public class CreateEObject extends UpdateMessage {

    private Id id;

    public CreateEObject(EClass eClass, Id id) {
        eClass.getClassifierID();
        this.id = id;

        //eClass.getEPackage().
    }
}
