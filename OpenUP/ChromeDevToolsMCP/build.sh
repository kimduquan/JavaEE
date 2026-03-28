. ../env.sh
docker build -t epf/chrome-devtools-mcp:1.0.0 .
mvn clean package -U
#helm install chrome-devtools-mcp target/helm/kubernetes/chrome-devtools-mcp --wait