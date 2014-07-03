package drunkmafia.thaumicinfusion.common.aspect;

import drunkmafia.thaumicinfusion.common.CommonProxy;
import drunkmafia.thaumicinfusion.common.block.TIBlocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.Aspect;

public class AspectEffect {
    public Block getBlock() {
        return TIBlocks.infusedBlock;
    }

    public void writeNBT(NBTTagCompound tagCompound){

    }

    public void readNBT(NBTTagCompound tagCompound){

    }
}
