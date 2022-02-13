package epf.persistence.internal.util;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import epf.persistence.internal.Embeddable;
import epf.persistence.internal.Entity;

/**
 * @author PC
 *
 */
public interface SchemaUtil {
	
	/**
	 * 
	 */
	String TERNANT_ID_FORMAT = "%s_%s";
	
	/**
	 * @param <T>
	 * @param metamodel
	 * @return
	 */
	static <T> Stream<Entity<T>> getEntities(final Metamodel metamodel) {
		return metamodel
				.getEntities()
    			.stream()
    			.map(entityType -> { 
    					Entity<T> entity = null;
    					try {
                    		@SuppressWarnings("unchecked")
                    		final EntityType<T> type = entityType.getClass().cast(entityType);
                    		entity = new Entity<>();
                    		entity.setType(type);
    	                	}
                    	catch(Exception ex) {
                    		throw ex;
                    	}
    					return entity;
    				}
    			)
    			.filter(entity -> entity != null);
	}
	
	/**
	 * @param <T>
	 * @param metamodel
	 * @return
	 */
	static <T> Stream<Embeddable<T>> getEmbeddables(final Metamodel metamodel){
		return metamodel
				.getEmbeddables()
    			.stream()
    			.map(embeddableType -> { 
    					Embeddable<T> embeddable = null;
    					try {
                    		@SuppressWarnings("unchecked")
                    		final EmbeddableType<T> type = embeddableType.getClass().cast(embeddableType);
                    		embeddable = new Embeddable<>();
                    		embeddable.setType(type);
    	                	}
                    	catch(Exception ex) {
                    		throw ex;
                    	}
    					return embeddable;
    				}
    			)
    			.filter(embeddable -> embeddable != null);
	}
	
	/**
	 * @param metamodel
	 * @param entityTables
	 * @param entityAttributes
	 */
	static void mapEntities(
			final Metamodel metamodel,
			final Map<String, EntityType<?>> entityTables, 
    		final Map<String, Map<String, Attribute<?,?>>> entityAttributes) {
		metamodel
		.getEntities()
		.forEach(entityType -> {
			String tableName = entityType.getName().toLowerCase(Locale.getDefault());
			final Table tableAnnotation = entityType.getJavaType().getAnnotation(Table.class);
			if(tableAnnotation != null) {
				tableName = tableAnnotation.name();
			}
			entityTables.put(tableName, entityType);
			final Map<String, Attribute<?,?>> attributes = new ConcurrentHashMap<>();
			entityType.getAttributes().forEach(attr -> {
				String columnName = attr.getName().toLowerCase(Locale.getDefault());
				if(attr.getJavaMember() instanceof Field) {
					final Field field = (Field) attr.getJavaMember();
					final Column columnAnnotation = field.getAnnotation(Column.class);
					if(columnAnnotation != null) {
						columnName = columnAnnotation.name();
					}
				}
				attributes.put(columnName, attr);
			});
			entityAttributes.put(tableName, attributes);
		});
	}
	
	/**
	 * @param schema
	 * @param ternant
	 * @return
	 */
	static String formatTernantId(final String schema, final String ternant) {
		return String.format(TERNANT_ID_FORMAT, schema, ternant);
	}
}
