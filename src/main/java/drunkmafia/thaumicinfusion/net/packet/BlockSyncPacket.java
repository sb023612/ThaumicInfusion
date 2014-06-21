package drunkmafia.thaumicinfusion.net.packet;

import cpw.mods.fml.common.FMLLog;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.PacketHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

/**
 * Created by DrunkMafia on 20/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class BlockSyncPacket implements IPacket {

    public BlockSyncPacket() {
    }

    private BlockData data;

    public BlockSyncPacket(BlockData data) {
        this.data = data;
    }

    @Override
    public void readBytes(ByteBuf bytes) throws IOException {
        data = new BlockData();
        data.readTag(PacketHelper.readNBTTagCompoundFromBuffer(bytes));
    }

    @Override
    public void writeBytes(ByteBuf bytes) throws IOException {
        NBTTagCompound tag = new NBTTagCompound();
        data.writeTag(tag);
        PacketHelper.writeNBTTagCompoundToBuffer(bytes, tag);
    }

    @Override
    public void executeClient(EntityPlayer player) throws IOException {
        BlockHelper.getWorldData(player.worldObj).addBlock(data);
    }

    @Override
    public void executeServer(EntityPlayer player) throws IOException {
        FMLLog.info("TI: Sync request ignored, Server does not know how to process");
    }
}
