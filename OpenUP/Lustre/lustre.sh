git clone https://github.com/HewlettPackard/lustre-csi-driver.git .
git checkout master
cd charts
helm install lustre-csi-driver lustre-csi-driver/ --values lustre-csi-driver/values.yaml