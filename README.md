# Running ATC checks on a transient ABAP Environment system

[back to master](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/master)

## Description

This pipeline goes through the following steps:

* Creating an SAP Cloud Platform ABAP Environment system
* Creating a Communication Arrangement for the Scenario SAP_COM_0510
* Pulling the specified Software Components / Git repositories
* Running the configured ABAP Test Cockpit (ATC) checks
* Deprovisioning the SAP Cloud Platform ABAP Environment sysmtem

Please note: currently, it is necessary to confirm manually, once the system is ready. The system is ready, if the confirmation email has been received by the initial administrator (email address provided in the manifest.yml). By using an extension for the "Prepare System" stage, the manual confirmation can be substituted by a "wait" statement. A waiting period of three hours is usually more than enough time. Nevertheless, it could happen that the system is not yet ready by that time and the pipeline will fail. 
