@Library('my-shared-library') _

pipeline {
    agent any
    
    environment {
        BRANCH_NAME = "develop"
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
    }

    post {
        always {
            script {
                myLibrary.cleanup()
            }
        }
    }
}
