package drunkmafia.thaumicinfusion.client.renderer.tile;

import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.block.tile.EssentiaConstructorTile;
import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;

/**
 * Created by DrunkMafia on 02/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EssentiaConstructorRenderer extends TileEntitySpecialRenderer {

    private IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID_INFO, "models/essentia_Constructor.obj"));

    private float yRotation = 0, speed = 2;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tpf) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.45F, (float) z + 0.5F);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID_INFO, "textures/models/EssentiaConstructor.png"));
        model.renderAll();
        GL11.glPopMatrix();

        renderStack((EssentiaConstructorTile)tile, x, y, z, tpf);
    }

    public void renderStack(EssentiaConstructorTile tile, double x, double y, double z, float tpf){
        if(tile.getStackInSlot(0) != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GL11.glRotatef(yRotation, 0, 1, 0);

            EntityItem entityitem = new EntityItem(tile.getWorldObj(), 0.0D, 0.0D, 0.0D, tile.getStackInSlot(0));
            entityitem.hoverStart = 0.0F;
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            GL11.glPopMatrix();
            yRotation += speed * tpf;
        }
    }
}
