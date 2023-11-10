package epf.workflow.schema.adapter;

import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.util.EitherJsonAdapter;

/**
 * @author PC
 *
 */
public class ContinueAsAdapter extends EitherJsonAdapter<String, ContinueAs> {

	/**
	 * 
	 */
	public ContinueAsAdapter() {
		super(String.class, ContinueAs.class);
	}
}
