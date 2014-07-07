package drunkmafia.thaumicinfusion.common.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by DrunkMafia on 04/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockHandler {

    //Contains methods which are banned from being ran straight from the block class. Prevents infinite loops from occurring
    private static ArrayList<Method> blackListedMethods = new ArrayList<Method>();

    static{
        String[] methNames = {
                "getLightValue"
        };

        Class[][] pars = {
                new Class[] {IBlockAccess.class, Integer.class, Integer.class, Integer.class}
        };

        Method[] meths = Block.class.getDeclaredMethods();
        for(Method meth : meths){
            int index = Arrays.asList(methNames).indexOf(meth);
            if(index != -1 && meth.getParameterTypes().equals(pars[index])){
                blackListedMethods.add(meth);
            }
        }
    }

    public static boolean isMethodSafe(Method method){
        return !blackListedMethods.contains(method);
    }

}
