package drunkmafia.thaumicinfusion.common;

import cpw.mods.fml.common.event.*;

import static drunkmafia.thaumicinfusion.common.block.TIBlocks.*;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("PreInit " + event.getSide().name());
        ThaumicInfusion.isServer = event.getSide().isServer();
        initBlocks();
    }

    public void init(FMLInitializationEvent event) {
        System.out.println("Init " + event.getSide().name());

        //initRecipies();

    }

    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("PostInit " + event.getSide().name());
    }
}
