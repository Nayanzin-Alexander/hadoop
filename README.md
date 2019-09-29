# Hadoop tutorials

### Used materials
* Running hadoop in docker https://www.youtube.com/watch?v=dLTI2HN9Ejg
* Hadoop ecosystem overview https://www.guru99.com/create-your-first-hadoop-program.html
* Testing http://m-mansur-ashraf.blogspot.com/2013/02/testing-mapreduce-with-mrunit.html

### Helpful commands:
##### Start the hadoop
* docker-compose up -d

##### Copy data into HDFS
* docker cp ./env/temp/SalesJan2009.csv namenode:/temp/
* docker exec -it namenode /bin/bash
* hdfs dfs -mkdir /input
* hdfs dfs -put /temp/SalesJan2009.csv /user/root/input

##### Package job to JAR
* mvn -pl salespercountry/ clean package

##### Run the job
* docker cp ./target/ namenode:/tmp/
* docker cp ./salespercountry/target/salespercountry-0.0.1.jar namenode:/temp/
* docker exec -it namenode /bin/bash
* hadoop jar temp/salespercountry-0.0.1.jar input output/report

##### Copy report on the local machine
* hdfs dfs -get /user/root/output/report /temp/
* docker cp namenode:/temp/report ./env/temp/report