pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        checkout([$class: 'GitSCM', 
                branches: [[name: '*/native']])
      }
    }

  }
}