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
import org.osgi.framework.BundleException;
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

    Felix felix;
    ComponentFactory<ITest> factoryService;

    Dictionary<String, Object> configSmall;
    Dictionary<String, Object> configLarge;


    @Setup(Level.Trial)
    public void prepare() throws BundleException, FileNotFoundException, InterruptedException {
        Map config = new HashMap();
        felix = new Felix(config);
        felix.start();
        // TODO install all bundles for the test
        Path scr = Paths.get(
                "/home/gitpod/.m2/repository/org/apache/felix/org.apache.felix.scr/2.1.14/org.apache.felix.scr-2.1.14.jar");

        Path testlib = Paths.get(
                "/workspace/jmh-benchmarks/benchmarks/libs/io/jatoms/test/test.bundle/1.0.0/test.bundle-1.0.0.jar");
        felix.getBundleContext().installBundle("scr", new FileInputStream(scr.toFile())).start();
        felix.getBundleContext().installBundle("testlib", new FileInputStream(testlib.toFile())).start();
        Thread.sleep(1000);
        ServiceReference<ComponentFactory> cmpFactory = felix.getBundleContext().getServiceReference(ComponentFactory.class);
        factoryService = felix.getBundleContext().getService(cmpFactory);

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
    public int osgiInstanceCreationNoConfig() {
        ComponentInstance<ITest> service = factoryService.newInstance(null);
        return service.hashCode();
    }


    @Benchmark
    public int osgiInstanceCreationSmallConfig() {
        ComponentInstance<ITest> service = factoryService.newInstance(configSmall);
        return service.hashCode();
    }

     @Benchmark
    public int osgiInstanceCreationLargeConfig() {
        ComponentInstance<ITest> service = factoryService.newInstance(configLarge);
        return service.hashCode();
    }
}
