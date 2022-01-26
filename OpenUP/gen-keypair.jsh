import java.security.*;
import java.util.*;
import java.util.Base64.Encoder;

String algorithm = System.getProperty("algorithm", "RSA");
int keySize = Integer.valueOf(System.getProperty("keysize", "2048"));
Path privateFile = Paths.get(System.getProperty("private", "private.pem"));
Path publicFile = Paths.get(System.getProperty("public", "public.pem"));
String encode = System.getProperty("encode", "url");
KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
generator.initialize(keySize);
KeyPair keyPair = generator.generateKeyPair();
PrivateKey privateKey = keyPair.getPrivate();
PublicKey publicKey = keyPair.getPublic();
Encoder encoder;
switch(encode) {
case "url":
	encoder = Base64.getUrlEncoder();
	break;
case "mime":
	encoder = Base64.getMimeEncoder();
	break;
	default:
		encoder = Base64.getEncoder();
		break;
}
String privateText = encoder.encodeToString(privateKey.getEncoded());
System.out.println("Private Key:");
System.out.println(privateText);
Files.write(
		privateFile, 
		Arrays.asList(
				"-----BEGIN PRIVATE KEY-----", 
				privateText, 
				"-----END PRIVATE KEY-----"),
		StandardOpenOption.TRUNCATE_EXISTING,
		StandardOpenOption.CREATE
		);
String publicText = encoder.encodeToString(publicKey.getEncoded());
System.out.println("Public Key:");
System.out.println(publicText);
Files.write(
		publicFile, 
		Arrays.asList(
				"-----BEGIN PUBLIC KEY-----", 
				publicText, 
				"-----END PUBLIC KEY-----"),
		StandardOpenOption.TRUNCATE_EXISTING,
		StandardOpenOption.CREATE
		);
/exit