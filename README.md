# First steps with JMH and OSGi

[![Gitpod - Code Now](https://img.shields.io/badge/Gitpod-code%20now-blue.svg?longCache=true)](https://gitpod.io#https://github.com/Sandared/jmh-benchmarks/blob/master/benchmarks/src/main/java/io/jatoms/jmh/OSGiBenchmark2.java)

## How to setup a new benchmark project
```
mvn archetype:generate
    -DinteractiveMode=false
    -DarchetypeGroupId=org.openjdk.jmh
    -DarchetypeArtifactId=jmh-java-benchmark-archetype
    -DgroupId=io.jatoms.jmh
    -DartifactId=benchmarks
    -Dversion=1.0.0
```

## How to run these benchmarks
```
cd benchmarks
mvn clean install
java -jar target/benchmarks.jar
```
