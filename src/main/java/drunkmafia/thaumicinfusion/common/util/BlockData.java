package drunkmafia.thaumicinfusion.common.util;

import drunkmafia.thaumicinfusion.common.aspect.AspectEffect;
import drunkmafia.thaumicinfusion.common.block.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class BlockData extends BlockSavable{

    private int containingID, blockID;

    private ArrayList<AspectEffect> dataEffects = new ArrayList<AspectEffect>();

    protected BlockData(){}

    public BlockData(ChunkCoordinates coords, Class[] list, int containingID, int blockID) {
        super(coords);
        this.blockID = blockID;
        this.containingID = containingID;
        for(AspectEffect effect : classesToEffects(list)) dataEffects.add(effect);
    }

    private AspectEffect[] classesToEffects(Class[] list){
        AspectEffect[] effects = new AspectEffect[list.length];
        for(int i = 0; i < effects.length; i++) try{  effects[i] = (AspectEffect) list[i].newInstance(); }catch (Exception E){}
        return effects;
    }

    private Class[] objsToClasses(Object... objs){
        Class[] classes = new Class[objs.length];
        for(int i = 0; i < objs.length; i++) classes[i] = objs[i].getClass();
        return classes;
    }

    public Method getMethod(Class c, String methName, Class[] classes){
        Method[] methods = c.getDeclaredMethods();
        for(Method meth : methods){
            if(meth.getName().matches(methName) && meth.getParameterTypes().length == classes.length){
                //Prevents Infinte loops
                if(c == Block.class && !BlockHandler.isMethodSafe(meth)) return null;
                return meth;
            }
        }
        return null;
    }

    public Object runBlockMethods(String methName, Object... pars) throws Exception{
        Method meth = getMethod(getContainingBlock().getClass(), methName, objsToClasses(pars));
        if(meth != null) return meth.invoke(getContainingBlock(), methName, pars);
        else{
            meth = getMethod(Block.class, methName, objsToClasses(pars));
            if(meth != null) return meth.invoke(getContainingBlock(), methName, pars);
        }
        return null;
    }

    public Object runEffectMethods(String methName, Object... pars) throws Exception{
        for(AspectEffect effect : dataEffects){
            Method meth = getMethod(effect.getClass(), methName, objsToClasses(pars));
            if(meth != null && meth.getAnnotation(BlockSubscribe.class) != null){
                return meth.invoke(effect, pars);
            }
        }
        return null;
    }

    public Object runMethod(String methName, Object... pars) {
        try{
            Object obj = runEffectMethods(methName, pars);
            if(obj != null) return obj;
            return runBlockMethods(methName, pars);
        }catch (Exception e){
            System.out.println("Failed To Run: " + methName);
            for(Class c : objsToClasses(pars)) System.out.println(c.getName());
        }
        return null;
    }

    public Block getContainingBlock() {
        return Block.getBlockById(containingID);
    }

    public Block getBlock() {
        return Block.getBlockById(blockID);
    }

    public Class[] getAspects(){
        Class[] classes = new Class[dataEffects.size()];
        for(int i = 0; i < classes.length; i++) classes[i] = dataEffects.get(i).getClass();
        return classes;
    }

    public void writeNBT(NBTTagCompound tagCompound) {
        super.writeNBT(tagCompound);
        tagCompound.setInteger("BlockID", blockID);

        tagCompound.setInteger("length", dataEffects.size());
        for(int i = 0; i < dataEffects.size(); i++){
            NBTTagCompound effectTag = new NBTTagCompound();
            dataEffects.get(i).writeNBT(effectTag);
            tagCompound.setTag("effect: " + i, effectTag);
        }

        tagCompound.setInteger("ContainingID", containingID);
    }

    public void readNBT(NBTTagCompound tagCompound) {
        super.readNBT(tagCompound);
        blockID = tagCompound.getInteger("BlockID");

        for(int i = 0; i < tagCompound.getInteger("length"); i++){
            NBTTagCompound effectTag = tagCompound.getCompoundTag("effect: " + i);
            AspectEffect effect = new AspectEffect();
            effect.readNBT(effectTag);
            dataEffects.add(effect);
        }
        containingID = tagCompound.getInteger("ContainingID");
    }
}
