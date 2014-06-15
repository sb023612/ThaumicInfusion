package drunkmafia.thaumicinfusion.common.item;

import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class InfusedItem extends ItemBlock {
    public InfusedItem() {
        super(TIBlocks.infusedBlock);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        BlockData data = BlockHelper.getDataFromStack(stack, x, y, z);
        if(data != null){
            BlockHelper.getWorldData(world).addBlock(new ChunkCoordinates(x, y, z), data);
            if(world.setBlock(x, y, z, data.getBlock())){
                return true;
            }else{
                BlockHelper.getWorldData(world).removeBlock(new ChunkCoordinates(x, y, z));
                return false;
            }
        }
        return false;
    }
}
