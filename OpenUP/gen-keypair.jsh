import java.security.*;
import java.util.*;
import java.util.Base64.Encoder;

interface StringUtil {
	static List<String> split(final String string, final int size) {
		List<String> strings = new ArrayList<>();
		for(int start = 0, end = size; end <= string.length(); start = end, end += size) {
			strings.add(string.substring(start, end));
			if(end + size > string.length()) {
				strings.add(string.substring(end));
			}
		}
		return strings;
	}
}

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
byte[] privateBytes = encoder.encode(privateKey.getEncoded());
String privateText = new String(privateBytes, "UTF-8");
List<String> privateLines = StringUtil.split(privateText, 64);
privateLines.add(0, "-----BEGIN PRIVATE KEY-----");
privateLines.add("-----END PRIVATE KEY-----");
System.out.println("Private Key:" + privateKey.getFormat());
System.out.println(privateText);
Files.write(
		privateFile, 
		privateLines,
		StandardOpenOption.TRUNCATE_EXISTING,
		StandardOpenOption.CREATE
		);
byte[] publicBytes = encoder.encode(publicKey.getEncoded());
String publicText = new String(publicBytes, "UTF-8");
List<String> publicLines = StringUtil.split(publicText, 64);
publicLines.add(0, "-----BEGIN PUBLIC KEY-----");
publicLines.add("-----END PUBLIC KEY-----");
System.out.println("Public Key:" + publicKey.getFormat());
System.out.println(publicText);
Files.write(
		publicFile, 
		publicLines,
		StandardOpenOption.TRUNCATE_EXISTING,
		StandardOpenOption.CREATE
		);
/exit