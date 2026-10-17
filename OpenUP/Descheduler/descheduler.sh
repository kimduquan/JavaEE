helm repo add descheduler https://kubernetes-sigs.github.io/descheduler/
helm install --upgrade --wait descheduler --namespace kube-system descheduler/descheduler --version 0.37.0