package drunkmafia.thaumicinfusion.common.util;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by DrunkMafia on 16/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public interface ISavable {
    public abstract void readTag(NBTTagCompound tagCompound);
    public abstract void writeTag(NBTTagCompound tagCompound);
}
