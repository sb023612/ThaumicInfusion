package drunkmafia.thaumicinfusion.common.block;

import com.sun.istack.internal.NotNull;
import drunkmafia.thaumicinfusion.common.block.tile.InfusionCoreTile;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.blocks.ItemJarFilled;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.Arrays;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 19/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class InfusionCoreBlock extends TIBlock implements ITileEntityProvider {

    protected InfusionCoreBlock() {
        super(Material.rock);
        setBlockName(infusionCoreBlock_UnlocalizedName);
        setCreativeTab(TITab.tab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer ent, int side, float hitX, float hitY, float hitZ) {
        if(world.isRemote || world.getTileEntity(x, y, z) == null) return true;

        InfusionCoreTile tile = (InfusionCoreTile) world.getTileEntity(x, y, z);
        ItemStack stack = ent.inventory.getCurrentItem();
        if(tile.isFormed()) {
            if(stack != null && stack.getItem() instanceof ItemWandCasting) return false;
            if (tile.hasStack() && ent.isSneaking() || stack == null) {
                ItemStack block = tile.takeStack();
                if (stack == null) {
                    int index = Arrays.asList(ent.inventory.mainInventory).indexOf(stack);
                    ent.inventory.mainInventory[index] = block;
                } else dropBlockAsItem(world, x, y, z, block);
            }else if (stack != null && stack.getItem() instanceof ItemBlock) {
                int index = Arrays.asList(ent.inventory.mainInventory).indexOf(stack);
                ent.inventory.mainInventory[index] = tile.putStack(stack);
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if(!world.isRemote){
            InfusionCoreTile tile = (InfusionCoreTile) world.getTileEntity(x, y, z);
            if(tile.hasStack()) world.spawnEntityInWorld(new EntityItem(world, x, y, z, tile.getStack()));
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new InfusionCoreTile();
    }
}
