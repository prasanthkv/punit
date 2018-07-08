package io.github.kvlabs.punit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;

public class PUnitTestClassRunner extends BlockJUnit4ClassRunner {

    private final Class<?> pojoClass;

    PUnitTestClassRunner(Class<?> testClass, Class<?> pojoClass) throws InitializationError {
        super(testClass);
        this.pojoClass = pojoClass;
    }

    @Override
    public Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(this.pojoClass);
    }


    @Override
    protected String getName() {
        return String.format("[%s]", this.pojoClass.getCanonicalName());
    }

    @Override
    protected String testName(final FrameworkMethod method) {
        return String.format("%s[%s]", method.getName(), this.pojoClass.getCanonicalName());
    }

    @Override
    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
    }

    @Override
    protected Statement classBlock(RunNotifier notifier) {
        return childrenInvoker(notifier);
    }
}