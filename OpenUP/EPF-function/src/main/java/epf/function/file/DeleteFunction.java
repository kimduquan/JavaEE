package epf.function.file;

import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Link;
import epf.function.Function;
import epf.naming.Naming;

public class DeleteFunction extends Function {
	
	private String fileName;

	public DeleteFunction(final String path) {
		super(Naming.FILE, HttpMethod.DELETE, path + "/{fileName}");
	}

	@Override
	public Link toLink(final Integer index) {
		return buildLink(index).build(fileName);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}
