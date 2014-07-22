package drunkmafia.thaumicinfusion.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.jarPedestalBlock_Model;
import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.jarPedestalBlock_Texture;

/**
 * Created by DrunkMafia on 20/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class JarPedestalItemRenderer implements IItemRenderer {

    private IModelCustom model = AdvancedModelLoader.loadModel(jarPedestalBlock_Model);

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return false;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        Minecraft.getMinecraft().renderEngine.bindTexture(jarPedestalBlock_Texture);
        model.renderAll();

        GL11.glPopMatrix();
    }
}
