package io.jatoms.jmh;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.felix.framework.Felix;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

import de.unia.smds.test.ITest;

@State(Scope.Thread)
@Threads(1)
@Fork(1)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
public class OSGiBenchmark2 {

    ComponentFactory<ITest> factoryService;
    Dictionary<String, Object> configSmall;
    Dictionary<String, Object> configLarge;

    @Setup(Level.Trial)
    public void prepare() throws BundleException, FileNotFoundException, InterruptedException, InvalidSyntaxException {
        // Configure Felix to use the framework classloader and delegate the classloading for org.osgi.service.component to this one,
        // as otherwise we get nasty classcastexceptions, as the framework and the bootstrapping code would have different instances of the same class
        Map<String, Object> config = new HashMap<>();
        config.put("org.osgi.framework.bootdelegation", "org.osgi.service.component");
        config.put("org.osgi.framework.bundle.parent", "framework");

        Felix felix = new Felix(config);
        felix.start();

        // install bundles for the test
        Path scr = Paths.get(
                "/home/gitpod/.m2/repository/org/apache/felix/org.apache.felix.scr/2.1.0/org.apache.felix.scr-2.1.0.jar");

        Path testlib = Paths.get(
                "/workspace/jmh-benchmarks/benchmarks/libs/io/jatoms/test/test.bundle/0.1.0/test.bundle-0.1.0.jar");

        felix.getBundleContext().installBundle("scr", new FileInputStream(scr.toFile())).start();;
        felix.getBundleContext().installBundle("testlib", new FileInputStream(testlib.toFile())).start();

        System.out.println("Felix Bundle Context: " + felix.getBundleContext());

        // wait for osgi to start all services we need
        Thread.sleep(1000);

        // the framework bundle has no service reference to ComponentFactory so we search the other bundles for such a reference
        Bundle[] bundles = felix.getBundleContext().getBundles();
        for(Bundle bundle : bundles){
            ServiceReference<ComponentFactory> ref = bundle.getBundleContext().getServiceReference(ComponentFactory.class);
            System.out.println("Reference: " + ref);
            if( ref != null ){
                factoryService = bundle.getBundleContext().getService(ref);
                break;
            }
        }

        configSmall = new Hashtable<>();
        configSmall.put("test", 1);

        configLarge = new Hashtable<>();
        configLarge.put("Test1", 1);
        configLarge.put("Test2", 4);
        configLarge.put("Test3", "Test3");
        configLarge.put("Test4", "supergeilertext");
        configLarge.put("Test5", 1);
        configLarge.put("Test6", 4);
        configLarge.put("Test7", "Test3");
        configLarge.put("Test8", "supergeilertext");
        configLarge.put("Test9", 1);
        configLarge.put("Test10", 4);
        configLarge.put("Test11", "Test3");
        configLarge.put("Test12", "supergeilertext");
    }

    @Benchmark
    public void osgiInstanceCreationNoConfig(Blackhole blackhole) {
        ComponentInstance<ITest> service = factoryService.newInstance(null);
        blackhole.consume(service);
    }

    // @Benchmark
    // public int osgiInstanceCreationSmallConfig() {
    // ComponentInstance<ITest> service = factoryService.newInstance(configSmall);
    // return service.hashCode();
    // }

    // @Benchmark
    // public int osgiInstanceCreationLargeConfig() {
    // ComponentInstance<ITest> service = factoryService.newInstance(configLarge);
    // return service.hashCode();
    // }
}
