spark-stream-stream-join-test
=============================

### Introduction
Example submitable stream-stream join Spark application to play with.

### Build the app
To build, you need Scala 2.12, git and maven on the box.
Do a git clone of this repo and then run:
```
cd spark-stream-stream-join-test
mvn clean package
```

### spark-submit
```
spark-submit --master spark://your-host:7077 --deploy-mode client --class com.spark.Main spark-stream-stream-join-test-1.0-SNAPSHOT-jar-with-dependencies.jar
```
