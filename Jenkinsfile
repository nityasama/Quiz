pipeline {
    agent any

  environment {
    DOCKERHUB_CREDENTIALS = 'dockerhub-credentials'
    IMAGE_NAME = 'nityasama/quiz-app'
  }

  stages {
    stage('Checkout') {
      steps {
        git 'https://github.com/nityasama/Quiz.git'  // Your repo URL
      }
    }

    stage('Build') {
      steps {
        script {
          // Run Maven build inside the Maven container with volume mounted for caching
          docker.image('maven:3.8.7-openjdk-17').inside("-v $HOME/.m2:/root/.m2") {
            sh 'mvn clean package -DskipTests'
          }
        }
      }
    }

    stage('Test') {
      steps {
        script {
          docker.image('maven:3.8.7-openjdk-17').inside("-v $HOME/.m2:/root/.m2") {
            sh 'mvn test'
          }
        }
      }
    }

    stage('Docker Build and Push') {
      steps {
        script {
          def dockerImage = docker.build("${IMAGE_NAME}:${BUILD_NUMBER}")
          docker.withRegistry('https://registry.hub.docker.com', DOCKERHUB_CREDENTIALS) {
            dockerImage.push()
          }
        }
      }
    }
  }

  post {
    success {
      echo 'Build, test, and Docker image push succeeded!'
    }
    failure {
      echo 'Pipeline failed. Check console output for errors.'
    }
  }
}
