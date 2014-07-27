package drunkmafia.thaumicinfusion.common.aspect.effect.vanilla;

import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import net.minecraft.world.IBlockAccess;

/**
 * Created by DrunkMafia on 25/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
@Effect(name = "potentia", aspects = ("potentia,"))
public class Potentia  extends Savable {

    public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int side){
        System.out.println("isProvidingWeakPower");
        return 15;
    }

    public int isProvidingStrongPower(IBlockAccess access, int x, int y, int z, int side){
        System.out.println("isProvidingStrongPower");
        return 15;
    }

    public boolean shouldCheckWeakPower(IBlockAccess access, int x, int y, int z, int side) {
        return true;
    }

    public boolean canConnectRedstone(IBlockAccess access, int x, int y, int z, int side) {
        return true;
    }
}
