sudo apt-get install docker.io -y
sudo cp ../daemon.json /etc/docker/
sudo systemctl restart docker
#sudo cp ../daemon.json /var/snap/docker/current/config/