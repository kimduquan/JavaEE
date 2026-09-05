sync && sudo sysctl -w vm.drop_caches=3
cd MicroK8s
./start.sh
cd ../