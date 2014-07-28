package drunkmafia.thaumicinfusion.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by DrunkMafia on 28/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class ReflectionHelper {
    public static List<Class> getClasses(String packageName) throws Exception{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        Enumeration<java.net.URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();

        while (resources.hasMoreElements()){
            java.net.URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs)
            classes.addAll(findClasses(directory, packageName));


        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException{
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) return classes;

        File[] files = directory.listFiles();
        for (File file : files){
            if (file.isDirectory())
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            else if (file.getName().endsWith(".class"))
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
        }

        return classes;
    }
}
