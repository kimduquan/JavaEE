pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        dir(path: 'OpenUP') {
          sh 'mvn clean install -U'
        }

      }
    }

  }
}