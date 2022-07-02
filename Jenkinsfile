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
        stage('远程ssh传输并启动jar') {
            steps{
                sshPublisher(publishers: [sshPublisherDesc(configName: 'tencentcloud', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'cd /root/sherly-test; sh run.sh restart', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: 'sherly-upr-admin/target/', sourceFiles: 'sherly-upr-admin/target/*.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
             }
        }
    }
}
