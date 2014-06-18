package drunkmafia.thaumicinfusion.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import drunkmafia.thaumicinfusion.common.commands.SpawnInfusedBlockCommand;

import static drunkmafia.thaumicinfusion.common.lib.ModInfo.*;

@Mod(modid = MODID_INFO, name = NAME_INFO, version = VERSION_INFO)
public class ThaumicInfusion {

    @Instance(MODID_INFO)
    public static ThaumicInfusion instance;

    @SidedProxy(clientSide = CLIENT_PROXY_PATH, serverSide = COMMON_PROXY_PATH)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLLog.info("TI: Initialization");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLLog.info("TI: Initialization");
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        FMLLog.info("TI: Post Initialization");
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        FMLLog.info("TI server starting test");
        event.registerServerCommand(new SpawnInfusedBlockCommand());
    }
}
