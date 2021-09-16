job('pact-consumer-demo-build') {
  scm {
    git('https://github.com/qadojo/pact-demo-consumer/')
  }
  steps {
    sh('npm i')
    sh('npm test')
  }
}
