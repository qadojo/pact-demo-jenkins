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
          statusUrl('http://localhost:8080/job/pact-demo-consumer-build/')
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
  }
  steps {
    shell('npm i')
    shell('npm test')
  }
}
