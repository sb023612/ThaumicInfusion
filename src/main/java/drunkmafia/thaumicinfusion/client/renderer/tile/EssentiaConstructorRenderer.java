package drunkmafia.thaumicinfusion.client.renderer.tile;

import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by DrunkMafia on 02/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EssentiaConstructorRenderer extends TileEntitySpecialRenderer {

    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID_INFO, "models/essentia_Constructor.obj"));

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tpf) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.45F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID_INFO, "textures/models/EssentiaConstructor.png"));
        model.renderAll();
        GL11.glPopMatrix();
    }
}
