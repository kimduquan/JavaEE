package epf.workflow.util;

/**
 * 
 */
public class OBJECT<T extends Object> {

	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private T object;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
