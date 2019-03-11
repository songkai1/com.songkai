pipeline {
    agent any

    parameters {
        choice(name:'buildMoudle',choices:['web'],description:'请选择要构建的子模块')

        choice(name:'profile',choices:['test','production'],description:'请选择要构建的环境配置')

        choice(name:'toServer',choices:['10.3.22.43'],description:'请选择要目标服务器')

        choice(name:'operateType',choices:['deploy','start','stop','restart'],description:'请选择要执行的操作类型')
    }

    options {
        skipDefaultCheckout()
        timeout(time: 1, unit: 'HOURS')
    }

    stages {
        stage('print-choice') {
            steps {
                echo "打包模块：【${params.buildMoudle}】,环境：【${params.profile}】,目标服务器：【${params.toServer}】,操作类型：【${params.operateType}】"
            }
        }

        stage('checkout') {
            steps {
                checkout scm
            }
        }

        stage('install-common') {
            when {
                equals expected: 'deploy', actual: params.operateType
            }
            steps {
                script {
                    withMaven(maven: 'maven',jdk: 'jdk') {
                        sh 'mvn clean install -pl ./ie-common -am -DskipTests'
                    }
                }
            }
        }

        stage('build') {
            when {
                equals expected: 'deploy', actual: params.operateType
            }
            steps {
                script {
                    withMaven(maven: 'maven',jdk: 'jdk') {
                        echo 'building...'
                        sh "mvn clean package -DskipTests -f ./ie-${params.buildMoudle}/pom.xml"
                        echo 'building finished.'
                    }
                }
            }
        }

        stage('deploy') {
            when {
                equals expected: 'deploy', actual: params.operateType
                expression {
                    currentBuild.result == null || currentBuild.result == 'SUCCESS'
                }
            }
            parallel {
                stage('test-deploy') {
                    when {
                        equals expected: 'test', actual: params.profile
                        equals expected: '10.3.54.69', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.3.54.69 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar pixiu@10.3.54.69:/JavaWeb/pixiu.wltest.com/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh pixiu@10.3.54.69 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/pixiu.wltest.com/oci"
                    }
                }

                stage('web-115-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'web', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.115 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.115:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('web-116-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'web', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.116 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.116:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('api-115-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'api', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.115 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.115:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('api-116-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'api', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.116 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.116:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('webservice-115-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'webservice', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.115 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.115:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('webservice-116-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'webservice', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.116 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.116:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('schedule-116-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'schedule', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.116 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.116:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('mq-115-prod-deploy') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'mq', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo copy ie-${params.buildMoudle}.jar to 10.230.248.115 ..."
                        sh "scp ./ie-${params.buildMoudle}/target/ie-${params.buildMoudle}.jar root@10.230.248.115:/JavaWeb/oci/${params.buildMoudle}_temp.jar"
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh deploy ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }


            }

        }

        stage('other') {
            when {
                expression {
                    params.operateType ==~ /(start|stop|restart)/
                }
            }
            parallel {
                stage('test-other') {
                    when {
                        equals expected: 'test', actual: params.profile
                        equals expected: '10.3.54.69', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.3.54.69 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh pixiu@10.3.54.69 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/pixiu.wltest.com/oci"
                    }
                }

                stage('web-115-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'web', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.115 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('web-116-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'web', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.116 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('api-115-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'api', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.115 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('api-116-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'api', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.116 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('webservice-115-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'webservice', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.115 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('webservice-116-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'webservice', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.116 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('schedule-116-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'schedule', actual: params.buildMoudle
                        equals expected: '10.230.248.116', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.116 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.116 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }
                stage('mq-115-prod-other') {
                    when {
                        equals expected: 'prod', actual: params.profile
                        equals expected: 'mq', actual: params.buildMoudle
                        equals expected: '10.230.248.115', actual: params.toServer
                    }
                    steps {
                        sh "echo ${params.operateType} project on 10.230.248.115 ..."
                        sh "chmod +x ./deploy.sh"
                        sh "ssh root@10.230.248.115 bash -s < ./deploy.sh ${params.operateType} ${params.buildMoudle} ${params.profile} /JavaWeb/oci"
                    }
                }

            }
        }

    }
    post {
        always {
            script {
                wrap([$class: 'BuildUser']) {
                    emailext (
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                        subject: "【Jenkins-" + '${BUILD_STATUS}' + "】${params.buildMoudle}[${params.profile}] ${params.operateType} on ${params.toServer}",
                        mimeType: 'text/html',
                        body: "操作人ID：【${BUILD_USER_ID}】 ,操作人姓名：【${BUILD_USER}】" + '<br/>${FILE,path="email.html"}',
                        attachLog: true,
                        from: 'rfd@rufengda.com',
                        to: 'xjw-java.list@itiaoling.com'
                    )
                }
            }
            deleteDir()
        }
    }
}