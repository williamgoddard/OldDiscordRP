package uniqueimpact.discordRP.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import uniqueimpact.discordRP.discord.commands.admin.*;
import uniqueimpact.discordRP.discord.commands.chat.WhisperCommand;
import uniqueimpact.discordRP.discord.commands.fun.EightBallCommand;
import uniqueimpact.discordRP.discord.commands.fun.ExcuseCommand;
import uniqueimpact.discordRP.discord.commands.fun.RollCommand;
import uniqueimpact.discordRP.discord.commands.fun.SecretCommand;
import uniqueimpact.discordRP.discord.commands.player_info.HelpCommand;
import uniqueimpact.discordRP.discord.commands.player_info.TimeCommand;
import uniqueimpact.discordRP.discord.commands.player_inv.*;
import uniqueimpact.discordRP.discord.commands.player_look.*;
import uniqueimpact.discordRP.discord.commands.player_move.DoorsCommand;
import uniqueimpact.discordRP.discord.commands.player_move.GotoCommand;
import uniqueimpact.discordRP.discord.commands.player_move.LockCommand;
import uniqueimpact.discordRP.discord.commands.player_move.UnlockCommand;
import uniqueimpact.discordRP.things.Roleplay;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private static final CommandHandler INSTANCE = new CommandHandler();

    Roleplay roleplay = Roleplay.getInstance();

    private final Map<String, Command> commands;

    private CommandHandler() {

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
        commands.put("show", new ShowCommand());
        commands.put("items", new ItemsCommand());
        commands.put("inv", new InvCommand());
        commands.put("clothes", new ClothesCommand());

        // Player Movement Commands
        commands.put("doors", new DoorsCommand());
        commands.put("goto", new GotoCommand());
        commands.put("lock", new LockCommand());
        commands.put("unlock", new UnlockCommand());

        // Player Chat Commands
        commands.put("whisper", new WhisperCommand());

        // Fun Commands
        commands.put("roll", new RollCommand());
        commands.put("8ball", new EightBallCommand());
        commands.put("excuse", new ExcuseCommand());
        commands.put("secret", new SecretCommand());

        // Info Commands
        commands.put("help", new HelpCommand());
        commands.put("time", new TimeCommand());

        commands.put("test", new TestCommand());

    }

    public static CommandHandler getInstance() {
        return CommandHandler.INSTANCE;
    }

    public final void handle(SlashCommandInteractionEvent commandEvent) {

        String commandName = commandEvent.getName();
        if (commands.containsKey(commandName)) {
            MessageCreateData reply = commands.get(commandName).run(commandEvent);
            commandEvent.reply(reply).queue();
        } else {
            commandEvent.reply("That command is not implemented yet :(");
        }

    }

}
