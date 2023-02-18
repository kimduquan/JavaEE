package epf.workflow.el;

import java.util.Objects;
import javax.el.ELContext;
import javax.el.MapELResolver;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public class JsonObjectELResolver extends MapELResolver {

	@Override
	public Object getValue(final ELContext context, final Object base, final Object property) {
		Objects.requireNonNull(context, "ELContext");
        if (base instanceof JsonObject) {
            context.setPropertyResolved(base, property);
            final JsonObject jsonObject = (JsonObject) base;
            return jsonObject.get(property);
        }
        return null;
	}

	@Override
	public Class<?> getType(final ELContext context, final Object base, final Object property) {
		Objects.requireNonNull(context, "ELContext");
		if (base instanceof JsonObject) {
            context.setPropertyResolved(true);
            return JsonValue.class;
        }
        return null;
	}

	@Override
	public void setValue(final ELContext context, final Object base, final Object property, final Object value) {
		Objects.requireNonNull(context, "ELContext");
		if (base instanceof JsonObject) {
            context.setPropertyResolved(base, property);
            final JsonObject jsonObject = (JsonObject) base;
            jsonObject.put(String.valueOf(property), (JsonValue)value);
        }
	}

	@Override
	public boolean isReadOnly(final ELContext context, final Object base, final Object property) {
		return true;
	}

	@Override
	public Class<?> getCommonPropertyType(final ELContext context, final Object base) {
		return String.class;
	}

}
