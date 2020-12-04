# Running ATC checks on a transient ABAP Environment system

[back to master](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/master)

## Description

This pipeline goes through the following steps:

* Creating an SAP Cloud Platform ABAP Environment system
* Creating a Communication Arrangement for the Scenario SAP_COM_0510
* Pulling the specified Software Components / Git repositories
* Running the configured ABAP Test Cockpit (ATC) checks
* Deprovisioning the SAP Cloud Platform ABAP Environment system

The results are displayed using the [Warnings Next Generation Plugin](https://www.jenkins.io/doc/pipeline/steps/warnings-ng/#warnings-next-generation-plugin). If you don't want to use this plugin - or if it's not available on your Jenkins server - leave out the extension for the ATC stage (.pipeline/extensions/ATC.groovy).
