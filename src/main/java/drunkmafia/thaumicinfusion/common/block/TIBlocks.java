package drunkmafia.thaumicinfusion.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import drunkmafia.thaumicinfusion.common.block.tile.EssentiaConstructorTile;
import net.minecraft.block.Block;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 01/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class TIBlocks {
    public static Block infusedBlock;
    public static Block essentiaBlock;
    public static Block essentiaConstrcutor;

    public static void initBlocks(){
        infusedBlock = new InfusedBlock();
        essentiaBlock = new EssentiaBlock();
        essentiaConstrcutor = new EssentiaConstructor();

        GameRegistry.registerBlock(essentiaBlock, essentiaBlock_RegistryName);
        GameRegistry.registerBlock(infusedBlock, infusedBlock_RegistryName);
        GameRegistry.registerBlock(essentiaConstrcutor, essentiaConstructorBlock_RegistryName);

        GameRegistry.registerTileEntity(EssentiaConstructorTile.class, essentiaConstructorBlock_TileEntity);
    }
/**
    public static void initRecipies(){
        System.out.println("Init Recipies");
        ArrayList<ItemStack> essentiaBlocks = new ArrayList<ItemStack>();
        essentiaBlock.getSubBlocks(null, null, essentiaBlocks);
        for (Aspect tag : Aspect.aspects.values()){

            ItemStack stack = new ItemStack(essentiaBlock, 1);
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setString("aspectTag", tag.getTag());
            stack.setTagCompound(tagCompound);

            ItemStack phial = new ItemStack(ConfigItems.itemEssence);
            ((ItemEssence)ConfigItems.itemEssence).setAspects(phial, new AspectList().add(tag, 8));
            GameRegistry.addShapelessRecipe(stack, phial);

            ItemStack brick = stack.copy();
            brick.setItemDamage(1);
            GameRegistry.addShapelessRecipe(brick, stack);

            ItemStack chisled = stack.copy();
            chisled.setItemDamage(2);
            GameRegistry.addShapelessRecipe(chisled, brick);
        }
    }**/
}
