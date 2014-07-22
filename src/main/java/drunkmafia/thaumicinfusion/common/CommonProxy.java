package drunkmafia.thaumicinfusion.common;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import drunkmafia.thaumicinfusion.common.container.InfusedBlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import static drunkmafia.thaumicinfusion.common.block.TIBlocks.initBlocks;

public class CommonProxy implements IGuiHandler {

    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("PreInit " + event.getSide().name());
        ThaumicInfusion.isServer = event.getSide().isServer();
        initBlocks();
    }

    public void init(FMLInitializationEvent event) {
        System.out.println("Init " + event.getSide().name());
    }

    public void postInit(FMLPostInitializationEvent event) {
        System.out.println("PostInit " + event.getSide().name());
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID){
            case 0: return new InfusedBlockContainer();
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}
}
