package drunkmafia.thaumicinfusion.common.aspect;

import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import thaumcraft.api.aspects.Aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class AspectHandler {

    private static ArrayList<Class> registeredEffects = new ArrayList<Class>();

    public static void registerEffect(Class effect){
        if(effect.isInstance(AspectEffect.class) && !registeredEffects.contains(effect))
            registeredEffects.add(effect);
        else throw new IllegalArgumentException(ModInfo.ASPECT_EFFECT_REGISTER_ERROR);
    }

    public static Class getEffectsClassFromAspect(Aspect aspect){
        try {
            for (Class c : registeredEffects) {
                AspectEffect effect = (AspectEffect)c.newInstance();
                Method m = c.getMethod("getAspect", new Class[]{});
                Aspect aEffect = (Aspect) m.invoke(effect, new Class[]{});
                if(aEffect.equals(aspect)) return c;
            }
        }catch (Exception e){}
        return null;
    }

    public static AspectEffect getEffectFromAspect(Aspect aspect){
        try {
            for (Class c : registeredEffects) {
                AspectEffect effect = (AspectEffect)c.newInstance();
                Method m = c.getMethod("getAspect", new Class[]{});
                Aspect aEffect = (Aspect) m.invoke(effect, new Class[]{});
                if(aEffect.equals(aspect)) return effect;
            }
        }catch (Exception e){}
        return null;
    }

    public static AspectEffect getEffectFromName(String effectName){
        try {
            for (Class c : registeredEffects) {
                String checkingEffect = c.getName();
                checkingEffect.toLowerCase();
                effectName.toLowerCase();
                if (checkingEffect.equals(effectName)) return (AspectEffect) c.newInstance();
            }
        }catch (Exception e){}
        return null;
    }
}
