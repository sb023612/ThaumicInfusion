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
import drunkmafia.thaumicinfusion.common.aspect.effect.TIEffects;
import drunkmafia.thaumicinfusion.common.commands.InfusedInWorldCommand;
import drunkmafia.thaumicinfusion.common.commands.SpawnInfusedBlockCommand;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import drunkmafia.thaumicinfusion.net.ChannelHandler;

import static drunkmafia.thaumicinfusion.common.lib.ModInfo.*;

@Mod(modid = MODID_INFO, name = NAME_INFO, version = VERSION_INFO)
public class ThaumicInfusion {

    @Instance(MODID_INFO)
    public static ThaumicInfusion instance;

    @SidedProxy(clientSide = CLIENT_PROXY_PATH, serverSide = COMMON_PROXY_PATH)
    public static CommonProxy proxy;

    public static boolean isServer;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TITab.init();
        TIEffects.init();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ChannelHandler.init();
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        FMLLog.info("TI server starting test");
        event.registerServerCommand(new SpawnInfusedBlockCommand());
        event.registerServerCommand(new InfusedInWorldCommand());
    }
}
