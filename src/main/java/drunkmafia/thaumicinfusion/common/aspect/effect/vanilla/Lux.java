package drunkmafia.thaumicinfusion.common.aspect.effect.vanilla;

import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.BlockSubscribe;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import net.minecraft.world.IBlockAccess;

/**
 * Created by DrunkMafia on 25/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
@Effect(name = "lux", aspects = ("lux,"))
public class Lux extends Savable {

    @BlockSubscribe
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return 10;
    }
}
