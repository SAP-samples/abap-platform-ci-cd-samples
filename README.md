# Running ATC checks on a static ABAP Environment system

[back to master](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/master)

## Description

This pipeline goes through the following steps:

* Pulling the specified Software Components / Git repositories
* Running the configured ABAP Test Cockpit (ATC) checks

For this a static, preconfigured system is being used.

Prerequisites are:

* [ABAP  System](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/f0163565eb554f009f990652ca41d1c6.html) was created via Cloud Cockpit with provisioning Parameter `is_development_allowed = false` 
* Communication Arrangement SAP_COM_0510 was [created via Service Key](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/1cc5a1da02594b93a70f6c0fe2bfdfe8.html)
* Initial [clone of software components](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/18564c54f529496ba420d4c83545a2ce.html) was triggered via Manage Software Components app

Credentials and hostname of the ABAP system are retrieved from the service key configured in .pipeline/config.yml

The results are displayed using the [Warnings Next Generation Plugin](https://www.jenkins.io/doc/pipeline/steps/warnings-ng/#warnings-next-generation-plugin). If you don't want to use this plugin - or if it's not available on your Jenkins server - leave out the extension for the ATC stage (.pipeline/extensions/ATC.groovy).
