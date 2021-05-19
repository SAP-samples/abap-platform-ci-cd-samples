# Build add-ons on an ABAP Environment system

[back to master](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/master)


## Description

Please refer to the [documentation](https://sap.github.io/jenkins-library/scenarios/abapEnvironmentAddons/) for more details about the scenario.

### Pipeline Stages

This pipeline goes through the following steps/stages:

#### [Initial Checks](https://www.project-piper.io/pipelines/abapEnvironment/stages/initialChecks/)
- Check the validity of defined addon product version
- Check the validity of defined software component versions

#### [Prepare System](https://www.project-piper.io/pipelines/abapEnvironment/stages/prepareSystem/)
- Preparing a system for add-on assembly
- Creating a Communication Arrangement for the Scenario SAP_COM_0510 via a service key in the assembly system

#### [Clone Repositories](https://www.project-piper.io/pipelines/abapEnvironment/stages/cloneRepositories/)
- Switch between branches of a git repository on a SAP Cloud Platform ABAP Environment system
- Clone software components relevant for the add-on build

#### [ATC](https://www.project-piper.io/pipelines/abapEnvironment/stages/ATC/)
- Check software components to be assembled as part of the add-on build via ABAP Test Cockpit (Check Variant: SAP_CLOUD_PLATFORM_ATC_DEFAULT)

**Note**: The ATC results are displayed using the [Warnings Next Generation Plugin](https://www.jenkins.io/doc/pipeline/steps/warnings-ng/#warnings-next-generation-plugin). If you don't want to use this plugin - or if it's not available on your Jenkins server - leave out the extension for the ATC stage (.pipeline/extensions/ATC.groovy).

#### [Build](https://www.project-piper.io/pipelines/abapEnvironment/stages/build/)
  * Creating a Communication Arrangement for the Scenario SAP_COM_0582 via a service key in the assembly system
  * Determine the ABAP delivery packages (name and type) to be created as part of the add-on build
  * Assembly of installation, support package or patch in in the assembly system
  * Upload resulting SAR archives and creating physical delivery packages in in the File Content Management System of SAP
  * Create a target vector for software lifecycle operations
  * Release the physical delivery packages
  * Trigger publication of the target vector with test scope

#### [Integration Tests](https://www.project-piper.io/pipelines/abapEnvironment/stages/integrationTest/)
  * Preparing a system for the add-on test installation, system is deprovisioned after successful testing has been confirmed

#### [Confirm](https://www.project-piper.io/pipelines/abapEnvironment/stages/confirm/)
Release Decision

#### [Publish](https://www.project-piper.io/pipelines/abapEnvironment/stages/publish/)
  * After confirmation: Trigger publication of the target vector with production scope

#### [Post](https://www.project-piper.io/pipelines/abapEnvironment/stages/post/)
  * After successful pipeline execution: assembly system is deleted

### Users
Create [Jenkins Credentials](https://www.jenkins.io/doc/book/using/using-credentials/) for following users.
#### cfCredentialsId
Plaform User in Cloud Foundry environment to create assembly system, add-on installation test system, create communication arrangements SAP_COM_0510 and SAP_COM_0582 via service keys. The user needs to be a member of the global account and has to have the [Space Developer role](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/967fc4e2b1314cf7afc7d7043b53e566.html).

#### abapAddonAssemblyKitCredentialsId
The communication with the AAKaaS needs a technical communication user. The creation and activation of such a user is described in [SAP note 2174416](https://launchpad.support.sap.com/#/notes/2174416). Make sure that this technical communication user is assigned to the customer number under which the “SAP CP ABAP ENVIRONMENT” tenants are licensed and for which the development namespace was reserved.

## Configuration
### [.pipeline/config.yml](.pipeline/config.yml)
A configuration file .pipeline/config.yml is used to provide all required values to run the pipeline.

**Pipeline Stage/Parameter**|**Description**|**Documentation**|**Remarks**
:-----|:-----|:-----|:-----
general &rsaquo; abapAddonAssemblyKitCredentialsId|Credentials stored in Jenkins for technical communication user to access AAKaaS|[Link](https://launchpad.support.sap.com/#/notes/2174416)|ID of username/password credentials
general &rsaquo; cfApiEndpoint|Cloud Foundry API Endpoint specific to region of Cloud Foundry Environment to be used|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/350356d1dc314d3199dca15bd2ab9b0e.html#loio879f37370d9b45e99a16538e0f37ff2c)|Subaccount Overview &rarr; Cloud Foundry Environment &rarr; API Endpoint
general &rsaquo; cfOrg|Cloud Foundry Organization used to create assembly system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/fe1ebf3cd6fe46798efcaf45c73a54ce.html)|Subaccount Overview &rarr; Cloud Foundry Environment &rarr; Org Name
general &rsaquo; cfSpace|Cloud Foundry Space used to create assembly system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/5209d55d8dd84228897112b0655d999b.html)| 
general &rsaquo; cfCredentialsId|Credentials stored in Jenkins for Space Developer user to authenticate to the Cloud Foundry API|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/967fc4e2b1314cf7afc7d7043b53e566.html)|User needs org member/subaccount administrator and assigned to space withs space developer role
general &rsaquo; cfServiceInstance|Service Instance Name of assembly system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)| 
stages &rsaquo; Prepare System|In this stage, the SAP BTP, ABAP environment system for add-on assembly is created.|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/prepareSystem/)| 
stages &rsaquo; Prepare System &rsaquo; abapSystemAdminEmail|E-Mail address for the initial administrator of the assembly system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)| 
stages &rsaquo; Prepare System &rsaquo; abapSystemDescription|Description for the assembly system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)|Shown in Landscape Portal application
stages &rsaquo; Prepare System &rsaquo; abapSystemID|Three character name of the assembly system - maps to 'sapSystemName'|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)|Shown in Landscape Portal application
stages &rsaquo; Clone Repositories &rsaquo; repositories|Specifies a YAML file containing configuration of repositories to be imported|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/cloneRepositories/#repositoriesyml)|addon.yml file can be used as format is the same as repositories.yml
stages &rsaquo; Clone Repositories &rsaquo; strategy|influences, which steps will be executed to import the repositories|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/cloneRepositories/#stage-parameters)|Clone' is recommended if a new system is created in the Prepare System stage<br> 'CheckoutPull' is recommended if a static system is used. The software component should be cloned beforehand.
stages &rsaquo; ATC|In this stage, ATC checks can be executed using abapEnvironmentRunATCCheck|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/ATC/)| 
stages &rsaquo; ATC &rsaquo; atcConfig|Path to a YAML configuration file for Packages and/or Software Components to be checked during ATC run|[Link](https://www.project-piper.io/steps/abapEnvironmentRunATCCheck/#atc-config-file-example)| 
stages &rsaquo; Build|This stage is responsible for building an ABAP add-on for the SAP BTP, ABAP environment|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/build/)| 
stages &rsaquo; Integration Tests|creates an SAP BTP, ABAP environment (Steampunk) system and installs the add-on product, that was built in the Build stage|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/integrationTest/)| 
stages &rsaquo; Integration Tests &rsaquo; cfOrg|Cloud Foundry Organization used to create installation test system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/fe1ebf3cd6fe46798efcaf45c73a54ce.html)|Subaccount Overview &rarr; Cloud Foundry Environment &rarr; Org Name
stages &rsaquo; Integration Tests &rsaquo; cfSpace|Cloud Foundry Space used to create installation test system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/5209d55d8dd84228897112b0655d999b.html)| 
stages &rsaquo; Integration Tests &rsaquo; cfServiceInstance|Service Instance Name of installation test system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)| 
stages &rsaquo; Integration Tests &rsaquo; abapSystemAdminEmail|E-Mail address for the initial administrator of the installation test system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)| 
stages &rsaquo; Integration Tests &rsaquo; abapSystemDescription|Description for the assembly system|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)|Shown in Landscape Portal application
stages &rsaquo; Integration Tests &rsaquo; abapSystemID|Three character name of the installation test system - maps to 'sapSystemName'|[Link](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/50b32f144e184154987a06e4b55ce447.html)|Shown in Landscape Portal application
stages &rsaquo; Publish|This stage publishes an add-on for the SAP BTP, ABAP environment|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/publish/)| 
stages &rsaquo; Post|This stage deletes the assembly system created in the Prepare System stage|[Link](https://www.project-piper.io/pipelines/abapEnvironment/stages/post/)| 

### [addon.yml](addon.yml)
Definition of addon product version/software component versions bundle to be assembled

### [atcConfig.yml](atcConfig.yml)
Configuration of software components checked via ATC, check variant `SAP_CLOUD_PLATFORM_ATC_DEFAULT` is used for check runs

### [sap_com_0510.json](sap_com_0510.json)
Service key parameters for creation of communication arrangement SAP_COM_0510

### [sap_com_0582.json](sap_com_0582.json)
Service key parameters for creation of communication arrangement SAP_COM_0582
