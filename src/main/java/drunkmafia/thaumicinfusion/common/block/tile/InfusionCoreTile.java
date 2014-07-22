package drunkmafia.thaumicinfusion.common.block.tile;

import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import drunkmafia.thaumicinfusion.common.block.JarPedestalBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileInfusionPillar;

import java.util.ArrayList;

/**
 * Created by DrunkMafia on 19/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class InfusionCoreTile extends TileEntity implements IWandable {

    private ItemStack inventory;
    private boolean isFormed;

    public ItemStack putStack(ItemStack stack){
        System.out.println("Tile: Putting Stack");
        if(inventory == null){
            inventory = stack;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return null;
        }else{
            if(stack.getItem() == inventory.getItem() && inventory.stackSize < 64){
                 if((stack.stackSize + inventory.stackSize) > 64){
                     stack.stackSize -= inventory.stackSize;
                     inventory.stackSize = 64;
                     worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                     return stack;
                 }else{
                     inventory.stackSize += stack.stackSize;
                     worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                     return null;
                 }
            }
        }
        System.out.println("Failed");
        return stack;
    }

    public ItemStack takeStack(){
        if(inventory == null) return null;
        ItemStack stack = inventory.copy();
        inventory = null;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        return stack;
    }

    public ItemStack getStack(){
        return inventory;
    }

    public boolean hasStack(){
        return inventory != null;
    }

    public boolean isFormed(){return isFormed;}

    public int distance = 10;

    public ArrayList<JarPedestalTile> getPedestals(){
        ArrayList<JarPedestalTile> jars = new ArrayList<JarPedestalTile>();
        for(int x = -distance + xCoord; x < distance + xCoord; x++){
            for(int y = -distance + yCoord; y < distance + yCoord; y++){
                for(int z = -distance + zCoord; z < distance + zCoord; z++){
                    if(worldObj.blockExists(x, y, z) && worldObj.getBlock(x, y, z) instanceof JarPedestalBlock) jars.add((JarPedestalTile)worldObj.getTileEntity(x, y, z));
                }
            }
        }
        return jars;
    }

    public void setupInfusion(){


    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setByte("isFormed", (byte)(isFormed ? 1 : 0));
        if(inventory == null) return;
        NBTTagCompound inventoryTag = new NBTTagCompound();
        inventory.writeToNBT(inventoryTag);
        tag.setTag("inventory", inventoryTag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        isFormed = tag.getByte("isFormed") == 1;
        if(tag.hasKey("inventory")) inventory = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("inventory"));
        else inventory = null;
    }

    @Override
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
        if(world.isRemote) return 0;
        System.out.println("Wand");
        if(!isFormed && buildInfusionCore()) return -1;
        else setupInfusion();
        return 0;
    }

    public boolean buildInfusionCore(){
        if(arePillars()){
            isFormed = true;

            worldObj.setBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 1, ConfigBlocks.blockStoneDevice, 4, 3);
            worldObj.setBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 1, ConfigBlocks.blockStoneDevice, 4, 3);
            worldObj.setBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 1, ConfigBlocks.blockStoneDevice, 4, 3);
            worldObj.setBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 1, ConfigBlocks.blockStoneDevice, 4, 3);
            placePillar(this.xCoord + 1, this.yCoord - 2, this.zCoord + 1, (byte)5);
            placePillar(this.xCoord + 1, this.yCoord - 2, this.zCoord - 1, (byte)4);
            placePillar(this.xCoord - 1, this.yCoord - 2, this.zCoord - 1, (byte)2);
            placePillar(this.xCoord - 1, this.yCoord - 2, this.zCoord + 1, (byte)3);
            return true;
        }
        return false;
    }

    public void placePillar(int x, int y, int z, byte orinatation){
        worldObj.setBlock(x, y, z, ConfigBlocks.blockStoneDevice, 3, 3);
        TileInfusionPillar pillar = (TileInfusionPillar) worldObj.getTileEntity(x, y, z);
        pillar.orientation = orinatation;
    }

    public boolean arePillars(){
        boolean val = checkBlock(ConfigBlocks.blockCosmeticSolid, 7, this.xCoord + 1, this.yCoord - 2, this.zCoord + 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 7, this.xCoord + 1, this.yCoord - 2, this.zCoord - 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 7, this.xCoord - 1, this.yCoord - 2, this.zCoord - 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 7, this.xCoord - 1, this.yCoord - 2, this.zCoord + 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 6, this.xCoord + 1, this.yCoord - 1, this.zCoord + 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 6, this.xCoord + 1, this.yCoord - 1, this.zCoord - 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 6, this.xCoord - 1, this.yCoord - 1, this.zCoord - 1);
        if(val == false) return val;
        val = checkBlock(ConfigBlocks.blockCosmeticSolid, 6, this.xCoord - 1, this.yCoord - 1, this.zCoord + 1);
        return val;
    }
    public boolean checkBlock(Block block, int meta, int x, int y, int z){
        if(worldObj.getBlock(x, y, z) == block && worldObj.getBlockMetadata(x, y, z) == meta) return true;
        return false;
    }

    @Override
    public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) { return wandstack; }

    @Override
    public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

    @Override
    public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
