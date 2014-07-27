package drunkmafia.thaumicinfusion.common.util;

import com.esotericsoftware.reflectasm.MethodAccess;
import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import drunkmafia.thaumicinfusion.common.block.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

public class BlockData extends BlockSavable {

    private int containingID, blockID;

    private ArrayList<MethodAccess> dataAccess = new ArrayList<MethodAccess>();
    private ArrayList<Savable> dataEffects = new ArrayList<Savable>();

    private MethodAccess methodAccess;

    protected BlockData() {
    }

    public BlockData(ChunkCoordinates coords, Class[] list, int containingID, int blockID) {
        super(coords);
        this.blockID = blockID;
        this.containingID = containingID;

        for (Savable effect : classesToEffects(list)){
            dataEffects.add(effect);
            dataAccess.add(MethodAccess.get(effect.getClass()));
        }
    }

    private Savable[] classesToEffects(Class[] list) {
        Savable[] effects = new Savable[list.length];
        for (int i = 0; i < effects.length; i++) {
            try {
                effects[i] = (Savable) list[i].newInstance();
            }catch (Exception e){}
        }
        return effects;
    }

    public Object runMethod(boolean shouldBlockRun, Object... pars) {
        if(methodAccess == null) methodAccess = MethodAccess.get(Block.class);
        String methName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int blockIndex = BlockHandler.getMethod(methName);

        for (int s = 0; s < dataEffects.size(); s++){
            Savable effect = dataEffects.get(s);
            int effectIndex = AspectHandler.getMethod(effect.getClass(), methName);
            if(effectIndex != -1) try {return dataAccess.get(s).invoke(effect, effectIndex, pars);}catch (Exception e){}
        }

        if(shouldBlockRun) {
            try {return methodAccess.invoke(getContainingBlock(),blockIndex, pars);}catch (Exception e){}
        }
        return null;
    }

    public boolean canOpenGUI(){
        for(Savable effect : dataEffects) return AspectHandler.getEffectGUI(effect.getClass()) != null;
        return false;
    }

    public Block getContainingBlock() {
        return Block.getBlockById(containingID);
    }

    public Block getBlock() {
        return Block.getBlockById(blockID);
    }

    public Class[] getEffects() {
        Class[] classes = new Class[dataEffects.size()];
        for (int i = 0; i < classes.length; i++) classes[i] = dataEffects.get(i).getClass();
        return classes;
    }

    public ArrayList<ArrayList<Aspect>> getAspects(){
        ArrayList<ArrayList<Aspect>> aspects = new ArrayList<ArrayList<Aspect>>();
        for(Savable effect : dataEffects) aspects.add(AspectHandler.getAspectsFromEffect(effect.getClass()));
        return aspects;
    }

    public void writeNBT(NBTTagCompound tagCompound) {
        super.writeNBT(tagCompound);
        tagCompound.setInteger("BlockID", blockID);

        tagCompound.setInteger("length", dataEffects.size());
        for (int i = 0; i < dataEffects.size(); i++) {
            NBTTagCompound effectTag = new NBTTagCompound();
            dataEffects.get(i).writeNBT(effectTag);
            tagCompound.setTag("effect: " + i, effectTag);
        }

        tagCompound.setInteger("ContainingID", containingID);
    }

    public void readNBT(NBTTagCompound tagCompound) {
        super.readNBT(tagCompound);
        blockID = tagCompound.getInteger("BlockID");

        for (int i = 0; i < tagCompound.getInteger("length"); i++) {
            dataEffects.add(Savable.loadDataFromNBT(tagCompound.getCompoundTag("effect: " + i)));
            dataAccess.add(MethodAccess.get(dataEffects.get(i).getClass()));
        }

        containingID = tagCompound.getInteger("ContainingID");
    }
}
