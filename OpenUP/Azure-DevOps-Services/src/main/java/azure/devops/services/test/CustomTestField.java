package azure.devops.services.test;

/**
 * @author PC
 * A custom field information. 
 * Allowed Key : Value pairs - ( AttemptId: int value, IsTestResultFlaky: bool)
 */
public class CustomTestField {

	/**
	 * Field Name.
	 */
	String fieldName;
	/**
	 * Field value.
	 */
	Object object;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
