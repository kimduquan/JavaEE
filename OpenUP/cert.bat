keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore dev.p12 -validity 365 -storepass changeit -ext san=dns:localhost
keytool -export -keystore dev.p12 -alias localhost -storepass changeit -file dev.crt