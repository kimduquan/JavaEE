if [ "$K8S_ENV" = "k8s" ]; then
    sudo snap install docker
    sudo cp ../daemon.json /var/snap/docker/current/config/
    sudo snap restart docker
else
    sudo apt-get install docker.io -y
    sudo cp ../daemon.json /etc/docker/
    sudo systemctl restart docker
fi