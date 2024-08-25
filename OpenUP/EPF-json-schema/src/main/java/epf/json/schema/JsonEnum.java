package epf.json.schema;

/**
 * @author PC
 *
 */
public class JsonEnum extends Value {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String[] _enum;

	public String[] getEnum() {
		return _enum;
	}

	public void setEnum(final String[] _enum) {
		this._enum = _enum;
	}
}
