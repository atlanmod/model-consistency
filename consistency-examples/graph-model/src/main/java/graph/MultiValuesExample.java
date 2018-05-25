/**
 */
package graph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Values Example</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link graph.MultiValuesExample#getNumbers <em>Numbers</em>}</li>
 * </ul>
 *
 * @see graph.GraphPackage#getMultiValuesExample()
 * @model
 * @generated
 */
public interface MultiValuesExample extends EObject {
	/**
	 * Returns the value of the '<em><b>Numbers</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Numbers</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Numbers</em>' attribute list.
	 * @see graph.GraphPackage#getMultiValuesExample_Numbers()
	 * @model unique="false"
	 * @generated
	 */
	EList<Integer> getNumbers();

} // MultiValuesExample
