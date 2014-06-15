package drunkmafia.thaumicinfusion.common.aspect;

import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.Aspect;

public class AspectEffect {

    private Aspect aspect;

    public AspectEffect(Aspect aspect){
        this.aspect = aspect;
    }

    public Aspect getAspect(){
        return aspect;
    }

    public Block getBlock() { return TIBlocks.infusedBlock; }

    public void writeTag(NBTTagCompound tagCompound){

    }

    public void readTag(NBTTagCompound tagCompound){

    }
}
