package epf.lang.messaging.messenger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import jakarta.persistence.metamodel.EntityType;
import jakarta.validation.ConstraintViolation;

public class ErrorMessageBuilder {
	
	public StringBuilder buildSystemErrorMessage(final Set<ConstraintViolation<GeneratedQuery>> violations) {
		final StringBuilder message = new StringBuilder();
		message.append("There are some valiation error:");
		violations.forEach(violation -> {
			message.append("\n    - ");
			message.append(violation.getMessage());
		});
		message.append("\nPlease correct them then re-answer.");
		return message;
	}
	
	public StringBuilder buildSystemErrorMessage(final String name, final Throwable ex, final Map<String, EntityType<?>> entitiesByJavaType) {
		final StringBuilder builder = new StringBuilder();
		String message = ex.getMessage();
		for(Map.Entry<String, EntityType<?>> entry : entitiesByJavaType.entrySet()) {
			message = message.replace(entry.getKey(), entry.getValue().getName());
		}
		builder.append("A error occur related to \"");
		builder.append(name);
		builder.append("\":");
		builder.append(message);
		builder.append("\nPlease correct it then re-answer.");
		return builder;
	}
	
	public StringBuilder buildMissingAttributesSystemMessage(final Map<String, List<String>> missingAttributes) {
		final StringBuilder builder = new StringBuilder();
		builder.append("Could not resolve below attribute(s) in \"template\":");
		missingAttributes.forEach((entityType, attributes) -> {
			builder.append("\n - '");
			builder.append(String.join("', '", attributes));
			builder.append("'");
			if(!entityType.isEmpty()) {
				builder.append(" of ");
				builder.append(entityType);
			}
		});
		builder.append("\nPlease correct them then re-answer.");
		return builder;
	}
}
