package epf.util.json.ext;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.adapter.JsonbAdapter;

public class Adapter implements JsonbAdapter<Object, JsonObject> {
	
	protected Class<?> getAdaptFromClass(final JsonObject obj) throws Exception {
		final String className = obj.getString(Naming.CLASS);
		final Class<?> cls = Class.forName(className);
		return cls;
	}
	
	protected Class<?> getAdaptToClass(final Object obj) throws Exception {
		return obj.getClass();
	}
	
	protected JsonObject adaptFrom(final JsonObject obj) throws Exception {
		return Json.createObjectBuilder(obj).remove(Naming.CLASS).build();
	}
	
	protected JsonObject adaptTo(final JsonObject obj, final Class<?> cls) throws Exception {
		return Json.createObjectBuilder(obj).add(Naming.CLASS, cls.getName()).build();
	}
	
	@Override
	public JsonObject adaptToJson(final Object obj) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create()){
			final String json = jsonb.toJson(obj);
			final JsonObject jsonObject = JsonUtil.readObject(json);
			final Class<?> cls = getAdaptToClass(obj);
			return adaptTo(jsonObject, cls);
		}
	}

	@Override
	public Object adaptFromJson(final JsonObject obj) throws Exception {
		final Class<?> cls = getAdaptFromClass(obj);
		final JsonObject adaptObject = adaptFrom(obj);
		try(Jsonb jsonb = JsonbBuilder.create()){
			return jsonb.fromJson(adaptObject.toString(), cls);
		}
	}
}
