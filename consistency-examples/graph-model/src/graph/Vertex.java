/**
 */
package graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link graph.Vertex#getLabel <em>Label</em>}</li>
 *   <li>{@link graph.Vertex#getWeigth <em>Weigth</em>}</li>
 * </ul>
 *
 * @see graph.GraphPackage#getVertex()
 * @model
 * @generated
 */
public interface Vertex extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see graph.GraphPackage#getVertex_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link graph.Vertex#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Weigth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weigth</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weigth</em>' attribute.
	 * @see #setWeigth(int)
	 * @see graph.GraphPackage#getVertex_Weigth()
	 * @model
	 * @generated
	 */
	int getWeigth();

	/**
	 * Sets the value of the '{@link graph.Vertex#getWeigth <em>Weigth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weigth</em>' attribute.
	 * @see #getWeigth()
	 * @generated
	 */
	void setWeigth(int value);

} // Vertex
