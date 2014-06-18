package drunkmafia.thaumicinfusion.common.util;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by DrunkMafia on 18/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockSavable implements ISavable {
    public int blockID;
    public Vector3F coords;

    public BlockSavable(){};

    public BlockSavable(int blockID, Vector3F coords){
        this.blockID = blockID;
        this.coords = coords;
    }

    @Override
    public void readTag(NBTTagCompound tag) {
        blockID = tag.getInteger("BlockID");
        int[] coordInts = tag.getIntArray("coords");
        coords = new Vector3F(coordInts[0], coordInts[1], coordInts[2]);
    }

    @Override
    public void writeTag(NBTTagCompound tag) {
        tag.setInteger("BlockID", blockID);
        tag.setIntArray("coords", new int[] {(int)coords.x, (int)coords.y, (int)coords.z});
    }
}
