package epf.workflow.schema.adapter;

import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.util.EitherAdapter;

/**
 * @author PC
 *
 */
public class ContinueAsAdapter extends EitherAdapter<String, ContinueAs> {

	/**
	 * 
	 */
	public ContinueAsAdapter() {
		super(String.class, ContinueAs.class);
	}
}
