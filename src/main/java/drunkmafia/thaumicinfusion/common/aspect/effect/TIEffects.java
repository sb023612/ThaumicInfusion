package drunkmafia.thaumicinfusion.common.aspect.effect;

import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import drunkmafia.thaumicinfusion.common.aspect.effect.vanilla.Motus;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DrunkMafia on 19/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class TIEffects {
    public static void init(){
        AspectHandler.registerEffect(new Aspect[]{Aspect.MOTION}, Motus.class);
    }
}