package me.endergaming.enderlibs.command;

import me.endergaming.enderlibs.text.MessageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    public List<MainCommand> commandList = new ArrayList<>();
//    public List<SubCommand> subCommandList = new ArrayList<>();

    /**
     * Please switch to using <b>register()</b>
     *
     * @param mainCommand
     */
    @Deprecated
    public void registerMainCommand(MainCommand mainCommand) {
        this.commandList.add(mainCommand);
        mainCommand.register();
//        commandList.forEach(BaseCommand::register);
    }

    /**
     * Please switch to using <b>register()</b>
     *
     * @param mainCommand
     * @param subCommand single subcommand
     */
    @Deprecated
    public void registerSubCommand(MainCommand mainCommand, SubCommand subCommand) {
        mainCommand.addSubCommand(subCommand);
//        subCommandList.add(subCommand);
    }

    /**
     * Use this to register just a single main command that has no subcommands.
     *
     * @param mainCommand
     */
    public void register(MainCommand mainCommand) {
        if (mainCommand.registered) {
            MessageUtils.log(MessageUtils.LogLevel.SEVERE, "&cTried to register &d" + mainCommand.command + "&c when it is already registered. Please fix!");
            return;
        }
        this.commandList.add(mainCommand);
        mainCommand.register();
    }

    /**
     * Use this to register a main command with one or more subcommands.
     * 
     * @param mainCommand
     * @param subCommands single or multiple subcommands
     */
    public void register(MainCommand mainCommand, SubCommand... subCommands) {
        if (mainCommand.registered) {
            MessageUtils.log(MessageUtils.LogLevel.SEVERE, "&cTried to register &d" + mainCommand.command + "&c when it is already registered. Please fix!");
            MessageUtils.log(MessageUtils.LogLevel.WARNING, "&eSub-command(s) still added for command &d" + mainCommand.command);
            return;
        } else {
            this.commandList.add(mainCommand);
            mainCommand.register();
        }
        Arrays.stream(subCommands).forEach(mainCommand::addSubCommand);
    }
}
