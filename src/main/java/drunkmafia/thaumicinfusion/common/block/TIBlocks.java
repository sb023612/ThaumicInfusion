package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import drunkmafia.thaumicinfusion.common.block.tile.*;
import drunkmafia.thaumicinfusion.common.util.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.Field;
import java.util.List;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 01/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class TIBlocks {

    public static void initBlocks() {
        try {
            List<Class> classesInPath = ReflectionHelper.getClasses("drunkmafia.thaumicinfusion.common.block");
            for(Class c : classesInPath){
                if(c != null && TIBlock.class.isAssignableFrom(c)) {
                    Block block = (Block) c.newInstance();
                    Field instance = c.getField("instance");
                    instance.setAccessible(true);
                    instance.set(null, block);

                    String unlocal = block.getUnlocalizedName();

                    GameRegistry.registerBlock(block, "reg_" + unlocal);
                    if(ITileEntityProvider.class.isAssignableFrom(c)){
                        GameRegistry.registerTileEntity(((ITileEntityProvider)block).createNewTileEntity(null, 0).getClass(), unlocal + "_TILE");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(InfusedBlock.instance != null);

        BlockHandler.init();
    }
}
