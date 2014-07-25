package drunkmafia.thaumicinfusion.common.aspect;

import drunkmafia.thaumicinfusion.common.util.EffectGUI;
import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import thaumcraft.api.aspects.Aspect;

import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class AspectHandler {

    private static HashMap<ArrayList<Aspect>, Class> registeredEffects = new HashMap<ArrayList<Aspect>, Class>();

    /**
     * This will register all effects within a given package
     *
     * @param path Root Package of effects
     */
    public static void registerPackage(String path){
        try {
            List<Class> classesInPath = getClasses(path);
            for(Class c : classesInPath){
                if(c != null) registerEffect(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerEffect(Class effect) {
        System.out.println("Registering Effect");
        try {
            if (effect.isAnnotationPresent(Effect.class) && Savable.class.isAssignableFrom(effect)) {
                Effect annotation = (Effect) effect.getAnnotation(Effect.class);
                ArrayList<Aspect> aspects = phaseStringForAspects(annotation.aspects());
                if (!registeredEffects.containsKey(aspects) && !registeredEffects.containsValue(effect)) {
                    System.out.println(annotation.name() + " Registered");
                    registeredEffects.put(aspects, effect);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAspectAnEffect(Aspect aspect){
        return registeredEffects.containsKey(aspect);
    }

    public static ArrayList<Aspect> phaseStringForAspects(String str) {
        ArrayList<Aspect> aspects = new ArrayList<Aspect>();
        ArrayList<Character> currentTagChars = new ArrayList<Character>();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (currentChar == ',') {
                char[] chars = new char[currentTagChars.size()];
                for (int c = 0; c < chars.length; c++) chars[c] = currentTagChars.get(c);
                if (chars.length > 0) {
                    String aspect = new String(chars);
                    if (Aspect.getAspect(aspect) != null) aspects.add(Aspect.getAspect(aspect));
                }
            } else currentTagChars.add(currentChar);
        }
        return aspects;
    }

    public static Class getEffectFromList(ArrayList<Aspect> aspects) {
        return registeredEffects.get(aspects);
    }

    public static ArrayList<Aspect> getAspectsFromEffect(Class effect) {
        if(effect.isAnnotationPresent(Effect.class)){
            Effect annotation = (Effect) effect.getAnnotation(Effect.class);
            return phaseStringForAspects(annotation.aspects());
        }
        return null;
    }

    public static EffectGUI getEffectGUI(Class effect){
        if(effect.isAnnotationPresent(Effect.class)){
            Effect annotation = (Effect) effect.getAnnotation(Effect.class);
            if(annotation.gui() != Object.class && EffectGUI.class.isAssignableFrom(annotation.gui())){
                try {
                    return (EffectGUI) annotation.gui().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Failed");
        return null;
    }

    public static List<Class> getClasses(String packageName) throws Exception{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();

        while (resources.hasMoreElements()){
            URL resource = resources.nextElement();
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
