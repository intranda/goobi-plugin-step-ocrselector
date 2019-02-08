pipeline {
  agent {
    docker {
      image 'maven:3-jdk-8'
      args '-v $HOME/.m2/root/.m2'
    }
  }

  stages {
    stage('build') {
      steps {
        sh 'mvn -f goobi-plugin-step-ocrselector/pom.xml install'
      }
    }
  }
  post {
    success {
      archiveArtifacts artifacts: 'goobi-plugin-step-ocrselector/target/plugin_intranda_step_ocrselector.jar', fingerprint:
      true
    }
  }
}
