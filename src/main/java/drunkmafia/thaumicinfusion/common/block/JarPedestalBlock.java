package drunkmafia.thaumicinfusion.common.block;

import drunkmafia.thaumicinfusion.common.block.tile.JarPedestalTile;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.lib.InventoryHelper;
import thaumcraft.common.tiles.TilePedestal;

import java.util.List;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 15/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class JarPedestalBlock extends Block implements ITileEntityProvider {
    public JarPedestalBlock(){
        super(Material.rock);
        setCreativeTab(TITab.tab);
        setBlockName(jarPedestalBlock_UnlocalizedName);
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(world.getTileEntity(x, y, z) == null) return true;
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        if ((metadata == 1) && ((tileEntity instanceof TilePedestal)))
        {
            TilePedestal ped = (TilePedestal)tileEntity;
            if (ped.getStackInSlot(0) != null)
            {
                InventoryHelper.dropItemsAtEntity(world, x, y, z, player);
                world.markBlockForUpdate(x, y, z);
                world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);


                return true;
            }
            if (player.getCurrentEquippedItem() != null)
            {
                ItemStack i = player.getCurrentEquippedItem().copy();
                i.stackSize = 1;
                ped.setInventorySlotContents(0, i);
                player.getCurrentEquippedItem().stackSize -= 1;
                if (player.getCurrentEquippedItem().stackSize == 0) {
                    player.setCurrentItemOrArmor(0, null);
                }
                player.inventory.markDirty();
                world.markBlockForUpdate(x, y, z);
                world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);

                return true;
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
    }

    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity){
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
        setBlockBounds(0.25F, 0.5F, 0.25F, 0.75F, 1.0F, 0.75F);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
        setBlockBounds(0.125F, 0.25F, 0.125F, 0.875F, 0.5F, 0.875F);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k){
        setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, -0.5F, 1.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new JarPedestalTile();
    }
}
