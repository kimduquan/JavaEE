package epf.workflow.schema;

public class RuntimeError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RuntimeError(final Exception exception) {
		super(exception);
	}
}
