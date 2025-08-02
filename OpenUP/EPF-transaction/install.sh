rm -R -d ObjectStore
. ../env.sh
. ../native_env.sh
mvn clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
helm install epf-transaction ./target/helm/kubernetes/epf-transaction