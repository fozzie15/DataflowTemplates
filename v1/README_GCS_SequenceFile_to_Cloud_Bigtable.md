
SequenceFile Files on Cloud Storage to Cloud Bigtable template
---
The Cloud Storage SequenceFile to Bigtable template is a pipeline that reads data
from SequenceFiles in a Cloud Storage bucket and writes the data to a Bigtable
table. You can use the template to copy data from Cloud Storage to Bigtable.


:memo: This is a Google-provided template! Please
check [Provided templates documentation](https://cloud.google.com/dataflow/docs/guides/templates/provided/sequencefile-to-bigtable)
on how to use it without having to build from sources using [Create job from template](https://console.cloud.google.com/dataflow/createjob?template=GCS_SequenceFile_to_Cloud_Bigtable).

:bulb: This is a generated documentation based
on [Metadata Annotations](https://github.com/GoogleCloudPlatform/DataflowTemplates#metadata-annotations)
. Do not change this file directly.

## Parameters

### Required parameters

* **bigtableProject**: The ID of the Google Cloud project that contains the Bigtable instance that you want to write data to.
* **bigtableInstanceId**: The ID of the Bigtable instance that contains the table.
* **bigtableTableId**: The ID of the Bigtable table to import.
* **sourcePattern**: The Cloud Storage path pattern to the location of the data. For example, `gs://your-bucket/your-path/prefix*`.

### Optional parameters

* **bigtableAppProfileId**: The ID of the Bigtable application profile to use for the import. If you don't specify an application profile, Bigtable uses the instance's default application profile (https://cloud.google.com/bigtable/docs/app-profiles#default-app-profile).
* **mutationThrottleLatencyMs**: Optional Set mutation latency throttling (enables the feature). Value in milliseconds. Defaults to: 0.



## Getting Started

### Requirements

* Java 17
* Maven
* [gcloud CLI](https://cloud.google.com/sdk/gcloud), and execution of the
  following commands:
  * `gcloud auth login`
  * `gcloud auth application-default login`

:star2: Those dependencies are pre-installed if you use Google Cloud Shell!

[![Open in Cloud Shell](http://gstatic.com/cloudssh/images/open-btn.svg)](https://console.cloud.google.com/cloudshell/editor?cloudshell_git_repo=https%3A%2F%2Fgithub.com%2FGoogleCloudPlatform%2FDataflowTemplates.git&cloudshell_open_in_editor=v1/src/main/java/com/google/cloud/bigtable/beam/sequencefiles/ImportJob.java)

### Templates Plugin

This README provides instructions using
the [Templates Plugin](https://github.com/GoogleCloudPlatform/DataflowTemplates#templates-plugin).

### Building Template

This template is a Classic Template, meaning that the pipeline code will be
executed only once and the pipeline will be saved to Google Cloud Storage for
further reuse. Please check [Creating classic Dataflow templates](https://cloud.google.com/dataflow/docs/guides/templates/creating-templates)
and [Running classic templates](https://cloud.google.com/dataflow/docs/guides/templates/running-templates)
for more information.

#### Staging the Template

If the plan is to just stage the template (i.e., make it available to use) by
the `gcloud` command or Dataflow "Create job from template" UI,
the `-PtemplatesStage` profile should be used:

```shell
export PROJECT=<my-project>
export BUCKET_NAME=<bucket-name>

mvn clean package -PtemplatesStage  \
-DskipTests \
-DprojectId="$PROJECT" \
-DbucketName="$BUCKET_NAME" \
-DstagePrefix="templates" \
-DtemplateName="GCS_SequenceFile_to_Cloud_Bigtable" \
-f v1
```

The `-DgcpTempLocation=<temp-bucket-name>` parameter can be specified to set the GCS bucket used by the DataflowRunner to write
temp files to during serialization. The path used will be `gs://<temp-bucket-name>/temp/`.

The command should build and save the template to Google Cloud, and then print
the complete location on Cloud Storage:

```
Classic Template was staged! gs://<bucket-name>/templates/GCS_SequenceFile_to_Cloud_Bigtable
```

The specific path should be copied as it will be used in the following steps.

#### Running the Template

**Using the staged template**:

You can use the path above run the template (or share with others for execution).

To start a job with the template at any time using `gcloud`, you are going to
need valid resources for the required parameters.

Provided that, the following command line can be used:

```shell
export PROJECT=<my-project>
export BUCKET_NAME=<bucket-name>
export REGION=us-central1
export TEMPLATE_SPEC_GCSPATH="gs://$BUCKET_NAME/templates/GCS_SequenceFile_to_Cloud_Bigtable"

### Required
export BIGTABLE_PROJECT=<bigtableProject>
export BIGTABLE_INSTANCE_ID=<bigtableInstanceId>
export BIGTABLE_TABLE_ID=<bigtableTableId>
export SOURCE_PATTERN=<sourcePattern>

### Optional
export BIGTABLE_APP_PROFILE_ID=<bigtableAppProfileId>
export MUTATION_THROTTLE_LATENCY_MS=0

gcloud dataflow jobs run "gcs-sequencefile-to-cloud-bigtable-job" \
  --project "$PROJECT" \
  --region "$REGION" \
  --gcs-location "$TEMPLATE_SPEC_GCSPATH" \
  --parameters "bigtableProject=$BIGTABLE_PROJECT" \
  --parameters "bigtableInstanceId=$BIGTABLE_INSTANCE_ID" \
  --parameters "bigtableTableId=$BIGTABLE_TABLE_ID" \
  --parameters "bigtableAppProfileId=$BIGTABLE_APP_PROFILE_ID" \
  --parameters "sourcePattern=$SOURCE_PATTERN" \
  --parameters "mutationThrottleLatencyMs=$MUTATION_THROTTLE_LATENCY_MS"
```

For more information about the command, please check:
https://cloud.google.com/sdk/gcloud/reference/dataflow/jobs/run


**Using the plugin**:

Instead of just generating the template in the folder, it is possible to stage
and run the template in a single command. This may be useful for testing when
changing the templates.

```shell
export PROJECT=<my-project>
export BUCKET_NAME=<bucket-name>
export REGION=us-central1

### Required
export BIGTABLE_PROJECT=<bigtableProject>
export BIGTABLE_INSTANCE_ID=<bigtableInstanceId>
export BIGTABLE_TABLE_ID=<bigtableTableId>
export SOURCE_PATTERN=<sourcePattern>

### Optional
export BIGTABLE_APP_PROFILE_ID=<bigtableAppProfileId>
export MUTATION_THROTTLE_LATENCY_MS=0

mvn clean package -PtemplatesRun \
-DskipTests \
-DprojectId="$PROJECT" \
-DbucketName="$BUCKET_NAME" \
-Dregion="$REGION" \
-DjobName="gcs-sequencefile-to-cloud-bigtable-job" \
-DtemplateName="GCS_SequenceFile_to_Cloud_Bigtable" \
-Dparameters="bigtableProject=$BIGTABLE_PROJECT,bigtableInstanceId=$BIGTABLE_INSTANCE_ID,bigtableTableId=$BIGTABLE_TABLE_ID,bigtableAppProfileId=$BIGTABLE_APP_PROFILE_ID,sourcePattern=$SOURCE_PATTERN,mutationThrottleLatencyMs=$MUTATION_THROTTLE_LATENCY_MS" \
-f v1
```

## Terraform

Dataflow supports the utilization of Terraform to manage template jobs,
see [dataflow_job](https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/dataflow_job).

Terraform modules have been generated for most templates in this repository. This includes the relevant parameters
specific to the template. If available, they may be used instead of
[dataflow_job](https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/dataflow_job)
directly.

To use the autogenerated module, execute the standard
[terraform workflow](https://developer.hashicorp.com/terraform/intro/core-workflow):

```shell
cd v1/terraform/GCS_SequenceFile_to_Cloud_Bigtable
terraform init
terraform apply
```

To use
[dataflow_job](https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/dataflow_job)
directly:

```terraform
provider "google-beta" {
  project = var.project
}
variable "project" {
  default = "<my-project>"
}
variable "region" {
  default = "us-central1"
}

resource "google_dataflow_job" "gcs_sequencefile_to_cloud_bigtable" {

  provider          = google-beta
  template_gcs_path = "gs://dataflow-templates-${var.region}/latest/GCS_SequenceFile_to_Cloud_Bigtable"
  name              = "gcs-sequencefile-to-cloud-bigtable"
  region            = var.region
  temp_gcs_location = "gs://bucket-name-here/temp"
  parameters        = {
    bigtableProject = "<bigtableProject>"
    bigtableInstanceId = "<bigtableInstanceId>"
    bigtableTableId = "<bigtableTableId>"
    sourcePattern = "<sourcePattern>"
    # bigtableAppProfileId = "<bigtableAppProfileId>"
    # mutationThrottleLatencyMs = "0"
  }
}
```
