curl -L https://github.com/kubernetes-sigs/cluster-api/releases/download/v1.13.4/clusterctl-linux-amd64 -o clusterctl
sudo install -o root -g root -m 0755 clusterctl /usr/local/bin/clusterctl
clusterctl version
./capd/capd.sh