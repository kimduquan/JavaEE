package epf.schema.internal;

import jakarta.persistence.metamodel.Attribute.PersistentAttributeType;
import jakarta.persistence.metamodel.Bindable.BindableType;
import jakarta.persistence.metamodel.PluralAttribute.CollectionType;
import jakarta.persistence.metamodel.Type.PersistenceType;

public class EnumBuilder {

	protected static epf.persistence.schema.BindableType buildBindableType(final BindableType type) {
		epf.persistence.schema.BindableType bindableType = null;
		switch(type) {
			case ENTITY_TYPE:
				bindableType = epf.persistence.schema.BindableType.ENTITY_TYPE;
				break;
			case PLURAL_ATTRIBUTE:
				bindableType = epf.persistence.schema.BindableType.PLURAL_ATTRIBUTE;
				break;
			case SINGULAR_ATTRIBUTE:
				bindableType = epf.persistence.schema.BindableType.SINGULAR_ATTRIBUTE;
				break;
			default:
				break;
		}
		return bindableType;
	}
	
	protected static epf.persistence.schema.PersistenceType buildPersistenceType(final PersistenceType type) {
		epf.persistence.schema.PersistenceType persistenceType = null;
		switch(type) {
		case BASIC:
			persistenceType = epf.persistence.schema.PersistenceType.BASIC;
			break;
		case EMBEDDABLE:
			persistenceType = epf.persistence.schema.PersistenceType.EMBEDDABLE;
			break;
		case ENTITY:
			persistenceType = epf.persistence.schema.PersistenceType.ENTITY;
			break;
		case MAPPED_SUPERCLASS:
			persistenceType = epf.persistence.schema.PersistenceType.MAPPED_SUPERCLASS;
			break;
		default:
			break;
		}
		return persistenceType;
	}
	
	protected static epf.persistence.schema.PersistentAttributeType buildPersistentAttributeType(final PersistentAttributeType type) {
		epf.persistence.schema.PersistentAttributeType attrType = null;
		switch(type) {
			case BASIC:
				attrType = epf.persistence.schema.PersistentAttributeType.BASIC;
				break;
			case ELEMENT_COLLECTION:
				attrType = epf.persistence.schema.PersistentAttributeType.ELEMENT_COLLECTION;
				break;
			case EMBEDDED:
				attrType = epf.persistence.schema.PersistentAttributeType.EMBEDDED;
				break;
			case MANY_TO_MANY:
				attrType = epf.persistence.schema.PersistentAttributeType.MANY_TO_MANY;
				break;
			case MANY_TO_ONE:
				attrType = epf.persistence.schema.PersistentAttributeType.MANY_TO_ONE;
				break;
			case ONE_TO_MANY:
				attrType = epf.persistence.schema.PersistentAttributeType.ONE_TO_MANY;
				break;
			case ONE_TO_ONE:
				attrType = epf.persistence.schema.PersistentAttributeType.ONE_TO_ONE;
				break;
			default:
				break;
		}
		return attrType;
	}
	
	protected static epf.persistence.schema.CollectionType buildCollectionType(final CollectionType type) {
		epf.persistence.schema.CollectionType collectionType = null;
		switch(type) {
			case COLLECTION:
				collectionType = epf.persistence.schema.CollectionType.COLLECTION;
				break;
			case LIST:
				collectionType = epf.persistence.schema.CollectionType.LIST;
				break;
			case MAP:
				collectionType = epf.persistence.schema.CollectionType.MAP;
				break;
			case SET:
				collectionType = epf.persistence.schema.CollectionType.SET;
				break;
			default:
				break;
		}
		return collectionType;
	}
}
