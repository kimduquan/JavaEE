#echo "net.ipv4.ip_nonlocal_bind=1" | sudo tee -a /etc/sysctl.conf
#sudo sysctl -p
#sudo sysctl -w net.ipv4.ip_nonlocal_bind=1
sudo docker compose up -d
sudo docker exec -it gateway-wiredoor-gateway-1 sudo iptables -t nat -A PREROUTING -p tcp --dport 7000 -j DNAT --to-destination 10.0.0.1:7000
sudo docker exec -it gateway-wiredoor-gateway-1 sudo iptables -t nat -A PREROUTING -p udp --dport 7000 -j DNAT --to-destination 10.0.0.1:7000
sudo docker exec -it gateway-wiredoor-gateway-1 sudo iptables -t nat -A PREROUTING -p udp --dport 7002 -j DNAT --to-destination 10.0.0.1:7002
sudo docker exec -it gateway-wiredoor-gateway-1 sudo iptables -t nat -A PREROUTING -p tcp --dport 1337 -j DNAT --to-destination 10.0.0.1:1337
sudo docker exec -it gateway-wiredoor-gateway-1 sudo iptables -t nat -A POSTROUTING -o wg0 -j MASQUERADE