package drunkmafia.thaumicinfusion.common.world;

import drunkmafia.thaumicinfusion.common.util.BlockData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldSavedData;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class InfusedBlockWorld extends WorldSavedData {

    private LinkedHashMap<ChunkCoordinates, BlockData> blocksInWorld = new LinkedHashMap<ChunkCoordinates, BlockData>();

    public InfusedBlockWorld(String worldName) {
        super(worldName);
    }

    public BlockData getBlock(ChunkCoordinates coordinates){
        return blocksInWorld.get(coordinates);
    }

    public void removeBlock(ChunkCoordinates coordinates){
        blocksInWorld.remove(coordinates);
    }

    public boolean addBlock(ChunkCoordinates coordinates, BlockData data){
        if(!blocksInWorld.containsKey(coordinates)){
            blocksInWorld.put(coordinates, data);
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        for(int i = 0; i < tag.getInteger("data_size"); i++){
            BlockData data = new BlockData();
            data.readTag(tag.getCompoundTag("data_" + i));
            blocksInWorld.put(data.getBlockCoords(), data);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        Iterator iterator = blocksInWorld.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            NBTTagCompound data = new NBTTagCompound();
            ((BlockData)entry.getValue()).writeTag(data);
            tag.setTag("data_" + i, data);
            i++;
        }
        tag.setInteger("data_size", i);
    }
}
