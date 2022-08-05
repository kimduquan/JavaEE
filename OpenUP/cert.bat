setlocal
call ./env.bat
keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -ext san=ip:127.0.0.1,dns:localhost -validity 365 -keypass changeit -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -exportcert -rfc -alias localhost -file dev.pem -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias localhost -file dev.pem -keypass changeit -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias accounts.google.com -file cert/accounts.google.com.cer -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias oauth2.googleapis.com -file cert/oauth2.googleapis.com.cer -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias openidconnect.googleapis.com -file cert/openidconnect.googleapis.com.cer -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias www.facebook.com -file cert/www.facebook.com.cer -keystore dev.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias www.googleapis.com -file cert/www.googleapis.com.cer -keystore dev.p12 -storepass changeit -storetype PKCS12
endlocal