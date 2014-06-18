package drunkmafia.thaumicinfusion.common.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drunkmafia.thaumicinfusion.common.ThaumicInfusion;
import drunkmafia.thaumicinfusion.common.world.TIWorldData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import static drunkmafia.thaumicinfusion.common.util.InfusionHelper.*;

public class BlockHelper {
    public static BlockData getDataFromStack(ItemStack stack, int x, int y, int z) {
        Class[] classes = getEffectsClassFromStack(stack);
        BlockData data = new BlockData(new Vector3F(x, y, z), classes, getInfusedID(stack), getBlockID(stack, classes));
        return data;
    }

    public static BlockData getBlockData(IBlockAccess access, int x, int y, int z) {
        return getBlockData(getWorld(access, x, y, z), x, y, z);
    }

    public static BlockData getBlockData(World world, int x, int y, int z) {
        BlockSavable savable = getWorldData(world).getBlock(new Vector3F(x, y, z));
        if(savable != null)
            return (BlockData) savable;
        return null;
    }

    public static TIWorldData getWorldData(World world) {
        TIWorldData worldData = (TIWorldData) world.perWorldStorage.loadData(TIWorldData.class, "TIDATA");
        if (worldData != null) return worldData;
        else {
            worldData = new TIWorldData(world.perWorldStorage.toString());
            world.perWorldStorage.setData("TIDATA", worldData);
        }
        return worldData;
    }

    public static World getWorld(IBlockAccess blockAccess, int x, int y, int z) {
        if (ThaumicInfusion.proxy.isServer) {
            Block block = blockAccess.getBlock(x, y, z);
            int meta = blockAccess.getBlockMetadata(x, y, z);
            for (WorldServer world : MinecraftServer.getServer().worldServers) {
                if (world.getBlock(x, y, z) == block && world.getBlockMetadata(x, y, z) == meta)
                    return world;
            }
        } else {
            return getClientWorld();
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public static World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
}
