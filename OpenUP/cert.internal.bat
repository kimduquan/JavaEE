setlocal
call ./env.bat
keytool -genkeypair -alias host.docker.internal -keyalg RSA -keysize 2048 -ext san=ip:127.0.0.1,dns:host.docker.internal -validity 365 -keypass changeit -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -exportcert -rfc -alias host.docker.internal -file host.docker.internal.pem -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias host.docker.internal -file host.docker.internal.pem -keypass changeit -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias accounts.google.com -file cert/accounts.google.com.cer -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias oauth2.googleapis.com -file cert/oauth2.googleapis.com.cer -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias openidconnect.googleapis.com -file cert/openidconnect.googleapis.com.cer -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias www.facebook.com -file cert/www.facebook.com.cer -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -alias www.googleapis.com -file cert/www.googleapis.com.cer -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
keytool -list -v -keystore host.docker.internal.p12 -storepass changeit -storetype PKCS12
endlocal