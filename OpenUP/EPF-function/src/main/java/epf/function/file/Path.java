package epf.function.file;

public class Path {

	private String lastModifiedTime;
	
	private boolean directory;
	
	private boolean executable;
	
	private boolean hidden;
	
	private boolean readable;
	
	private boolean symbolicLink;
	
	private boolean writable;
	
	private Path[] list;
	
	private long size;

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(final String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public boolean isDirectory() {
		return directory;
	}

	public void setDirectory(final boolean directory) {
		this.directory = directory;
	}

	public boolean isExecutable() {
		return executable;
	}

	public void setExecutable(final boolean executable) {
		this.executable = executable;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isReadable() {
		return readable;
	}

	public void setReadable(final boolean readable) {
		this.readable = readable;
	}

	public boolean isSymbolicLink() {
		return symbolicLink;
	}

	public void setSymbolicLink(final boolean symbolicLink) {
		this.symbolicLink = symbolicLink;
	}

	public boolean isWritable() {
		return writable;
	}

	public void setWritable(final boolean writable) {
		this.writable = writable;
	}

	public Path[] getList() {
		return list;
	}

	public void setList(final Path[] list) {
		this.list = list;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
