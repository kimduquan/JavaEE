:: keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore dev.p12 -validity 365 -storepass changeit -ext san=dns:localhost
keytool -importcert -noprompt -trustcacerts -alias accounts.google.com -file cert/accounts.google.com.cer -keystore dev.p12 -storepass changeit
keytool -importcert -noprompt -trustcacerts -alias oauth2.googleapis.com -file cert/oauth2.googleapis.com.cer -keystore dev.p12 -storepass changeit
keytool -importcert -noprompt -trustcacerts -alias openidconnect.googleapis.com -file cert/openidconnect.googleapis.com.cer -keystore dev.p12 -storepass changeit
keytool -importcert -noprompt -trustcacerts -alias www.facebook.com -file cert/www.facebook.com.cer -keystore dev.p12 -storepass changeit
keytool -importcert -noprompt -trustcacerts -alias www.googleapis.com -file cert/www.googleapis.com.cer -keystore dev.p12 -storepass changeit