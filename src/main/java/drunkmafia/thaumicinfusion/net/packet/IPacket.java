package drunkmafia.thaumicinfusion.net.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

/**
 * Created by DrunkMafia on 20/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public interface IPacket {
    public void readBytes(ByteBuf bytes) throws IOException;

    public void writeBytes(ByteBuf bytes) throws IOException;

    public void executeClient(EntityPlayer player) throws IOException;

    public void executeServer(EntityPlayer player) throws IOException;
}
