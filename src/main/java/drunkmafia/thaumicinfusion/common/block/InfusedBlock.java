package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drunkmafia.thaumicinfusion.common.ThaumicInfusion;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.BlockSavable;
import drunkmafia.thaumicinfusion.net.ChannelHandler;
import drunkmafia.thaumicinfusion.net.packet.server.BlockDestroyedPacketC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.Random;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.infusedBlock_UnlocalizedName;

public class InfusedBlock extends Block {

    public InfusedBlock() {
        super(Material.rock);
        setCreativeTab(TITab.tab);
        setBlockName(infusedBlock_UnlocalizedName);
    }

    public boolean isBlockData(BlockSavable savable){
        if (savable != null && savable instanceof BlockData) return true;
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        BlockData data = (BlockData) BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (data != null && data.getContainingBlock() != null)
            return data.getContainingBlock().getIcon(access, x, y, z, side);
        return Blocks.stone.getIcon(access, x, y, z, side);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack stack) {
        if (!world.isRemote) {
            BlockData data = BlockHelper.getDataFromStack(stack, x, y, z);
            if (data != null) {
                BlockHelper.getWorldData(world).addBlock(data);
            }
        }
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            System.out.println(world.isRemote);
            ((BlockData) blockData).runMethod(true, world, x, y, z, ent, stack);
        }
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (!world.isRemote && blockData != null) {
            BlockHelper.getWorldData(world).removeBlock(blockData.getCoords());
            ChannelHandler.network.sendToAll(new BlockDestroyedPacketC(blockData.getCoords()));
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem();
        if(stack != null && stack.getItem() instanceof ItemWandCasting) {
            player.openGui(ThaumicInfusion.instance, 0, world, x, y, z);
        }else{
            BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
            if (isBlockData(blockData)) {
                Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z, player, side, hitX, hitY, hitZ);
                if (obj != null) return (Boolean) obj;
            }
        }
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, access, x, y, z);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, rand);
    }

    @Override
    public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, sourceX, sourceY, sourceZ);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, block);
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity ent, float fall) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, ent, fall);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, ent);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, ent);
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer ent) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData))
            ((BlockData) blockData).runMethod(true, world, x, y, z, ent);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z, side, hitX, hitY, hitZ, meta);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, meta);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess access, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, meta);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public int getLightValue(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(false, access, x, y, z);
            if (obj != null) return (Integer) obj;
        }
        return getLightValue();
    }

    @Override
    public int getLightOpacity(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Integer) obj;
        }
        return getLightOpacity();
    }

    @Override
    public int getFlammability(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true,  access, x, y, z, face);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true,  access, x, y, z, face);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public int colorMultiplier(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z);
            if (obj != null) return (ForgeDirection[]) obj;
        }
        return null;
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, player, world, x, y, z);
            if (obj != null) return (Float) obj;
        }
        return 0;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z, side);
            if (obj != null) return (Integer) obj;
        }
        return 0;
    }

    @Override
    public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, ent, world, x, y, z, explosionX, explosionY, explosionZ);
            if (obj != null) return (Float) obj;
        }
        return 0;
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z);
            if (obj != null) return (Float) obj;
        }
        return 0;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockAccess access, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, side);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true,  world, x, y, z);
            if (obj != null) return (Float) obj;
        }
        return 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, side);
            if (obj != null) return (Boolean) obj;
        }
        return true;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z, axis);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z, side, colour);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess access, int x, int y, int z, ForgeDirection side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, side);
            if (obj != null) return (Boolean) obj;
        }
        return true;
    }

    @Override
    public boolean isBeaconBase(IBlockAccess access, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, beaconX, beaconY, beaconZ);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isBed(IBlockAccess access, int x, int y, int z, EntityLivingBase player) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, player);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isBedFoot(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess access, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, meta);
            if (obj != null) return (Boolean) obj;
        }
        return true;
    }

    @Override
    public boolean isBurning(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isFertile(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true,  world, x, y, z, side);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isFlammable(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, face);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isFoliage(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true,access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isLadder(IBlockAccess access, int x, int y, int z, EntityLivingBase entity) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, entity);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isLeaves(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess access, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, direction, plantable);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean getWeakChanges(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess access, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, side);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess access, int x, int y, int z, Entity entity) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, access, x, y, z, entity);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, type, access, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if (isBlockData(blockData)) {
            Object obj = ((BlockData) blockData).runMethod(true, world, x, y, z);
            if (obj != null) return (Boolean) obj;
        }
        return false;
    }
}
