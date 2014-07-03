package drunkmafia.thaumicinfusion.common.commands;

import drunkmafia.thaumicinfusion.common.util.InfusionHelper;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

import static drunkmafia.thaumicinfusion.common.util.InfusionHelper.getAspectsFromEffects;

public class SpawnInfusedBlockCommand extends CommandBase {

    @Override
    public String getCommandName(){
        return "infuse";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender){
        return "/infuse Will infuse the block in hand";
    }

    public int getSlotIDOfStack(ItemStack stack, EntityPlayer player){
        for(int i = 0; i < player.inventory.mainInventory.length; i++)
            if(player.inventory.mainInventory[i] == stack) return i;
        return -1;
    }

    public static ArrayList<ArrayList<Aspect>> getAspectsFromTags(String[] commands){
        ArrayList<ArrayList<Aspect>> aspects = new ArrayList<ArrayList<Aspect>>();
        for(int i = 0; i < commands.length; i++){
            String str = commands[i];
            if(str != null){
                if(str.matches("\\+") && ((i + 1) < commands.length) && (i > 0) && (Aspect.getAspect(commands[i + 1]) != null)){
                    aspects.get(i - 1).add(Aspect.getAspect(commands[i + 1]));
                    i++;
                }
                if(Aspect.getAspect(str) != null){
                    ArrayList<Aspect> aspects1 = new ArrayList<Aspect>();
                    aspects1.add(Aspect.getAspect(str));
                    aspects.add(aspects1);
                }
            }
        }
        return  aspects;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] command){
        EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName());
        if(player != null && command.length > 0){
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack != null && stack.getItem() instanceof ItemBlock){
                ArrayList<ArrayList<Aspect>> list = getAspectsFromTags(command);
                if(list != null && list.size() > 0) {
                    ItemStack infusedStack = InfusionHelper.getInfusedItemStack(list, Block.getIdFromBlock(Block.getBlockFromItem(stack.getItem())), stack.stackSize, stack.getItemDamage());
                    if (infusedStack != null) {
                        int slotID = getSlotIDOfStack(stack, player);
                        if (slotID != -1)
                            player.inventory.setInventorySlotContents(slotID, infusedStack);
                        else
                            player.entityDropItem(infusedStack, 0);
                    } else
                        sender.addChatMessage(new ChatComponentText("An error occurred while trying to create the infused version of the block"));
                }else
                    sender.addChatMessage(new ChatComponentText("Failed to find the aspects specified, please try again"));
            }else{
                if(stack == null) sender.addChatMessage(new ChatComponentText("You must be holding a block"));
                if(!(stack.getItem() instanceof ItemBlock)) sender.addChatMessage(new ChatComponentText("This is not infuseable, please try something else"));
            }
        }else{
            if(player == null) sender.addChatMessage(new ChatComponentText("Could not find player"));
            if(command.length == 0) sender.addChatMessage(new ChatComponentText("You must specify the aspects you want infused"));
        }
    }
}
