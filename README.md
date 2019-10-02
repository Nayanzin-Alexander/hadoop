# Hadoop tutorials

### Used materials
* Running hadoop in docker https://www.youtube.com/watch?v=dLTI2HN9Ejg
* Hadoop ecosystem overview https://www.guru99.com/create-your-first-hadoop-program.html
* Testing http://m-mansur-ashraf.blogspot.com/2013/02/testing-mapreduce-with-mrunit.html

### Usage
##### Start the hadoop
* docker-compose up -d

##### Copy data into HDFS
* docker cp ./env/temp/SalesJan2009.csv namenode:/temp/
* docker exec -it namenode /bin/bash
* hdfs dfs -mkdir /salespercountry/input
* hdfs dfs -put /temp/SalesJan2009.csv /user/root/salespercountry/input

##### Package job to JAR
* mvn -pl salespercountry/ clean package

##### Run the job
* docker cp ./salespercountry/target/salespercountry-0.0.1.jar namenode:/temp/
* docker exec -it namenode /bin/bash
* hadoop jar temp/salespercountry-0.0.1.jar salespercountry/input salespercounty/report

##### Copy report on the local machine
* hdfs -dfs get /user/root/salespercountry/report /temp/salespercountryreport
* docker cp namenode:/temp/report ./env/temp/report

#### Usage of URLCat jar
* mvn -pl urlcat/ clean package
* docker cp ./urlcat/target/urlcat-0.0.1.jar namenode:/temp/
* hadoop jar /temp/urlcat-0.0.1.jar hdfs:/user/root/salespercountry/input/SalesJan2009.csv

#### Usage of filecopy jar
TODO finish. Page 57

### Helpful commands
####HDFS
* -copyFromLocal
* -copyToLocal
* -ls -l.
* -mkdir
* distcp
* archive -archiveName files.har /my/files /my

####Hadoop I/O