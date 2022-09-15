call kubectl apply -f https://raw.githubusercontent.com/jenkinsci/kubernetes-operator/master/config/crd/bases/jenkins.io_jenkins.yaml
call kubectl apply -f https://raw.githubusercontent.com/jenkinsci/kubernetes-operator/master/deploy/all-in-one-v1alpha2.yaml
call kubectl create namespace jenkins
call helm repo add jenkins https://raw.githubusercontent.com/jenkinsci/kubernetes-operator/master/chart
call helm install jenkins jenkins/jenkins-operator -n jenkins