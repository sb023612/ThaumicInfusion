package drunkmafia.thaumicinfusion.common.world;

import drunkmafia.thaumicinfusion.common.util.BlockSavable;
import drunkmafia.thaumicinfusion.common.util.ISavable;
import drunkmafia.thaumicinfusion.common.util.Vector3F;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by DrunkMafia on 18/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class TIWorldData extends WorldSavedData {

    private HashMap<Integer, HashMap<Vector3F, BlockSavable>> blocksData;

    public TIWorldData(String worldName){
        super("TIData_" + worldName);
        blocksData = new HashMap<Integer, HashMap<Vector3F, BlockSavable>>();
    }

    public void addBlock(BlockSavable block){
        if(block != null && block.blockID != 0 && block.coords != null){
            if(blocksData.containsKey(block.blockID)){
                blocksData.get(block.blockID).put(block.coords, block);
            }else{
                HashMap<Vector3F, BlockSavable> newMap = new HashMap<Vector3F, BlockSavable>();
                newMap.put(block.coords, block);
                blocksData.put(block.blockID, newMap);
            }
        }else{
            System.out.println("Failed to add block due to a null value");
        }
        markDirty();
    }

    public void removeBlock(Vector3F coords){
        Object[] ids = blocksData.entrySet().toArray();
        for(int i = 0; i < ids.length; i++){
            if(blocksData.get(((Map.Entry) ids[i]).getKey()).containsKey(coords))
                blocksData.get(((Map.Entry) ids[i]).getKey()).remove(coords);
        }
        markDirty();
    }

    public BlockSavable getBlock(Vector3F coords){
        Object[] ids = blocksData.entrySet().toArray();
        for(int i = 0; i < ids.length; i++){
            if(blocksData.get(((Map.Entry) ids[i]).getKey()).containsKey(coords))
                return (BlockSavable) blocksData.get(((Map.Entry) ids[i]).getKey()).get(coords);
        }
        return null;
    }

    public void removeBlock(BlockSavable data){
        removeBlock(data.coords);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        System.out.println("World data reading");
        for(int i = 0; i < tag.getInteger("IDS"); i++){
            HashMap<Vector3F, BlockSavable> blocks = new HashMap<Vector3F, BlockSavable>();
            for(int b = 0; b < tag.getInteger("ID:" + i + "B_Length"); b++){
                BlockSavable block = new BlockSavable();
                block.readTag(tag.getCompoundTag("ID:" + i + "B:" + b));
                blocks.put(block.coords, block);
            }
            blocksData.put(tag.getInteger("ID:" + i + "B_ID"), blocks);
        }
        System.out.println("World data finished reading");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        System.out.println("World data writing");
        Object[] ids = blocksData.entrySet().toArray();
        tag.setInteger("IDS" , ids.length);
        for(int i = 0; i < ids.length; i++){
            Map.Entry idEnt = (Map.Entry) ids[i];
            Object[] blocks = ((HashMap<Vector3F, ISavable>) idEnt.getValue()).entrySet().toArray();
            tag.setInteger("ID:" + i + "B_ID", (Integer)idEnt.getKey());
            tag.setInteger("ID:" + i + "B_Length", blocks.length);
            for(int b = 0; b < blocks.length; b++){
                BlockSavable blockSavable = (BlockSavable)((Map.Entry)blocks[b]).getValue();
                NBTTagCompound saveTag = new NBTTagCompound();
                blockSavable.writeTag(saveTag);
                tag.setTag("ID:" + i + "B:" + b, saveTag);
            }
        }
        System.out.println("World data finished writeing");
    }
}
