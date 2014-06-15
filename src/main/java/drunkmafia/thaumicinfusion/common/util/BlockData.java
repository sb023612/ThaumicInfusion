package drunkmafia.thaumicinfusion.common.util;

import drunkmafia.thaumicinfusion.common.aspect.AspectEffect;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import java.lang.reflect.Method;
import java.util.*;

public class BlockData {

    private ChunkCoordinates blockCoords;
    private int infusedID, blockID;

    private LinkedHashMap<String, MethodObj> dataMethods = new LinkedHashMap<String, MethodObj>();
    private LinkedHashMap<String, String> dataObjs = new LinkedHashMap<String, String>();

    public BlockData(){}

    public BlockData(ChunkCoordinates coordinates, Class[] list, int infusedID, int blockID){
        blockCoords = coordinates;
        this.infusedID = infusedID;
        this.blockID = blockID;
        setupData(list);
    }

    public void setupData(Class[] list){
        for(Class effect : list) {
            addMethods(effect.getClass());
        }
        addMethods(Block.getBlockById(infusedID).getClass());
    }

    public void addMethods(Class c){
        try {
            Object obj = c.newInstance();
            for (Method meth : c.getDeclaredMethods()) {
                if (!dataObjs.containsKey(meth.getName())) dataObjs.put(meth.getName(), c.getName());
                if (!dataMethods.containsKey(c.getName())) dataMethods.put(c.getName(), new MethodObj(obj, meth));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object runMethod(String methName, Object... pars){
        try{
            MethodObj obJ = dataMethods.get(dataObjs.get(methName));
            return obJ.getMeth().invoke(obJ.getObj(), pars);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Block getBlock(){
        return Block.getBlockById(infusedID);
    }

    public ArrayList<MethodObj> getAllObjs(){
        ArrayList<MethodObj> objs = new ArrayList<MethodObj>();
        Iterator iterator = dataObjs.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            if(!objs.contains(entry.getValue())) objs.add((MethodObj) entry.getValue());
        }
        return objs;
    }

    public ChunkCoordinates getBlockCoords(){
        return blockCoords;
    }

    public void writeTag(NBTTagCompound tagCompound){
        ArrayList<MethodObj> objs = getAllObjs();
        tagCompound.setInteger("obj_size", objs.size());
        for(int i = 0; i < objs.size(); i++) {
            MethodObj obj = objs.get(i);
            if(obj.getObj() instanceof AspectEffect){
                AspectEffect effect = (AspectEffect) obj.getObj();
                NBTTagCompound effectTag = new NBTTagCompound();
                effect.readTag(effectTag);
                tagCompound.setTag("obj_" + i, effectTag);
            }
        }
        tagCompound.setInteger("blockID", blockID);
        tagCompound.setInteger("infusedID", infusedID);
        tagCompound.setIntArray("coords", new int[] {blockCoords.posX, blockCoords.posY, blockCoords.posZ});
    }

    public void readTag(NBTTagCompound tagCompound){
        AspectEffect[] effects = new AspectEffect[tagCompound.getInteger("obj_size")];
        for(int i = 0; i < effects.length; i++){

        }
    }
}

class MethodObj{
    private Object obj;
    private Method linkedMeth;

    public MethodObj(Object obj, Method linkedMeth){
        this.obj = obj;
        this.linkedMeth = linkedMeth;
    }

    public Object getObj(){
        return obj;
    }

    public Method getMeth(){
        return linkedMeth;
    }
}
