package drunkmafia.thaumicinfusion.common;

import cpw.mods.fml.common.event.*;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.item.TIItems;
import drunkmafia.thaumicinfusion.common.tab.TITab;

public class CommonProxy {

    public boolean isServer;

    public void preInit(FMLPreInitializationEvent event){
        System.out.println("PreInit " + event.getSide().name());
        isServer = event.getSide().isServer();

        TITab.init();

        TIBlocks.init();
        TIItems.init();
    }

    public void init(FMLInitializationEvent event){
        System.out.println("Init " + event.getSide().name());
    }

    public void postInit(FMLPostInitializationEvent event){
        System.out.println("PostInit " + event.getSide().name());
    }
}
