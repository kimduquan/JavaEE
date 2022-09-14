pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        dir(path: 'OpenUP') {
          bat(script: 'mvn clean', returnStatus: true, returnStdout: true)
        }

      }
    }

  }
}