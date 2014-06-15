package drunkmafia.thaumicinfusion.common.util;

import drunkmafia.thaumicinfusion.common.aspect.AspectEffect;
import drunkmafia.thaumicinfusion.common.aspect.AspectHandler;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import drunkmafia.thaumicinfusion.common.item.TIItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class InfusionHelper {

    public static int getBlockID(ItemStack stack, Class[] aspects){
        AspectEffect[] effects = getEffectsFromClasses(aspects);
        int defBlock = Block.getIdFromBlock(TIBlocks.infusedBlock);
        for(AspectEffect effect : effects){
            int effectBlock = Block.getIdFromBlock(effect.getBlock());
            if(effectBlock != defBlock){
                return effectBlock;
            }
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

    public static AspectList getAspectsFromStack(ItemStack stack){
        AspectList list = new AspectList();
        NBTTagCompound tag = stack.getTagCompound();
        if(tag != null) {
            for (int i = 0; i < tag.getInteger("infusedAspect_Size"); i++){
                list.add(Aspect.getAspect(tag.getString("infusedAspect_" + i)), 8);
            }
        }
        return list;
    }

    public static AspectEffect[] getEffectsFromStack(ItemStack stack){
        Aspect[] aspects = getAspectsFromStack(stack).getAspects();
        if(aspects.length > 0){
            AspectEffect[] effects = new AspectEffect[aspects.length];
            for(int i = 0; i < effects.length; i++){
                effects[i] = AspectHandler.getEffectFromAspect(aspects[i]);
            }
            return effects;
        }
        return null;
    }

    public static Class[] getEffectsClassFromStack(ItemStack stack){
        Aspect[] aspects = getAspectsFromStack(stack).getAspects();
        if(aspects.length > 0){
            Class[] effects = new Class[aspects.length];
            for(int i = 0; i < effects.length; i++){
                effects[i] = AspectHandler.getEffectsClassFromAspect(aspects[i]);
            }
            return effects;
        }
        return null;
    }

    public static AspectEffect[] getEffectsFromClasses(Class[] classes){
        try {
            AspectEffect[] effects = new AspectEffect[classes.length];
            for(int i = 0; i < effects.length; i++){
                effects[i] = (AspectEffect) classes[i].newInstance();
            }
        }catch (Exception e){}
        return null;
    }

    public static AspectEffect[] getEffectsFromAspects(AspectList list){
        AspectEffect[] effects = new AspectEffect[list.size()];
        Aspect[] aspects = list.getAspects();
        for(int i = 0; i < effects.length; i++) effects[i] = AspectHandler.getEffectFromAspect(aspects[i]);
        return effects;
    }

    public static ItemStack getInfusedItemStack(AspectList list, int infusedID, int size, int meta){
        Aspect[] aspects = list.getAspects();
        ItemStack stack = new ItemStack(TIItems.infusedBlock, meta, size);
        NBTTagCompound tag = new NBTTagCompound();
        stack.writeToNBT(tag);
        for(int i = 0; i < aspects.length; i++){
            tag.setString("infusedAspect_" + i, aspects[i].getTag());
        }
        tag.setInteger("infusedAspect_Size", aspects.length);
        stack.setTagCompound(tag);
        return stack;
    }
}
