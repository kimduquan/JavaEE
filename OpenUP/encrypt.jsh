/env --class-path EPF-utility\target\EPF-utility-1.0.0.jar

import epf.util.security.KeyUtil;
import epf.util.security.CryptoUtil;
import java.util.Base64.Encoder;

String algorithm = System.getProperty("algorithm", "AES");
int keySize = Integer.valueOf(System.getProperty("keysize", "256"));
String string = System.getProperty("string", "");

var secretKey = KeyUtil.generateSecret(algorithm, keySize);

var bytes = string.getBytes("UTF-8");

var encrypt = CryptoUtil.encrypt(bytes, secretKey);

var encryptString = Base64.getUrlEncoder().encodeToString(encrypt);

var secretString = KeyUtil.encodeToString(secretKey);

System.out.println("SecretKey:" + secretString);
System.out.println("String:" + encryptString);

/exit