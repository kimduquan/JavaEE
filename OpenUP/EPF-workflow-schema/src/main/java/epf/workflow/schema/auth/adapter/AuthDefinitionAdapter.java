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
public class AuthDefinitionAdapter implements JsonbAdapter<AuthDefinition, Map<?, ?>> {

	@Override
	public Map<?, ?> adaptToJson(final AuthDefinition obj) throws Exception {
		return JsonUtil.toMap(obj);
	}

	@Override
	public AuthDefinition adaptFromJson(final Map<?, ?> obj) throws Exception {
		final AuthDefinition authDefinition = new AuthDefinition();
		Scheme scheme = Scheme.basic;
		Object string = obj.get("scheme");
		if(string != null) {
			scheme = Scheme.valueOf(string.toString());
		}
		authDefinition.setScheme(scheme);
		final String name = obj.get("name").toString();
		authDefinition.setName(name);
		final Map<?, ?> props = (Map<?, ?>) obj.get("properties");
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
