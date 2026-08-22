kubectl -n rook-ceph delete --force CephBlockPool ceph-blockpool
kubectl -n rook-ceph delete --force CephFilesystem ceph-filesystem
kubectl -n rook-ceph delete --force CephFilesystemSubVolumeGroup ceph-filesystem-csi
kubectl -n rook-ceph delete --force CephObjectStore ceph-objectstore
kubectl delete crd cephblockpoolradosnamespaces.ceph.rook.io
kubectl delete crd cephblockpools.ceph.rook.io
kubectl delete crd cephbucketnotifications.ceph.rook.io
kubectl delete crd cephbuckettopics.ceph.rook.io
kubectl delete crd cephclients.ceph.rook.io
kubectl delete crd cephclusters.ceph.rook.io
kubectl delete crd cephcosidrivers.ceph.rook.io
kubectl delete crd cephfilesystemmirrors.ceph.rook.io
kubectl delete crd cephfilesystems.ceph.rook.io
kubectl delete crd cephfilesystemsubvolumegroups.ceph.rook.io
kubectl delete crd cephnfses.ceph.rook.io
kubectl delete crd cephnvmeofgateways.ceph.rook.io
kubectl delete crd cephobjectrealms.ceph.rook.io
kubectl delete crd cephobjectstoreaccounts.ceph.rook.io
kubectl delete crd cephobjectstores.ceph.rook.io
kubectl delete crd cephobjectstoreusers.ceph.rook.io
kubectl delete crd cephobjectzonegroups.ceph.rook.io
kubectl delete crd cephobjectzones.ceph.rook.io
kubectl delete crd cephrbdmirrors.ceph.rook.io
kubectl delete crd objectbucketclaims.objectbucket.io
kubectl delete crd objectbuckets.objectbucket.io
kubectl delete namespace rook-ceph