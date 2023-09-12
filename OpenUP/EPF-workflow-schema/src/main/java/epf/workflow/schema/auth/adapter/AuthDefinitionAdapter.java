package epf.workflow.schema.auth.adapter;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;
import epf.util.json.JsonUtil;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.BasicPropertiesDefinition;
import epf.workflow.schema.auth.BearerPropertiesDefinition;
import epf.workflow.schema.auth.OAuth2PropertiesDefinition;
import epf.workflow.schema.auth.Scheme;

/**
 * @author PC
 *
 */
public class AuthDefinitionAdapter implements JsonbAdapter<AuthDefinition, JsonObject> {

	@Override
	public JsonObject adaptToJson(final AuthDefinition obj) throws Exception {
		return JsonUtil.toJsonObject(obj);
	}

	@Override
	public AuthDefinition adaptFromJson(final JsonObject obj) throws Exception {
		final AuthDefinition authDefinition = new AuthDefinition();
		final Scheme scheme = Scheme.valueOf(obj.getString("scheme", Scheme.basic.name()));
		authDefinition.setScheme(scheme);
		final String name = obj.getString("name");
		authDefinition.setName(name);
		final JsonValue props = obj.get("properties");
		if(props != null) {
			switch(scheme) {
				case basic:
					authDefinition.setProperties(JsonUtil.fromJson(props.toString(), BasicPropertiesDefinition.class));
					break;
				case bearer:
					authDefinition.setProperties(JsonUtil.fromJson(props.toString(), BearerPropertiesDefinition.class));
					break;
				case oauth2:
					authDefinition.setProperties(JsonUtil.fromJson(props.toString(), OAuth2PropertiesDefinition.class));
					break;
				default:
					break;
			}
		}
		return authDefinition;
	}

}
