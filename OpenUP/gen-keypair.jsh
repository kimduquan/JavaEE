import java.security.*;
import java.util.*;
import java.util.Base64.Encoder;

interface KeyUtil {
	
	static void generateKeyPair(final String algorithm, final int keySize, final StringBuilder privateKeyText, final StringBuilder publicKeyText) throws Exception {
		final KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
		generator.initialize(keySize);
		final KeyPair keyPair = generator.generateKeyPair();
		final byte[] lineSeparator = System.lineSeparator().getBytes("UTF-8");
		final Encoder encoder = Base64.getMimeEncoder(64, lineSeparator);
		final PrivateKey privateKey = keyPair.getPrivate();
		privateKeyText.append("-----BEGIN PRIVATE KEY-----");
		privateKeyText.append(System.lineSeparator());
		privateKeyText.append(encoder.encodeToString(privateKey.getEncoded()));
		privateKeyText.append(System.lineSeparator());
		privateKeyText.append("-----END PRIVATE KEY-----");

		final PublicKey publicKey = keyPair.getPublic();
		publicKeyText.append("-----BEGIN PUBLIC KEY-----");
		publicKeyText.append(System.lineSeparator());
		publicKeyText.append(encoder.encodeToString(publicKey.getEncoded()));
		publicKeyText.append(System.lineSeparator());
		publicKeyText.append("-----END PUBLIC KEY-----");
	}
	
	static void generateKeyPair(final String algorithm, final int keySize, final Path privateKey, final Path publicKey) throws Exception {
		final StringBuilder privateKeyText = new StringBuilder();
		final StringBuilder publicKeyText = new StringBuilder();
		generateKeyPair(algorithm, keySize, privateKeyText, publicKeyText);
		Files.write(privateKey, privateKeyText.toString().getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
		Files.write(publicKey, publicKeyText.toString().getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
	}
}

String algorithm = System.getProperty("algorithm", "RSA");
int keySize = Integer.valueOf(System.getProperty("keysize", "2048"));
Path privateFile = Paths.get(System.getProperty("private", "private.pem"));
Path publicFile = Paths.get(System.getProperty("public", "public.pem"));
KeyUtil.generateKeyPair(algorithm, keySize, privateFile, publicFile);
/exit