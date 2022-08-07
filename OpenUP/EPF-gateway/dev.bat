del .\EPF-gateway.log.*
setlocal
call ../env.bat
call ../config.bat
call config.bat
java -version
call mvn quarkus:dev -Ddebug=5006 -Djavax.net.debug=ssl,handshake
:: call mvn quarkus:dev -Ddebug=5006 -Djavax.net.debug=ssl,handshake
endlocal