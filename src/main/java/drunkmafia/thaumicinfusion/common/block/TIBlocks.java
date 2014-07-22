package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import drunkmafia.thaumicinfusion.common.block.tile.*;
import net.minecraft.block.Block;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 01/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class TIBlocks {
    public static final Block infusedBlock = new InfusedBlock();
    public static final Block essentiaBlock = new EssentiaBlock();
    public static final Block jarPedestalBlock = new JarPedestalBlock();
    public static final Block infusionCoreBlock = new InfusionCoreBlock();

    public static void initBlocks() {
        GameRegistry.registerBlock(essentiaBlock, essentiaBlock_RegistryName);
        GameRegistry.registerBlock(infusedBlock, infusedBlock_RegistryName);
        GameRegistry.registerBlock(jarPedestalBlock, jarPedestalBlock_RegistryName);
        GameRegistry.registerBlock(infusionCoreBlock, infusionCoreBlock_RegistryName);

        GameRegistry.registerTileEntity(JarPedestalTile.class, jarPedestalBlock_TileEntity);
        GameRegistry.registerTileEntity(InfusionCoreTile.class, infusionCoreBlock_TileEntity);

        BlockHandler.init();
    }
}
