pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                git branch: 'main', poll: false, url: 'https://github.com/qadojo/pact-demo-consumer'
                sh 'echo "hi there"'
            }
        }
    }
}
