pipeline {
    agent any
    stages {
        stage('Example') {
            steps {
                script {
                    getPlatformName()
                }
            }
        }
    }
}

def getPlatformName() {
    return config.platform
}