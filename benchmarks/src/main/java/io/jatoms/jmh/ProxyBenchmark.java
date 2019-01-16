package io.jatoms.jmh;

import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Threads(1)
@Fork(1)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
public class ProxyBenchmark {

    @Param({"1", "3", "5", "10"})
    public int calldepth;

    private Receiver receiver;

    @Setup(Level.Trial)
    public void setup(){
        receiver = (Receiver) Proxy.newProxyInstance(OSGiReceiver.class.getClassLoader(), new Class[]{Receiver.class}, new ExampleProxy(new OSGiReceiver()));
    }

    @Benchmark
    public int proxyBenchmark() {
        return receiver.receive(5, calldepth);
    }

}