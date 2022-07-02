pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('maven构建') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('远程部署') {
            steps{
                sshPublisher(publishers: [sshPublisherDesc(configName: 'tencentcloud', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'cd /root/sherly; sh run.sh restart', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/root/sherly', remoteDirectorySDF: false, removePrefix: 'sherly-upr-admin/target/', sourceFiles: 'sherly-upr-admin/target/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
             }
        }
    }
}
