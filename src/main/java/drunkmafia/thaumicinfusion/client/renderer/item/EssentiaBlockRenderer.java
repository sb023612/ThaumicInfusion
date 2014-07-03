package drunkmafia.thaumicinfusion.client.renderer.item;

import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;

/**
 * Created by DrunkMafia on 01/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EssentiaBlockRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null || stack == null) return;

        RenderBlocks renderBlocks = new RenderBlocks();
        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        TIBlocks.essentiaBlock.setBlockBoundsForItemRender();
        renderBlocks.setRenderBoundsFromBlock(TIBlocks.essentiaBlock);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderBlocks.renderFaceYNeg(TIBlocks.essentiaBlock, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(TIBlocks.essentiaBlock, 0, stack.getItemDamage()));
        tessellator.draw();

        int rgb = Aspect.getAspect(tag.getString("aspectTag")).getColor();
        float f2 = (float)(rgb >> 16 & 255) / 255.0F;
        float f3 = (float)(rgb >> 8 & 255) / 255.0F;
        float f4 = (float)(rgb & 255) / 255.0F;
        GL11.glColor4f(f2, f3, f4, 1.0F);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(TIBlocks.essentiaBlock, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(TIBlocks.essentiaBlock, 1, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(TIBlocks.essentiaBlock, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(TIBlocks.essentiaBlock, 2, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceZPos(TIBlocks.essentiaBlock, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(TIBlocks.essentiaBlock, 3, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(TIBlocks.essentiaBlock, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(TIBlocks.essentiaBlock, 4, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(TIBlocks.essentiaBlock, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(TIBlocks.essentiaBlock, 5, stack.getItemDamage()));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glPopMatrix();
    }
}
