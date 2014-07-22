package drunkmafia.thaumicinfusion.common.commands;

import drunkmafia.thaumicinfusion.common.util.BlockHelper;
import drunkmafia.thaumicinfusion.common.util.InfusionHelper;
import drunkmafia.thaumicinfusion.common.util.ReflectionHelper;
import drunkmafia.thaumicinfusion.common.world.TIWorldData;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import thaumcraft.api.aspects.Aspect;

import java.util.ArrayList;

/**
 * Created by DrunkMafia on 21/06/2014.
 * <p/>
 * See http://www.wtfpl.net/txt/copying for licence
 */
public class InfusedInWorldCommand extends CommandBase {
    @Override
    public String getCommandName(){
        return "getblocks";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender){
        return "/getblocks Will display info about all infused blocks";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] command){
        if(!sender.getEntityWorld().isRemote) {
            EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName());
            if (player != null && !player.worldObj.isRemote) {
                TIWorldData data = BlockHelper.getWorldData(player.worldObj);
                if (data != null) {
                    System.out.println("Blocks in world: " + data.getNoOfBlocks());
                    player.addChatComponentMessage(new ChatComponentText("Blocks in world: " + data.getNoOfBlocks()));
                }
            }
        }
    }
}
