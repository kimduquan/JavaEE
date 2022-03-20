package epf.util.security;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;

/**
 * @author PC
 *
 */
public interface KeyStoreUtil {

	/**
	 * @param keyStoreType
	 * @param keyStoreFile
	 * @param keyStorePassword
	 * @return
	 * @throws Exception
	 */
	static KeyStore loadKeyStore(final String keyStoreType, final Path keyStoreFile, final char[] keyStorePassword) throws Exception {
		final KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		try(InputStream keyStoreInput = Files.newInputStream(keyStoreFile.toRealPath())){
			keyStore.load(keyStoreInput, keyStorePassword);
		}
		return keyStore;
	}
}
