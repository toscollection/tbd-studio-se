# Automatic TUJ generation

How I see TUJ usage : (TDD perspective ?)

AC + Regression testing : I want to implement a new distribution and would like to have "ACs" before starting to work on the implementation and regression tests once implemented for maintenance purpose.

Fix validation : I have a fix to do on a component, I should challenge the fix against the regression tests. Then if the fix requires a specific treatment for a specific distrib/version I should add a TUJ for that.

Feature validation + Regression testing : I have a feature on an old component, I should challenge the regression tests and add a new TUJ for the feature.

## AC : 

* I want to generate a new functional job for a selected distribution version from another job of another version of the distribution
* Handle subjobs
* Generate random ID
* Handle group of jobs (folder) ?
* Context handling ?
* Compare local file handling ?

## Off topic : 

* Distribution data that is not in a t\*Configuration component

## Extensions :

* Cross distribution with complex migration
* Colibri generation support (group file migration)
* Template TUJs
* On-the-go generation with template

## Use cases :

1. I want to create a `Local Spark` `2.2` TUJ from `2.1` for `spark_local_basics_j01_210`
> `tujGenerator -i ./talend-spark-21-local -o ./talend-spark-22-local -t spark_local_basics_j01_210 --distribution-name=SPARK_LOCAL --distribution-version=SPARK_2_2_0`

2. I want to create all `Local Spark` `2.2` TUJs from `2.1` ?
> `tujGenerator -i ./talend-spark-21-local -o ./talend-spark-22-local --distribution-name=SPARK_LOCAL --distribution-version=SPARK_2_2_0`

## How to migrate

a TUJ is represented by :

* a .project file
* some folders containing jobs (process, process_mr, process_storm) and each job containing
	* .item file representing the flow
	* .properties file representing the properties of the job
	* .screenshot file

### Project file

### Job item file

A job is represented by :
* `context`
	* `contextParameter` : context parameters
* `parameters` : job configuration (SparkConfiguration)
	* `elementParameter` : job parameters
* `node` : for one component 
	* `elementParameter` : component parameters
	* `metadata` : schema
		* `column` : schema column
* `connection` : link between components
* `subjob` : for each subjob starts

#### tRunJob

* PROCESS : child job name
* PROCESS:PROCESS_TYPE_PROCESS : pointer on the job ID in the .properties file of the child job

#### t\*Connection

#### t\*Configuration

#### Other Components

* DEFINE_STORAGE_CONFIGURATION + STORAGE_CONFIGURATION
* USE_EXISTING_CONNECTION + CONNECTION
* DISTRIBUTION + HBASE_VERSION

tSparkConfiguration_1 is in job parameters node with :

* SPARK_LOCAL_MODE
* SPARK_LOCAL_VERSION
* DISTRIBUTION
* SPARK_VERSION

### Job properties file

### Process

Start by mapping TUJ to objects for easier processing.

#### Proposal 1

* Start by DI jobs
	* Find tRunJob if any
		* Generate and store old - new ID relation for future use
	* Find t\*Connection or t\*Configuration if any
		* Follow migration order and replace distribution and its version
	* Find any component that has distribution properties and does not use a t\*Connection or t\*Configuration
* Iterate over child jobs with the same process

#### Proposal 2

* Start by DI jobs
	* Find tRunJob if any
* Create an AST from job hierarchy and XML files
* Use custom processors (walker in the AST performing transformation if conditions are met)
* Use custom collectors to retrieve information (find job id relation)

for directory mode : retrieve list of all jobs and start to process DI jobs and check what's left after for non-DI jobs.

finish by exporting new TUJ.




