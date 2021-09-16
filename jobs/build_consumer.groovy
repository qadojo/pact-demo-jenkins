job('pact-demo-consumer-build') {
  scm {
    git {
      remote {
        github('qadojo/pact-demo-consumer')
        refspec('+refs/pull/*:refs/remotes/origin/pr/*')
      }
      branch('${sha1}')
    }
  }
  triggers {
    githubPullRequest {
      admin('github-bot-qadojo')
      useGitHubHooks()
      extensions {
        commitStatus {
          context('QADojo demo Jenkins')
          statusUrl('${BUILD_URL}')
          completedStatus('SUCCESS', 'Build succeed')
          completedStatus('FAILURE', 'Build failed')
          completedStatus('ERROR', 'Build failed')
        }
      }
    }
  }
  logRotator {
    daysToKeep(1)
  }
  wrappers {
    nodejs('v16.9.1')
    credentialsBinding {
      usernamePassword('PACT_BROKER_USERNAME', 'PACT_BROKER_PASSWORD', 'pact-demo-broker')
    }
    preBuildCleanup {
      excludePattern('node_modules/')
    }
  }
  steps {
    shell('git checkout $GIT_BRANCH')
    shell('npm i')
    shell('npm test')
    shell('npm run pact:publish')
  }
}
