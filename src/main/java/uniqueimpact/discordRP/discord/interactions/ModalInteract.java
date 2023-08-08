package uniqueimpact.discordRP.discord.interactions;

import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import uniqueimpact.discordRP.things.Roleplay;

public interface ModalInteract {

    static Roleplay roleplay = Roleplay.getInstance();

        public void run(String[] interactionParts, ModalInteraction modalInteraction);

}
