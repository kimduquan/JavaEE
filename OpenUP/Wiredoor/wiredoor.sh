git submodule update --init --recursive
cp .env docker-setup/.env
cd docker-setup
sudo docker compose up -d
sudo docker compose ps wiredoor
cd ../
sudo docker compose up -d