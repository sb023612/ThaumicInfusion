package drunkmafia.thaumicinfusion.common.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by DrunkMafia on 04/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockHandler {

    //Contains methods which are banned from being ran straight from the block class. Prevents infinite loops from occurring
    private static ArrayList<Method> blackListedMethods = new ArrayList<Method>();

    static{
        try {
            blackListedMethods.add(Block.class.getDeclaredMethod("getLightValue", new Class[] {IBlockAccess.class, Integer.class, Integer.class, Integer.class}));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static boolean isMethodSafe(Method method){
        return !blackListedMethods.contains(method);
    }

}
