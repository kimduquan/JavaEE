cd EPF-agent-webapp
git clean -f -d
./build.sh

cd webapp/agent
git clean -f -d
./build.sh
cd ../../../

cd EPF-cache
git clean -f -d
./build.sh
cd ../

cd EPF-concurrent
git clean -f -d
./build.sh
cd ../

cd EPF-config
git clean -f -d
./build.sh
cd ../

cd EPF-event
git clean -f -d
./build.sh
cd ../

cd EPF-gateway
git clean -f -d
./build.sh
cd ../

cd EPF-management
git clean -f -d
./build.sh
cd ../

cd EPF-mcp-gateway
git clean -f -d
./build.sh
cd ../

cd EPF-messaging
git clean -f -d
./build.sh
cd ../

cd EPF-net
git clean -f -d
./build.sh
cd ../

cd EPF-persistence
git clean -f -d
./build.sh
cd ../

cd EPF-query
git clean -f -d
./build.sh
cd ../

cd EPF-registry
git clean -f -d
./build.sh
cd ../

cd EPF-transaction
git clean -f -d
./build.sh
cd ../

cd EPF-workflow-management
git clean -f -d
./build.sh
cd ../

cd EPF-workflow-v1
git clean -f -d
./build.sh
cd ../