package graph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public interface MultiValuesExample extends EObject {

    int OWNER = 0;
    int NUMBERS = 1;

    EList<Integer> getNumbers();

    String output();
}
