#echo "net.ipv4.ip_nonlocal_bind=1" | sudo tee -a /etc/sysctl.conf
#sudo sysctl -p
sudo sysctl -w net.ipv4.ip_nonlocal_bind=1
sudo docker compose up -d