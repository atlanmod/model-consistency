package graph.impl;

import graph.MultiValuesExample;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;


public class MutliValuesExampleImpl implements MultiValuesExample {

    private EList<Integer> numbers = new BasicEList<>();

    @Override
    public EList<Integer> getNumbers() {
        return numbers;
    }

}
