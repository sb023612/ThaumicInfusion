package drunkmafia.thaumicinfusion.common.block;

import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Objects;
import java.util.Random;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

public class InfusedBlock extends Block {

    protected InfusedBlock() {
        super(Material.rock);
        setBlockName(infusedBlock_UnlocalizedName);
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        BlockHelper.getBlockData(access, x, y, z).runMethod("setBlockBoundsBasedOnState", access, x, y, z);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("setBlockBoundsBasedOnState", world, x, y, z, rand);
    }

    @Override
    public void onPlantGrow(World world, int x, int y, int z, int sourceX, int sourceY, int sourceZ) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onPlantGrow", x, y, z, sourceX, sourceY, sourceZ);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("setBlockBoundsBasedOnState", world, x, y, z, block);
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity ent, float fall) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onFallenUpon", world, x, y, z, ent, fall);
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity ent) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onEntityWalking", world, x, y, z, ent, ent);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onEntityCollidedWithBlock", world, x, y, z, ent);
    }

    @Override
    public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onBlockPreDestroy", world, x, y, z, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase ent, ItemStack stack) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onBlockPlacedBy", world, x, y, z, ent, stack);
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer ent) {
        BlockHelper.getBlockData(world, x, y, z).runMethod("onBlockClicked", world, x, y, z, ent);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int meta) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("isProvidingWeakPower", access, x, y, z, meta);
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess access, int x, int y, int z, int meta) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("isProvidingStrongPower", access, x, y, z, meta);
    }

    @Override
    public int getLightValue(IBlockAccess access, int x, int y, int z) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("getLightValue", access, x, y, z);
    }

    @Override
    public int getLightOpacity(IBlockAccess access, int x, int y, int z) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("getLightOpacity", access, x, y, z);
    }

    @Override
    public int getFlammability(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("getFlammability", access, x, y, z, face);
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("getFireSpreadSpeed", access, x, y, z, face);
    }

    @Override
    public int getMixedBrightnessForBlock(IBlockAccess access, int x, int y, int z) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("getMixedBrightnessForBlock", access, x, y, z);
    }

    @Override
    public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
        return (IIcon) BlockHelper.getBlockData(access, x, y, z).runMethod("getIcon", access, x, y, z, side);
    }

    @Override
    public int colorMultiplier(IBlockAccess access, int x, int y, int z) {
        return (Integer) BlockHelper.getBlockData(access, x, y, z).runMethod("colorMultiplier", access, x, y, z);
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
        return (ForgeDirection[])BlockHelper.getBlockData(world, x, y, z).runMethod("getValidRotations", world, x, y, z);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        return (Float) BlockHelper.getBlockData(world, x, y, z).runMethod("getPlayerRelativeBlockHardness", player, world, x, y, z);
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return (Integer) BlockHelper.getBlockData(world, x, y, z).runMethod("getComparatorInputOverride", world, x, y, z, side);
    }

    @Override
    public float getExplosionResistance(Entity ent, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return (Float) BlockHelper.getBlockData(world, x, y, z).runMethod("getExplosionResistance", ent, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        return (Float)BlockHelper.getBlockData(world, x, y, z).runMethod("getBlockHardness", world, x, y, z);
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockAccess access, int x, int y, int z, int side) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("shouldCheckWeakPower", access, x, y, z);
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return (Float) BlockHelper.getBlockData(world, x, y, z).runMethod("getEnchantPowerBonus", world, x, y, z);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) {
        return (Boolean) BlockHelper.getBlockData(access, x, y, z).runMethod("shouldSideBeRendered", access, x, y, z);
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("rotateBlock", world, x, y, z, axis);
    }

    @Override
    public boolean recolourBlock(World world, int x, int y, int z, ForgeDirection side, int colour) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("recolourBlock", world, x, y, z, side, colour);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("onBlockActivated", world, x, y, z, player, hitX, hitY, hitZ);
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("isWood", world, x, y, z);
    }

    @Override
    public boolean isSideSolid(IBlockAccess access, int x, int y, int z, ForgeDirection side) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isSideSolid", access, x, y, z);
    }

    @Override
    public boolean isAir(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isAir", access, x, y, z);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess access, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isBeaconBase", access, x, y, z, beaconX, beaconY, beaconZ);
    }

    @Override
    public boolean isBed(IBlockAccess access, int x, int y, int z, EntityLivingBase player) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isBed", access, x, y, z, player);
    }

    @Override
    public boolean isBedFoot(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isBedFoot", access, x, y, z);
    }

    @Override
    public boolean isBlockSolid(IBlockAccess access, int x, int y, int z, int meta) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isBlockSolid", access, x, y, z, meta);
    }

    @Override
    public boolean isBurning(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isBurning", access, x, y, z);
    }

    @Override
    public boolean isFertile(World world, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("isFertile", world, x, y, z);
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("isFireSource", world, x, y, z, side);
    }

    @Override
    public boolean isFlammable(IBlockAccess access, int x, int y, int z, ForgeDirection face) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isFlammable", access, x, y, z, face);
    }

    @Override
    public boolean isFoliage(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isFoliage", access, x, y, z);
    }

    @Override
    public boolean isLadder(IBlockAccess access, int x, int y, int z, EntityLivingBase entity) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isLadder", access, x, y, z, entity);
    }

    @Override
    public boolean isLeaves(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isLeaves", access, x, y, z);
    }

    @Override
    public boolean isNormalCube(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isNormalCube", access, x, y, z);
    }

    @Override
    public boolean isReplaceable(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("isReplaceable", access, x, y, z);
    }

    @Override
    public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("isReplaceableOreGen", world, x, y, z, target);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("canPlaceBlockOnSide", world, x, y, z, side);
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("canPlaceTorchOnTop", world, x, y, z);
    }

    @Override
    public boolean canSustainPlant(IBlockAccess access, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("canSustainPlant", access, x, y, z, direction, plantable);
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("canSustainLeaves", access, x, y, z);
    }

    @Override
    public boolean getWeakChanges(IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("getWeakChanges", access, x, y, z);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return (AxisAlignedBB)BlockHelper.getBlockData(world, x, y, z).runMethod("getSelectedBoundingBoxFromPool", world, x, y, z);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess access, int x, int y, int z, int side) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("canConnectRedstone", access, x, y, z, side);
    }

    @Override
    public boolean canEntityDestroy(IBlockAccess access, int x, int y, int z, Entity entity) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("canEntityDestroy", access, x, y, z, entity);
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess access, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(access, x, y, z).runMethod("canCreatureSpawn", type, access, x, y, z);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return (Boolean)BlockHelper.getBlockData(world, x, y, z).runMethod("canBlockStay", world, x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return (AxisAlignedBB)BlockHelper.getBlockData(world, x, y, z).runMethod("getCollisionBoundingBoxFromPool", world, x, y, z);
    }
}
