package io.github.kvlabs.punit.it;

import io.github.kvlabs.punit.PUnitRunner;
import io.github.kvlabs.punit.PackageToScan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PUnitRunner.class)
@PackageToScan(value = { "com.github.kvlabs.pojo.v1", "com.github.kvlabs.pojo.v2"})
public class PUnitRunnerIntegrationTest {

    Class<?> claszz;

    public PUnitRunnerIntegrationTest(Class<?> claszz){
        this.claszz = claszz;
    }

    @Test
    public void test(){
        Assert.assertNotNull(claszz);
    }

}