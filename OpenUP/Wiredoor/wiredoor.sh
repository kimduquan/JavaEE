. ../env.sh
. ../frp/env.sh
echo "FRP_BIND_PORT=$FRP_BIND_PORT" >> .env
echo "FRP_KCP_BIND_PORT=$FRP_KCP_BIND_PORT" >> .env
echo "FRP_QUIC_BIND_PORT=$FRP_QUIC_BIND_PORT" >> .env
echo "FRP_TCP_MUX_HTTP_CONNECT_PORT=$FRP_TCP_MUX_HTTP_CONNECT_PORT" >> .env
git submodule update --init --recursive
cp .env docker-setup/.env
cd docker-setup
sudo docker compose up -d --wait
sudo docker compose ps wiredoor
cd ../
sudo docker compose up -d --wait