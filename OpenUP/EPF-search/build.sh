. ../env.sh
docker build -t epf/epf-search:1.0.0 .
mvn clean package -U
helm install epf-search target/helm/kubernetes/epf-search --wait