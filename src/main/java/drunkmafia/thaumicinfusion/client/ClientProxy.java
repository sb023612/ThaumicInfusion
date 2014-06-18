package drunkmafia.thaumicinfusion.client;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import drunkmafia.thaumicinfusion.client.renderer.item.InfusedItemRenderer;
import drunkmafia.thaumicinfusion.common.CommonProxy;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.item.TIItems;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("PreInit " + event.getSide().name());
        isServer = event.getSide().isServer();

        TITab.init();

        TIBlocks.init();
        TIItems.init();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        System.out.println("Init " + event.getSide().name());
        MinecraftForgeClient.registerItemRenderer(TIItems.infusedBlock, new InfusedItemRenderer());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("PostInit " + event.getSide().name());
    }
}
