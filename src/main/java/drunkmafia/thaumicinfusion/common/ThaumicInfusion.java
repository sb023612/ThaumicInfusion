package drunkmafia.thaumicinfusion.common;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import drunkmafia.thaumicinfusion.common.commands.SpawnInfusedBlockCommand;

import static drunkmafia.thaumicinfusion.common.lib.ModInfo.*;

@Mod(modid = MODID_INFO, name = NAME_INFO, version = VERSION_INFO)
public class ThaumicInfusion {

    @Instance(MODID_INFO)
    public static ThaumicInfusion instance;

    @SidedProxy(clientSide = CLIENT_PROXY_PATH, serverSide = COMMON_PROXY_PATH)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event){
        event.registerServerCommand(new SpawnInfusedBlockCommand());
    }
}
