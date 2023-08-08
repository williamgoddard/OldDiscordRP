package uniqueimpact.discordRP.discord.listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import org.jetbrains.annotations.NotNull;
import uniqueimpact.discordRP.discord.commands.CommandHandler;
import uniqueimpact.discordRP.discord.interactions.ModalInteractHandler;
import uniqueimpact.discordRP.things.Roleplay;

public class InteractionListener implements EventListener{

    private final Roleplay roleplay = Roleplay.getInstance();

    private final CommandHandler commandHandler = CommandHandler.getInstance();
    private final ModalInteractHandler interactHandler = ModalInteractHandler.getInstance();

    @Override
    public void onEvent(@NotNull GenericEvent event) {

        if (event instanceof SlashCommandInteractionEvent) {
            commandHandler.handle((SlashCommandInteractionEvent) event);
        } else if (event instanceof ModalInteraction) {
            interactHandler.handle((ModalInteraction) event);
        }

        roleplay.save();

    }

}
