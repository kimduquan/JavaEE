del .\EPF-gateway.log.*
setlocal
call ../env.bat
call ../config.bat
call config.bat
call mvn quarkus:dev -Ddebug=5006
:: call mvn quarkus:dev -Ddebug=5006 -Djavax.net.debug=ssl,handshake
endlocal