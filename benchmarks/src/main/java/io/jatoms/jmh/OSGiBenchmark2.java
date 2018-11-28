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
import org.apache.felix.scr.impl.manager.ComponentFactoryImpl;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
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

    Felix felix;
    ComponentFactory<ITest> factoryService;

    Dictionary<String, Object> configSmall;
    Dictionary<String, Object> configLarge;

    @Setup(Level.Trial)
    public void prepare() throws BundleException, FileNotFoundException, InterruptedException, InvalidSyntaxException {
        Map config = new HashMap();
        felix = new Felix(config);
        felix.start();
        // TODO install all bundles for the test
        Path scr = Paths.get(
                "/home/gitpod/.m2/repository/org/apache/felix/org.apache.felix.scr/2.1.0/org.apache.felix.scr-2.1.0.jar");

        Path testlib = Paths.get(
                "/workspace/jmh-benchmarks/benchmarks/libs/io/jatoms/test/test.bundle/1.0.0/test.bundle-1.0.0.jar");
        felix.getBundleContext().installBundle("scr", new FileInputStream(scr.toFile())).start();
        felix.getBundleContext().installBundle("testlib", new FileInputStream(testlib.toFile())).start();
        Thread.sleep(1000);

        System.out.println("Felix Bundle Context: " + felix.getBundleContext());
        Bundle[] bundles = felix.getBundleContext().getBundles();
        for (Bundle bundle : bundles) {
            System.out.println("\tBundle: " + bundle.getSymbolicName());
            ServiceReference<ComponentFactory> cmpFactory = bundle.getBundleContext().getServiceReference(ComponentFactory.class);
            System.out.println("\t\tServiceReference: " + cmpFactory);
            if(cmpFactory != null){
                System.out.println("\t\tService: " + bundle.getBundleContext().getService(cmpFactory));
               ComponentInstance instance = bundle.getBundleContext().getService(cmpFactory).newInstance(null);
                // System.out.println("\t\tService Class: " + bundle.getBundleContext().getService(cmpFactory).getClass());
                // System.out.println("\t\tService Interfaces: " + bundle.getBundleContext().getService(cmpFactory).getClass().getInterfaces());
                // System.out.println("\t\tEQUALS COMPFACTORY: " + (bundle.getBundleContext().getService(cmpFactory) instanceof ComponentFactory));
            } else {
                System.out.println("\t\tService: NULL");
            }

        }

        // ServiceReference<ComponentFactory> cmpFactory =
        // felix.getBundleContext().getServiceReference(ComponentFactory.class);
        // factoryService = felix.getBundleContext().getService(cmpFactory);

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

    // @Benchmark
    // public int osgiInstanceCreationSmallConfig() {
    //     ComponentInstance<ITest> service = factoryService.newInstance(configSmall);
    //     return service.hashCode();
    // }

    // @Benchmark
    // public int osgiInstanceCreationLargeConfig() {
    //     ComponentInstance<ITest> service = factoryService.newInstance(configLarge);
    //     return service.hashCode();
    // }
}
