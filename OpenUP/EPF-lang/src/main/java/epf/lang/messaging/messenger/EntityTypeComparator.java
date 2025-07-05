package epf.lang.messaging.messenger;

import java.lang.reflect.Field;
import java.util.Comparator;
import jakarta.persistence.Transient;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.Bindable;
import jakarta.persistence.metamodel.EntityType;

public class EntityTypeComparator implements Comparator<EntityType<?>> {
	
	private final String packages;
	
	public EntityTypeComparator(String packages) {
		this.packages = packages;
	}

	@Override
	public int compare(final EntityType<?> entityType1, final EntityType<?> entityType2) {
		final Class<?> superClass1 = entityType1.getJavaType().getSuperclass();
		if(superClass1 != null && superClass1.getName().equals(entityType2.getJavaType().getName())) {
			return 1;
		}
		final Class<?> superClass2 = entityType2.getJavaType().getSuperclass();
		if(superClass2 != null && superClass2.getName().equals(entityType1.getJavaType().getName())) {
			return -1;
		}
		final int deepLevel1 = entityType1.getClass().getName().split("\\.").length;
		final int deepLevel2 = entityType2.getClass().getName().split("\\.").length;
		if(deepLevel1 > deepLevel2) {
			return 1;
		}
		if(deepLevel2 > deepLevel1) {
			return -1;
		}
		int res1 = 0;
		int count1 = 0;
		for(Attribute<?, ?> attr : entityType1.getAttributes()) {
			if(attr.getJavaMember() instanceof Field) {
				final Field field = (Field) attr.getJavaMember();
				if(field.getAnnotation(Transient.class) == null && attr instanceof Bindable) {
					final Bindable<?> bindable = (Bindable<?>) attr;
					if(bindable.getBindableJavaType().getName().startsWith(packages) && (attr.isAssociation() || attr.isCollection())) {
						count1++;
						if(bindable.getBindableJavaType().getName().equals(entityType2.getJavaType().getName())) {
							res1 = 1;
						}
						else if(superClass2 != null && bindable.getBindableJavaType().getName().equals(superClass2.getName())) {
							res1 = 1;
						}
					}
				}
			}
		}
		int res2 = 0;
		int count2 = 0;
		for(Attribute<?, ?> attr : entityType2.getAttributes()) {
			if(attr.getJavaMember() instanceof Field) {
				final Field field = (Field) attr.getJavaMember();
				if(field.getAnnotation(Transient.class) == null && attr instanceof Bindable) {
					final Bindable<?> bindable = (Bindable<?>) attr;
					if(bindable.getBindableJavaType().getName().startsWith(packages) && (attr.isAssociation() || attr.isCollection())) {
						count2++;
						if(bindable.getBindableJavaType().getName().equals(entityType1.getJavaType().getName())) {
							res2 = -1;
						}
						else if(superClass1 != null && bindable.getBindableJavaType().getName().equals(superClass1.getName())) {
							res2 = -1;
						}
					}
				}
			}
		}
		if(res1 + res2 == 0) {
			if(count1 > count2) {
				return 1;
			}
			else if(count2 > count1) {
				return -1;
			}
			return 0;
		}
		return res1 + res2;
	}
}
