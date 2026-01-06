mkdir work
docker run -d -e JUPYTER_PASSWORD="123456" -p 8888:8888 -p 2222:22  -v ./work:/workspace/work --gpus all unsloth/unsloth