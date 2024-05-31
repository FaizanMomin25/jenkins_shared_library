pipeline {
    agent any 
    environment {
        registryURI = "https://registry.hub.docker.com/"
        registry = "faizanmomin2508/faizan_cloudethix_nginx"
        registryCredential = '01_docker_Hub_creds'
        }
stages {
        stage('Building image from project dir from shared library') {
            environment {
                registry_endpoint = "${env.registryURI}" + "${env.registry}"
                tag_commit_id     = "${env.registry}" + ":$GIT_COMMIT"
            }
            steps{
                script {
                def app = docker.build(tag_commit_id)
                docker.withRegistry( registry_endpoint, registryCredential ) {
                app.push()
                }
            }
        }
    }

        stage('Remove Unused docker image from shared library') {
            steps{
                sh "docker rmi $registry:$GIT_COMMIT"
                }
        }
    }
    post { 
        always { 
            echo 'Deleting Workspace Dir from shared library'
            deleteDir() /* clean up our workspace */
        }
    }
}
