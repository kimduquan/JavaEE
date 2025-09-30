#sudo zgenhostid -f -o /etc/hostid
#sudo dnf config-manager --add-repo https://downloads.whamcloud.com/public/lustre/latest-release/el8.10/server
#sudo dnf config-manager --add-repo https://downloads.whamcloud.com/public/e2fsprogs/latest/el8
#sudo dnf install --enablerepo=downloads.whamcloud.com_public_lustre_latest-release_el8.10_server --enablerepo=downloads.whamcloud.com_public_e2fsprogs_latest_el8 --nogpgcheck kernel
#sudo dnf install --nogpgcheck http://download.zfsonlinux.org/epel/8/kmod/x86_64/kmod-zfs-2.2.8-1.el8.x86_64.rpm
#sudo dnf install --enablerepo=downloads.whamcloud.com_public_lustre_latest-release_el8.10_server --enablerepo=downloads.whamcloud.com_public_e2fsprogs_latest_el8 --nogpgcheck kmod-lustre kmod-lustre-osd-zfs e2fsprogs
git clone https://github.com/HewlettPackard/lustre-csi-driver.git .
git checkout master
cd charts
helm install lustre-csi-driver lustre-csi-driver/ --values lustre-csi-driver/values.yaml
