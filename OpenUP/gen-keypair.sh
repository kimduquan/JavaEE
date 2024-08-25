. ./env.sh
rm dev.p12
rm dev.pem
export JAVA_HOME=$JAVA11_HOME
export PATH=$PATH:$JAVA_HOME\bin
jshell ./gen-keypair.jsh