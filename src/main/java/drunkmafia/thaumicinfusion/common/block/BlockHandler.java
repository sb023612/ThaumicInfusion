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
    private static HashMap<Object[], Method> blockMethods = new HashMap<Object[], Method>();

    public static void addBlock(String key, InfusedBlock block){
        infusedBlocks.put(key, block);
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

    public static void phaseBlock(){
        Method[] methods = Block.class.getDeclaredMethods();
        for(Method meth : methods)
            blockMethods.put(new Object[] {meth.getName(), meth.getParameterTypes().length}, meth);
        System.out.println("Phased though block class, methods found: " + methods.length);
    }

    public static Method getMethod(String methName, int pars){
        return blockMethods.get(new Object[] {methName, pars});
    }

    public static void init() {
        phaseBlock();
    }
}
