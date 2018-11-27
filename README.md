# First steps with JMH

## Setup
```
mvn archetype:generate
    -DinteractiveMode=false
    -DarchetypeGroupId=org.openjdk.jmh
    -DarchetypeArtifactId=jmh-java-benchmark-archetype
    -DgroupId=io.jatoms.jmh
    -DartifactId=benchmarks
    -Dversion=1.0.0
```

## Run
```
mvn clean install
java -jar target/benchmarks.jar
```
