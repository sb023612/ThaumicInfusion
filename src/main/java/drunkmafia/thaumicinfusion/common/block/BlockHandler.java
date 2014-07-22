package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DrunkMafia on 04/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockHandler {

    private static HashMap<String, InfusedBlock> infusedBlocks = new HashMap<String, InfusedBlock>();
    //Contains methods which are banned from being ran straight from the block class. Prevents infinite loops from occurring
    private static ArrayList<Method> blackListedMethods = new ArrayList<Method>();

    public static void addBlock(String key, InfusedBlock block){
        infusedBlocks.put(key, block);
    }

    public static void addMethod(Method meth) {
        if (meth != null && !blackListedMethods.contains(meth)) {
            FMLLog.info("Method: " + meth.getName() + " has been added to the black list");
            blackListedMethods.add(meth);
        }
    }

    public static InfusedBlock getBlock(String key){
        return infusedBlocks.get(key);
    }

    public static Method getMethod(String methName, Class[] pars) {
        Method[] methods = Block.class.getDeclaredMethods();
        for (Method meth : methods)
            if (meth.getName().matches(methName) && meth.getParameterTypes().length == pars.length) return meth;
        return null;
    }

    public static void init() {
        addMethod(getMethod("getLightValue", new Class[]{IBlockAccess.class, Integer.class, Integer.class, Integer.class}));
    }

    public static boolean isMethodSafe(Method method) {
        return !blackListedMethods.contains(method);
    }
}
