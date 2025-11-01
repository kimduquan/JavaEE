package epf.mcp.gateway;

import java.util.List;
import epf.persistence.schema.EntityType;

public class SchemaBuilder {

	private List<EntityType> entities;
	
	public SchemaBuilder entities(final List<EntityType> entities) {
		this.entities = entities;
		return this;
	}
	
	public StringBuilder build() {
		final StringBuilder builder = new StringBuilder();
		entities.forEach(entity -> {
			builder.append("\\nclass ");
			builder.append(entity.getName());
			if(entity.getSupertype() != null) {
				builder.append(" extends ");
				builder.append(entity.getSupertype().getJavaType());
			}
			builder.append(" {\n");
			entity.getDeclaredSingularAttributes().forEach(attribute -> {
				builder.append("\t" + attribute.getJavaType() + " " + attribute.getName() + ";\n");
			});
			entity.getDeclaredPluralAttributes().forEach(attribute -> {
				builder.append('\t');
				switch(attribute.getCollectionType()) {
					case COLLECTION:
						builder.append("Collection<");
						break;
					case LIST:
						builder.append("List<");
						break;
					case MAP:
						builder.append("Map<String, ");
						break;
					case SET:
						builder.append("Set<");
						break;
					default:
						break;
				}
				builder.append(attribute.getElementType().getJavaType() + "> " + attribute.getName() + ";\n");
			});
			builder.append('}');
		});
		return builder;
	}
}
