. ../../env.sh
. env.sh
echo "FRP_BIND_PORT=$FRP_BIND_PORT" >> .env
echo "FRP_KCP_BIND_PORT=$FRP_KCP_BIND_PORT" >> .env
echo "FRP_QUIC_BIND_PORT=$FRP_QUIC_BIND_PORT" >> .env
echo "FRP_TCP_MUX_HTTP_CONNECT_PORT=$FRP_TCP_MUX_HTTP_CONNECT_PORT" >> .env
WIREDOOR_GATEWAY_CONTAINER="gateway-wiredoor-cli-1"
sudo docker compose up -d --wait
sudo docker exec -it $WIREDOOR_GATEWAY_CONTAINER sudo iptables -t nat -A PREROUTING -p tcp --dport $FRP_BIND_PORT -j DNAT --to-destination "10.0.0.1:$FRP_BIND_PORT"
sudo docker exec -it $WIREDOOR_GATEWAY_CONTAINER sudo iptables -t nat -A PREROUTING -p udp --dport $FRP_KCP_BIND_PORT -j DNAT --to-destination "10.0.0.1:$FRP_KCP_BIND_PORT"
sudo docker exec -it $WIREDOOR_GATEWAY_CONTAINER sudo iptables -t nat -A PREROUTING -p udp --dport $FRP_QUIC_BIND_PORT -j DNAT --to-destination "10.0.0.1:$FRP_QUIC_BIND_PORT"
sudo docker exec -it $WIREDOOR_GATEWAY_CONTAINER sudo iptables -t nat -A PREROUTING -p tcp --dport $FRP_TCP_MUX_HTTP_CONNECT_PORT -j DNAT --to-destination "10.0.0.1:$FRP_TCP_MUX_HTTP_CONNECT_PORT"
sudo docker exec -it $WIREDOOR_GATEWAY_CONTAINER sudo iptables -t nat -A POSTROUTING -o wg0 -j MASQUERADE