package drunkmafia.thaumicinfusion.common.aspect;

import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AspectHandler {

    private static HashMap<ArrayList<Aspect>, Class> registeredEffects = new HashMap<ArrayList<Aspect>, Class>();

    public static void registerEffect(Class effect) {
        try {
            if (effect.isAnnotationPresent(Effect.class) && Savable.class.isAssignableFrom(effect)) {
                Effect annotation = (Effect) effect.getAnnotation(Effect.class);
                ArrayList<Aspect> aspects = phaseStringForAspects(annotation.aspects());
                if (!registeredEffects.containsKey(aspects) && !registeredEffects.containsValue(effect))
                    registeredEffects.put(aspects, effect);
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
        Object[] entries = registeredEffects.entrySet().toArray();
        for (int i = 0; i < entries.length; i++) {
            Map.Entry ent = (Map.Entry) entries[i];
            if (((Class) ent.getValue()).isInstance(effect)) return (ArrayList<Aspect>) ent.getKey();
        }
        return null;
    }
}
