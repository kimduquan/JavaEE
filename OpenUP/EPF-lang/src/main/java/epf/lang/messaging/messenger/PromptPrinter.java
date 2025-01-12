package epf.lang.messaging.messenger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Transient;
import jakarta.persistence.metamodel.Bindable;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

public class PromptPrinter {
	
	private static final Logger LOGGER = Logger.getLogger(PromptPrinter.class.getName());
	
	private final String packages;
	private final Map<String, EntityType<?>> handledEntitiesByJavaType = new HashMap<>();
	
	public PromptPrinter(String packages) {
		this.packages = packages;
	}
	
	private void printEnumTypes(final EntityType<?> entityType, final StringBuilder prompt) {
		for(Class<?> clazz : entityType.getJavaType().getDeclaredClasses()) {
			if(clazz.isEnum()) {
				prompt.append("    enum ");
				prompt.append(clazz.getSimpleName());
				prompt.append(" {\n");
				for(Object enumConstant : clazz.getEnumConstants()) {
					prompt.append("        ");
					prompt.append(enumConstant.toString());
					prompt.append(",");
					try {
						final Field enumField = clazz.getField(enumConstant.toString());
						final Description enumDesc = enumField.getAnnotation(Description.class);
						if(enumDesc != null) {
							prompt.append(" // ");
							prompt.append(enumDesc.value());
						}
					}
					catch(Exception ex) {
						LOGGER.log(Level.SEVERE, ex.toString());
					}
					prompt.append("\n");
				}
				prompt.append("    };\n");
			}
		}
	}
	
	private void printAttributes(final EntityType<?> entityType, final StringBuilder prompt) {
		entityType.getDeclaredAttributes().forEach(attr -> {
			if(attr.getJavaMember() instanceof Field) {
				final Field field = (Field) attr.getJavaMember();
				if(field.getAnnotation(Transient.class) == null) {
					if(attr instanceof Bindable) {
						final Bindable<?> bindable = (Bindable<?>) attr;
						if(attr.isAssociation() || attr.isCollection()) {
							if(bindable.getBindableJavaType().getName().startsWith(packages)) {
								prompt.append("    ");
								if(attr instanceof CollectionAttribute) {
									prompt.append(String.format("Collection<%s>", bindable.getBindableJavaType().getSimpleName()));
								}
								else if(attr instanceof ListAttribute) {
									prompt.append(String.format("List<%s>", bindable.getBindableJavaType().getSimpleName()));
								}
								else if(attr instanceof MapAttribute) {
									prompt.append(String.format("Map<%s>", bindable.getBindableJavaType().getSimpleName()));
								}
								else if(attr instanceof SetAttribute) {
									prompt.append(String.format("Set<%s>", bindable.getBindableJavaType().getSimpleName()));
								}
								else if(attr instanceof SingularAttribute) {
									prompt.append(bindable.getBindableJavaType().getSimpleName());
								}
							}
							else {
								return;
							}
						}
						else {
							prompt.append("    ");
							prompt.append(attr.getJavaType().getSimpleName());
						}
					}
					else {
						prompt.append("    ");
						prompt.append(attr.getJavaType().getSimpleName());
					}
					prompt.append(' ');
					prompt.append(attr.getName());
					final DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
					if(defaultValue != null) {
						prompt.append(" = ");
						if(attr.getJavaType() == String.class) {
							prompt.append('"');
							prompt.append(defaultValue.value());
							prompt.append('"');
						}
						else if(attr.getJavaType().isEnum()) {
							prompt.append(attr.getJavaType().getSimpleName());
							prompt.append('.');
							prompt.append(defaultValue.value().replace(' ', '_').replace('-', '_'));
						}
						else {
							prompt.append(defaultValue.value());
						}
					}
					prompt.append(";");
					final Description fieldDesc = field.getAnnotation(Description.class);
					if(fieldDesc != null && !fieldDesc.value().isEmpty()) {
						prompt.append(" // ");
						prompt.append(fieldDesc.value());
					}
					prompt.append('\n');
				}
			}
		});
	}
	
	public void printEntityType(final EntityType<?> entityType, final StringBuilder prompt, final Map<String, EntityType<?>> entitiesByJavaType) {
		if(!handledEntitiesByJavaType.containsKey(entityType.getJavaType().getName())) {
			final Class<?> superClass = entityType.getJavaType().getSuperclass();
			EntityType<?> superEntityType = null;
			if(superClass != null && superClass.getName().startsWith(packages)) {
				superEntityType = entitiesByJavaType.get(superClass.getName());
				printEntityType(superEntityType, prompt, entitiesByJavaType);
			}
			final Description description = entityType.getJavaType().getAnnotation(Description.class);
			if(description != null && !description.value().isEmpty()) {
				prompt.append(" // ");
				prompt.append(description.value());
				prompt.append('\n');
			}
			prompt.append("class ");
			prompt.append(entityType.getName());
			if(superEntityType != null) {
				prompt.append(" extends ");
				prompt.append(superEntityType.getName());
			}
			prompt.append(" {\n");
			printEnumTypes(entityType, prompt);
			printAttributes(entityType, prompt);
			prompt.append("};\n");
			handledEntitiesByJavaType.put(entityType.getJavaType().getName(), entityType);
		}
	}
}
