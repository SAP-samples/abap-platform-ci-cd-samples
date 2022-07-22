void call(Map params) {
  //access stage name
  echo "Start - Extension for stage: ${params.stageName}"

  //access config
  echo "Current stage config: ${params.config}"

  //execute original stage as defined in the template
  params.originalStage()
  
  recordIssues tools: [checkStyle(pattern: '**/ATCResults.xml')], qualityGates: [[threshold: 1, type: 'TOTAL_ERROR', unstable: false], [threshold: 1, type: 'TOTAL_NORMAL', unstable: false], [threshold: 1, type: 'TOTAL', unstable: true]]

  if (currentBuild.result == 'FAILURE') {
    error('Stopping build due to ATC Check quality gate')
  }
  
  echo "End - Extension for stage: ${params.stageName}"
}
return this
