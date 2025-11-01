package epf.persistence.schema;

public class PluralAttribute extends Attribute {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CollectionType collectionType;
	private Type elementType;
	private BindableType bindableType;
	private String bindableJavaType;

	public CollectionType getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(CollectionType collectionType) {
		this.collectionType = collectionType;
	}

	public Type getElementType() {
		return elementType;
	}

	public void setElementType(Type elementType) {
		this.elementType = elementType;
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
