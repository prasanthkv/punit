package io.github.kvlabs.punit;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Class to can packages for classes
 *
 * @author kvprasanth
 */
public class PackageScanner {

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        String path = packageName.replace('.', '/');
        Enumeration resources = Thread.currentThread().getContextClassLoader().getResources(path);
        List<File> dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class<?> currentClass = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                if(!ignore(currentClass)) {
                    classes.add(currentClass);
                }
            }
        }
        return classes;
    }

    /**
     * Checks the given class need to be ignored
     *
     * @param clazz to be evaluated
     * @return true if this class which need to be ignored
     */
    private static boolean ignore(Class<?> clazz){
        return (clazz.getAnnotation(Ignore.class) != null);
    }
}
