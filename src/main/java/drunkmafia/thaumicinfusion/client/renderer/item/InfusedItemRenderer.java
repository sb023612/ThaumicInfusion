package drunkmafia.thaumicinfusion.client.renderer.item;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.item.EntityItem;
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

            ItemStack jar = new ItemStack(Block.getBlockById(tag.getInteger("infusedID")), tag.getInteger("infusedMETA"));
            jar.stackSize = 1;
            EntityItem entityitem = new EntityItem(FMLClientHandler.instance().getClient().theWorld, 0.0D, 0.0D, 0.0D, jar);
            entityitem.hoverStart = 0.0F;

            GL11.glPushMatrix();

            GL11.glScalef(1.6F, 1.6F, 1.6F);

            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);

            GL11.glPopMatrix();

        }
    }
}
