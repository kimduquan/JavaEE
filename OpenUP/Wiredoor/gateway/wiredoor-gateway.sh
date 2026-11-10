../docker.sh
. ../../env.sh ../../
. ../../frp/env.sh
echo "WIREDOOR_URL=https://$PUBLIC_IP" >> .env
echo "GATEWAY_IP=$GATEWAY_IP" >> .env
echo "FRP_BIND_PORT=$FRP_BIND_PORT" >> .env
echo "FRP_KCP_BIND_PORT=$FRP_KCP_BIND_PORT" >> .env
echo "FRP_QUIC_BIND_PORT=$FRP_QUIC_BIND_PORT" >> .env
echo "FRP_TCP_MUX_HTTP_CONNECT_PORT=$FRP_TCP_MUX_HTTP_CONNECT_PORT" >> .env
sudo docker compose up -d --wait
./start.sh