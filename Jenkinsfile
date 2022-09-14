pipeline {
  agent any
  stages {
    stage('package') {
      steps {
        dir(path: 'OpenUP') {
          bat(returnStatus: true, returnStdout: true, script: 'mvn clean package')
        }

      }
    }

  }
}