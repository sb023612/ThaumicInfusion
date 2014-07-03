package drunkmafia.thaumicinfusion.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import drunkmafia.thaumicinfusion.common.ThaumicInfusion;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.ChunkEvent;

/**
 * Created by DrunkMafia on 27/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EventHookContainerClass {

    @SubscribeEvent
    public void blockLoadEvent(ChunkEvent.Load load){
        //ThaumicInfusion.channelHandler.sendTo(new RequestChunkBlocksPacket(load.getChunk()), Minecraft.getMinecraft().thePlayer);
    }

}
