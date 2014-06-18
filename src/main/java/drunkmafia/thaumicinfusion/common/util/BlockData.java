package drunkmafia.thaumicinfusion.common.util;

import drunkmafia.thaumicinfusion.common.aspect.AspectEffect;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlockData extends BlockSavable{

    private int infusedID;

    private LinkedHashMap<String, Class> methodClasses = new LinkedHashMap<String, Class>();

    public BlockData(Vector3F coords, Class[] list, int infusedID, int blockID) {
        super(blockID, coords);
        this.infusedID = infusedID;
        setupData(list);
    }

    public void setupData(Class[] list) {
        for (Class effect : list) {
            addMethods(effect.getClass());
        }
        addMethods(Block.getBlockById(infusedID).getClass());
    }

    public void addMethods(Class c) {
        try {
            for (Method meth : c.getDeclaredMethods()) {
                if(!methodClasses.containsKey(meth.getName()))methodClasses.put(meth.getName(), c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object runMethod(String methName, Object... pars) {
        try {
            Method meth = methodClasses.get(methName).getDeclaredMethod(methName, (Class[])pars);
            if(meth != null) return meth.invoke(methodClasses.get(methName), pars);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Block getBlock() {
        return Block.getBlockById(infusedID);
    }

    public void writeTag(NBTTagCompound tagCompound) {
        Object[] objects = methodClasses.entrySet().toArray();
        tagCompound.setInteger("length", objects.length);
        for(int i = 0; i < objects.length; i++){
            Map.Entry entry = (Map.Entry) objects[i];
            tagCompound.setString("Method:" + i, (String)entry.getKey());
            tagCompound.setString("Class:" + i, ((Class)entry.getValue()).getName());
        }

        tagCompound.setInteger("InfusedID", infusedID);
    }

    public void readTag(NBTTagCompound tagCompound) {
        try {
            for (int i = 0; i < tagCompound.getInteger("length"); i++) {
                methodClasses.put(tagCompound.getString("Method:" + i), Class.forName(tagCompound.getString("Class:" + i)));
            }
        }catch (Exception e){
            System.out.println("Failed to load class when reading data");
            e.printStackTrace();
        }

        infusedID = tagCompound.getInteger("InfusedID");
    }
}
