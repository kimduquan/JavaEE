/env --class-path EPF-utility/target/EPF-utility-1.0.0.jar

import epf.util.security.KeyUtil;
import epf.util.security.SecurityUtil;
import java.util.Base64.Encoder;
import javax.crypto.SecretKey;

String algorithm = System.getProperty("algorithm", "AES");
int keySize = Integer.valueOf(System.getProperty("keysize", "256"));
String string = System.getProperty("string", "");
String secret = System.getProperty("secret", "");

SecretKey secretKey = null;

if(secret.isEmpty()){
	secretKey = KeyUtil.generateSecret(algorithm, keySize);
	var secretString = KeyUtil.encodeToString(secretKey);
	System.out.println("SecretKey:" + secretString);
} else{
	secretKey = KeyUtil.decodeFromString(secret, algorithm);
}

var encryptString = SecurityUtil.encrypt(string, secretKey);

System.out.println("String:" + encryptString);

/exit