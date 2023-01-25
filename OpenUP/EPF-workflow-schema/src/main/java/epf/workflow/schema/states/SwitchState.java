package epf.workflow.schema.states;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import epf.workflow.schema.defaultdef.DefaultConditionDefinition;
import epf.workflow.schema.switchconditions.DataCondition;
import epf.workflow.schema.switchconditions.EventCondition;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "eventConditions",
    "dataConditions",
    "defaultCondition",
    "usedForCompensation"
})
public class SwitchState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Defines conditions evaluated against events
     * 
     */
    @JsonbProperty("eventConditions")
    @Valid
    private List<EventCondition> eventConditions = new ArrayList<EventCondition>();
    /**
     * Defines conditions evaluated against state data
     * 
     */
    @JsonbProperty("dataConditions")
    @Valid
    private List<DataCondition> dataConditions = new ArrayList<DataCondition>();
    /**
     * Switch state default condition definition
     * 
     */
    @JsonbProperty("defaultCondition")
    @Valid
    private DefaultConditionDefinition defaultCondition;
    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    private boolean usedForCompensation = false;

    /**
     * Defines conditions evaluated against events
     * 
     */
    @JsonbProperty("eventConditions")
    public List<EventCondition> getEventConditions() {
        return eventConditions;
    }

    /**
     * Defines conditions evaluated against events
     * 
     */
    @JsonbProperty("eventConditions")
    public void setEventConditions(final List<EventCondition> eventConditions) {
        this.eventConditions = eventConditions;
    }

    /**
     * Defines conditions evaluated against state data
     * 
     */
    @JsonbProperty("dataConditions")
    public List<DataCondition> getDataConditions() {
        return dataConditions;
    }

    /**
     * Defines conditions evaluated against state data
     * 
     */
    @JsonbProperty("dataConditions")
    public void setDataConditions(List<DataCondition> dataConditions) {
        this.dataConditions = dataConditions;
    }

    /**
     * Switch state default condition definition
     * 
     */
    @JsonbProperty("defaultCondition")
    public DefaultConditionDefinition getDefaultCondition() {
        return defaultCondition;
    }

    /**
     * Switch state default condition definition
     * 
     */
    @JsonbProperty("defaultCondition")
    public void setDefaultCondition(final DefaultConditionDefinition defaultCondition) {
        this.defaultCondition = defaultCondition;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public boolean isUsedForCompensation() {
        return usedForCompensation;
    }

    /**
     * If true, this state is used to compensate another state. Default is false
     * 
     */
    @JsonbProperty("usedForCompensation")
    public void setUsedForCompensation(final boolean usedForCompensation) {
        this.usedForCompensation = usedForCompensation;
    }
}
