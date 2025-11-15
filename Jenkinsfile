pipeline {
    agent any

    tools {
        maven 'maven-3'
    }

    environment {
        // Sonar
        SONAR_HOST_URL = 'http://13.53.140.19:9000'
        SONAR_TOKEN    = credentials('sonar-token')

        // Docker Hub (Username with password / token)
        DOCKERHUB      = credentials('docker-hub-creds')

        // Image version – example 2024-11-14_12-34-56
        VERSION        = "${env.BUILD_ID}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/mcode123-commits/restaurantcatalog.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                      mvn sonar:sonar \
                        -Dsonar.projectKey=restaurantcatalog \
                        -Dsonar.host.url=${SONAR_HOST_URL} \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

stage('Check code coverage') {
    steps {
        script {
            def sonarQubeApiUrl = "${SONAR_HOST_URL}/api"
            def componentKey = "restaurantcatalog"

            def response = sh(
                script: """
                    curl -s -u "${SONAR_TOKEN}:" \
                      "${sonarQubeApiUrl}/measures/component?component=${componentKey}&metricKeys=coverage"
                """,
                returnStdout: true
            ).trim()

            echo "RAW Sonar API response: >>>${response}<<<"
        }
    }
}


        stage('Docker Build and Push') {
            steps {
                sh '''
                  echo "$DOCKERHUB_PSW" | docker login -u "$DOCKERHUB_USR" --password-stdin
                  docker build -t mankusmichal/restaurant-catalog:${VERSION} .
                  docker push mankusmichal/restaurant-catalog:${VERSION}
                '''
            }
        }

        stage('Cleanup Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Update Image Tag in GitOps') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/master']],
                    extensions: [],
                    userRemoteConfigs: [[
                        credentialsId: 'git-ssh',
                        url: 'git@github.com:mcode123-commits/deployment.git'
                    ]]
                ])

                script {
                    sh """
                      sed -i "s|image:.*|image: mankusmichal/restaurant-catalog:${VERSION}|" aws/restaurant-manifest.yml
                    """

                    sh '''
                      git config user.name "Jenkins"
                      git config user.email "jenkins@example.com"
                      git add .
                      git commit -m "Update image tag" || echo "No changes to commit"
                    '''

                    sshagent(credentials: ['git-ssh']) {
                        sh 'git push origin master'
                    }
                }
            }
        }
    }
}
