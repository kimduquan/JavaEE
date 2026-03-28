. ../env.sh
mvn clean package -U
helm install chrome-devtools-mcp target/helm/kubernetes/chrome-devtools-mcp --wait