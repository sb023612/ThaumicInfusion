package drunkmafia.thaumicinfusion.common.util;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;

/**
 * Created by DrunkMafia on 29/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockSavable {

    private ChunkCoordinates coordinates;

    public BlockSavable(){}

    public static BlockSavable loadDataFromNBT(NBTTagCompound tag){
        if(tag.hasKey("class")) {
            try {
                Class c = Class.forName(tag.getString("class"));
                if(c.getSuperclass() == BlockSavable.class) {
                    BlockSavable data = (BlockSavable) c.newInstance();
                    data.readNBT(tag);
                    return data;
                }
            }catch (Exception e){
                FMLLog.bigWarning("TI: Failed to load data, block is now at risk of causing errors");
            }
        }
        return null;
    }

    public BlockSavable(ChunkCoordinates coordinates){
        this.coordinates = coordinates;
    }

    public ChunkCoordinates getCoords(){
        return coordinates;
    }

    public void writeNBT(NBTTagCompound tagCompound){
        tagCompound.setString("class", this.getClass().getCanonicalName());
        tagCompound.setIntArray("coords", new int[] {coordinates.posX, coordinates.posY, coordinates.posZ});
    }

    public void readNBT(NBTTagCompound tagCompound){
        int[] coords = tagCompound.getIntArray("coords");
        coordinates = new ChunkCoordinates(coords[0], coords[1], coords[2]);
    }
}
