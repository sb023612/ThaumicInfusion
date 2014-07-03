package drunkmafia.thaumicinfusion.common.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

/**
 * Created by DrunkMafia on 20/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class PacketHelper {

    /**
     * Writes a compressed NBTTagCompound to a buffer
     */
    public static void writeNBTTagCompoundToBuffer(ByteBuf bytes, NBTTagCompound tag) throws IOException{
        if (tag == null){
            bytes.writeShort(-1);
        }else{
            byte[] abyte = CompressedStreamTools.compress(tag);
            bytes.writeShort((short)abyte.length);
            bytes.writeBytes(abyte);
        }
    }

    /**
     * Reads a compressed NBTTagCompound from a buffer
     */
    public static NBTTagCompound readNBTTagCompoundFromBuffer(ByteBuf bytes) throws IOException{
        short short1 = bytes.readShort();

        if (short1 < 0){
            return null;
        }else{
            byte[] abyte = new byte[short1];
            bytes.readBytes(abyte);
            return CompressedStreamTools.decompress(abyte);
        }
    }
}
