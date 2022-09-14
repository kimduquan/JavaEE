pipeline {
  agent any
  stages {
    stage('checkout') {
      steps {
        dir(path: 'OpenUP') {
          bat 'mvn clean package'
        }

      }
    }

  }
  environment {
    JAVA8_HOME = 'C:\\jdk8u345-b01'
    JAVA11_HOME = 'C:\\jdk-11.0.16.1+1'
    JAVA_HOME = 'C:\\jdk-11.0.16.1+1'
    PLUTO_HOME = 'C:\\pluto-3.1.1'
    KAFKA_HOME = 'C:\\kafka_2.13-3.2.1\\bin\\windows'
    WILDFLY_HOME = 'C:\\wildfly-24.0.1.Final\\bin'
    JAEGER_HOME = 'C:\\jaeger-1.36.0-windows-amd64'
    MAVEN_HOME = 'C:\\apache-maven-3.8.6\\bin'
  }
}