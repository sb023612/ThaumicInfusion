package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.common.FMLLog;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.nio.channels.Channel;
import java.util.Random;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

public class InfusedBlock extends Block {

    public InfusedBlock() {
        super(Material.rock);
        setCreativeTab(TITab.tab);
        setBlockName(infusedBlock_UnlocalizedName);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        BlockData data = (BlockData)BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(data != null && data.getContainingBlock() != null) return data.getContainingBlock().getIcon(access, x, y, z, side);
        return Blocks.stone.getIcon(access, x, y, z, side);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack stack) {
        if(!world.isRemote) {
            BlockData data = BlockHelper.getDataFromStack(stack, x, y, z);
            if (data != null) {
                BlockHelper.getWorldData(world).addBlock(data);
            }
        }
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(!world.isRemote){
            BlockHelper.getWorldData(world).removeBlock(blockData.getCoords());
            ChannelHandler.network.sendToAll(new BlockDestroyedPacketC(blockData.getCoords()));
        }
    }

    @Override
    public int getLightValue(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("getLightValue", new Class[] {IBlockAccess.class, Integer.class, Integer.class, Integer.class}, access, x, y, z);
        return 0;
    }

    /**
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("setBlockBoundsBasedOnState", access, x, y, z);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("setBlockBoundsBasedOnState", world, x, y, z, rand);
    }

    @Override
    public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("onPlantGrow", x, y, z, sourceX, sourceY, sourceZ);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("setBlockBoundsBasedOnState", world, x, y, z, block);
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity ent, float fall) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("onFallenUpon", world, x, y, z, ent, fall);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("onEntityWalking", world, x, y, z, ent, ent);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("onEntityCollidedWithBlock", world, x, y, z, ent);
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer ent) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData)((BlockData)blockData).runMethod("onBlockClicked", world, x, y, z, ent);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("isProvidingWeakPower", access, x, y, z, meta);
        return 0;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess access, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("isProvidingStrongPower", access, x, y, z, meta);
        return 0;
    }

    @Override
    public int getLightValue(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        //if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("getLightValue", access, x, y, z);
        return 0;
    }

    @Override
    public int getLightOpacity(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("getLightOpacity", access, x, y, z);
        return 0;
    }

    @Override
    public int getFlammability(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("getFlammability", access, x, y, z, face);
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("getFireSpreadSpeed", access, x, y, z, face);
        return 0;
    }

    @Override
    public int getMixedBrightnessForBlock(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("getMixedBrightnessForBlock", access, x, y, z);
        return 0;
    }

    @Override
    public int colorMultiplier(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("colorMultiplier", access, x, y, z);
        return 0;
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (ForgeDirection[]) ((BlockData)blockData).runMethod("getValidRotations", world, x, y, z);
        return null;
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Float) ((BlockData)blockData).runMethod("getPlayerRelativeBlockHardness", player, world, x, y, z);
        return 0;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Integer) ((BlockData)blockData).runMethod("getComparatorInputOverride", world, x, y, z, side);
        return 0;
    }

    @Override
    public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Float) ((BlockData)blockData).runMethod("getExplosionResistance", ent, world, x, y, z, explosionX, explosionY, explosionZ);
        return 0;
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Float) ((BlockData)blockData).runMethod("getBlockHardness", world, x, y, z);
        return 0;
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockAccess access, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("shouldCheckWeakPower", access, x, y, z);
        return false;
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Float) ((BlockData)blockData).runMethod("getEnchantPowerBonus", world, x, y, z);
        return 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("shouldSideBeRendered", access, x, y, z);
        return false;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("rotateBlock", world, x, y, z, axis);
        return false;
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("recolourBlock", world, x, y, z, side, colour);
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("onBlockActivated", world, x, y, z, player, hitX, hitY, hitZ);
        return false;
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isWood", world, x, y, z);
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockAccess access, int x, int y, int z, ForgeDirection side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isSideSolid", access, x, y, z);
        return false;
    }

    @Override
    public boolean isAir(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isAir", access, x, y, z);
        return false;
    }

    @Override
    public boolean isBeaconBase(IBlockAccess access, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isBeaconBase", access, x, y, z, beaconX, beaconY, beaconZ);
        return false;
    }

    @Override
    public boolean isBed(IBlockAccess access, int x, int y, int z, EntityLivingBase player) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isBed", access, x, y, z, player);
        return false;
    }

    @Override
    public boolean isBedFoot(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isBedFoot", access, x, y, z);
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess access, int x, int y, int z, int meta) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isBlockSolid", access, x, y, z, meta);
        return false;
    }

    @Override
    public boolean isBurning(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isBurning", access, x, y, z);
        return false;
    }

    @Override
    public boolean isFertile(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isFertile", world, x, y, z);
        return false;
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isFireSource", world, x, y, z, side);
        return false;
    }

    @Override
    public boolean isFlammable(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isFlammable", access, x, y, z, face);
        return false;
    }

    @Override
    public boolean isFoliage(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isFoliage", access, x, y, z);
        return false;
    }

    @Override
    public boolean isLadder(IBlockAccess access, int x, int y, int z, EntityLivingBase entity) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isLadder", access, x, y, z, entity);
        return false;
    }

    @Override
    public boolean isLeaves(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isLeaves", access, x, y, z);
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isNormalCube", access, x, y, z);
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("isReplaceable", access, x, y, z);
        return false;
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canPlaceTorchOnTop", world, x, y, z);
        return false;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess access, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canSustainPlant", access, x, y, z, direction, plantable);
        return false;
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canSustainLeaves", access, x, y, z);
        return false;
    }

    @Override
    public boolean getWeakChanges(IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("getWeakChanges", access, x, y, z);
        return false;
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess access, int x, int y, int z, int side) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canConnectRedstone", access, x, y, z, side);
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess access, int x, int y, int z, Entity entity) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canEntityDestroy", access, x, y, z, entity);
        return false;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess access, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(access, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canCreatureSpawn", type, access, x, y, z);
        return false;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        BlockSavable blockData = BlockHelper.getData(world, new ChunkCoordinates(x, y, z));
        if(blockData != null && blockData instanceof BlockData) return (Boolean) ((BlockData)blockData).runMethod("canBlockStay", world, x, y, z);
        return false;
    }**/
}
