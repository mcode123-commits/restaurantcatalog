pipeline {
    agent any

    tools {
        maven 'maven-3'
    }

    environment {
        SONAR_HOST_URL = 'http://13.53.140.19:9000'
        SONAR_TOKEN    = credentials('sonar-token')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/mcode123-commits/restaurantcatalog.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                      mvn sonar:sonar \
                        -Dsonar.projectKey=fullstack \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }
    }
}
