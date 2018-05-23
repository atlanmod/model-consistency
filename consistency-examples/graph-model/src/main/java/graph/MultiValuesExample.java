package graph;

import org.eclipse.emf.common.util.EList;

public interface MultiValuesExample {

    int OWNER = 0;
    int NUMBERS = 1;

    EList<Integer> getNumbers();

}
