. ../env.sh ../
$kubectl kustomize https://github.com/kubernetes-csi/external-snapshotter/client/config/crd | $kubectl create -f -
$kubectl -n kube-system kustomize https://github.com/kubernetes-csi/external-snapshotter/deploy/kubernetes/snapshot-controller | $kubectl create -f -
$kubectl kustomize https://github.com/kubernetes-csi/external-snapshotter/deploy/kubernetes/csi-snapshotter | $kubectl create -f -