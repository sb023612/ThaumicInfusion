package drunkmafia.thaumicinfusion.client.renderer.item;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class InfusedItemRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        NBTTagCompound tag = item.stackTagCompound;
        if (tag != null) {
            GL11.glPushMatrix();

            switch (type) {
                case ENTITY:
                    GL11.glScalef(0.5F, 0.5F, 0.5F);
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                    break;
                default:
                    GL11.glScalef(1F, 1F, 1F);
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
            GL11.glRotatef(180, 1, 0, 0);
            TextureManager textMan = Minecraft.getMinecraft().getTextureManager();
            textMan.bindTexture(textMan.getResourceLocation(0));
            ItemStack stack = new ItemStack(Block.getBlockFromName(tag.getString("block")));
            if (stack != null) {
                RenderManager.instance.itemRenderer.renderItem(null, stack, 10);
            }
            GL11.glPopMatrix();

        }
    }
}
