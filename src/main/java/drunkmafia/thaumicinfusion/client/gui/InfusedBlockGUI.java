package drunkmafia.thaumicinfusion.client.gui;

import cpw.mods.fml.client.FMLClientHandler;
import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import drunkmafia.thaumicinfusion.common.container.InfusedBlockContainer;
import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import drunkmafia.thaumicinfusion.common.util.BlockData;
import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.EffectGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

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

    private EffectGUI currentEffect;

    public InfusedBlockGUI(ChunkCoordinates coordinates) {
        super(new InfusedBlockContainer());

        xSize = 180;
        ySize = 104;

        world = FMLClientHandler.instance().getClient().theWorld;
        data = (BlockData) BlockHelper.getData(world, coordinates);
    }

    public void setupEffect(ArrayList<Aspect> aspects){
        if(aspects != null){
            Class effect = AspectHandler.getEffectFromList(slider.getSelectedEffect());
            if(effect != null){
                currentEffect = AspectHandler.getEffectGUI(effect);
                if(currentEffect != null) {
                    currentEffect.fontRendererObj = fontRendererObj;

                    currentEffect.guiTop = (this.height - this.ySize) / 2 + 20;
                    currentEffect.guiLeft = (this.width - this.xSize) / 2 + 20;

                    currentEffect.initGui();
                }
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        slider = new Slider(this, (guiLeft + (xSize / 2)) - (118 / 2), guiTop + ySize + 10, data.getAspects());
        setupEffect(slider.getSelectedEffect());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float tpf, int mouseX, int mouseY) {
        if(data != null){
            Minecraft.getMinecraft().renderEngine.bindTexture(gui);
            drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

            if(currentEffect != null) currentEffect.drawGuiContainerBackgroundLayer(tpf, mouseX, mouseY);

            slider.drawGuiContainerBackgroundLayer(tpf, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        if(data != null){
            if(currentEffect != null) currentEffect.drawGuiContainerForegroundLayer(mouseX, mouseY);

            slider.drawGuiContainerForegroundLayer(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int lastButtonClicked) {
        super.mouseClicked(mouseX, mouseY, lastButtonClicked);
        slider.mouseClicked(mouseX, mouseY, lastButtonClicked);
        setupEffect(slider.getSelectedEffect());
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        slider.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        setupEffect(slider.getSelectedEffect());
    }

    protected int getLeft(){
        return guiLeft;
    }

    protected int getTop(){
        return guiTop;
    }

    protected FontRenderer getFontRenderer(){
        return fontRendererObj;
    }
}
