package epf.persistence.schema;

public class SingularAttribute extends Attribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean id;
	private boolean version;
	private boolean optional;
	private Type type;
	private BindableType bindableType;
	private String bindableJavaType;
	
	public boolean isId() {
		return id;
	}
	public void setId(boolean id) {
		this.id = id;
	}
	public boolean isVersion() {
		return version;
	}
	public void setVersion(boolean version) {
		this.version = version;
	}
	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
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
