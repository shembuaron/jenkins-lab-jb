@Library('my-shared-library') _

pipeline {
    agent any
    
    environment {
        BRANCH_NAME = 'develop'
    }
    
    stages {
        stage('Build') {
            steps {
                script {
                    myLibrary.buildApp()
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    myLibrary.deployApp(env.BRANCH_NAME)
                }
            }
        }
        stage('Approve Deployment') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def userInput = input message: 'FOR QA: Do you approve of the deployment? (<ip>:5001/register)',
                                        parameters: [choice(name: 'Proceed', choices: 'Proceed\nAbort', description: 'Choose an option')]
                        env.USER_INPUT = userInput
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                myLibrary.cleanup()
            }
        }
        success {
            echo "Deployment successful"
        }
        failure {
            echo "Deployment failed. Logging..."
        }
    }
}


# Doesn't work
pipeline {
    agent any

    environment {
        IMAGE_NAME = 'app-generator:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/app-generator/app-generator.git'
            }
        }

        stage('Lint & Static Analysis') {
            steps {
                sh '''
                    python3 -m venv venv
                    . venv/bin/activate
                    pip install --upgrade pip
                    pip install flake8
                    [ -f requirements.txt ] && pip install -r requirements.txt || echo "No requirements.txt found"
                    flake8 . --count --select=E9,F63,F7,F82 --show-source --statistics
                '''
            }
        }

        stage('Execute Tests') {
            steps {
                sh '''
                    . venv/bin/activate
                    pip install pytest
                    pytest app-generator/
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build app-generator/ -t ${IMAGE_NAME}"
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace and local Docker images...'
            sh "docker rmi ${IMAGE_NAME}"
        }
        success {
            echo 'Pipeline Succeeded!'
        }
        failure {
            echo 'Pipeline Failed!'
        }
    }
}
