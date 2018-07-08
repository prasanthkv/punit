package io.github.kvlabs.punit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for a class which provides packages which need to be scanned for com.github.kvlabs.pojo's.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public  @interface PackageToScan {
    /**
     * Returns an array of packages which need to be scanned for com.github.kvlabs.pojo's.
     * @return an array of packages which need to be scanned for com.github.kvlabs.pojo's.
     */
    String[] value();
}