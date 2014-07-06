package drunkmafia.thaumicinfusion.common.block.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by DrunkMafia on 02/07/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class EssentiaConstructorTile extends TileEntity implements IInventory, ISidedInventory {

    private ItemStack inv;

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int var1) {
        return inv;
    }

    @Override
    public ItemStack decrStackSize(int id, int amount) {
        inv.stackSize -= amount;
        return inv;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return inv;
    }

    @Override
    public void setInventorySlotContents(int var1, ItemStack stack) {
        inv = stack;
    }

    @Override
    public String getInventoryName() {
        return "essentiaConstructor";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        if(inv != null) {
            NBTTagCompound stackTag = new NBTTagCompound();
            inv.writeToNBT(stackTag);
            tagCompound.setTag("inv", stackTag);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        inv = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("inv"));
    }
}
