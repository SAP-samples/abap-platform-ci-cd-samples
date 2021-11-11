void call(Map params) {
  //access stage name
  echo "Start - Extension for stage: ${params.stageName}"

  //access config
  echo "Current stage config: ${params.config}"

  //execute original stage as defined in the template
  params.originalStage()
  
  junit allowEmptyResults: true, testResults: '**/AUnitResults.xml'
  
  echo "End - Extension for stage: ${params.stageName}"
}
return this