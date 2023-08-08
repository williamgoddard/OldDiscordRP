package uniqueimpact.discordRP.discord.interactions;

import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import uniqueimpact.discordRP.things.Chara;
import uniqueimpact.discordRP.utils.InvalidInputException;

public class SetCharacterDescriptionModalInteract implements ModalInteract {

    @Override
    public void run(String[] interactionParts, ModalInteraction modalInteraction) {

        String channelId = modalInteraction.getChannel().getId();
        Chara character;
        try {
            character = roleplay.findCharacterByChannel(channelId);
        } catch (InvalidInputException e) {
            modalInteraction.reply(e.getMessage()).queue();
            return;
        }

        try {
            character.setDescription(modalInteraction.getValues().get(0).getAsString());
        } catch (InvalidInputException e) {
            modalInteraction.reply(e.getMessage()).queue();
        }

        modalInteraction.reply("The character's description was updated successfully.").queue();

    }

}
