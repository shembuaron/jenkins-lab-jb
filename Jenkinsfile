@Library('my-shared-library') _

pipeline {
    agent {
      node { label 'k8s-agent' }
    }
    
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
        stage('Archive Artifacts') {
            steps {
                sh 'touch flask_app.log'
                archiveArtifacts artifacts: 'flask_app.log', allowEmptyArchive: true
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
