# Building add-ons for a Software as a Service solution (for SAP Partners)

[back to master](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/master)


## Description

This pipeline goes through the following steps/stages:

* __Initial Checks__
  * Check the validity of defined addon product version
  * Check the validity of defined software component versions
* __Prepare System__
  * Preparing a system for add-on assembly
  * Creating a Communication Arrangement for the Scenario SAP_COM_0510 via a service key in the assembly system
* __Clone Repositories__
  * Switch between branches of a git repository on a SAP Cloud Platform ABAP Environment system
  * Clone software components relevant for the add-on build
* __ATC__
  * Check software components to be assembled as part of the add-on build via ABAP Test Cockpit (Check Variant: SAP_CLOUD_PLATFORM_ATC_DEFAULT)
* __Build__
  * Creating a Communication Arrangement for the Scenario SAP_COM_0582 via a service key in the assembly system
  * Determine the ABAP delivery packages (name and type) to be created as part of the add-on build
  * Assembly of installation, support package or patch in in the assembly system
  * Upload resulting SAR archives and creating physical delivery packages in in the File Content Management System of SAP
  * Create a target vector for software lifecycle operations
  * Release the physical delivery packages
  * Trigger publication of the target vector with test scope
* __Integration Tests__
  * Preparing a system for the add-on test installation, system is deprovisioned after successful testing has been confirmed
* __Confirm__: Release Decision
* __Publish__
  * After confirmation: Trigger publication of the target vector with production scope
* __Post__
  * After successful pipeline execution: assembly system is deprovisioned
  
The ATC results are displayed using the [Warnings Next Generation Plugin](https://www.jenkins.io/doc/pipeline/steps/warnings-ng/#warnings-next-generation-plugin). If you don't want to use this plugin - or if it's not available on your Jenkins server - leave out the extension for the ATC stage (.pipeline/extensions/ATC.groovy).

**Please note:** Currently, it is necessary to manually confirm ABAP system provisionings. The system is ready, if the confirmation email has been received by the initial administrator (email address provided via provisioning parameter admin_email). By using an extension for the "Prepare System" stage, the manual confirmation can be substituted by a "wait" statement. A waiting period of three hours is usually more than enough. Nevertheless, it could happen that the system is not yet ready by that time and the pipeline will fail. Please have a look at this [example](https://github.com/SAP-samples/abap-platform-ci-cd-samples/tree/atc-transient-wait).

## Users
Create Jenkins Credentials for following users:
* __cfCredentialsId__: Plaform User in Cloud Foundry environment: to create assembly system, add-on installation test system, create communication arrangements SAP_COM_0510 and SAP_COM_0582 via service keys
* __abapAddonAssemblyKitCredentialsId__: Technical Communication User (S-User) via SAP One Support Launchpad: to create delivery packages via Add-on Assembly Kit as a Service

## Configuration
### .pipeline/config.yml
#### general
*Valid for all stages and steps*

* `abapAddonAssemblyKitCredentialsId`: Technical Communication User (S-User) via SAP One Support Launchpad via Jenkins Credentials
* `addonDescriptorFileName`: add-on descriptor file
* `cfApiEndpoint`: API Endpoint specific to region of Cloud Foundry Environment to be used
* `cfOrg`, `cfSpace`, `cfServiceInstance` pointing to space where assembly system is created
* `cfServiceKeyName` pointing to service key that is created for SAP_COM_0510 communication arrangement
#### stages
* __Prepare System__: configuration of assembly system defined in `cfService`, `cfServicePlan`, `abapSystemAdminEmail`, `abapSystemDescription`, `abapSystemIsDevelopmentAllowed`, `abapSystemID`, `abapSystemSizeOfPersistence`, `abapSystemSizeOfRuntime`, `includeAddon`; service key configuration for creation of SAP_COM_0510 communication arrangement in `sap_com_0510.json` as well as name of service key to be created.
The service provisioning parameters are described in [Create an ABAP System](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/f0163565eb554f009f990652ca41d1c6.html).

**Please note:** The add-on assembly system should be provisioned as abap/standard service instance with parameter `is_developmend_allowed` set to *false* to prevent any unwanted changes to development objects.
* __Clone Repositories__: `repositories` list of software components to be imported is provided in the `addon.yml` file; add-on build-specific import `strategy` is defined

**Please note:** This will perform a pull of the software components with the abapEnvironmentPullGitRepo step, a checkout of branches with the abapEnvironmentCheckoutBranch step followed by a pull of the software component again with the abapEnvironmentPullGitRepo step.
* __ATC__: `atcConfig` pointing to configuration file for ATC checks
* __Build__: `cfServiceKeyName` for creation of SAP_COM_0582 communication arrangement via parameters defined in `cfServiceKeyConfig` file (`sap_com_0582.json`)
* __Integration Tests__: `cfOrg`, `cfSpace`, `cfServiceInstance`, `cfServiceInstanceName` pointing to space where add-on installation test system shall be created.  The configuration of the add-on installation test system is defined in `cfService`, `cfServicePlan`, `abapSystemAdminEmail`, `abapSystemDescription`, `abapSystemIsDevelopmentAllowed`, `abapSystemID`, `abapSystemSizeOfPersistence`, `abapSystemSizeOfRuntime`, `includeAddon`.
The service provisioning parameters are described in [Create an ABAP System](https://help.sap.com/viewer/65de2977205c403bbc107264b8eccf4b/Cloud/en-US/f0163565eb554f009f990652ca41d1c6.html).
With `confirmDeletion` the deletion of the add-on installation test system has to be confirmed.

**Please note:** The add-on installation test system should be provisioned as abap-oem/standard service instance with parameter `is_developmend_allowed` set to *false*.
The parameters `addon_product_version` and `addon_product_name` will be taken from the configuration in `addon.yml` file
* __Publish__: With `targetVectorScope: 'P'` the stage is enabled
* __Post__: With `confirmDeletion` the deletion of the assembly system has to be confirmed, `cfDeleteServiceKeys` to always delete service keys before ABAP system deletion

### addon.yml
Definition of addon product version/software component versions bundle to be assembled

### atcConfig.yml
Configuration of software components checked via ATC, check variant `SAP_CLOUD_PLATFORM_ATC_DEFAULT` is provided

### sap_com_0510.json
Service key parameters for creation of communication arrangement SAP_COM_0510

### sap_com_0582.json
Service key parameters for creation of communication arrangement SAP_COM_0582

## Extensions
* __ATC.groovy__: Display ATC results using the [Warnings Next Generation Plugin](https://www.jenkins.io/doc/pipeline/steps/warnings-ng/#warnings-next-generation-plugin)
