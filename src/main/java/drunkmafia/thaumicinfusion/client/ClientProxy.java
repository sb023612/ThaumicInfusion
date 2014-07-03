package drunkmafia.thaumicinfusion.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import drunkmafia.thaumicinfusion.client.cape.CapeHandler;
import drunkmafia.thaumicinfusion.client.renderer.item.*;
import drunkmafia.thaumicinfusion.client.renderer.tile.EssentiaConstructorRenderer;
import drunkmafia.thaumicinfusion.common.CommonProxy;
import drunkmafia.thaumicinfusion.common.ThaumicInfusion;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.block.tile.EssentiaConstructorTile;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import static drunkmafia.thaumicinfusion.common.block.TIBlocks.*;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ThaumicInfusion.isServer = event.getSide().isServer();
        initBlocks();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        System.out.println("Init " + event.getSide().name());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TIBlocks.essentiaBlock), new EssentiaBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(EssentiaConstructorTile.class, new EssentiaConstructorRenderer());

        //initRecipies();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("PostInit " + event.getSide().name());
        CapeHandler.init();
    }
}
