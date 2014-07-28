package drunkmafia.thaumicinfusion.client.renderer.item;

import com.sun.javafx.geom.Vec3f;
import drunkmafia.thaumicinfusion.common.block.EssentiaBlock;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.block.tile.InfusionCoreTile;
import drunkmafia.thaumicinfusion.common.util.RGB;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;

import java.lang.reflect.Method;

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
        EssentiaBlock.instance.setBlockBoundsForItemRender();
        renderBlocks.setRenderBoundsFromBlock(EssentiaBlock.instance);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        RGB rgb = new RGB(Aspect.getAspect(tag.getString("aspectTag")).getColor());
        GL11.glColor4f(rgb.getR(), rgb.getG(), rgb.getB(), 1.0F);

        System.out.println("Render");

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderBlocks.renderFaceYNeg(EssentiaBlock.instance, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(EssentiaBlock.instance, 0, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderBlocks.renderFaceYPos(EssentiaBlock.instance, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(EssentiaBlock.instance, 1, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderBlocks.renderFaceZNeg(EssentiaBlock.instance, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(EssentiaBlock.instance, 2, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderBlocks.renderFaceZPos(EssentiaBlock.instance, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(EssentiaBlock.instance, 3, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXNeg(EssentiaBlock.instance, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(EssentiaBlock.instance, 4, stack.getItemDamage()));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderBlocks.renderFaceXPos(EssentiaBlock.instance, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(EssentiaBlock.instance, 5, stack.getItemDamage()));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glPopMatrix();
    }
}
