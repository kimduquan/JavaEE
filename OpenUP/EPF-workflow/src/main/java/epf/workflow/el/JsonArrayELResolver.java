package epf.workflow.el;

import java.util.Objects;
import javax.el.ArrayELResolver;
import javax.el.ELContext;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.json.JsonArray;
import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public class JsonArrayELResolver extends ArrayELResolver {
	
	private int toInteger(final Object p) {

        if (p instanceof Integer) {
            return ((Integer) p).intValue();
        }
        if (p instanceof Character) {
            return ((Character) p).charValue();
        }
        if (p instanceof Boolean) {
            return ((Boolean) p).booleanValue() ? 1 : 0;
        }
        if (p instanceof Number) {
            return ((Number) p).intValue();
        }
        if (p instanceof String) {
            return Integer.parseInt((String) p);
        }
        throw new IllegalArgumentException();
    }
	
	/**
	 * 
	 */
	public JsonArrayELResolver() {
		super(true);
	}

	@Override
    public Class<?> getType(final ELContext context, final Object base, final Object property) {
		Objects.requireNonNull(context, "ELContext");
        if (base instanceof JsonArray) {
            context.setPropertyResolved(true);
            final int index = toInteger(property);
            final JsonArray array = (JsonArray) base;
            if (index < 0 || index >= array.size()) {
                throw new PropertyNotFoundException();
            }
            return array.get(index).getClass();
        }
        return null;
    }
	
	@Override
    public Object getValue(final ELContext context, final Object base, final Object property) {
		Objects.requireNonNull(context, "ELContext");
        if (base instanceof JsonArray) {
            context.setPropertyResolved(base, property);
            final int index = toInteger(property);
            final JsonArray array = (JsonArray) base;
            if (index >= 0 && index < array.size()) {
                return array.get(index);
            }
        }
        return null;
    }
	
	@Override
    public void setValue(final ELContext context, final Object base, final Object property, final Object val) {
		Objects.requireNonNull(context, "ELContext");
		if (base instanceof JsonArray) {
            context.setPropertyResolved(base, property);
            if (isReadOnly(context, null, null)) {
                throw new PropertyNotWritableException();
            }
            if (!(val instanceof JsonValue)) {
                throw new ClassCastException();
            }
            final int index = toInteger(property);
            final JsonArray array = (JsonArray) base;
            if (index < 0 || index >= array.size()) {
                throw new PropertyNotFoundException();
            }
            array.set(index, (JsonValue)val);
        }
    }
	
	@Override
    public Class<?> getCommonPropertyType(final ELContext context, final Object base) {
		Objects.requireNonNull(context, "ELContext");
		if (base instanceof JsonArray) {
            return Integer.class;
        }
        return null;
    }
}
