## Data Engineer Challenge

This is attempt to solve [Data Engineer Challenge](https://github.com/tamediadigital/hiring-challenges/tree/master/data-engineer-challenge)

### Compiling and running

#### Compiling
Use maven to build the project with **mvn clean package** from root of the project.
 
#### Running
java -jar target/spring-kafka-2.5.1.jar

#### Configuration
By default in application properties we are using following configuration: (you can provide your own arguments with env variable, java program arguments of entire application.properties )
- simple.kafka.bootstrap-servers=localhost:29092
- simple.topic.name=visits-2
- simple.kafka.publish-topic-name=count-per-minute

### Explanation

In kafka-docker folder i first executed **docker-compose up** to set up kafka broker and zookeeper

Create topic for source data:

**docker exec kafka_kafka_1 bash /bin/kafka-topics --create  --zookeeper zookeeper:2181    --replication-factor 1 --partitions 1   --topic visits-1**

Download [test data](https://tda-public.s3.eu-central-1.amazonaws.com/hire-challenge/stream.jsonl.gz)

Copy test data to container: 

**docker cp stream.jsonl.gz kafka_kafka_1:/home/appuser/**


Populate topic with messages from test data file:

**docker exec kafka_kafka_1 bash -c 'zcat stream.jsonl.gz | /bin/kafka-console-producer --broker-list localhost:9092 --topic visits-2'**
 
### Now we have the data in kafka topic, let's read this data follow great [Baeldung article](https://www.baeldung.com/spring-kafka) 
 
###### Step 1 configure listener to listen to data (simple listener)
>Relevant code can be found in: com.tamediadigital.data.engineer package

###### Step 2 provide single threaded non scalable solution:
>com.tamediadigital.data.engineer.core.SimpleClickStreamService

Since this is first time i use Kafka, i have decided to create simple listener with in memory list to implement solution quickly.
After executing code i printed results in the stdout and also send them to the destination topic.

I have decided to use json output in order to be flexible for improving the solution with new intervals in the future (Hours, Days...):
> {
  	timeUnit: "Minute"
  	intervalStart: "120"
  	distinctUsersCount: 124
  }



###### Step 3 Create test for the solution
I have tested the solution with simple unit test since i have used Ports and Adapters architecture

###### Step 4 try to provide solution that scales
After investigating on the web i found out that problem is **clickstream analysis** and that good fit (apart from big data frameworks) is kafka streaming 

So i have tried to implement it properly, but since i am fresh to Kafka did not manage to do so.

I can discuss my attempt in more details in the interview and I provide far from optimal solution this time