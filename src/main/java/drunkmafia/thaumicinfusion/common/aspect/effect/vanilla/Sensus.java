package drunkmafia.thaumicinfusion.common.aspect.effect.vanilla;

import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.Savable;
import drunkmafia.thaumicinfusion.common.util.annotation.Effect;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import thaumcraft.common.items.armor.ItemGoggles;

/**
 * Created by DrunkMafia on 25/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
@Effect(name = "sensus", aspects = ("sensus,"))
public class Sensus extends Savable {

    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        BlockData data = (BlockData)BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[0];
        if(stack != null && stack.getItem() instanceof ItemGoggles){
            System.out.println("has Goggles");
            data.getContainingBlock().setBlockBoundsBasedOnState(access, x, y, z);
        }
        else{
            data.getContainingBlock().setBlockBounds(x, y, z, x, y, z);
        }
    }
}
