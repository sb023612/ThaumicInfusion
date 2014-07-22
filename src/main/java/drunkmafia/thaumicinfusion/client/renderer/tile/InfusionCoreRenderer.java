package drunkmafia.thaumicinfusion.client.renderer.tile;

import com.sun.javafx.geom.Vec3f;
import drunkmafia.thaumicinfusion.common.block.tile.InfusionCoreTile;
import drunkmafia.thaumicinfusion.common.block.tile.JarPedestalTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.tile.ModelCube;

import static drunkmafia.thaumicinfusion.common.lib.BlockInfo.*;

/**
 * Created by DrunkMafia on 19/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class InfusionCoreRenderer extends TileEntitySpecialRenderer {

    private IModelCustom model = AdvancedModelLoader.loadModel(infusionCore_Model);

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float deltaTime) {
        renderCore(new Vec3f((float)x + 0.5F,(float) y + 0.5F,(float) z + 0.5F), new Vec3f(1.4F, 1.3F, 1.4F), new Vec3f(0, 0, 0), 0);
        renderCore(new Vec3f((float)x + 0.5F,(float) y + 0.5F,(float) z + 0.5F), new Vec3f(1F, 1.3F, 1F), new Vec3f(0, 0, 0), 0);

        InfusionCoreTile coreTile = (InfusionCoreTile) tile;

        if(!coreTile.hasStack()) return;

        ItemStack jar = coreTile.getStack();
        jar.stackSize = 1;
        EntityItem entityitem = new EntityItem(tile.getWorldObj(), 0.0D, 0.0D, 0.0D, jar);
        entityitem.hoverStart = 0.0F;

        GL11.glPushMatrix();

        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
        GL11.glScalef(1.6F, 1.6F, 1.6F);

        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);

        GL11.glPopMatrix();
    }

    public void renderCore(Vec3f coords, Vec3f scale, Vec3f rotation, float angle){
        GL11.glPushMatrix();

        GL11.glTranslatef(coords.x, coords.y, coords.z);
        GL11.glScalef(scale.x, scale.y, scale.z);
        GL11.glRotatef(angle, rotation.x, rotation.y, rotation.z);

        Minecraft.getMinecraft().renderEngine.bindTexture(infusionCore_Texture);
        model.renderAll();

        GL11.glPopMatrix();
    }
}
