package drunkmafia.thaumicinfusion.net.packet.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.BlockSavable;
import drunkmafia.thaumicinfusion.net.ChannelHandler;
import drunkmafia.thaumicinfusion.net.packet.server.BlockSyncPacketC;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChunkCoordinates;

import java.io.IOException;

/**
 * Created by DrunkMafia on 28/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class RequestBlockPacketS implements IMessage {

    public RequestBlockPacketS(){}

    private ChunkCoordinates coordinates;

    public RequestBlockPacketS(ChunkCoordinates coordinates){
        this.coordinates = coordinates;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        coordinates = new ChunkCoordinates(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(coordinates.posX);
        buf.writeInt(coordinates.posY);
        buf.writeInt(coordinates.posZ);
    }

    public static class Handler implements IMessageHandler<RequestBlockPacketS, IMessage> {

        @Override
        public IMessage onMessage(RequestBlockPacketS message, MessageContext ctx) {
            if(message.coordinates == null || ctx.side.isClient()) return null;
            BlockSavable data = BlockHelper.getData(ChannelHandler.getPlayer(ctx).worldObj, message.coordinates);
            if(data != null)
                return new BlockSyncPacketC(data);
            return null;
        }
    }
}
