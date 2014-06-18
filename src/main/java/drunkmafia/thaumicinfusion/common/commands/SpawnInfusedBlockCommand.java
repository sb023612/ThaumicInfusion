package drunkmafia.thaumicinfusion.common.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class SpawnInfusedBlockCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "Displays a test message";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        // Still empty for now
    }
}
