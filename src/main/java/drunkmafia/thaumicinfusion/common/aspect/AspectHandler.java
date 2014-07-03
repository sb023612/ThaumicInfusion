package drunkmafia.thaumicinfusion.common.aspect;

import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AspectHandler {

    private static HashMap<ArrayList<Aspect>, Class> registeredEffects = new HashMap<ArrayList<Aspect>, Class>();

    public static void registerEffect(Aspect[] aspects, Class effect) {
        try {
            Object obj = effect.newInstance();
            if (aspects.length != 0 && obj instanceof AspectEffect && !registeredEffects.containsKey(aspects) && !registeredEffects.containsValue(effect))
                registeredEffects.put(toArrayToList(aspects), effect);
            else {
                if (aspects.length == 0)
                    throw new IllegalArgumentException(ModInfo.ASPECT_EFFECT_REGISTER_ERROR + ": Aspects are empty");
                if (!(obj instanceof AspectEffect))
                    throw new IllegalArgumentException(ModInfo.ASPECT_EFFECT_REGISTER_ERROR + ": Effect is not the correct instance");
                if (registeredEffects.containsKey(aspects) || registeredEffects.containsValue(effect))
                    throw new IllegalArgumentException(ModInfo.ASPECT_EFFECT_REGISTER_ERROR + ": Already registered");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Aspect> toArrayToList(Aspect[] aspects){
        ArrayList<Aspect> list = new ArrayList<Aspect>();
        for(Aspect aspect : aspects) list.add(aspect);
        return list;
    }

    public static Class getEffectFromList(ArrayList<Aspect> aspects){
        return registeredEffects.get(aspects);
    }

    public static ArrayList<Aspect> getAspectsFromEffect(Class effect){
        Object[] entries = registeredEffects.entrySet().toArray();
        for(int i = 0; i < entries.length; i++) {
            Map.Entry ent = (Map.Entry) entries[i];
            if(((Class)ent.getValue()).isInstance(effect)) return (ArrayList<Aspect>) ent.getKey();
        }
        return null;
    }
}
