# First steps with JMH and OSGi

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io#https://github.com/Sandared/jmh-benchmarks/blob/master/benchmarks/src/main/java/io/jatoms/jmh/OSGiBenchmark2.java)

## How to run these benchmarks
```
Click on the Gitpod button above
cd benchmarks
mvn clean install
java -jar target/benchmarks.jar
```

## Reminder: How to setup a new benchmark project
```
mvn archetype:generate
    -DinteractiveMode=false
    -DarchetypeGroupId=org.openjdk.jmh
    -DarchetypeArtifactId=jmh-java-benchmark-archetype
    -DgroupId=io.jatoms.jmh
    -DartifactId=benchmarks
    -Dversion=1.0.0
```

