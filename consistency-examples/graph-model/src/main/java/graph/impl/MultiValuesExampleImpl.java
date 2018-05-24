package graph.impl;

import graph.MultiValuesExample;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;


public class MultiValuesExampleImpl extends MinimalEObjectImpl.Container implements MultiValuesExample {

    private EList<Integer> numbers = new BasicEList<>();

    @Override
    public EList<Integer> getNumbers() {
        return numbers;
    }

    @Override
    public String output() {
        StringBuilder output = new StringBuilder();
        if (numbers != null) {
            for (Integer each : numbers) {
                output.append("\n\t").append(each);
            }
        }
        return output.toString();
    }

}
