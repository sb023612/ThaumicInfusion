package drunkmafia.thaumicinfusion.common.aspect;

import com.esotericsoftware.reflectasm.MethodAccess;
import drunkmafia.thaumicinfusion.common.util.EffectGUI;
import drunkmafia.thaumicinfusion.common.util.ReflectionHelper;
import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import thaumcraft.api.aspects.Aspect;

import java.io.File;
import java.net.URL;
import java.util.*;

public class AspectHandler {

    private static HashMap<ArrayList<Aspect>, Class> registeredEffects = new HashMap<ArrayList<Aspect>, Class>();
    private static HashMap<Class, HashMap<String, Integer>> effectMethods = new HashMap<Class,HashMap<String, Integer>>();

    /**
     * This will register all effects within a given package
     *
     * @param path Root Package of effects
     */
    public static void registerPackage(String path){
        try {
            List<Class> classesInPath = ReflectionHelper.getClasses(path);
            for(Class c : classesInPath){
                if(c != null) registerEffect(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerEffect(Class effect) throws Exception {
        if (effect.isAnnotationPresent(Effect.class) && Savable.class.isAssignableFrom(effect)) {
            Effect annotation = (Effect) effect.getAnnotation(Effect.class);
            ArrayList<Aspect> aspects = phaseStringForAspects(annotation.aspects());
            if (!registeredEffects.containsKey(aspects) && !registeredEffects.containsValue(effect)) {
                registeredEffects.put(aspects, effect);
                phaseEffect(effect);
            }
        }
    }

    private static void phaseEffect(Class effect){
        MethodAccess methodAccess = MethodAccess.get(effect);
        String[] methods = methodAccess.getMethodNames();

        HashMap<String, Integer> effectsMeth = new HashMap<String, Integer>();
        for(String name : methods) {
            effectsMeth.put(name, methodAccess.getIndex(name));
        }
        effectMethods.put(effect, effectsMeth);
    }

    public static int getMethod(Class effect, String name){
        if(effectMethods.get(effect).containsKey(name))
            return effectMethods.get(effect).get(name);
        return -1;
    }

    public static boolean isAspectAnEffect(ArrayList<Aspect> aspects){
        return registeredEffects.containsKey(aspects);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
