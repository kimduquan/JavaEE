package epf.workflow.schema.adapter;

import epf.util.json.ext.adapter.StringOrObjectAdapter;
import epf.workflow.schema.ContinueAs;

/**
 * @author PC
 *
 */
public class ContinueAsAdapter extends StringOrObjectAdapter<ContinueAs> {

	/**
	 * 
	 */
	public ContinueAsAdapter() {
		super(ContinueAs.class);
	}
}
