package drunkmafia.thaumicinfusion.common.block;

import drunkmafia.thaumicinfusion.common.block.tile.EssentiaConstructorTile;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 02/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EssentiaConstructor extends Block implements ITileEntityProvider {
    protected EssentiaConstructor() {
        super(Material.rock);
        setBlockName(essentiaConstructorBlock_UnlocalizedName);
        setCreativeTab(TITab.tab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote) return true;

        ItemStack stack = player.getCurrentEquippedItem();
        if(stack != null && world.getTileEntity(x, y, z) != null){
            EssentiaConstructorTile tile = (EssentiaConstructorTile) world.getTileEntity(x, y, z);
            ItemStack inv = tile.getStackInSlot(0);
            if(inv == null && isStackValid(stack)){
                tile.setInventorySlotContents(0, stack);
            }
        }

        return false;
    }

    private boolean isStackValid(ItemStack stack){
        if(stack.getItem() instanceof ItemBlock){
            Block block = Block.getBlockFromItem(stack.getItem());
            if(block == Blocks.stone) return true;
            else if(block == Blocks.stonebrick) return true;
        }
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new EssentiaConstructorTile();
    }
}
