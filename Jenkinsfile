pipeline {
  agent any
  stages {
    stage('dev') {
      steps {
        dir(path: 'OpenUP') {
          bat(script: 'dev.bat', returnStatus: true, returnStdout: true)
        }

      }
    }

  }
}