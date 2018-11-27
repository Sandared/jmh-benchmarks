package io.jatoms.jmh;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.felix.framework.Felix;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

@State(Scope.Thread)
@Threads(1)
@Fork(1)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
public class OSGiBenchmark2 {

	Felix felix;


	@Setup
    public void prepare() throws BundleException {
        Map config = new HashMap();
        felix = new Felix(config);
		felix.start();
		// TODO install all bundles for the test
		felix.getBundleContext().installBundle("");
    }

    @Benchmark
    public int osgiMethodCall() {

    	Bundle bundle = felix.getBundle();

    	if(bundle == null) {
    		throw new RuntimeException("Bundle was null");
    	}
    	return System.identityHashCode(bundle);
    }
}
