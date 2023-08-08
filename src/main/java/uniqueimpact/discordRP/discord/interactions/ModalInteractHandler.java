package uniqueimpact.discordRP.discord.interactions;

import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

import java.util.HashMap;
import java.util.Map;

public class ModalInteractHandler {

    private static final ModalInteractHandler INSTANCE = new ModalInteractHandler();

    private final Map<String, ModalInteract> interactions;

    private ModalInteractHandler() {

        this.interactions = new HashMap<String, ModalInteract>();

        interactions.put("SCD", new SetCharacterDescriptionModalInteract());

    }

    public static ModalInteractHandler getInstance() {
        return ModalInteractHandler.INSTANCE;
    }

    public final void handle(ModalInteraction modalInteraction) {

        String[] interactionParts = modalInteraction.getModalId().split(" ");
        String interactionKey = interactionParts[0];
        if (interactions.containsKey(interactionKey)) {
            interactions.get(interactionKey).run(interactionParts, modalInteraction);
        } else {
            modalInteraction.reply("This interaction is not implemented yet.").queue();
        }

    }

}
