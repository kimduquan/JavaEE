pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        git(changelog: true, poll: true, branch: 'native', url: 'https://github.com/kimduquan/JavaEE.git', credentialsId: 'Github_PAT')
      }
    }

  }
}