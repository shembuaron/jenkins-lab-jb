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
