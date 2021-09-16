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
      useGitHubHooks()
      extensions {
        commitStatus {
          context('QADojo demo Jenkins')
          statusUrl('http://localhost:8080/job/pact-demo-provider-build/')
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
