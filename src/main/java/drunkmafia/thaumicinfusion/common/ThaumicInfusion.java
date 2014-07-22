package drunkmafia.thaumicinfusion.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import drunkmafia.thaumicinfusion.common.aspect.effect.TIEffects;
import drunkmafia.thaumicinfusion.common.commands.InfusedInWorldCommand;
import drunkmafia.thaumicinfusion.common.commands.SpawnInfusedBlockCommand;
import drunkmafia.thaumicinfusion.common.tab.TITab;
import drunkmafia.thaumicinfusion.net.ChannelHandler;
import net.minecraft.item.Item;

import static drunkmafia.thaumicinfusion.common.lib.ModInfo.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class ThaumicInfusion {

    @Instance(MODID)
    public static ThaumicInfusion instance;

    @SidedProxy(clientSide = CLIENT_PROXY_PATH, serverSide = COMMON_PROXY_PATH)
    public static CommonProxy proxy;

    public static boolean isServer;
    public Item debug;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TITab.init();
        TIEffects.init();
        proxy.preInit(event);

        debug = new DebugItem();

        GameRegistry.registerItem(debug, "debug");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ChannelHandler.init();
        proxy.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new SpawnInfusedBlockCommand());
        event.registerServerCommand(new InfusedInWorldCommand());
    }
}
