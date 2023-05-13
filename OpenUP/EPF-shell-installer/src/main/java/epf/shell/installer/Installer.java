package epf.shell.installer;

import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Installer {

	public static void main(String ... args) {
		com.izforge.izpack.installer.bootstrap.Installer.main(args);
	}
}
