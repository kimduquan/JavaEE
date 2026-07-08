helm repo add rustfs https://charts.rustfs.com --force-update
kubectl create secret generic rustfs --from-literal=RUSTFS_ACCESS_KEY='85da6aac2aba19a6dbf66034d199af599616644578306c8f7116e86f4fb0a7a3' --from-literal=RUSTFS_SECRET_KEY='064f13d6b689a11459949e5d5259158230eed290780de054b50c60e1d3c130fd'
kubectl delete -f ingress.yaml
helm upgrade --install rustfs rustfs/rustfs -f values-rustfs.yaml --wait
kubectl apply -f ingress.yaml