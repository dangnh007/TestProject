@Library('acadiaBuildTools@develop') _

import com.vibrenthealth.jenkinsLibrary.VibrentConstants
import com.vibrenthealth.jenkinsLibrary.Utils

def project = "PMTAutomationFramework"
def branch
def branchType
def pullRequest
def branchCheckout
def refspecs
def resultsHomeDir
def commitSha
def testAgainst = "develop"
def testAgainstRaw = testAgainst
def containerName = "vibrent/automation-framework"
def uuid = UUID.randomUUID().toString()
def podName = "${project}-${env.BRANCH_NAME.replaceAll(/\//, '-')}-${env.BUILD_NUMBER}"
def podLabel = podName
def pullSecrets = ['reg.vibrenthealth.com', 'dockergroup.vibrenthealth.com']
podTemplate(
        name: podName,
        cloud: 'default',
        label: podLabel,
        imagePullSecrets: pullSecrets,
    containers: kubeUtils.getCiContainers(containerList: ['maven', 'python', 'kubectl', 'helm', 'docker', 'docker-api', 'docker-chrome', 'aws-cli-api', 'aws-cli-chrome', 'android-gradle', 'ansible-playbook']),
        volumes: [
                hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
                hostPathVolume(mountPath: '/root/.m2', hostPath: '/data/m2repo')
        ],
        idleTimeout: 30
) {
    node(podLabel) {
        def utils = new Utils(this)
        branch = env.BRANCH_NAME.replaceAll(/\//, "-")
        branchType = env.BRANCH_NAME.split(/\//)[0]
        env.PROJECT = project
        stackNameRegex = "[^A-Za-z0-9-]"
        stackName = "${env.PROJECT}-$branch-${env.BUILD_NUMBER}".replaceAll(/(feature-|release-)/, "").replaceAll(stackNameRegex, "-").toLowerCase().take(60)
        pullRequest = env.CHANGE_ID
        resultsHomeDir = "${env.WORKSPACE.minus('/home/jenkins/')}"
        if (pullRequest) {
            branchCheckout = "pr/${pullRequest}"
            refspecs = '+refs/pull/*/head:refs/remotes/origin/pr/*'
            testAgainstRaw = env.CHANGE_TARGET
            testAgainst = testAgainstRaw.replaceAll(/\//, "-")
        } else {
            if (branchType == 'release') {
                testAgainstRaw = env.BRANCH_NAME
                testAgainst = branch
            }
            branchCheckout = env.BRANCH_NAME
            refspecs = '+refs/heads/*:refs/remotes/origin/*'
        }
        ansiColor('xterm') {
            def containers = [
                    [
                            "name"            : "${containerName}-default",
                            "pathToDockerfile": "src/main/resources/docker/default/Dockerfile"

                    ],
                    [
                            "name"            : "${containerName}-chrome",
                            "pathToDockerfile": "src/main/resources/docker/chrome/Dockerfile"
//                    ],
//                    [
//                            "name"            : "${containerName}-android",
//                            "pathToDockerfile": "src/main/resources/docker/android/Dockerfile"
//                    ],
//                    [
//                            "name"            : "${containerName}-iphone",
//                            "pathToDockerfile": "src/main/resources/docker/iphone/Dockerfile"
                    ]
            ]
            def artifacts = [
                    [
                            "name"    : "PMT-Automation-Framework",
                            "path"    : "target/PMT-Automation-Framework-0.0.1-SNAPSHOT.jar",
                            "group"   : "com.vibrent.health",
                            "artifact": "PMT-Automation-Framework",
                            "version" : "1.0"]
            ]
            def versions = [env.PROJECT]
            ciPipeline(
                    project: env.PROJECT,
                    alwaysPublish: true,
                    ciImages: containers,
                    ciArtifacts: artifacts,
                    versionedArtifacts: versions,
                    testTimeout: 60,
                    checkout: {
                        stage("Checkout") {
                            cleanWs()
                            checkout([
                                    $class           : 'GitSCM',
                                    branches         : [[name: "*/${branchCheckout}"]],
                                    userRemoteConfigs: [[
                                                                credentialsId: 'e08f3fab-ba06-459b-bebb-5d7df5f683a3',
                                                                url          : 'git@github.com:VibrentHealth/PMTAutomationFramework.git',
                                                                refspec      : "${refspecs}"
                                                        ]]
                            ])
                            commitSha = utils.getShortCommitSha()
                        }
                    },
                    build: { buildStage ->
                        parallel(
                                "Setup Credentials": {
                                    withCredentials([
                                            usernamePassword(
                                                    credentialsId: 'jira-user',
                                                    usernameVariable: 'jirausername',
                                                    passwordVariable: 'jirapassword'
                                            ),
                                            usernamePassword(
                                                    credentialsId: 'vbr-jenkins',
                                                    usernameVariable: 'nexususername',
                                                    passwordVariable: 'nexuspassword'
                                            ),
                                            usernamePassword(
                                                    credentialsId: 'vibrentqa3',
                                                    usernameVariable: 'gmailusername',
                                                    passwordVariable: 'gmailpassword'
                                            ),
                                            [$class           : 'AmazonWebServicesCredentialsBinding',
                                             credentialsId    : 'SESMailTrapS3Credentials',
                                             accessKeyVariable: 'S3_ACCESS_KEY_ID',
                                             secretKeyVariable: 'S3_SECRET_ACCESS_KEY'
                                            ],
                                            string(
                                                    credentialsId: 'SESS3MailTrapBucketName',
                                                    variable: 'S3_BUCKET'
                                            ),
                                            usernamePassword(
                                                    credentialsId: 'VibrentHealthTestAutomationEmail',
                                                    usernameVariable: 'EMAIL_USERNAME',
                                                    passwordVariable: 'EMAIL_PASSWORD'
                                            )
                                    ]) {
                                        buildStage('Setup JIRA Creds') {
                                            sh "sed -i 's/<jira.username></<jira.username>${env.jirausername}</' pom.xml"
                                            sh "sed -i 's/<jira.password></<jira.password>${env.jirapassword}</' pom.xml"
                                        }
                                        buildStage('Setup Email Creds') {
                                            sh "sed -i 's/<email.username></<email.username>${env.EMAIL_USERNAME}</' pom.xml"
                                            sh "sed -i 's/<email.password></<email.password>${env.EMAIL_PASSWORD}</' pom.xml"
                                        }
                                        buildStage('Setup S3 Creds') {
                                            sh "sed -i 's/<s3access></<s3access>${env.S3_ACCESS_KEY_ID}</' pom.xml"
                                            sh "sed -i 's/<s3secret></<s3secret>${env.S3_SECRET_ACCESS_KEY}</' pom.xml"
                                            sh "sed -i 's/<s3bucket></<s3bucket>${env.S3_BUCKET}</' pom.xml"
                                        }
                                        buildStage('Setup Gmail Creds') {
                                            sh "sed -i 's/gmail.password=/gmail.password=${env.gmailpassword}/' src/test/resources/program.properties"
                                        }
                                    }
                                }
                                ,
                                "StandUp Application": {
                                    helmDeploy(
                                            gitBranch: testAgainstRaw,
                                            stackName: "${stackName}",
                                            missionControlEnabled: true,
                                            chartsRepo: "devcharts",
                                            stageFunc: buildStage
                                    )
                                                    // prevent db pods from being evicted while running tests
                                    container('kubectl') {
                                        sh "kubectl annotate pods -n ${stackName} --all cluster-autoscaler.kubernetes.io/safe-to-evict=false"
                                    }
                                },
//                                "Build Android Application": {
//                                        buildMobileApplication.android(
//                                                stackName: stackName,
//                                                appBranch: testAgainst,
//                                                buildNumber: "1.0.${env.BUILD_NUMBER}",
//                                                sauce: true
//                                        )
//                                    },
//                                "Build iOS Application": {
//                                    buildMobileApplication.iOS(
//                                            stackName: stackName,
//                                            appVersion: testAgainst,
//                                            buildNumber: "1.0.${env.BUILD_NUMBER}",
//                                            sauce: true
//                                    )
//                                },
                                "Quick Run Of Sonar": {
                                    if (branch != 'develop' && branch != 'master' && branchType != 'release' && branchType != 'config') {
                                        container('maven') {
                                            sh "mvn clean compile"
                                        }
                                        runSonarAnalysis(
                                                pullRequest: pullRequest,
                                                tool: 'mvn',
                                                sonarBranch: env.BRANCH_NAME,
                                                branchName: branch,
                                                branchType: branchType,
                                                project: env.PROJECT,
                                                projectVersion: "1.0.${env.BUILD_NUMBER}",
                                                stageFunc: buildStage
                                        )
                                    }
                                }
                        )
                    },
                    unitTest: { failableStage ->
                        parallel(
                                "Unit Test": {
                                    container('maven') {
                                        sh "mvn clean package -Dautomation.url.sub=https://sub-${stackName}.qak8s.vibrenthealth.com -Dautomation.url.sub=https://admin-${stackName}.qak8s.vibrenthealth.com"
                                        sh "cat target/coverage-reports/jacoco-ut.exec >> jacoco-ut.exec;"
                                        sh "mkdir -p results/unit/"
                                        sh "cp -r target results/unit/"
                                    }
                                },
                                "Integration Test": {
                                    container('maven') {
                                        sh "mvn clean test -Dalt.build.dir=int -Dsurefire.includes='**/*Test.java' -Dautomation.url.sub=https://sub-${stackName}.qak8s.vibrenthealth.com -Dautomation.url.sub=https://admin-${stackName}.qak8s.vibrenthealth.com"
                                        sh "cat int/coverage-reports/jacoco-ut.exec >> jacoco-it.exec;"
                                        sh "mkdir -p results/integration/"
                                        sh "cp -r int results/integration/"
                                    }
                                }
                        )
                    },
                    sonar: { failableStage ->
                        runSonarAnalysis(
                                pullRequest: pullRequest,
                                tool: 'mvn',
                                sonarBranch: env.BRANCH_NAME,
                                branchName: branch,
                                branchType: branchType,
                                project: env.PROJECT,
                                projectVersion: "1.0.${env.BUILD_NUMBER}",
                                unitTestPaths: 'results/unit/target/surefire-reports,results/integration/target/surefire-reports,results/default/target/failsafe-reports,results/chrome/target/failsafe-reports',
                                jacocoUTPath: 'jacoco-ut.exec',
                                jacocoITPath: 'jacoco-it.exec',
                                stageFunc: failableStage
                        )
                    },
                    deploy: {},
                    test: { failableStage ->
                                runDockerTests(
                                        testBranch: branch,
                                        registry: VibrentConstants.CIREG_REGISTRY,
                                        buildNumber: env.BUILD_NUMBER,
                                        framework: "PMTAutomationFramework",
                                        other: "-Dautomation.url.mc=https://missioncontrol-${stackName}.qak8s.vibrenthealth.com",
                                        platforms: [
                                                [
                                                        name       : 'api',
                                                        container  : "${containerName}-default",
                                                        tags       : '@api',
                                                        defaultWait: 15,
                                                        threads    : 1
                                                ],
                                                [
                                                        name       : 'chrome',
                                                        container  : "${containerName}-chrome",
                                                        tags       : '~@api --tags @smoke',
                                                        defaultWait: 15,
                                                        threads    : 1
//                                                    ],
//                                                    [
//                                                            name       : 'android',
//                                                            tags       : '~@api --tags @smoke',
//                                                            defaultWait: 30,
//                                                            threads    : 2,
//                                                            hub        : true,
//                                                            app        : "${stackName}.apk"
//                                                    ],
//                                                    [
//                                                            name       : 'iphone',
//                                                            tags       : '~@api --tags @smoke',
//                                                            defaultWait: 30,
//                                                            threads    : 2,
//                                                            hub        : true,
//                                                            app        : "${stackName}.zip"
                                                ]
                                        ],
                                        stageFunc: failableStage
                        )
                    },
                    publish: { failableStage ->
                        if (branchCheckout == "develop" || branchType == "release") {
                            build job: 'Gherkin Parser autotest', parameters: [string(name: 'Branch', value: branchCheckout)]
                        }
                        sshagent(['e08f3fab-ba06-459b-bebb-5d7df5f683a3']) {
                            // tag our code with build number
                            sh "git tag -f 1.0.${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
                            sh "git push -f --tags"
                        }
                        helmUtils.updateDependenciesInUmbrella(
                            chartRepoBranch: env.BRANCH_NAME,
                            umbrellaCharts: ['acadia'], fromProject: env.PROJECT, fromBuildNumber: "${branch}-${env.BUILD_NUMBER}",
                            dependencies: [], containers: [
                                [
                                    "name": containerName,
                                    "tag" : "${branch}-${commitSha}"
                                ],
                            ],
                            stageFunc: failableStage
                        ).each { k,v -> v.call() }
                    }
            )
        }
    }
}
