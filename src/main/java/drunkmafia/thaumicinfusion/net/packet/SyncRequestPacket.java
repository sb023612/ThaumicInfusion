package drunkmafia.thaumicinfusion.net.packet;

import cpw.mods.fml.common.FMLLog;
import drunkmafia.thaumicinfusion.common.ThaumicInfusion;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.Vector3F;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.io.IOException;

/**
 * Created by DrunkMafia on 20/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class SyncRequestPacket implements IPacket {

    public SyncRequestPacket() {
    }

    private Vector3F coords;

    public SyncRequestPacket(Vector3F vec) {
        coords = vec;
    }

    @Override
    public void readBytes(ByteBuf bytes) throws IOException {
        coords = new Vector3F(bytes.readInt(), bytes.readInt(), bytes.readInt());
    }

    @Override
    public void writeBytes(ByteBuf bytes) throws IOException {
        bytes.writeInt((int) coords.x);
        bytes.writeInt((int) coords.y);
        bytes.writeInt((int) coords.z);
    }

    @Override
    public void executeClient(EntityPlayer player) throws IOException {
        FMLLog.info("TI: Sync request ignored, Client does not know how to process");
    }

    @Override
    public void executeServer(EntityPlayer player) throws IOException {
        BlockData data = (BlockData) BlockHelper.getWorldData(player.worldObj).getBlock(coords);
        if (data != null) ThaumicInfusion.channelHandler.sendTo(new BlockSyncPacket(data), (EntityPlayerMP) player);
    }
}
