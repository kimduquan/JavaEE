package epf.workflow.expressions;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.el.ELContext;
import jakarta.el.ELProcessor;
import jakarta.el.MapELResolver;
import jakarta.el.PropertyNotFoundException;
import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public class JsonELResolver extends MapELResolver {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(JsonELResolver.class.getName());
	
	/**
	 * 
	 */
	public static final String LENGTH = "length";
	
	/**
	 * 
	 */
	private final JsonValue data;
	
	/**
	 * @param data
	 */
	public JsonELResolver(final JsonValue data) {
		this.data = data;
	}

	@Override
	public Object getValue(final ELContext context, final Object base, final Object property) {
		Objects.requireNonNull(context, "ELContext");
		JsonValue jsonBase = data;
		if(base instanceof JsonValue) {
			jsonBase = (JsonValue) base;
		}
		switch(jsonBase.getValueType()) {
			case ARRAY:
				final JsonArray jsonArray = jsonBase.asJsonArray();
				if(property instanceof Integer) {
					final Integer index = (Integer) property;
					if(index >= 0 && index < jsonArray.size()) {
			            context.setPropertyResolved(base, property);
			            final JsonValue jsonValue = jsonArray.get(index);
			            return asObject(jsonValue);
					}
				}
				else if(property instanceof String && LENGTH.equals(property)) {
		            context.setPropertyResolved(base, property);
					try {
						return jsonArray.size();
					} 
					catch (Exception e) {
						LOGGER.log(Level.SEVERE, "getValue", e);
					}
				}
			case OBJECT:
				final JsonObject jsonObject = jsonBase.asJsonObject();
				if(property != null && jsonObject.containsKey(property)) {
		            context.setPropertyResolved(base, property);
		            final JsonValue jsonValue =  jsonObject.get(property);
		            return asObject(jsonValue);
				}
			default:
				break;
		}
		throw new PropertyNotFoundException();
    }

	@Override
	public Class<?> getType(final ELContext context, final Object base, final Object property) {
		Objects.requireNonNull(context, "ELContext");
		JsonValue jsonBase = data;
		if(base instanceof JsonValue) {
			jsonBase = (JsonValue) base;
		}
		switch(jsonBase.getValueType()) {
			case ARRAY:
				final JsonArray jsonArray = jsonBase.asJsonArray();
				if(property instanceof Integer) {
					final Integer index = (Integer) property;
					if(index >= 0 && index < jsonArray.size()) {
			            context.setPropertyResolved(base, property);
			            return getClass(jsonArray.get(index));
					}
				}
				else if(property instanceof String && LENGTH.equals(property)) {
		            context.setPropertyResolved(base, property);
					return Integer.class;
				}
			case OBJECT:
				final JsonObject jsonObject = jsonBase.asJsonObject();
				if(property != null && jsonObject.containsKey(property)) {
		            context.setPropertyResolved(base, property);
		            return getClass(jsonObject.get(property));
				}
			default:
				break;
		}
		throw new PropertyNotFoundException();
	}

	@Override
	public void setValue(final ELContext context, final Object base, final Object property, final Object value) {
		Objects.requireNonNull(context, "ELContext");
        JsonValue jsonValue = null;
        if(value instanceof JsonValue) {
        	jsonValue = (JsonValue) value;
        }
        else if(value != null){
        	try {
				jsonValue = JsonUtil.toJsonValue(value);
			} 
        	catch (Exception e) {
        		LOGGER.log(Level.SEVERE, "setValue", e);
			}
        }
        else {
        	jsonValue = JsonValue.NULL;
        }
		JsonValue jsonBase = data;
		if(base instanceof JsonValue) {
			jsonBase = (JsonValue) base;
		}
		switch(jsonBase.getValueType()) {
			case ARRAY:
				final JsonArray jsonArray = jsonBase.asJsonArray();
				if(property instanceof Integer) {
					final Integer index = (Integer) property;
					if(index >= 0 && index < jsonArray.size()) {
			            context.setPropertyResolved(base, property);
			            jsonArray.set(index, jsonValue);
			            return;
					}
				}
				else if(property instanceof String && LENGTH.equals(property)) {
		            context.setPropertyResolved(base, property);
					return;
				}
			case OBJECT:
				final JsonObject jsonObject = jsonBase.asJsonObject();
				if(property != null && jsonObject.containsKey(property)) {
		            context.setPropertyResolved(base, property);
		            jsonObject.put(property.toString(), jsonValue);
		            return;
				}
			default:
				break;
		}
		throw new PropertyNotFoundException();
	}

	@Override
	public boolean isReadOnly(final ELContext context, final Object base, final Object property) {
		JsonValue jsonBase = data;
		if(base instanceof JsonValue) {
			jsonBase = (JsonValue) base;
		}
		switch(jsonBase.getValueType()) {
			case ARRAY:
				if(property instanceof String && LENGTH.equals(property)) {
					return true;
				}
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	public Class<?> getCommonPropertyType(final ELContext context, final Object base) {
		JsonValue jsonBase = data;
		if(base instanceof JsonValue) {
			jsonBase = (JsonValue) base;
		}
		switch(jsonBase.getValueType()) {
			case ARRAY:
				return Object.class;
			case OBJECT:
				return String.class;
			default:
				break;
		}
		return null;
	}
	
	private Class<?> getClass(final JsonValue jsonValue) {
		Class<?> cls = null;
		if(jsonValue != null) {
			switch(jsonValue.getValueType()) {
				case ARRAY:
					cls = new Object[0].getClass();
					break;
				case FALSE:
					cls = Boolean.class;
					break;
				case NULL:
					break;
				case NUMBER:
					cls = Number.class;
					break;
				case OBJECT:
					cls = JsonValue.class;
					break;
				case STRING:
					cls = String.class;
					break;
				case TRUE:
					cls = Boolean.class;
					break;
				default:
					break;
			}
		}
		return cls;
	}
	
	private Object asObject(final JsonValue jsonValue) {
		if(jsonValue != null) {
			switch(jsonValue.getValueType()) {
				case ARRAY:
					return jsonValue;
				case FALSE:
					return false;
				case NULL:
					break;
				case NUMBER:
					return ((JsonNumber)jsonValue).numberValue();
				case OBJECT:
					return jsonValue;
				case STRING:
					return ((JsonString)jsonValue).getString();
				case TRUE:
					return true;
				default:
					break;
			}
		}
		return null;
	}
	
	/**
	 * @param elProcessor
	 */
	public void defineBean(final ELProcessor elProcessor) {
		switch(data.getValueType()) {
			case ARRAY:
				try {
					elProcessor.defineBean(LENGTH, JsonUtil.toJsonValue(data.asJsonArray().size()));
				} 
				catch (Exception e) {
					LOGGER.log(Level.SEVERE, "defineBean", e);
				}
				break;
			case OBJECT:
				data.asJsonObject().forEach((name, value) -> {
					elProcessor.defineBean(name, asObject(value));
				});
				break;
			default:
				break;
		}
	}
}
