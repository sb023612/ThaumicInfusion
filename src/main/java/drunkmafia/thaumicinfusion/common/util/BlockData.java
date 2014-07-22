package drunkmafia.thaumicinfusion.common.util;

import com.esotericsoftware.reflectasm.MethodAccess;
import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockData extends BlockSavable {

    private int containingID, blockID;

    private ArrayList<Savable> dataEffects = new ArrayList<Savable>();

    protected BlockData() {
    }

    public BlockData(ChunkCoordinates coords, Class[] list, int containingID, int blockID) {
        super(coords);
        this.blockID = blockID;
        this.containingID = containingID;
        for (Savable effect : classesToEffects(list)) dataEffects.add(effect);
    }

    private Savable[] classesToEffects(Class[] list) {
        Savable[] effects = new Savable[list.length];
        for (int i = 0; i < effects.length; i++)
            try {
                effects[i] = (Savable) list[i].newInstance();
            } catch (Exception E) {
            }
        return effects;
    }

    public Object runMethod(boolean shouldBlockRun, String methName, Object... pars) {
        try {
            for(Savable savable : dataEffects){
                MethodAccess blockAccess = MethodAccess.get(savable.getClass());
                if(Arrays.asList(blockAccess.getMethodNames()).contains(methName)) return blockAccess.invoke(savable, methName, pars);
            }
            if(shouldBlockRun) {
                MethodAccess blockAccess = MethodAccess.get(getContainingBlock().getClass());
                if(Arrays.asList(blockAccess.getMethodNames()).contains(methName))
                    return blockAccess.invoke(getContainingBlock(), methName, pars);
                else{
                    blockAccess = MethodAccess.get(Block.class);
                    if(Arrays.asList(blockAccess.getMethodNames()).contains(methName)) blockAccess.invoke(getContainingBlock(), methName, pars);
                }
            }
        }catch (Exception e){System.out.println("Failed: " + methName);}
        return null;
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
        Class[] classes = getEffects();
        ArrayList<ArrayList<Aspect>> aspects = new ArrayList<ArrayList<Aspect>>();
        for(Class c : classes) aspects.add(AspectHandler.getAspectsFromEffect(c));
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

        for (int i = 0; i < tagCompound.getInteger("length"); i++)
            dataEffects.add(Savable.loadDataFromNBT(tagCompound.getCompoundTag("effect: " + i)));

        containingID = tagCompound.getInteger("ContainingID");
    }
}
