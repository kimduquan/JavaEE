#VERSION="$(curl --silent https://storage.googleapis.com/kubevirt-prow/release/kubevirt/kubevirt/stable.txt)"
#kubectl create --filename="https://github.com/kubevirt/kubevirt/releases/download/${VERSION}/kubevirt-operator.yaml"
#kubectl create --filename="https://github.com/kubevirt/kubevirt/releases/download/${VERSION}/kubevirt-cr.yaml"
kubectl create -f kubevirt-operator.yaml
kubectl create -f kubevirt-cr.yaml