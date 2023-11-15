package epf.workflow.schema.adapter;

import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.util.StringOrObjectJsonAdapter;

/**
 * @author PC
 *
 */
public class ContinueAsAdapter extends StringOrObjectJsonAdapter<ContinueAs> {

	/**
	 * 
	 */
	public ContinueAsAdapter() {
		super(ContinueAs.class);
	}
}
