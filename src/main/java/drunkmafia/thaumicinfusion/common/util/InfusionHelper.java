package drunkmafia.thaumicinfusion.common.util;

import drunkmafia.thaumicinfusion.common.CommonProxy;
import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.Aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class InfusionHelper {

    public static int getBlockID(Class[] aspects){
        int defBlock = Block.getIdFromBlock(TIBlocks.infusedBlock);
        try {
            for (Class c : aspects) {
                Method meth = c.getMethod("getBlock", new Class[]{});
                if(meth != null){
                    int id = Block.getIdFromBlock((Block) meth.invoke(c.newInstance()));
                    if (id != defBlock) {
                        return id;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return defBlock;
    }

    public static int getInfusedID(ItemStack stack){
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null) {
            return tag.getInteger("infusedID");
        }
        return -1;
    }

    public static Class[] getEffectsFromStack(ItemStack stack){
        NBTTagCompound tag = stack.stackTagCompound;
        Class[] effects = new Class[tag.getInteger("infusedAspect_Size")];
            for (int i = 0; i < effects.length; i++) {
                try {
                    Class c = Class.forName(tag.getString("infusedAspect_" + i));
                    if(c != null) effects[i] = c;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        return effects;
    }

    public static ArrayList<ArrayList<Aspect>> getAspectsFromEffects(Class[] classes){
        ArrayList<ArrayList<Aspect>> aspects = new ArrayList<ArrayList<Aspect>>();
        for(Class c : classes){
            aspects.add(AspectHandler.getAspectsFromEffect(c));
        }
        return aspects;
    }

    private static Class[] getEffectsFromList(ArrayList<ArrayList<Aspect>> list) {
        Class[] effects = new Class[list.size()];
        for(int i = 0; i < effects.length; i++){
            effects[i] = AspectHandler.getEffectFromList(list.get(i));
        }
        return effects;
    }

    public static ItemStack getInfusedItemStack(ArrayList<ArrayList<Aspect>> list, int infusedID, int size, int meta){
        System.out.println("Infusion");
        if(list == null) return null;
        Class[] effects = getEffectsFromList(list);
        ItemStack stack = new ItemStack(TIBlocks.infusedBlock, size);
        NBTTagCompound tag = new NBTTagCompound();
        for(int i = 0; i < effects.length; i++){
            tag.setString("infusedAspect_" + i, effects[i].getName());
        }
        tag.setInteger("infusedAspect_Size", effects.length);
        tag.setInteger("infusedID", infusedID);
        tag.setInteger("infusedMETA", meta);
        stack.setTagCompound(tag);
        return stack;
    }
}
