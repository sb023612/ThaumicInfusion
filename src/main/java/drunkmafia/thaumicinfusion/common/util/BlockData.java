package drunkmafia.thaumicinfusion.common.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drunkmafia.thaumicinfusion.common.aspect.AspectEffect;
import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlockData extends BlockSavable{

    private int containingID, blockID;

    private ArrayList<AspectEffect> dataEffects = new ArrayList<AspectEffect>();
    private HashMap<MethodStore, MethodOBJ> dataMethods = new HashMap<MethodStore, MethodOBJ>();

    protected BlockData(){}

    public BlockData(ChunkCoordinates coords, Class[] list, int containingID, int blockID) {
        super(coords);
        this.blockID = blockID;
        this.containingID = containingID;
        setupData(classesToEffects(list));
    }

    private AspectEffect[] classesToEffects(Class[] list){
        AspectEffect[] effects1 = new AspectEffect[list.length];
        try{
            for(int i = 0 ; i < list.length; i++) effects1[i] = (AspectEffect) list[i].newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return effects1;
    }

    public void setupData(AspectEffect[] effects){
        try{
            for(AspectEffect effect : effects){
                dataEffects.add(effect);

                Method[] methods = effect.getClass().getDeclaredMethods();
                for(Method meth : methods){
                    BlockSubscribe subscribe = meth.getAnnotation(BlockSubscribe.class);
                    if(subscribe != null && Block.class.getDeclaredMethod(meth.getName(), meth.getParameterTypes()) != null){
                        dataMethods.put(new MethodStore(meth.getName(), meth.getParameterTypes()), new MethodOBJ(meth, dataEffects.indexOf(effect)));
                    }
                }
                Method[] blockMethods = getContainingBlock().getClass().getMethods();
                for(Method meth : blockMethods){
                    if(!dataMethods.containsKey(meth.getName())){
                        dataMethods.put(new MethodStore(meth.getName(), meth.getParameterTypes()), new MethodOBJ(meth, getContainingBlock()));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Class[] objsToClasses(Object... objs){
        Class[] classes = new Class[objs.length];
        for(int i = 0; i < objs.length; i++) classes[i] = objs[i].getClass();
        return classes;
    }

    public Object runMethod(String methName, Object... pars) {
        try{
            MethodOBJ obj = dataMethods.get(new MethodStore(methName, objsToClasses(pars)));
            System.out.println(dataMethods.size());
            if(obj != null) {
                if (obj.isBlock) return obj.meth.invoke(getContainingBlock(), pars);
                else return obj.meth.invoke(dataEffects.get(obj.index), pars);
            }else System.out.println("Obj was null");
        }catch (Exception e){
            e.printStackTrace();
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

        AspectEffect[] effects = new AspectEffect[tagCompound.getInteger("length")];
        for(int i = 0; i < effects.length; i++){
            NBTTagCompound effectTag = tagCompound.getCompoundTag("effect: " + i);
            AspectEffect effect = new AspectEffect();
            effect.readNBT(effectTag);
            effects[i] = effect;
        }

        setupData(effects);

        containingID = tagCompound.getInteger("ContainingID");
    }

    class MethodOBJ {

        public boolean isBlock;
        public Method meth;
        public int index;
        public Block block;

        protected MethodOBJ(Method method, int index){
            System.out.println("Effect OBJ MADE");
            meth = method;
            this.index = index;
            isBlock = false;
        }

        protected MethodOBJ(Method method, Block block){
            System.out.println("Block OBJ MADE");
            meth = method;
            this.block = block;
            isBlock = true;
        }

    }

    class MethodStore {

        public String name;
        public Class[] pars;

        protected MethodStore(String methName, Class[] pars){
            name = methName;
            this.pars = pars;
        }
    }
}
