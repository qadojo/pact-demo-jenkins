job('pact-consumer-demo-build') {
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
          statusUrl('https://jenkins.pact-demo.qadojo.ru/')
          completedStatus('SUCCESS', 'Build succeed')
          completedStatus('FAILURE', 'Build failed')
          completedStatus('ERROR', 'Build failed')
        }
      }
    }
  }
  steps {
    shell('npm i')
    shell('npm test')
  }
}
