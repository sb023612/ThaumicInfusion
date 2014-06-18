package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

public class TIBlocks {

    public static Block infusedBlock;

    public static void init() {
        infusedBlock = new InfusedBlock();

        GameRegistry.registerBlock(infusedBlock, infusedBlock_RegistryName);
    }
}
