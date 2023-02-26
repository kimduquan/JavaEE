package epf.workflow.state.schema.mapping;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.nosql.mapping.AttributeConverter;
import epf.workflow.schema.mapping.ArrayAttributeConverter;
import epf.workflow.state.schema.CallbackState;
import epf.workflow.state.schema.EventState;
import epf.workflow.state.schema.ForEachState;
import epf.workflow.state.schema.InjectState;
import epf.workflow.state.schema.OperationState;
import epf.workflow.state.schema.ParallelState;
import epf.workflow.state.schema.SleepState;
import epf.workflow.state.schema.State;
import epf.workflow.state.schema.SwitchState;
import epf.workflow.state.schema.Type;
import epf.workflow.util.EnumUtil;

/**
 * @author PC
 *
 */
public class StateArrayConverter implements AttributeConverter<State[], List<Object>> {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(StateArrayConverter.class.getName());

	@Override
	public List<Object> convertToDatabaseColumn(final State[] attribute) {
		try {
			final JsonArray jsonArray = JsonUtil.toJsonArray(attribute);
			return JsonUtil.asList(jsonArray);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToDatabaseColumn", ex);
			return null;
		}
	}

	@Override
	public State[] convertToEntityAttribute(final List<Object> dbData) {
		try {
			final List<Object> list = ArrayAttributeConverter.convertToList(dbData);
			return JsonUtil.fromLisṭ̣(list, this::newState).toArray(new State[0]);
		} 
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", ex);
			return null;
		}
	}
	
	private State newState(final Map<String, Object> map) {
		if(map != null) {
			final Type type = EnumUtil.valueOf(Type.class, (String)map.get("type"));
			try {
				switch(type) {
					case callback:
						return JsonUtil.fromMap(map, CallbackState.class);
					case event:
						return JsonUtil.fromMap(map, EventState.class);
					case foreach:
						return JsonUtil.fromMap(map, ForEachState.class);
					case inject:
						return JsonUtil.fromMap(map, InjectState.class);
					case operation:
						return JsonUtil.fromMap(map, OperationState.class);
					case parallel:
						return JsonUtil.fromMap(map, ParallelState.class);
					case sleep:
						return JsonUtil.fromMap(map, SleepState.class);
					case Switch:
						return JsonUtil.fromMap(map, SwitchState.class);
					default:
						break;
				}
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "newState", ex);
				return null;
			}
		}
		return null;
	}
}