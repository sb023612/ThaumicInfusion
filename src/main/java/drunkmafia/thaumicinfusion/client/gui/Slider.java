package drunkmafia.thaumicinfusion.client.gui;

import drunkmafia.thaumicinfusion.common.block.InfusedBlock;
import drunkmafia.thaumicinfusion.common.lib.ModInfo;
import drunkmafia.thaumicinfusion.common.util.RGB;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

/**
 * Created by DrunkMafia on 22/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class Slider {

    protected InfusedBlockGUI gui;
    private ResourceLocation slider = new ResourceLocation(ModInfo.MODID, "textures/gui/Slider.png");
    private int guiLeft, guiTop, posX, posY, sliderX, xSize = 118, ySize = 12, frame, maxFrames = 5;
    private float cooldown, timeFrame = 2, incrementing = 0.5F;

    private ArrayList<SliderSection> section = new ArrayList<SliderSection>();

    public Slider(InfusedBlockGUI gui, int posX, int posY, ArrayList<ArrayList<Aspect>> sections){
        this.gui = gui;
        this.posX = posX;
        this.posY = posY;
        guiLeft = gui.getLeft();
        guiTop = gui.getTop();

        sliderX = posX + (118 / 2);
        sections = new ArrayList<ArrayList<Aspect>>();
        ArrayList<Aspect> aspects1 = new ArrayList<Aspect>();
        aspects1.add(Aspect.MOTION);
        ArrayList<Aspect> aspects2 = new ArrayList<Aspect>();
        aspects2.add(Aspect.DARKNESS);
        sections.add(aspects1);
        sections.add(aspects2);

        for(int i = 0; i < sections.size(); i++){
            ArrayList<Aspect> aspect = sections.get(i);

            int sectionSizeX = xSize;
            if(sections.size() > 1) sectionSizeX = xSize / sections.size();
            int sectionPosX = posX;
            if(i > 0) sectionPosX += sectionSizeX * i;

            section.add(new SliderSection(this, aspect, sectionPosX, posY + 3, sectionSizeX * i, sectionSizeX, 6));
        }
    }

    public void drawGuiContainerBackgroundLayer(float tpf, int mouseX, int mouseY) {
        gui.drawTexturedModalRect(posX, posY, 0, 107, xSize, ySize);

        for(SliderSection aspect : section) aspect.drawGuiContainerBackgroundLayer(tpf, mouseX, mouseY);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        gui.drawTexturedModalRect(sliderX, posY, 122, 107, 14, 14);
    }

    protected void drawGuiContainerForegroundLayer(int mouseY, int mouseX) {

    }

    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        sliderX = mouseX;
    }

    public class SliderSection {

        private Slider slider;
        private ArrayList<Aspect> aspects;
        private RGB rgb;
        private int posX, posY, u, xSize, ySize;

        public SliderSection(Slider slider, ArrayList<Aspect> aspects, int posX, int posY, int u, int xSize, int ySize){
            this.slider = slider;
            this.aspects = aspects;
            this.posX = posX;
            this.posY = posY;
            this.u = u;
            this.xSize = xSize;
            this.ySize = ySize;
            rgb = new RGB(aspects.get(0).getColor());
        }

        public void drawGuiContainerBackgroundLayer(float tpf, int mouseX, int mouseY) {
            frame++;
            if(frame >= maxFrames) frame = 1;

            GL11.glColor3f(rgb.getR(), rgb.getG(), rgb.getB());

            slider.gui.drawTexturedModalRect(posX, posY, u, 122 + (6 * frame), xSize, ySize);
        }
    }
}
