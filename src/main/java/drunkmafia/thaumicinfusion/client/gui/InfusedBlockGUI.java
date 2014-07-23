package drunkmafia.thaumicinfusion.client.gui;

import cpw.mods.fml.client.FMLClientHandler;
import drunkmafia.thaumicinfusion.common.container.InfusedBlockContainer;
import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by DrunkMafia on 12/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class InfusedBlockGUI extends GuiContainer {

    protected BlockData data;
    private World world;
    private ResourceLocation gui = new ResourceLocation(ModInfo.MODID, "textures/gui/InfusedGUI.png");
    private Slider slider;

    public InfusedBlockGUI(ChunkCoordinates coordinates) {
        super(new InfusedBlockContainer());

        xSize = 180;
        ySize = 104;

        world = FMLClientHandler.instance().getClient().theWorld;
        data = (BlockData) BlockHelper.getData(world, coordinates);
    }

    @Override
    public void initGui() {
        super.initGui();
        slider = new Slider(this, (guiLeft + (xSize / 2)) - 118, guiTop + ySize + 10, data.getAspects());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float tpf, int mouseX, int mouseY) {
        if(data != null){
            Minecraft.getMinecraft().renderEngine.bindTexture(gui);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

            slider.drawGuiContainerBackgroundLayer(tpf, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        if(data != null){
            slider.drawGuiContainerForegroundLayer(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        slider.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
    }

    public int getLeft(){
        return guiLeft;
    }

    public int getTop(){
        return guiTop;
    }

    private float rotationY, speedRot = 0.5F;

    public void renderBlock(float tpf, int mouseX, int mouseY){
        ItemStack block = new ItemStack(data.getContainingBlock(), world.getBlockMetadata(data.getCoords().posX, data.getCoords().posY, data.getCoords().posZ));
        block.stackSize = 1;
        EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, block);
        entityitem.hoverStart = 0.0F;

        rotationY += speedRot * tpf;

        GL11.glPushMatrix();
        GL11.glColor4f(0F, 0F, 0F, 0.3F);
        GL11.glTranslatef(guiLeft + (xSize / 2), guiTop + (ySize / 2), 100);
        float scale = xSize;
        GL11.glScalef(-scale, scale, scale);

        GL11.glRotatef(rotationY, 0, 1, 0);

        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0, 0, 0, 0, 0);

        RenderHelper.disableStandardItemLighting();

        GL11.glPopMatrix();
    }
}
