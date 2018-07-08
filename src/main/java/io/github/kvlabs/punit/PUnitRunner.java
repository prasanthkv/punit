package io.github.kvlabs.punit;


import org.apache.commons.collections4.CollectionUtils;
import org.junit.runners.Suite;

import java.lang.annotation.*;
import java.util.*;

import org.junit.runner.Runner;
import org.junit.runners.model.TestClass;

/**
 * Junit runner for pojo
 */
public class PUnitRunner extends Suite {

    private final List<Runner> runners = new ArrayList<>();

    /**
     * Only called reflectively. Do not use programmatically.
     */
    public PUnitRunner(Class<?> klass) throws Throwable {
        super(klass, Collections.<Runner>emptyList());
        Set<Class<?>> classes = getPojos(getTestClass());
        for (Class<?> clazz : classes){
            runners.add(new PUnitTestClassRunner(getTestClass().getJavaClass(), clazz));
        }
    }

    @Override
    protected Annotation[] getRunnerAnnotations() {
        return new Annotation[0];
    }

    @Override
    protected List<Runner> getChildren() {
        return runners;
    }

    /**
     * Get class which need to be validate
     *
     * @param testClass
     * @return Unique Class
     * @throws Exception
     */
    private Set<Class<?>> getPojos(TestClass testClass) throws Exception {
        Set<Class<?>> packages = new HashSet<>();
        PackageToScan annotation = testClass.getAnnotation(PackageToScan.class);
        if (annotation == null) {
            throw new Exception("Unable to find @PackageToScan annotation " + testClass.getName());
        }
        for (String packageTest : annotation.value()) {
            packages.addAll(PackageScanner.getClasses(packageTest));
        }
        if (CollectionUtils.isEmpty(packages)) {
            throw new Exception("No public static parameters method on class " + testClass.getName());
        }
        return packages;
    }
}
