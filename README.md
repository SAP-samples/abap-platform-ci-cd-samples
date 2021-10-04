[![REUSE status](https://api.reuse.software/badge/github.com/SAP-samples/abap-platform-ci-cd-samples)](https://api.reuse.software/info/github.com/SAP-samples/abap-platform-ci-cd-samples)

# ABAP Platform CI/CD Samples
The ABAP Environment pipeline makes use of APIs of the SAP BTP (esp. cf cli) and  SAP BTP, ABAP environment (project name "Steampunk"). While the pipeline contains a fixed set of steps and stages, it is possible to adapt it using different configurations. This repository shows and explains different examples of these configurations. 

## Requirements
To create the examples shown in this repository yourself, a Jenkins server and the entitlements for SAP BTP, ABAP environment are required.

## Configuration
Please have a look at the [documentation of the piper project](https://sap.github.io/jenkins-library/pipelines/abapEnvironment/introduction/) to learn more about the configurations necessary to run the examples in this repository.

## How to use this repository

A Jenkins pipeline is defined by a "Jenkinsfile" and other pipeline specific configuration files. The examples in this repository consist of all required files for a specific use case. **The folders ".reuse" and "LICENSES" are required for this Open Source project and do not belong to the pipeline configuration.**

If you want to recreate a specifc use case, you can copy the associated files and replace the context specifc values (API Endpoints, Jenkins Credential IDs etc.)

## ABAP Pipeline Examples - Overview

* [Running ATC checks on a transient ABAP Environment system](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/atc-transient)
* [Running ATC checks on a static ABAP Environment system](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/atc-static)
* [Build add-ons on a transient ABAP Environment system (SAP Partner use case)](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/addon-build)
* [Build add-ons on a static ABAP Environment system (SAP Partner use case)](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/addon-build-static)

## Contributing

This project is only updated by SAP employees.
 
## Known Issues
A list of known issues is available on the GitHub issues page of this repository.

## License
Copyright (c) 2020 SAP SE or an SAP affiliate company. All rights reserved. This project is licensed under the Apache Software License, version 2.0 except as noted otherwise in the [LICENSE](LICENSES/Apache-2.0.txt) file.
