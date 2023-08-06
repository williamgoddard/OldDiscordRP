package uniqueimpact.discordRP.discord.listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import uniqueimpact.discordRP.discord.commands.CommandHandler;
import uniqueimpact.discordRP.things.Roleplay;

public class InteractionListener implements EventListener{

    private final Roleplay roleplay = Roleplay.getInstance();

    private final CommandHandler commandHandler = CommandHandler.getInstance();

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof SlashCommandInteractionEvent) {
            commandHandler.handle((SlashCommandInteractionEvent) event);
        }

        roleplay.save();

    }

}
