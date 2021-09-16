job('pact-demo-provider-build') {
  scm {
    git {
      remote {
        github('qadojo/pact-demo-provider')
        refspec('+refs/pull/*:refs/remotes/origin/pr/*')
      }
      branch('${sha1}')
    }
  }
  triggers {
    githubPullRequest {
      admin('github-bot-qadojo')
      userWhitelist('0x06065a@gmail.com')
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
    preBuildCleanup()
  }
  steps {
    shell('npm i')
    shell('npm run pact:verify')
  }
}
