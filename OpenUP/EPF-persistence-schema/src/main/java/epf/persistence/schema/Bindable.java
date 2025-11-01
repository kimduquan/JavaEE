package epf.persistence.schema;

import java.io.Serializable;

public class Bindable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BindableType bindableType;
	private String bindableJavaType;
	
	public BindableType getBindableType() {
		return bindableType;
	}
	public void setBindableType(BindableType bindableType) {
		this.bindableType = bindableType;
	}
	public String getBindableJavaType() {
		return bindableJavaType;
	}
	public void setBindableJavaType(String bindableJavaType) {
		this.bindableJavaType = bindableJavaType;
	}
}
