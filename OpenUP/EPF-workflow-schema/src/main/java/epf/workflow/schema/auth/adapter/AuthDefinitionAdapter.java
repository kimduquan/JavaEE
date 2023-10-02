package epf.workflow.schema.auth.adapter;

import java.util.Map;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.BasicPropertiesDefinition;
import epf.workflow.schema.auth.BearerPropertiesDefinition;
import epf.workflow.schema.auth.OAuth2PropertiesDefinition;
import epf.workflow.schema.auth.Scheme;

/**
 * @author PC
 *
 */
public class AuthDefinitionAdapter implements JsonbAdapter<AuthDefinition, Map<String, Object>> {

	@Override
	public Map<String, Object> adaptToJson(final AuthDefinition obj) throws Exception {
		return JsonUtil.toMap(obj);
	}

	@Override
	public AuthDefinition adaptFromJson(final Map<String, Object> obj) throws Exception {
		final AuthDefinition authDefinition = new AuthDefinition();
		final Scheme scheme = Scheme.valueOf(obj.getOrDefault("scheme", Scheme.basic.name()).toString());
		authDefinition.setScheme(scheme);
		final String name = obj.get("name").toString();
		authDefinition.setName(name);
		@SuppressWarnings("unchecked")
		final Map<String, Object> props = (Map<String, Object>) obj.get("properties");
		if(props != null) {
			switch(scheme) {
				case basic:
					authDefinition.setProperties(JsonUtil.fromMap(props, BasicPropertiesDefinition.class));
					break;
				case bearer:
					authDefinition.setProperties(JsonUtil.fromMap(props, BearerPropertiesDefinition.class));
					break;
				case oauth2:
					authDefinition.setProperties(JsonUtil.fromMap(props, OAuth2PropertiesDefinition.class));
					break;
				default:
					break;
			}
		}
		return authDefinition;
	}

}
