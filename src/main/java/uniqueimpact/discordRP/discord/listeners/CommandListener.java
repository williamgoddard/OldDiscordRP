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
import uniqueimpact.discordRP.discord.commands.player_inv.*;
import uniqueimpact.discordRP.discord.commands.player_look.ClothesCommand;
import uniqueimpact.discordRP.discord.commands.player_look.InvCommand;
import uniqueimpact.discordRP.discord.commands.player_look.ItemsCommand;
import uniqueimpact.discordRP.discord.commands.player_look.LookCommand;
import uniqueimpact.discordRP.discord.commands.player_move.DoorsCommand;
import uniqueimpact.discordRP.discord.commands.player_move.GotoCommand;
import uniqueimpact.discordRP.discord.commands.player_move.LockCommand;
import uniqueimpact.discordRP.discord.commands.player_move.UnlockCommand;
import uniqueimpact.discordRP.things.Roleplay;

import java.util.HashMap;
import java.util.Map;

public class CommandListener implements EventListener{

    Roleplay roleplay = Roleplay.getInstance();

    private final Map<String, Command> commands;

    public CommandListener() {

        this.commands = new HashMap<String, Command>();

        // Admin Commands
        commands.put("adminhelp", new AdminHelpCommand());
        commands.put("room", new RoomCommand());
        commands.put("character", new CharacterCommand());
        commands.put("inventory", new InventoryCommand());
        commands.put("item", new ItemCommand());
        commands.put("door", new DoorCommand());

        // Player Item Commands
        commands.put("drop", new DropCommand());
        commands.put("take", new TakeCommand());
        commands.put("undress", new UndressCommand());
        commands.put("wear", new WearCommand());
        commands.put("undressdrop", new UndressDropCommand());
        commands.put("takewear", new TakeWearCommand());

        // Player Look Commands
        commands.put("look", new LookCommand());
        commands.put("items", new ItemsCommand());
        commands.put("inv", new InvCommand());
        commands.put("clothes", new ClothesCommand());

        // Player Movement Commands
        commands.put("doors", new DoorsCommand());
        commands.put("goto", new GotoCommand());
        commands.put("lock", new LockCommand());
        commands.put("unlock", new UnlockCommand());

        // Fun Commands
        commands.put("roll", new RollCommand());
        commands.put("8ball", new EightBallCommand());
        commands.put("excuse", new ExcuseCommand());
        commands.put("secret", new SecretCommand());

        commands.put("help", new HelpCommand());

    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof SlashCommandInteractionEvent) {

            SlashCommandInteractionEvent commandEvent = (SlashCommandInteractionEvent) event;

            String commandName = commandEvent.getName();
            if (commands.containsKey(commandName)) {
                String reply = commands.get(commandName).run(commandEvent);
                commandEvent.reply(reply).queue();
                roleplay.save();
            } else {
                commandEvent.reply("That command is not implemented yet :(").queue();
            }

        }

    }

}
