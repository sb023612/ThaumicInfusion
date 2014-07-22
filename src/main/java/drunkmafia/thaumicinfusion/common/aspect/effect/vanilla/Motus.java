package drunkmafia.thaumicinfusion.common.aspect.effect.vanilla;

import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.BlockSubscribe;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by DrunkMafia on 19/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
@Effect(aspects = ("motus,"))
public class Motus extends Savable {

    private ForgeDirection dir;

    public Motus() {
        dir = ForgeDirection.EAST;
    }

    @BlockSubscribe
    public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
        moveEntity(ent);
    }

    @BlockSubscribe
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
        moveEntity(ent);
    }

    public void moveEntity(Entity ent) {
        switch (dir) {
            case NORTH:
                ent.motionX = 1;
                break;
            case SOUTH:
                ent.motionX = -1;
                break;
            case EAST:
                ent.motionZ = 1;
                break;
            case WEST:
                ent.motionZ = -1;
                break;
        }
    }
}
