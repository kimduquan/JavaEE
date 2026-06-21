helm repo add rustfs https://charts.rustfs.com
helm upgrade --install rustfs rustfs/rustfs -f values-rustfs.yaml --wait