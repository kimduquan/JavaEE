call ./shutdown.bat
call ./clean.bat
call ./startupc.bat
call ./compile.bat
cd EPF-transaction-internal
call ./dev.bat
cd ../
cd EPF-transaction
call ./dev.bat
cd ../
cd EPF-persistence
call ./dev.bat
cd ../
cd EPF-gateway
call ./dev.bat
cd ../
call ./install.bat &
cd EPF-shell
call ./dev.bat
cd ../
cd EPF-tests
call ./dev.bat