job('pact-consumer-demo-build') {
  scm {
    git('https://github.com/qadojo/pact-demo-consumer/')
  }
  steps {
    shell('npm i')
    shell('npm test')
  }
}
