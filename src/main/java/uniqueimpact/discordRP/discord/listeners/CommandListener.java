package uniqueimpact.discordRP.discord.listeners;

import uniqueimpact.discordRP.discord.commands.*;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import uniqueimpact.discordRP.discord.commands.admin.*;
import uniqueimpact.discordRP.discord.commands.fun.EightBallCommand;
import uniqueimpact.discordRP.discord.commands.fun.ExcuseCommand;
import uniqueimpact.discordRP.discord.commands.fun.RollCommand;
import uniqueimpact.discordRP.discord.commands.fun.SecretCommand;
import uniqueimpact.discordRP.discord.commands.player.*;

import java.util.HashMap;
import java.util.Map;

public class CommandListener implements EventListener{

    private final Map<String, Command> commands;

    public CommandListener() {

        this.commands = new HashMap<String, Command>();

        commands.put("room", new RoomCommand());
        commands.put("character", new CharacterCommand());
        commands.put("inventory", new InventoryCommand());
        commands.put("item", new ItemCommand());
        commands.put("door", new DoorCommand());

        commands.put("drop", new DropCommand());
        commands.put("take", new TakeCommand());
        commands.put("undress", new UndressCommand());
        commands.put("wear", new WearCommand());
        commands.put("undressdrop", new UndressDropCommand());
        commands.put("takewear", new TakeWearCommand());

        commands.put("roll", new RollCommand());
        commands.put("8ball", new EightBallCommand());
        commands.put("excuse", new ExcuseCommand());
        commands.put("secret", new SecretCommand());

    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof SlashCommandInteractionEvent) {

            SlashCommandInteractionEvent commandEvent = (SlashCommandInteractionEvent) event;

            String commandName = commandEvent.getName();
            if (commands.containsKey(commandName)) {
                commandEvent.reply(commands.get(commandName).run(commandEvent)).queue();
            } else {
                commandEvent.reply("That command is not implemented yet :(").queue();
            }

        }

    }

}
