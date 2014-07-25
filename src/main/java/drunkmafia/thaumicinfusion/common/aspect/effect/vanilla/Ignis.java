package drunkmafia.thaumicinfusion.common.aspect.effect.vanilla;

import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.BlockSubscribe;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by DrunkMafia on 25/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
@Effect(name = "ignis", aspects = ("ignis,"))
public class Ignis  extends Savable {

    @BlockSubscribe
    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face){
        return 0;
    }

    @BlockSubscribe
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face){
        return true;
    }

    @BlockSubscribe
    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face){
        return 4096;
    }

    @BlockSubscribe
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side){
        return true;
    }
}
